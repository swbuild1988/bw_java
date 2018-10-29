package com.bandweaver.tunnel.service.em;

import java.util.ArrayList;
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
import com.bandweaver.tunnel.common.biz.constant.ProcessTypeEnum;
import com.bandweaver.tunnel.common.biz.constant.em.ActionEnum;
import com.bandweaver.tunnel.common.biz.constant.em.FinishEnum;
import com.bandweaver.tunnel.common.biz.constant.em.TargetEnum;
import com.bandweaver.tunnel.common.biz.dto.em.EmPlanDto;
import com.bandweaver.tunnel.common.biz.itf.ActivitiService;
import com.bandweaver.tunnel.common.biz.itf.em.EmPlanService;
import com.bandweaver.tunnel.common.biz.itf.mam.mapping.MeasObjMapService;
import com.bandweaver.tunnel.common.biz.itf.mam.measobj.MeasObjService;
import com.bandweaver.tunnel.common.biz.pojo.em.EmPlan;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObj;
import com.bandweaver.tunnel.common.biz.vo.em.EmPlanVo;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.ContextUtil;
import com.bandweaver.tunnel.common.platform.util.DataTypeUtil;
import com.bandweaver.tunnel.common.platform.util.DateUtil;
import com.bandweaver.tunnel.common.platform.util.PropertiesUtil;
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
	private AmqpTemplate amqpTemplate;

	@Override
	public void doBusiness(ActivitiEvent activitiEvent, TaskEntity taskEntity) {
		
		String processDefinitionId = activitiEvent.getProcessDefinitionId();
		ProcessDefinition processDefinition = activitiService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
		if (processDefinition == null) {
			throw new RuntimeException("Process not deployed yet !");
		}
		LogUtil.info("Get KEY:" + processDefinition.getKey());
		Map<String, Object> variables = runtimeService.getVariables(activitiEvent.getExecutionId());
		LogUtil.info("Get variables:" + variables);
		Integer sectionId = DataTypeUtil.toInteger(variables.get("sectionId"));

		EmPlan emPlan = getEmPlanByProcessKeyAndTaskKey(processDefinition.getKey(), taskEntity.getTaskDefinitionKey());
		LogUtil.info("Get emPlan from DB:" + emPlan);
		

		// 第一步：获取目标
		List<MeasObj> list = new ArrayList<>();
		TargetEnum targetEnum = TargetEnum.getEnum(emPlan.getTargetKey());
		switch (targetEnum) {
		case ASSIGN_TO:// 指定对象
			list = measObjService.getMeasObjByTargetVal(emPlan.getTargetValue());
			break;
		case TYPE:// 类型
			list = measObjService.getMeasObjsByTargetValAndVars(emPlan.getTargetValue(),sectionId);
			break;
		default:
			break;
		}
		LogUtil.info("Get measObjList:" + list );
		
		
		boolean isSuccess = true;//执行结果
		try {
			// 第二步：做什么事情
			ActionEnum actionEnum = ActionEnum.getEnum(emPlan.getActionKey());
			Integer actionValue = emPlan.getActionValue();
			switch (actionEnum) {
			case METION:// 提示信息类
				LogUtil.info("提示信息为：" + actionValue);
				//do something...
				break;
				
			case NOTICE:// 通知类
				LogUtil.info("通知值：" + actionValue);
				//do something...
				break;
				
			case SWITCH:// 联动输出类型
				LogUtil.info("联动输出类型");
				for (MeasObj measObj : list) {
					measObjMapService.doAction(measObj.getId(), actionValue);
				}
				break;
				
			default:
				break;
			}
		} catch (Exception e) {
			LogUtil.info(taskEntity.getName() + "任务执行失败" );
			isSuccess = false;
			e.printStackTrace();
		}
		
		ContextUtil.getSession().setAttribute("emPlan", emPlan);
		ContextUtil.getSession().setAttribute("isSuccess", isSuccess);
	}


	
	@Override
	public void nextTask(String processInstanceId) {
		
		boolean isFinished = false;
		while(!isFinished) {
			EmPlan emPlan = (EmPlan) ContextUtil.getSession().getAttribute("emPlan");
			boolean isSuccess = (boolean) ContextUtil.getSession().getAttribute("isSuccess");
			LogUtil.info("Get emPlan from session:" + emPlan);
			LogUtil.info("Get isSuccess:" + isSuccess);
			
			if(emPlan == null) {
				isFinished = true;
			}else if(emPlan != null && emPlan.getFinishKey() == 3){
				isFinished = true;
			}else {
				isFinished = emPlan.getIsFinished();
			}
			
			// 第三步：怎样结束
			FinishEnum finishEnum = FinishEnum.getEnum(emPlan.getFinishKey());
			switch (finishEnum) {
			
			case AUTO:
				LogUtil.info("自动结束");
				sendMsg(emPlan,processInstanceId,isSuccess);
				activitiService.completeTaskByProcessIntance(processInstanceId);
				continue;
				
			case SEMI_AUTO:
				LogUtil.info("半自动结束，需要确认执行结果");
				if(isSuccess) {
					sendMsg(emPlan,processInstanceId,isSuccess);
					activitiService.completeTaskByProcessIntance(processInstanceId);
				}else {
					LogUtil.info("节点任务执行失败，流程暂停");
					isFinished = true;
					sendMsg(emPlan,processInstanceId,isSuccess);
				}
				continue;
				
			case MANUAL:
				LogUtil.info("手动结束,需要确认,processInstanceId :" + processInstanceId);
				sendMsg(emPlan, processInstanceId,isSuccess);
				continue;
				
			default:
				continue;
			}
		}
	}
	
	
	
	/*
	 * 发送消息到MQ队列
	 */
	public void sendMsg(EmPlan emPlan, String processInstanceId, Boolean isFinished) {
		// 通过流程实例id获取历史任务节点
		List<HistoricTaskInstance> list = activitiService.getHistoricTaskInstanceListByInstanceId(processInstanceId);
		List<JSONObject> result = new ArrayList<>();
		for (HistoricTaskInstance historicTaskInstance : list) {
			JSONObject json = new JSONObject();
			json.put("node", historicTaskInstance.getName());
			json.put("status", historicTaskInstance.getEndTime() == null ? "进行中" : "完成");
			result.add(json);
		}
		
		ProcessTypeEnum processTypeEnum = ProcessTypeEnum.getEnum(emPlan.getProcessKey());
		
		JSONObject json = new JSONObject();
		json.put("processName", processTypeEnum.getName());
		json.put("process", result);
		json.put("processInstanceId", processInstanceId);
		
		LogUtil.info("Send to MQ：" + json.toString() );
		amqpTemplate.convertAndSend((String)PropertiesUtil.getValue(processTypeEnum.getQueue()), json.toString());
		
	}


	public EmPlan getEmPlanByProcessKeyAndTaskKey(String key, String taskKey) {
		return emPlanMapper.getEmPlanByProcessKeyAndTaskKey(key, taskKey);
	}



	@Override
	public void start(Integer sectionId, Integer processValue) {
		ProcessTypeEnum processTypeEnum = ProcessTypeEnum.getEnum(processValue);
		LogUtil.info("开始启动【" + processTypeEnum.getName() + "】");
		
		Map<String, Object> variables = new HashMap<>();
		variables.put("sectionId", sectionId);

		LogUtil.info("启动流程");
		ProcessDefinition processDefinition = activitiService.getLastestProcessDefinition((String) PropertiesUtil.getValue(ProcessTypeEnum.FIRE_PLAN.getBpmnPath()));
		ProcessInstance processInstance = activitiService.startProcessInstanceById(processDefinition.getId(),variables);
		LogUtil.debug("Get processInstance:" + processInstance);
		
		nextTask(processInstance.getId());
		
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

	

}
