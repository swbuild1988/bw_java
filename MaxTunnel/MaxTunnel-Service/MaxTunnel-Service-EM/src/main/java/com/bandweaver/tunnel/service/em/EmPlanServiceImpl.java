package com.bandweaver.tunnel.service.em;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.constant.NodeStatusEnum;
import com.bandweaver.tunnel.common.biz.constant.ProcessTypeEnum;
import com.bandweaver.tunnel.common.biz.constant.SwitchEnum;
import com.bandweaver.tunnel.common.biz.constant.em.ActionEnum;
import com.bandweaver.tunnel.common.biz.constant.em.FinishEnum;
import com.bandweaver.tunnel.common.biz.constant.em.TargetEnum;
import com.bandweaver.tunnel.common.biz.constant.em.UnitTypeEnum;
import com.bandweaver.tunnel.common.biz.dto.em.EmPlanDto;
import com.bandweaver.tunnel.common.biz.itf.ActivitiService;
import com.bandweaver.tunnel.common.biz.itf.MqService;
import com.bandweaver.tunnel.common.biz.itf.em.EmPlanService;
import com.bandweaver.tunnel.common.biz.itf.mam.mapping.MeasObjMapService;
import com.bandweaver.tunnel.common.biz.itf.mam.maxview.SubSystemService;
import com.bandweaver.tunnel.common.biz.itf.mam.measobj.MeasObjService;
import com.bandweaver.tunnel.common.biz.pojo.em.EmPlan;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObj;
import com.bandweaver.tunnel.common.biz.vo.em.EmPlanVo;
import com.bandweaver.tunnel.common.platform.exception.BandWeaverException;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.ContextUtil;
import com.bandweaver.tunnel.common.platform.util.DataTypeUtil;
import com.bandweaver.tunnel.common.platform.util.DateUtil;
import com.bandweaver.tunnel.common.platform.util.PropertiesUtil;
import com.bandweaver.tunnel.common.platform.util.StringTools;
import com.bandweaver.tunnel.dao.em.EmPlanMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class EmPlanServiceImpl implements EmPlanService {

	@Autowired
	private EmPlanMapper emPlanMapper;
	@Autowired
	private ActivitiService activitiService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private MeasObjService measObjService;
	@Autowired
	private MeasObjMapService measObjMapService;
	@Autowired
	private MqService mqService;
	@Autowired
	private SubSystemService subSystemService;

	@Override
	public void doBusiness(ActivitiEvent activitiEvent, TaskEntity taskEntity) {
		String processDefinitionId = activitiEvent.getProcessDefinitionId();
		ProcessDefinition processDefinition = activitiService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
		if (processDefinition == null) 
			throw new BandWeaverException("流程尚未部署");
		LogUtil.debug("Get KEY:" + processDefinition.getKey());
		
		Map<String, Object> variables = runtimeService.getVariables(activitiEvent.getExecutionId());
		LogUtil.debug("Get variables:" + variables);
		Integer sectionId = DataTypeUtil.toInteger(variables.get("sectionId"));

		EmPlan emPlan = getEmPlanByProcessKeyAndTaskKey(processDefinition.getKey(), taskEntity.getTaskDefinitionKey());
		LogUtil.debug("Get emPlan from DB:" + emPlan);
		if(StringTools.isNullOrEmpty(emPlan)) 
			throw new BandWeaverException("流程节点不存在");
		

		// 第一步：获取目标
		Collection<MeasObj> list = new ArrayList<>();
		TargetEnum targetEnum = TargetEnum.getEnum(emPlan.getTargetKey());
		if(TargetEnum.ASSIGN_TO == targetEnum) {
			list = measObjService.getMeasObjByTargetVal(emPlan.getTargetValue());
		}else if (TargetEnum.TYPE == targetEnum) {
			list = measObjService.getMeasObjsByTargetValAndSection(emPlan.getTargetValue(),sectionId);
		}
		
		
		// 第二步：做什么事情
		ActionEnum actionEnum = ActionEnum.getEnum(emPlan.getActionKey());
		if(ActionEnum.METION == actionEnum) {
			LogUtil.info("提示信息为：" +  emPlan.getActionValue());
			//TODO
			
		}else if (ActionEnum.NOTICE == actionEnum) {
			Integer unitTypeId = DataTypeUtil.toInteger(emPlan.getActionValue());
			LogUtil.info("通知单位类型为：" + UnitTypeEnum.getEnum(unitTypeId).getName() );
			//TODO
			
		}else if (ActionEnum.SWITCH == actionEnum) {
			Integer inputValue = DataTypeUtil.toInteger(emPlan.getActionValue());
			for (MeasObj measObj : list) {
				boolean flag = subSystemService.doAction(measObj.getId(), inputValue);
				LogUtil.info("监测对象[" + measObj.getName() + "]执行[ " + SwitchEnum.getEnum(inputValue).getName() + " ]结果["+ flag +"]");
			}
		}
		
		ContextUtil.getSession().setAttribute("emPlan", emPlan);
	}


	
	@Override
	public void nextTask(String processInstanceId,Integer objectId) {
		
		boolean isFinished = false;
		while(!isFinished) {
			EmPlan emPlan = (EmPlan) ContextUtil.getSession().getAttribute("emPlan");
			LogUtil.debug("Get emPlan from session:" + emPlan);
			
			if(emPlan == null) {
				isFinished = true;
			}else if(emPlan != null && emPlan.getFinishKey() == FinishEnum.MANUAL.getValue()){
				isFinished = true;
			}else {
				isFinished = emPlan.getIsFinished();
			}
			
			// 第三步：怎样结束
			FinishEnum finishEnum = FinishEnum.getEnum(emPlan.getFinishKey());
			if(FinishEnum.AUTO == finishEnum) {
				LogUtil.info("自动结束");
				sendMsg(emPlan,processInstanceId,objectId);
				activitiService.completeTaskByProcessIntance(processInstanceId);
				continue;
			}else if (FinishEnum.MANUAL == finishEnum) {
				LogUtil.info("手动结束,需要确认");
				sendMsg(emPlan, processInstanceId,objectId);
				continue;
			}
			
		}
	}
	
	
	
	/*
	 * 发送消息到MQ队列
	 */
	public void sendMsg(EmPlan emPlan, String processInstanceId,Integer objectId) {
		// 通过流程实例id获取历史任务节点
		List<HistoricTaskInstance> list = activitiService.getHistoricTaskInstanceListByInstanceId(processInstanceId);
		List<JSONObject> result = new ArrayList<>();
		for (HistoricTaskInstance historicTaskInstance : list) {
			JSONObject json = new JSONObject();
			json.put("node", historicTaskInstance.getName());
			json.put("status", historicTaskInstance.getEndTime() == null ? NodeStatusEnum.PROCESSING.getValue() : NodeStatusEnum.FINISHED.getValue());
			json.put("time", DateUtil.getCurrentDate());
			json.put("desc","启动或打开了ID为[" + emPlan.getTargetValue() + "]的设备");
			json.put("id", emPlan.getTargetValue());
			result.add(json);
		}
		
		ProcessTypeEnum processTypeEnum = ProcessTypeEnum.getEnum(emPlan.getProcessKey());
		
		JSONObject json = new JSONObject();
		json.put("processName", processTypeEnum.getName());
		json.put("processKey", emPlan.getProcessKey());
		json.put("processInstanceId", processInstanceId);
		json.put("range", processTypeEnum.getRange());
		json.put("process", result);
		json.put("objectId", objectId);
		json.put("nodeList", getNodeListByProcessKey(emPlan.getProcessKey()));
		
		mqService.sendToPlanUMQueue(json.toJSONString());
		mqService.sendToPlanVMQueue(json.toJSONString());
	}


	public EmPlan getEmPlanByProcessKeyAndTaskKey(String processKey, String taskKey) {
		return emPlanMapper.getEmPlanByProcessKeyAndTaskKey(processKey, taskKey);
	}



	@Override
	public void start(Integer sectionId, Integer processValue,Integer objectId) {
		ProcessTypeEnum processTypeEnum = ProcessTypeEnum.getEnum(processValue);
		LogUtil.info("开始启动【" + processTypeEnum.getName() + "】");
		
		Map<String, Object> variables = new HashMap<>();
		variables.put("sectionId", sectionId);
		variables.put("objectId", objectId);

		ProcessDefinition processDefinition = activitiService.getLastestProcessDefinition((String)PropertiesUtil.getValue(processTypeEnum.getBpmnPath()));
		ProcessInstance processInstance = activitiService.startProcessInstanceById(processDefinition.getId(),variables);
		LogUtil.debug("Get processInstance:" + processInstance);
		
		nextTask(processInstance.getId(),objectId);
		
	}



	@Override
	public void add(EmPlan record) {
		record.setCrtTime(DateUtil.getCurrentDate());
		emPlanMapper.insertSelective(record);
	}



	@Override
	public void delete(Integer id) {
		emPlanMapper.deleteByPrimaryKey(id);
	}



	@Override
	public void update(EmPlan record) {
		emPlanMapper.updateByPrimaryKeySelective(record);
	}



	@Override
	public EmPlanDto getById(Integer id) {
		return emPlanMapper.getById(id);
	}



	@Override
	public void deleteBatch(List<Integer> list) {
		emPlanMapper.deleteBatch(list);
	}



	@Override
	public PageInfo<EmPlanDto> dataGrid(EmPlanVo vo) {
		PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
		List<EmPlanDto> list = getByCondition(vo);
		return new PageInfo<>(list);
	}



	private List<EmPlanDto> getByCondition(EmPlanVo vo) {
		List<EmPlanDto> list = emPlanMapper.getByCondition(vo);
		if(list == null || list.isEmpty()) {
			return Collections.emptyList();
		}
		return list;
	}



	@Override
	public List<JSONObject> getNodeListByProcessKey(String processKey) {
		List<EmPlanDto> list = emPlanMapper.getNodeListByProcessKey(processKey);
		if(list == null || list.isEmpty()) {
			return Collections.emptyList();
		}
		
		List<JSONObject> nodeList = new ArrayList<>();
		for (EmPlanDto emPlanDto : list) {
			JSONObject j = new JSONObject();
			j.put("stepName", emPlanDto.getTaskName());
			j.put("targetName", emPlanDto.getTargetName());
			j.put("targetValue", emPlanDto.getTargetValue());
			j.put("actionName", emPlanDto.getActionName());
			j.put("actionValue",emPlanDto.getActionValue() );
			j.put("finishName", emPlanDto.getFinishName());
			j.put("finishValue", emPlanDto.getFinishValue());
			
			j.put("statusStr", NodeStatusEnum.WAITING.getName());
			j.put("statusVal", NodeStatusEnum.WAITING.getValue());
			nodeList.add(j);
		}
		
		return nodeList;
	}

	

}
