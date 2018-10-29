package com.bandweaver.tunnel.controller.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.bandweaver.tunnel.common.biz.dto.message.ProcessMessageDto;
import com.bandweaver.tunnel.common.biz.dto.omm.InspectionPlanDto;
import com.bandweaver.tunnel.common.biz.itf.ProcessBaseService;
import com.bandweaver.tunnel.common.biz.pojo.ListPageUtil;
import com.bandweaver.tunnel.common.biz.pojo.ProcessBase;
import com.bandweaver.tunnel.common.biz.pojo.User;
import com.bandweaver.tunnel.common.biz.pojo.omm.InspectionPlan;
import com.bandweaver.tunnel.common.biz.vo.AuditVo;
import com.bandweaver.tunnel.common.platform.util.ContextUtil;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.constant.ProcessStatusEnum;
import com.bandweaver.tunnel.common.biz.constant.ProcessTypeEnum;
import com.bandweaver.tunnel.common.biz.dto.MyTaskDto;
import com.bandweaver.tunnel.common.biz.itf.ActivitiService;
import com.bandweaver.tunnel.common.biz.itf.MyTaskService;
import com.bandweaver.tunnel.common.biz.itf.oam.ReqHistoryService;
import com.bandweaver.tunnel.common.biz.itf.omm.InspectionPlanService;
import com.bandweaver.tunnel.common.biz.vo.MyTaskVo;
import com.bandweaver.tunnel.common.biz.vo.oam.ReqHistoryVo;
import com.bandweaver.tunnel.common.platform.constant.StatusCodeEnum;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.CommonUtil;
import com.github.pagehelper.PageInfo;

/**
 * 工作流管理
 *
 * @author shaosen
 * @date 2018年7月26日
 */
@Controller
@ResponseBody
public class ActivitiController {

    @Autowired
    private ActivitiService activitiService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private InspectionPlanService inspectionPlanService;
    @Autowired
    private ReqHistoryService reqHistoryService;
    @Autowired
    private MyTaskService myTaskService;
    @Autowired
    private ProcessBaseService processBaseService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private TaskService taskService;

