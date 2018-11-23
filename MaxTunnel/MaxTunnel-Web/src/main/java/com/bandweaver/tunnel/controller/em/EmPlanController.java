package com.bandweaver.tunnel.controller.em;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.NativeHistoricProcessInstanceQuery;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.constant.NodeStatusEnum;
import com.bandweaver.tunnel.common.biz.constant.ProcessStatusEnum;
import com.bandweaver.tunnel.common.biz.constant.ProcessTypeEnum;
import com.bandweaver.tunnel.common.biz.constant.em.ActionEnum;
import com.bandweaver.tunnel.common.biz.constant.em.FinishEnum;
import com.bandweaver.tunnel.common.biz.constant.em.TargetEnum;
import com.bandweaver.tunnel.common.biz.constant.mam.ObjectType;
import com.bandweaver.tunnel.common.biz.dto.em.EmPlanDto;
import com.bandweaver.tunnel.common.biz.itf.ActivitiService;
import com.bandweaver.tunnel.common.biz.itf.em.EmPlanService;
import com.bandweaver.tunnel.common.biz.pojo.PageBean;
import com.bandweaver.tunnel.common.biz.pojo.em.EmPlan;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObj;
import com.bandweaver.tunnel.common.biz.vo.em.EmPlanVo;
import com.bandweaver.tunnel.common.biz.vo.em.PlanPageVo;
import com.bandweaver.tunnel.common.platform.constant.StatusCodeEnum;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.CommonUtil;
import com.bandweaver.tunnel.common.platform.util.ContextUtil;
import com.bandweaver.tunnel.common.platform.util.PropertiesUtil;
import com.bandweaver.tunnel.controller.common.BaseController;
import com.bandweaver.tunnel.service.mam.measobj.MeasObjModuleCenter;
import com.github.pagehelper.PageInfo;

/**
 * 应急预案管理
 * 
 * @author shaosen
 * @date 2018年7月30日
 */
@Controller
@ResponseBody
public class EmPlanController extends BaseController<EmPlan>{

