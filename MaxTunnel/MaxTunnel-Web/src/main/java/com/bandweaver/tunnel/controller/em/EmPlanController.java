package com.bandweaver.tunnel.controller.em;

import java.util.List;
import java.util.Set;

import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.NativeHistoricProcessInstanceQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.constant.ProcessTypeEnum;
import com.bandweaver.tunnel.common.biz.dto.em.EmPlanDto;
import com.bandweaver.tunnel.common.biz.itf.ActivitiService;
import com.bandweaver.tunnel.common.biz.itf.SectionService;
import com.bandweaver.tunnel.common.biz.itf.em.EmPlanService;
import com.bandweaver.tunnel.common.biz.itf.em.ObjectBindService;
import com.bandweaver.tunnel.common.biz.pojo.PageBean;
import com.bandweaver.tunnel.common.biz.pojo.Section;
import com.bandweaver.tunnel.common.biz.pojo.em.EmPlan;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObj;
import com.bandweaver.tunnel.common.biz.vo.em.EmPlanVo;
import com.bandweaver.tunnel.common.biz.vo.em.PlanPageVo;
import com.bandweaver.tunnel.common.platform.constant.StatusCodeEnum;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.CommonUtil;
import com.bandweaver.tunnel.common.platform.util.ContextUtil;
import com.bandweaver.tunnel.common.platform.util.DataTypeUtil;
import com.bandweaver.tunnel.common.platform.util.PropertiesUtil;
import com.bandweaver.tunnel.controller.common.BaseController;
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
	@Autowired
	private SectionService sectionService;
	@Autowired
	private ObjectBindService objectBindService;
	
	
	/**批量部署 
	 * @param value "4001,4002,4003"
	 * @return   
	 * @author shaosen
	 * @Date 2018年11月29日
	 */
	@RequestMapping(value="emplans/deploy/batch/{value}",method=RequestMethod.GET)
	public JSONObject deploy(@PathVariable String value) {
		
		String[] arr = value.split(",");
		for (String str : arr) {
			ProcessTypeEnum processTypeEnum = ProcessTypeEnum.getEnum(DataTypeUtil.toInteger(str));
			activitiService.deploy((String)PropertiesUtil.getValue(processTypeEnum.getBpmnPath()), 
					(String)PropertiesUtil.getValue(processTypeEnum.getPngPath()),processTypeEnum.getName());
		}
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}
	


	/**手动执行预案 
	 * @param storeId 
	 * @param areaId 
	 * @param processValue 应急预案类型
	 * @return   
	 * @author shaosen
	 * @Date 2018年12月20日
	 */
	@RequestMapping(value="emplans/start",method=RequestMethod.POST)
	public JSONObject startPlan(@RequestBody JSONObject reqJson) {
//		CommonUtil.hasAllRequired(reqJson, "storeId,areaId,processValue");
//		Integer storeId = reqJson.getInteger("storeId");
//		Integer areaId = reqJson.getInteger("areaId");
		Integer processValue = reqJson.getInteger("processValue");
		Integer sectionId = reqJson.getInteger("sectionId");
//		Section section = sectionService.getSectionByStoreAndArea(storeId, areaId);
//		if(section == null)
//			throw new BandWeaverException("section不存在");
//		emPlanService.start(section.getId(),processValue);
		
		//查询仓以及仓关联的进气出气仓等
		List<Section> sectionList = sectionService.getSectionListByParentId(sectionId);
		emPlanService.start(sectionList,processValue);
		
		//最后再查一次
		EmPlan emPlan = (EmPlan) ContextUtil.getSession().getAttribute("emPlan");
		String processInstanceId = (String) ContextUtil.getSession().getAttribute("processInstanceId");
		Set<MeasObj> measObjList = (Set<MeasObj>) ContextUtil.getSession().getAttribute("measObjList");
//		emPlanService.sendMsg(emPlan,processInstanceId,section.getId());
		emPlanService.sendMsg(emPlan,processInstanceId,sectionList,measObjList);
		return CommonUtil.success();
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
	public JSONObject update(@RequestBody EmPlan record){
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
	 * @return {"msg":"请求成功","code":"200","data":["打开灯","打开风机","打开百叶"]}
	 * @author shaosen
	 * @Date 2018年11月12日
	 */
	@RequestMapping(value="emplans/process-key/{processKey}",method=RequestMethod.GET)
	public JSONObject getNodeListByProcessKey(@PathVariable String processKey) {
		//方案1
//		List<EmPlanDto> list = emPlanService.getListByProcessKey(processKey);
//		list = list.stream().sorted(Comparator.comparing(EmPlanDto::getTaskKey)).collect(Collectors.toList());
//		List<String> taskNameList = list.stream().map(e -> e.getTaskName()).collect(Collectors.toList());
//		return CommonUtil.success(taskNameList);
		//方案2
		List<JSONObject> list = emPlanService.getNodeListByProcessKey(processKey,null);
		JSONObject returnData = new JSONObject();
		returnData.put("processName", ProcessTypeEnum.getEnum(processKey).getName());
		returnData.put("planStatus", list);
		return CommonUtil.success(returnData);
		
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
	
	
	/**通过监测对象获取关联的所有预案类型 
	 * @param id
	 * @return   {"msg":"请求成功","code":"200","data":[{"name":"消防预案","id":4001},{"name":"通风预案","id":4003}]}
	 * @author shaosen
	 * @Date 2018年12月20日
	 */
	@RequestMapping(value="object-bind/{id}/plans",method=RequestMethod.GET)
	public JSONObject getPlansByObject(@PathVariable Integer id) {
		List<JSONObject> returnData = objectBindService.getPlansByObject(id);
		return CommonUtil.success(returnData);
	}
	

}