    /**
     * 当前登录用户代办任务分页查询
     *
     * @return JSONObject
     * @throws @author shaosen
     * @date 2018年7月26日
     */
    @RequestMapping(value = "users/activiti/task/datagrid", method = RequestMethod.POST)
    public JSONObject taskDataGrid(@RequestBody MyTaskVo vo) {
        // 获取当前访问请求的用户
        User user = ContextUtil.getLoginUser();

        // 根据用户id获得其所有的流程任务
        List<Task> tasks = activitiService.getActiveTasksByUser(user.getId().intValue());
        LogUtil.info("获得任务数：" + tasks.size());

        // 返回的流程消息结果
        List<ProcessMessageDto> result = new ArrayList<>();

        for (Task task : tasks) {
        	LogUtil.info(" taskId : " + task.getId());
            ProcessMessageDto processMessageDto = new ProcessMessageDto();
            // 获取流程类型
            ProcessTypeEnum processTypeEnum = activitiService.getProcessType(task);
            processMessageDto.setProcessType(processTypeEnum.getValue());
            processMessageDto.setProcessTypeName(processTypeEnum.getName());
            // 根据taskId找到对应的流程实例ID，在获取相对应的我们表里面存储的流程
            ProcessBase processBase = activitiService.getProcessBaseByTask(task.getId());
            if(processBase == null) {
            	LogUtil.info("  get processBase is null ! ");
            	continue;
            }
            processMessageDto.setId(processBase.getId());
            processMessageDto.setStatus(processBase.getProcessStatus());
            processMessageDto.setCrtTime(processBase.getCreateTime());
            // 设置流程实例
            processMessageDto.setProcessInstanceId(task.getProcessInstanceId());
            processMessageDto.setTaskKey(task.getTaskDefinitionKey());
            // 设置申请人
            processMessageDto.setStaff("要删掉的部分");
            result.add(processMessageDto);
        }
        if(vo.getProcessType() != null)
        	result = result.stream().filter(a -> a.getProcessType() == vo.getProcessType()).collect(Collectors.toList());
        if(vo.getStartTime() != null)
        	result = result.stream().filter(a -> a.getCrtTime().getTime() - vo.getStartTime().getTime() >= 0).collect(Collectors.toList());
        if(vo.getEndTime() != null)
        	result = result.stream().filter(a -> a.getCrtTime().getTime() - vo.getEndTime().getTime() <= 0).collect(Collectors.toList());
        ListPageUtil<ProcessMessageDto> ls = new ListPageUtil<>(result, vo.getPageNum(), vo.getPageSize());
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, ls);
    }
    
    /**
     * 获取用户的所有任务
     * @param vo
     * @return
     * @author liuya
     * @Date 2018年9月14日
     */
    @RequestMapping(value = "users/activiti/allTask/datagrid", method = RequestMethod.POST)
    public JSONObject historyTask(@RequestBody MyTaskVo vo) {
    	 // 获取当前访问请求的用户
        User user = ContextUtil.getLoginUser();
        
        // 返回的流程消息结果
        List<ProcessMessageDto> result = new ArrayList<>();
        
    	// 根据用户id获得其代办的流程任务
        List<Task> tasks = activitiService.getActiveTasksByUser(user.getId().intValue());
        LogUtil.info("获得代办任务数：" + tasks.size());
        
        //代办任务
        for (Task task : tasks) {
        	LogUtil.info(" taskId : " + task.getId());
            ProcessMessageDto processMessageDto = new ProcessMessageDto();
            // 获取流程类型
            ProcessTypeEnum processTypeEnum = activitiService.getProcessType(task);
            processMessageDto.setProcessType(processTypeEnum.getValue());
            processMessageDto.setProcessTypeName(processTypeEnum.getName());
            // 根据taskId找到对应的流程实例ID，在获取相对应的我们表里面存储的流程
            ProcessBase processBase = activitiService.getProcessBaseByTask(task.getId());
            if(processBase == null) {
            	LogUtil.info("  get processBase is null ! ");
            	continue;
            }
            processMessageDto.setId(processBase.getId());
            processMessageDto.setStatus(processBase.getProcessStatus());
            processMessageDto.setCrtTime(processBase.getCreateTime());
            // 设置流程实例
            processMessageDto.setProcessInstanceId(task.getProcessInstanceId());
            processMessageDto.setTaskKey(task.getTaskDefinitionKey());
            // 设置申请人
            processMessageDto.setStaff("要删掉的部分");
            // 设置完成状态
            processMessageDto.setIsFinished(processBase.getIsFinished());
            result.add(processMessageDto);
        }
        // 根据用户id获得其历史的流程任务
    	List<HistoricTaskInstance> historyTask = activitiService.getHistoryTasksByUser(user.getId().intValue());
    	LogUtil.info("获得任务数：" + historyTask.size());
        //历史任务
    	for(HistoricTaskInstance task : historyTask) {
    		LogUtil.info(" taskId : " + task.getId());
            ProcessMessageDto processMessageDto = new ProcessMessageDto();
            // 获取流程类型
            ProcessTypeEnum processTypeEnum = activitiService.getHistoryProcessType(task);

            if(processTypeEnum == null) { continue;}
            	
        	processMessageDto.setProcessType(processTypeEnum.getValue());
        	processMessageDto.setProcessTypeName(processTypeEnum.getName());
            
            // 根据taskId找到对应的流程实例ID，在获取相对应的我们表里面存储的流程
            ProcessBase processBase = activitiService.getProcessBaseByHistoryTask(processTypeEnum,task);

            if(processBase == null) {
            	LogUtil.info("  get processBase is null ! ");
            	continue;
            }
            processMessageDto.setId(processBase.getId());
            processMessageDto.setStatus(processBase.getProcessStatus());
            processMessageDto.setCrtTime(processBase.getCreateTime());
            // 设置流程实例
            processMessageDto.setProcessInstanceId(task.getProcessInstanceId());
            processMessageDto.setTaskKey(task.getTaskDefinitionKey());
            // 设置申请人
            processMessageDto.setStaff("要删掉的部分");
            // 设置完成状态
            processMessageDto.setIsFinished(processBase.getIsFinished());
            
            boolean flag = false;
            for(ProcessMessageDto dto : result) {
            	if(dto.getId() == processMessageDto.getId())
            		flag = true;
            }
            if(flag)continue;
            result.add(processMessageDto);
    	}
    	if(vo.getProcessType() != null)
        	result = result.stream().filter(a -> a.getProcessType() == vo.getProcessType()).collect(Collectors.toList());
        if(vo.getStartTime() != null)
        	result = result.stream().filter(a -> a.getCrtTime().getTime() - vo.getStartTime().getTime() >= 0).collect(Collectors.toList());
        if(vo.getEndTime() != null)
        	result = result.stream().filter(a -> a.getCrtTime().getTime() - vo.getEndTime().getTime() <= 0).collect(Collectors.toList());
        ListPageUtil<ProcessMessageDto> ls = new ListPageUtil<>(result, vo.getPageNum(), vo.getPageSize());
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, ls);
    }

    /**
     * 获取任务详情
     *
     * @param @param processDefinitionId 流程定义id
     * @param @param processType 流程类型（枚举）
     * @param
     * @return {"msg":"请求成功","code":"200","data":{"id":2,"approverId":1,"staff":{"id":1,"name":"张三","deptId":1,"positionId":1,"accountId":1,"crtTime":1529942400000},"tunnel":{"id":1,"name":"凤岭北路"},"status":1,"statusName":"审核中","action":1,"actionName":"参观","preTime":null,"enterTime":null,"exitTime":null,"crtTime":1531901522000,"visitorNumber":null,"visitorInfo":null,"processDefinitionId":"reqProcess:2:15","remark":null,"company":{"id":1,"name":"波汇科技","crtTime":1529596800000}}}
     * @throws @author shaosen
     * @date 2018年7月26日
     */
    @RequestMapping(value = "users/activiti/task/detail/{processInstanceId}", method = RequestMethod.GET)
    public JSONObject getTaskDetail(@PathVariable("processInstanceId") String processInstanceId) {

        ProcessTypeEnum type = activitiService.getProcessTypeByProcessInstance(processInstanceId);

        Object o = processBaseService.getProcessByTypeAndProcessInstance(type, processInstanceId);
        InspectionPlanDto dto = (InspectionPlanDto) o;

        //获取批注
        List<Comment> list = new ArrayList<>();
        List<HistoricTaskInstance> hisList = historyService.createHistoricTaskInstanceQuery().processInstanceId(processInstanceId).list();
        if(hisList!=null && hisList.size()>0){
        	for(HistoricTaskInstance hti : hisList){
		    	//任务ID
		    	String htaskId = hti.getId();
		    	LogUtil.info("hisTaskId: " + htaskId);
		    	//获取批注信息
		    	List<Comment> taskList = taskService.getTaskComments(htaskId);//对用历史完成后的任务ID
		    	list.addAll(taskList);
        	}
        }
        if(list != null && list.size() > 0)
        	dto.setComment(list.get(0).getFullMessage());
        
        //获取审批结果
        List<HistoricVariableInstance> ls = historyService.createHistoricVariableInstanceQuery().processInstanceId(processInstanceId).list();
        if(list != null && list.size()>0){
			for(HistoricVariableInstance hiv : ls){
				if("result".equals(hiv.getVariableName())) {
					LogUtil.info("result: " + hiv.getValue());
					dto.setResult((String)hiv.getValue());
				}
			}
		}
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, dto);
    }

    @RequestMapping(value = "users/activiti/process/audit", method = RequestMethod.POST)
    public JSONObject auditRequest(@RequestBody AuditVo vo) {
        LogUtil.info("audit vo： " + vo);
        Map<String, Object> variables = new HashMap<>();
        variables.put("result", vo.getValue() == 1 ? "agree" : "disagree");
        activitiService.completeTask(activitiService.getActiveTaskByProcessIntance(vo.getProcessInstanceId()).getId(), variables, vo.getRemark());

        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }

    /**
     * 当前用户所有申请列表分页查询
     *
     * @return JSONObject
     * @throws @author shaosen
     * @date 2018年7月6日
     */
    @RequestMapping(value = "users/activiti/apply/datagrid", method = RequestMethod.POST)
    public JSONObject getMyRequestList(@RequestBody MyTaskVo vo) {
        PageInfo<MyTaskDto> pageInfo = myTaskService.applyDataGrid(vo);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, pageInfo);
    }

    /**
     * 获取登录用户消息总数
     *
     * @param
     * @return { "msg":"请求成功", "code":"200", "data":2 }
     * @throws @author shaosen
     * @date 2018年7月24日
     */
    @RequestMapping(value = "users/msg/count", method = RequestMethod.GET)
    public JSONObject getMsgCount() {
        // 获取当前访问请求的用户
        User user = ContextUtil.getLoginUser();

        // 根据用户id获得其所有的流程任务
        List<Task> tasks = activitiService.getActiveTasksByUser(user.getId().intValue());

        int count = tasks.size();
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, count);
    }

}