	@Autowired
	private ActivitiService activitiService;
	@Autowired
	private EmPlanService emPlanService;
	
	
	/**预案流程部署（只需执行一次即可，如果执行多次，则使用最新版本） 
	 * @param value 枚举ProcessTypeEnum的value值
	 * @return   
	 * @author shaosen
	 * @Date 2018年10月10日
	 */
	@RequestMapping(value="emplans/deploy/{value}",method=RequestMethod.GET)
	public JSONObject deploy(@PathVariable Integer value) {
		
		ProcessTypeEnum processTypeEnum = ProcessTypeEnum.getEnum(value);
		activitiService.deploy((String)PropertiesUtil.getValue(processTypeEnum.getBpmnPath()), 
				(String)PropertiesUtil.getValue(processTypeEnum.getPngPath()),processTypeEnum.getName());
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}
	

	
	/**启动预案 
	 * @param sectionId 
	 * @param processValue
	 * @return    {"processInstanceId":"12501","process":[{"node":"开启声光报警","time":1542164713911,"id":1,"status":1,"desc":"启动或打开了ID为[1]的设备"},{"node":"调用摄像头","time":1542164713911,"id":1,"status":1,"desc":"启动或打开了ID为[1]的设备"},{"node":"值班人员确认","time":1542164713911,"id":1,"status":2,"desc":"启动或打开了ID为[1]的设备"}],"processName":"消防预案","processKey":"FirePlanProcess","range":50.0,"objectId":1}
	 * @author shaosen
	 * @Date 2018年10月16日
	 */
	@RequestMapping(value = "emplans/start/sections/{sectionId}/process-type/{processValue}", method = RequestMethod.GET)
	public JSONObject start(@PathVariable("sectionId")Integer sectionId, @PathVariable("processValue") Integer processValue) {
		Integer objectId = 1;//TODO
		emPlanService.start(sectionId,processValue,objectId);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);

	}



	/**
	 * 手动确认
	 * @param iid 流程实例id processInstanceId
	 * @param result yes or no
	 * @param objectId 监测对象id
	 * @return
	 * @author shaosen
	 * @Date 2018年10月10日
	 */
	@RequestMapping(value = "emplans/confirm/processInstanceId/{processInstanceId}/result/{result}/objectId/{objectId}", method = RequestMethod.GET)
	public JSONObject manualConfirm(@PathVariable("processInstanceId") String processInstanceId, 
			@PathVariable("result") String result,
			@PathVariable("objectId") Integer objectId) {
		Task task = activitiService.getActiveTaskByProcessIntance(processInstanceId);
		if (task == null) {
			throw new RuntimeException("Error: Task is null!");
		}
		LogUtil.info(result.equalsIgnoreCase("yes") ? "同意" : "拒绝");
		Map<String, Object> variables = new HashMap<>();
		variables.put("result", result);
		activitiService.completeTask(task.getId(), variables);
		
		if(result.equalsIgnoreCase("yes")) {
			emPlanService.nextTask(processInstanceId,objectId);
		}
		
		//最后再查一次
		EmPlan emPlan = (EmPlan) ContextUtil.getSession().getAttribute("emPlan");
		emPlanService.sendMsg(emPlan,processInstanceId,objectId);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}

	
	/**添加流程节点 
	 * @param processKey 
	 * @param taskKey usertask1
	 * @param taskName 开启声光报警
	 * @param targetKey TargetEnum枚举
	 * @param targetValue
	 * @param actionKey ActionEnum枚举
	 * @param actionValue
	 * @param finishKey FinishEnum枚举
	 * @param finishValue
	 * @param isFinished 是否结束 0：否 ；1：是
	 * @param 
	 * @return   
	 * @author shaosen
	 * @Date 2018年10月17日
	 */
	@RequestMapping(value = "emplans",method = RequestMethod.POST)
	public JSONObject add(@RequestBody EmPlan record){
		emPlanService.add(record);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}
	
	
	/**删除流程节点 
	 * @param id 主键
	 * @return   
	 * @author shaosen
	 * @Date 2018年10月17日
	 */
	@RequestMapping(value = "emplans/{id}",method = RequestMethod.DELETE)
	public JSONObject delete(@PathVariable Integer id){
		emPlanService.delete(id);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}

	/**批量删除
	 * @param ids 
	 * @return   
	 * @author shaosen
	 * @Date 2018年10月17日
	 */
	@RequestMapping(value = "emplans/batch/{ids}",method = RequestMethod.DELETE)
	public JSONObject deleteBatch(@PathVariable String ids){
		List<Integer> list = convertStringToList(ids);
		emPlanService.deleteBatch(list);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}

	
	/**修改流程节点 
	 * @param record 参考添加接口
	 * @return   
	 * @author shaosen
	 * @Date 2018年10月17日
	 */
	@RequestMapping(value = "emplans",method = RequestMethod.PUT)
	public JSONObject update(EmPlan record){
		emPlanService.update(record);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}

	
	/**通过主键获取流程节点
	 * @param id 主键
	 * @return   {"msg":"请求成功","code":"200","data":{"id":1,"processKey":"FirePlanProcess","taskKey":"usertask1","taskName":"开启声光报警","targetKey":2,"targetName":"类型","targetValue":41,"actionKey":3,"actionName":"联动输出类型","actionValue":1,"finishKey":2,"finishName":"半自动","finishValue":1,"isFinished":false,"crtTime":1539014400000}}
	 * @author shaosen
	 * @Date 2018年10月17日
	 */
	@RequestMapping(value = "emplans/{id}",method = RequestMethod.GET)
	public JSONObject getById(@PathVariable Integer id){
		EmPlanDto dto = emPlanService.getById(id);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,dto);
	}
	
	/**通过processKey获取流程节点列表 
	 * @param processKey
	 * @return   {"msg":"请求成功","code":"200","data":{"processName":"消防预案","planStatus":[{"finishName":"半自动","finishValue":1,"targetName":"类型","statusStr":"待完成","statusVal":3,"stepName":"开启声光报警","actionValue":1,"targetValue":41,"actionName":"联动输出类型"},{"finishName":"半自动","finishValue":1,"targetName":"指定对象","statusStr":"待完成","statusVal":3,"stepName":"调用摄像头","actionValue":1,"targetValue":1,"actionName":"提示信息类"},{"finishName":"手动","finishValue":1,"targetName":"指定对象","statusStr":"待完成","statusVal":3,"stepName":"值班人员确认","actionValue":1,"targetValue":1,"actionName":"提示信息类"},{"finishName":"自动","finishValue":1,"targetName":"指定对象","statusStr":"待完成","statusVal":3,"stepName":"打开风机","actionValue":1,"targetValue":1,"actionName":"提示信息类"},{"finishName":"自动","finishValue":1,"targetName":"指定对象","statusStr":"待完成","statusVal":3,"stepName":"打开风阀","actionValue":1,"targetValue":1,"actionName":"提示信息类"},{"finishName":"自动","finishValue":1,"targetName":"指定对象","statusStr":"待完成","statusVal":3,"stepName":"打开百叶","actionValue":1,"targetValue":1,"actionName":"提示信息类"},{"finishName":"自动","finishValue":1,"targetName":"指定对象","statusStr":"待完成","statusVal":3,"stepName":"启动干粉灭火","actionValue":1,"targetValue":1,"actionName":"提示信息类"},{"finishName":"自动","finishValue":1,"targetName":"指定对象","statusStr":"待完成","statusVal":3,"stepName":"通知相关单位","actionValue":1,"targetValue":1,"actionName":"提示信息类"}]}}
	 * @author shaosen
	 * @Date 2018年11月12日
	 */
	@RequestMapping(value="emplans/process-key/{processKey}",method=RequestMethod.GET)
	public JSONObject getNodeListByProcessKey(@PathVariable String processKey) {
		
		ProcessTypeEnum processTypeEnum = ProcessTypeEnum.getEnum(processKey);
		JSONObject result = new JSONObject();
		result.put("processName", processTypeEnum.getName());
		result.put("planStatus", emPlanService.getNodeListByProcessKey(processKey));
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, result);
		
	}
	
	
	/**流程节点分页查询 
	 * @param processKey 支持模糊查询
	 * @param taskKey usertask1
	 * @param taskName 支持模糊查询
	 * @param targetKey TargetEnum枚举
	 * @param actionKey ActionEnum枚举
	 * @param finishKey FinishEnum枚举
	 * @param isFinished 是否结束 0：否 ；1：是
	 * @param startTime
	 * @param endTime
	 * @param pageNum 必须
	 * @param pageSize 必须
	 * @param 
	 * @param 
	 * @return   {"msg":"请求成功","code":"200","data":{"total":8,"list":[{"id":2,"processKey":"FirePlanProcess","taskKey":"usertask2","taskName":"调用摄像头","targetKey":1,"targetName":"指定对象","targetValue":1,"actionKey":1,"actionName":"提示信息类","actionValue":1,"finishKey":2,"finishName":"半自动","finishValue":1,"isFinished":false,"crtTime":1539100800000},{"id":6,"processKey":"FirePlanProcess","taskKey":"usertask6","taskName":"打开百叶","targetKey":1,"targetName":"指定对象","targetValue":1,"actionKey":1,"actionName":"提示信息类","actionValue":1,"finishKey":1,"finishName":"自动","finishValue":1,"isFinished":false,"crtTime":1539100800000},{"id":5,"processKey":"FirePlanProcess","taskKey":"usertask5","taskName":"打开风阀","targetKey":1,"targetName":"指定对象","targetValue":1,"actionKey":1,"actionName":"提示信息类","actionValue":1,"finishKey":1,"finishName":"自动","finishValue":1,"isFinished":false,"crtTime":1539100800000},{"id":4,"processKey":"FirePlanProcess","taskKey":"usertask4","taskName":"打开风机","targetKey":1,"targetName":"指定对象","targetValue":1,"actionKey":1,"actionName":"提示信息类","actionValue":1,"finishKey":1,"finishName":"自动","finishValue":1,"isFinished":false,"crtTime":1539100800000},{"id":3,"processKey":"FirePlanProcess","taskKey":"usertask3","taskName":"值班人员确认","targetKey":1,"targetName":"指定对象","targetValue":1,"actionKey":1,"actionName":"提示信息类","actionValue":1,"finishKey":3,"finishName":"手动","finishValue":1,"isFinished":false,"crtTime":1539100800000}],"pageNum":1,"pageSize":5,"size":5,"startRow":1,"endRow":5,"pages":2,"prePage":0,"nextPage":2,"isFirstPage":true,"isLastPage":false,"hasPreviousPage":false,"hasNextPage":true,"navigatePages":8,"navigatepageNums":[1,2],"navigateFirstPage":1,"navigateLastPage":2,"firstPage":1,"lastPage":2}}
	 * @author shaosen
	 * @Date 2018年10月17日
	 */
	@RequestMapping(value = "emplans/datagrid",method = RequestMethod.POST)
	public JSONObject dataGrid(@RequestBody EmPlanVo vo) {
		PageInfo<EmPlanDto> pageInfo = emPlanService.dataGrid(vo);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, pageInfo);
	}
	

	
	/**
	 * 应急预案流程记录管理分页查询
	 * @param ProcessInstanceId 流程实例id（搜索条件）
	 * @param processKey 流程定义key(必须)
	 * @param startTime 时间戳
	 * @param endTime 时间戳
	 * @param pageNum 必须
	 * @param pageSize 必须
	 * @param 
	 * @return  
	 * {"msg":"请求成功","code":"200","data":{"pageNum":1,"pageSize":3,"totalPage":1,"totalCount":1,"list":[{"id":"5","processInstanceId":"5","processDefinitionId":"FirePlanProcess:1:4","processDefinitionKey":null,"processDefinitionName":null,"processDefinitionVersion":null,"deploymentId":null,"startTime":1533870537321,"endTime":null,"durationInMillis":null,"deleteReason":null,"endActivityId":null,"businessKey":null,"startUserId":null,"startActivityId":"startevent1","superProcessInstanceId":null,"tenantId":"","name":null,"localizedName":null,"description":null,"localizedDescription":null,"queryVariables":null,"processVariables":{},"persistentState":{"processDefinitionId":"FirePlanProcess:1:4","durationInMillis":null,"endStateName":null,"deploymentId":null,"businessKey":null,"name":null,"processDefinitionName":null,"endTime":null,"deleteReason":null,"processDefinitionVersion":null,"superProcessInstanceId":null,"processDefinitionKey":null}}]}}  
	 * @author shaosen
	 * @Date 2018年8月10日
	 */
	@RequestMapping(value="plans/his-proc-inst/datagrid",method=RequestMethod.POST)
	public JSONObject getHisProInstDataGrid(@RequestBody PlanPageVo vo) {
		
		//考虑到如果发布了新流程，processKey的后缀会发生改变，因此根据processDefinitionId模糊查询历史实例表（包含正在执行的实例）
		NativeHistoricProcessInstanceQuery query = activitiService.createHisProcInstDefIdNameLikeQuery(vo);
		
		//开始分页
		PageBean<HistoricProcessInstance> page = new PageBean<>(vo.getPageNum(), vo.getPageSize(), query.list().size());
		page.setList(query.listPage(page.getStart(), vo.getPageSize()));
		
		LogUtil.info(" page : " + page);
		
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, page);
	}
	
	/**
	 *获取流程详情 
	 * @param iid 流程实例id
	 * @return  
	 * {"msg":"请求成功","code":"200","data":[{"id":"6","processInstanceId":"5","processDefinitionId":"FirePlanProcess:1:4","processDefinitionKey":null,"processDefinitionName":null,"processDefinitionVersion":null,"deploymentId":null,"startTime":1533870537321,"endTime":1533870537398,"durationInMillis":77,"deleteReason":null,"activityId":"startevent1","activityName":"Start","activityType":"startEvent","executionId":"5","assignee":null,"taskId":null,"calledProcessInstanceId":null,"tenantId":"","time":1533870537321,"persistentState":{"executionId":"5","durationInMillis":77,"endTime":1533870537398,"assignee":null,"deleteReason":null}},{"id":"7","processInstanceId":"5","processDefinitionId":"FirePlanProcess:1:4","processDefinitionKey":null,"processDefinitionName":null,"processDefinitionVersion":null,"deploymentId":null,"startTime":1533870537398,"endTime":1533870543081,"durationInMillis":5683,"deleteReason":null,"activityId":"usertask1","activityName":"开启声光报警","activityType":"userTask","executionId":"5","assignee":null,"taskId":"8","calledProcessInstanceId":null,"tenantId":"","time":1533870537398,"persistentState":{"executionId":"5","durationInMillis":5683,"endTime":1533870543081,"assignee":null,"deleteReason":null}},{"id":"10","processInstanceId":"5","processDefinitionId":"FirePlanProcess:1:4","processDefinitionKey":null,"processDefinitionName":null,"processDefinitionVersion":null,"deploymentId":null,"startTime":1533870543081,"endTime":1533870548274,"durationInMillis":5193,"deleteReason":null,"activityId":"usertask2","activityName":"调用摄像头","activityType":"userTask","executionId":"5","assignee":null,"taskId":"11","calledProcessInstanceId":null,"tenantId":"","time":1533870543081,"persistentState":{"executionId":"5","durationInMillis":5193,"endTime":1533870548274,"assignee":null,"deleteReason":null}},{"id":"12","processInstanceId":"5","processDefinitionId":"FirePlanProcess:1:4","processDefinitionKey":null,"processDefinitionName":null,"processDefinitionVersion":null,"deploymentId":null,"startTime":1533870548274,"endTime":null,"durationInMillis":null,"deleteReason":null,"activityId":"usertask3","activityName":"值班人员确认","activityType":"userTask","executionId":"5","assignee":null,"taskId":"13","calledProcessInstanceId":null,"tenantId":"","time":1533870548274,"persistentState":{"executionId":"5","durationInMillis":null,"endTime":null,"assignee":null,"deleteReason":null}}]}  
	 * @author shaosen
	 * @Date 2018年8月10日
	 */
	@RequestMapping(value="plans/his-proc-inst/task/{iid}",method=RequestMethod.GET)
	public JSONObject getHisProcInstDet(@PathVariable String iid) {
		//通过流程实例id查询历史活动表，获取每个节点详情
		List<HistoricActivityInstance> list = activitiService.getHisActInstListByProcessInstanceId(iid);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,list);
	}
	
	

}
