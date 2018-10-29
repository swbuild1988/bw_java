package com.bandweaver.tunnel.service.common;

import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.bandweaver.tunnel.common.biz.constant.ProcessTypeEnum;
import com.bandweaver.tunnel.common.biz.itf.ProcessBaseService;
import com.bandweaver.tunnel.common.biz.pojo.ProcessBase;
import com.bandweaver.tunnel.common.biz.vo.MyTaskVo;
import com.bandweaver.tunnel.common.platform.util.PropertiesUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.history.NativeHistoricProcessInstanceQuery;
import org.activiti.engine.impl.ProcessEngineImpl;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskInfoQuery;
import org.activiti.image.ProcessDiagramGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bandweaver.tunnel.common.biz.dto.StaffDto;
import com.bandweaver.tunnel.common.biz.itf.ActivitiService;
import com.bandweaver.tunnel.common.biz.itf.StaffService;
import com.bandweaver.tunnel.common.biz.pojo.User;
import com.bandweaver.tunnel.common.biz.vo.StaffVo;
import com.bandweaver.tunnel.common.biz.vo.em.PlanPageVo;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.ContextUtil;

@Service
public class ActivitiServiceImpl implements ActivitiService {

    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private ProcessEngine processEngine;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private StaffService staffService;
    @Autowired
    private ProcessBaseService processBaseService;


    @Override
    public String deploy(String bpmnPath, String pngPath, String processName) {
        //流程部署
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource(bpmnPath)
                .addClasspathResource(pngPath)
                .name(processName)
                .deploy();
        LogUtil.info("发布的编号：" + deployment.getId());
        LogUtil.info("发布的名称：" + deployment.getName());
        LogUtil.info("发布的时间：" + deployment.getDeploymentTime());

        // 流程定义
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .deploymentId(deployment.getId()).singleResult();
        LogUtil.info("流程定义编号：" + processDefinition.getId());
        LogUtil.info("流程定义KEY：" + processDefinition.getKey());
        LogUtil.info("流程定义名称：" + processDefinition.getName());

        return processDefinition.getId();
    }

    /**
     * 新的
     *
     * @param processTypeEnum
     * @return
     */
    @Override
    public String startProcessInstance(ProcessTypeEnum processTypeEnum) {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey(processTypeEnum.getProcessKey()).singleResult();

        String processDefinitionId = "";
        if (processDefinition == null) {
            processDefinitionId = deploy((String) PropertiesUtil.getValue(processTypeEnum.getBpmnPath()),
                    (String) PropertiesUtil.getValue(processTypeEnum.getPngPath()),
                    processTypeEnum.getName());
        } else {
            processDefinitionId = processDefinition.getId();
        }
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinitionId);
        return processInstance.getId();
    }

    /**
     * @param processTypeEnum
     * @param var
     * @return
     */
    @Override
    public String startProcessInstance(ProcessTypeEnum processTypeEnum, int... var) {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey(processTypeEnum.getProcessKey()).singleResult();
        
        String processDefinitionId = "";
        if (processDefinition == null) {
        	/*LogUtil.info(" start deploy : =============");
        	LogUtil.info(" get bpmnPath : " + (String) PropertiesUtil.getValue(processTypeEnum.getBpmnPath()));
        	LogUtil.info(" get pngPath : " + (String) PropertiesUtil.getValue(processTypeEnum.getPngPath()));
        	LogUtil.info(" get name : " + processTypeEnum.getName());*/
        	
            processDefinitionId = deploy((String) PropertiesUtil.getValue(processTypeEnum.getBpmnPath()),
                    (String) PropertiesUtil.getValue(processTypeEnum.getPngPath()),
                    processTypeEnum.getName());
        } else {
            processDefinitionId = processDefinition.getId();
        }

        Map<String, Object> vars = new HashMap<>();
        for (int i = 0; i < var.length; i++) {
            vars.put("var" + i, var[i]);
        }

        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinitionId, vars);
        return processInstance.getId();
    }

    @Override
    public void getImageByProcessInstance(String processInstanceId, HttpServletResponse response) throws FileNotFoundException, IOException {
    	//获取历史流程实例
	    HistoricProcessInstance processInstance =  historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
		BpmnModel bpmnModel = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());
		
        Context.setProcessEngineConfiguration(((ProcessEngineImpl) processEngine).getProcessEngineConfiguration());
        ProcessDiagramGenerator processDiagramGenerator = processEngine.getProcessEngineConfiguration().getProcessDiagramGenerator();
        ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(processInstance.getProcessDefinitionId());

        LogUtil.info("获取细节");
        List<HistoricActivityInstance> highLightedActivitList = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).list();
        //高亮环节id集合
        List<String> highLightedActivitis = new ArrayList<String>();
        //高亮线路id集合
        LogUtil.info("寻找高亮路线集合");
        List<String> highLightedFlows = getHighLightedFlows(processDefinitionEntity, highLightedActivitList);

        for (HistoricActivityInstance tempActivity : highLightedActivitList) {
            String activityId = tempActivity.getActivityId();
            highLightedActivitis.add(activityId);
        }

        LogUtil.info(highLightedActivitis);
        LogUtil.info(highLightedFlows);

        //中文显示的是口口口，设置字体就好了
        InputStream is = processDiagramGenerator.generateDiagram(bpmnModel, "png", highLightedActivitis, highLightedFlows, "宋体", "宋体", null, null, 1.0);
        
        //测试：把流程图写到项目固定的路径下
       /* String path = (String) PropertiesUtil.getValue("activiti.png.webapp.path");
        String realPath = ContextUtil.getRequest().getServletContext().getRealPath(path);
        LogUtil.info("realPath:"+realPath);
        //创建目录
        String uuid = UUID.randomUUID().toString();
        String targetPath = realPath + "\\" + uuid + ".png";
		FileUtil.copyStream(is, new FileOutputStream(targetPath));
		LogUtil.info("path:"+path+"\\"+uuid+".png");*/
		
		byte[] buffer=new byte[1024];
		int len=0;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();//转化成字节输出流
		while((len=is.read(buffer))!=-1){
			bos.write(buffer,0,len);
		}
		bos.flush();
		response.setContentType("image/png");//解决图片乱码问题
		OutputStream outputStream = response.getOutputStream();
		outputStream.write(bos.toByteArray());
    }

    /**
     * 新的
     * 进行下一个任务
     *
     * @param processIntanceId
     * @return
     */
    @Override
    public int completeTaskByProcessIntance(String processIntanceId) {
        Task task = taskService.createTaskQuery().processInstanceId(processIntanceId).singleResult();

        if(task == null) 
        	throw new RuntimeException("No task with processInstanceId ["+ processIntanceId +"]!");

        LogUtil.info("TASK[" + task.getId() + "]已完成，进行下一个任务");
        taskService.complete(task.getId());
        return 1;
    }

    /**
     * 进行下一个任务
     *
     * @param processIntanceId
     * @param map
     * @return
     */
    @Override
    public int completeTaskByProcessIntance(String processIntanceId, Map<String, Object> map) {
        Task task = taskService.createTaskQuery().processInstanceId(processIntanceId).singleResult();

        // 这个流程已经没有任务了
        if (task == null) return 0;

        taskService.complete(task.getId(), map);
        LogUtil.info("next task, current task is：" + task.getId());
        LogUtil.info("参数" + map);
        return 1;
    }

    /**
     * 根据流程获取活动的任务
     *
     * @param processIntanceId
     * @return
     */
    @Override
    public List<Task> getActiveTasksByProcessIntance(String processIntanceId) {
        List<Task> tasks = taskService.createTaskQuery().processInstanceId(processIntanceId).list();

        LogUtil.info("当前流程 " + processIntanceId + " 的活动任务：");
        LogUtil.info(tasks);

        return tasks;
    }

    /**
     * 获取用户的代办任务
     *
     * @param userId
     * @return
     */
    @Override
    public List<Task> getActiveTasksByUser(int userId) {
        TaskInfoQuery query = taskService.createTaskQuery().taskAssignee(String.valueOf(userId));
        List<Task> tasks = query.list();
        return tasks;
    }
    
    /**
     * 获取用户的历史任务
     *
     * @param userId
     * @return
     */
    @Override
    public List<HistoricTaskInstance> getHistoryTasksByUser(int userId) {
        HistoricTaskInstanceQuery history = historyService.createHistoricTaskInstanceQuery().taskAssignee(String.valueOf(userId));
        List<HistoricTaskInstance> historyTasks = history.list();
        return historyTasks;
    }
    
    @Override
    public List<HistoricTaskInstance> getAllActiveTasks() {
        HistoricTaskInstanceQuery history = historyService.createHistoricTaskInstanceQuery();
        List<HistoricTaskInstance> historyTasks = history.list();
        return historyTasks;
    }

    /**
     * 获取分页用户任务
     *
     * @param vo
     * @return
     */
    @Override
    public List<Task> getActiveTasksByVO(MyTaskVo vo) {
        TaskInfoQuery query = taskService.createTaskQuery().taskAssignee(String.valueOf(vo.getId().intValue()));

//        PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
        List<Task> tasks = query.listPage((vo.getPageNum() - 1) * vo.getPageSize(), vo.getPageNum() * vo.getPageSize());
//        List<Task> tasks = query.list();
        LogUtil.info("tasks from activiti: " + tasks);
        return tasks;
    }

    /**
     * 根据任务获得流程类型
     *
     * @param task
     * @return
     */
    public ProcessTypeEnum getProcessType(Task task) {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionId(task.getProcessDefinitionId()).singleResult();

        ProcessTypeEnum type = ProcessTypeEnum.getEnum(processDefinition.getKey());
        return type;
    }
    
    /**
     * 根据历史任务实例获得流程类型
     *
     * @param task
     * @return
     */
    public ProcessTypeEnum getHistoryProcessType(HistoricTaskInstance task) {
        ProcessDefinition processDefinition = repositoryService
        		.getProcessDefinition(task.getProcessDefinitionId());

        ProcessTypeEnum type = ProcessTypeEnum.getEnum(processDefinition.getKey());
        return type;
    }

    /**
     * 根据流程实例获得流程类型
     *
     * @param processInstanceId
     * @return
     */
    @Override
    public ProcessTypeEnum getProcessTypeByProcessInstance(String processInstanceId) {
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        HistoricProcessInstance hisProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        String definitionId = 
        		null == processInstance
        		? hisProcessInstance.getProcessDefinitionId()
        				: processInstance.getProcessDefinitionId();
        
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionId(definitionId).singleResult();
        ProcessTypeEnum type = ProcessTypeEnum.getEnum(processDefinition.getKey());
        return type;
    }

    /**
     * 获取流程实例的当前单个任务
     *
     * @param processInstanceId
     * @return
     */
    @Override
    public Task getActiveTaskByProcessIntance(String processInstanceId) {
        Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
        assertNotNull(task);
        LogUtil.info("流程 " + processInstanceId + " 的活动任务：" + task.getId());

        return task;
    }

    /**
     * 根据任务获取流程实例
     *
     * @param taskId
     * @return
     */
    @Override
    public ProcessInstance getProcessInstanceByTask(String taskId) {
        String processIntanceId = taskService.createTaskQuery().taskId(taskId).singleResult().getProcessInstanceId();
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processIntanceId).singleResult();
        return processInstance;
    }

    /**
     * 完结任务
     *
     * @param taskId
     */
    @Override
    public void completeTask(String taskId) {
        taskService.complete(taskId);
        LogUtil.info("任务 " + taskId + " 完结");
    }

    /**
     * 完结任务并配以参数
     *
     * @param taskId
     * @param map
     */
    @Override
    public void completeTask(String taskId, Map<String, Object> map) {
        taskService.complete(taskId, map);
        LogUtil.info("任务：" + taskId);
        LogUtil.info("参数：" + map);
        LogUtil.info("完结");
    }

    /**
     * 完结任务，配参数，并加上批注
     *
     * @param taskId
     * @param map
     * @param comment
     */
    @Override
    public void completeTask(String taskId, Map<String, Object> map, String comment) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();

        if (task == null) {
            LogUtil.info("无任务");
            return;
        }

        String processIntanceId = task.getProcessInstanceId();
        // 添加备注
        if (comment != null) taskService.addComment(taskId, processIntanceId, comment);
        LogUtil.info("task：" + taskId);
        LogUtil.info("comment：" + comment);
        // 完结任务
        taskService.complete(taskId, map);
        LogUtil.info("varible：" + map);
        LogUtil.info("over");
    }

    /**
     * 添加任务备注
     *
     * @param taskId
     * @param processIntanceId
     * @param message
     */
    @Override
    public void addTaskComment(String taskId, String processIntanceId, String message) {
        taskService.addComment(taskId, processIntanceId, message);
    }

    /**
     * 获得任务备注
     *
     * @param taskId
     * @return
     */
    @Override
    public String getTaskComment(String taskId) {
        return null;
    }


    /**
     * 根据任务id，获取最基本的流程信息（我们表中存储的）
     *
     * @param taskId
     * @return
     */
    @Override
    public ProcessBase getProcessBaseByTask(String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();

        return getProcessBaseByProcessInstance(task.getProcessInstanceId());
    }
    
    
    /**
	 * 根据历史任务实例，获取最基本的流程信息（我们表中存储的）
	 * @param taskId
	 * @return
	 */
    @Override
    public ProcessBase getProcessBaseByHistoryTask(ProcessTypeEnum processTypeEnum,HistoricTaskInstance task) {
		return processBaseService.getProcessBaseByTypeAndProcessInstance(processTypeEnum, task.getProcessInstanceId());
	}

    /**
     * 根据流程实例获取基础的流程信息
     *
     * @param processInstanceId
     * @return
     */
    @Override
    public ProcessBase getProcessBaseByProcessInstance(String processInstanceId) {

        ProcessTypeEnum typeEnum = getProcessTypeByProcessInstance(processInstanceId);

        return processBaseService.getProcessBaseByTypeAndProcessInstance(typeEnum, processInstanceId);
    }

    /**
     * @param processInstanceId
     */
    @Override
    public void updateProcessBaseStatusByProcessInstance(String processInstanceId) {
        LogUtil.info("start update status: " + processInstanceId);
        Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();

        if (task == null) return;
        ProcessTypeEnum typeEnum = getProcessTypeByProcessInstance(processInstanceId);

        LogUtil.info("process status: " + task.getName() + task.getId());
        LogUtil.info("process type: " + typeEnum.getName());
        processBaseService.updateProcessStatus(typeEnum, processInstanceId, task.getName());
    }

    /**
     * 更新流程状态
     *
     * @param processDefinitionId
     * @param processInstanceId
     * @param status
     */
    @Override
    public void updateProcessBaseStatus(String processDefinitionId, String processInstanceId, String status) {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();

        ProcessTypeEnum typeEnum = ProcessTypeEnum.getEnum(processDefinition.getKey());

        processBaseService.updateProcessStatus(typeEnum, processInstanceId, status);
    }

    /**
     * @param processInstanceId
     * @param status
     */
    @Override
    public void updateProcessBaseStatusByProcessInstance(String processInstanceId, String status) {

        LogUtil.info("start update status: " + processInstanceId);
        ProcessTypeEnum typeEnum = getProcessTypeByProcessInstance(processInstanceId);

        LogUtil.info("process status: " + status);
        processBaseService.updateProcessStatus(typeEnum, processInstanceId, status);
    }

    /**
     * 根据流程实例结束任务
     *
     * @param processInstanceId
     */
    @Override
    public void finishProcessBaseByProcessInstance(String processInstanceId) {
        ProcessTypeEnum typeEnum = getProcessTypeByProcessInstance(processInstanceId);

        processBaseService.finishProcess(typeEnum, processInstanceId);
    }

    /****************************************************************************************************/

/*    @Override
    public void startProcessInstance(int requestStaffId, int approverId, String processDefinitionId) {

        //通过流程定义id启动流程实例
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinitionId);
        LogUtil.info("流程实例的id：" + processInstance.getId());

        //分配任务
        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
        taskService.claim(task.getId(), String.valueOf(requestStaffId));
        taskService.complete(task.getId());
        LogUtil.info("申请完毕，待审核");
    }*/

    @Override
    public Integer getStaffId() {
        //获取登录用户
        User user = ContextUtil.getLoginUser();
        List<StaffDto> staffList = staffService.getDtoListByCondition(new StaffVo(user.getId(), null, null, null, null, null));
        if (staffList == null || staffList.isEmpty())
            throw new RuntimeException("获取账号为[" + user.getName() + "]的员工信息为空");
        return staffList.get(0).getId();
    }

    @Override
    public List<Task> getTaskList(Integer staffId) {
        List<Task> list = taskService.createTaskQuery().taskAssignee(String.valueOf(staffId)).list();    // 正在执行的任务列表
        return list;
    }



    /**
     * 获取需要高亮的线
     *
     * @param processDefinitionEntity
     * @param historicActivityInstances
     * @return
     */
    private List<String> getHighLightedFlows(
            ProcessDefinitionEntity processDefinitionEntity,
            List<HistoricActivityInstance> historicActivityInstances) {
        List<String> highFlows = new ArrayList<String>();// 用以保存高亮的线flowId
        for (int i = 0; i < historicActivityInstances.size() - 1; i++) {// 对历史流程节点进行遍历
            ActivityImpl activityImpl = processDefinitionEntity
                    .findActivity(historicActivityInstances.get(i)
                            .getActivityId());// 得到节点定义的详细信息
            List<ActivityImpl> sameStartTimeNodes = new ArrayList<ActivityImpl>();// 用以保存后需开始时间相同的节点
            ActivityImpl sameActivityImpl1 = processDefinitionEntity
                    .findActivity(historicActivityInstances.get(i + 1)
                            .getActivityId());
            // 将后面第一个节点放在时间相同节点的集合里
            sameStartTimeNodes.add(sameActivityImpl1);
            for (int j = i + 1; j < historicActivityInstances.size() - 1; j++) {
                HistoricActivityInstance activityImpl1 = historicActivityInstances
                        .get(j);// 后续第一个节点
                HistoricActivityInstance activityImpl2 = historicActivityInstances
                        .get(j + 1);// 后续第二个节点
                if (activityImpl1.getStartTime().equals(
                        activityImpl2.getStartTime())) {
                    // 如果第一个节点和第二个节点开始时间相同保存
                    ActivityImpl sameActivityImpl2 = processDefinitionEntity
                            .findActivity(activityImpl2.getActivityId());
                    sameStartTimeNodes.add(sameActivityImpl2);
                } else {
                    // 有不相同跳出循环
                    break;
                }
            }
            List<PvmTransition> pvmTransitions = activityImpl
                    .getOutgoingTransitions();// 取出节点的所有出去的线
            for (PvmTransition pvmTransition : pvmTransitions) {
                // 对所有的线进行遍历
                ActivityImpl pvmActivityImpl = (ActivityImpl) pvmTransition
                        .getDestination();
                // 如果取出的线的目标节点存在时间相同的节点里，保存该线的id，进行高亮显示
                if (sameStartTimeNodes.contains(pvmActivityImpl)) {
                    highFlows.add(pvmTransition.getId());
                }
            }
        }
        return highFlows;
    }

	@Override
	public ProcessDefinition getLastestProcessDefinition(String bpmnPath) {
	
		//获取最新发布的流程
		List<ProcessDefinition> list = processEngine.getRepositoryService()
				.createProcessDefinitionQuery()
				.processDefinitionResourceName(bpmnPath)
				.orderByProcessDefinitionVersion()
				.desc()
				.list();
		if(list == null || list.size() <= 0) {
			throw new RuntimeException("流程尚未发布！");
		}
		LogUtil.info(" get lastest version : " + list.get(0).getVersion() );
		
		return list.get(0);
	}

	@Override
	public ProcessInstance startProcessInstanceById(String pid) {
		return runtimeService.startProcessInstanceById(pid);
		
	}
	@Override
	public ProcessInstance startProcessInstanceById(String pid,Map<String,Object> variables) {
		return runtimeService.startProcessInstanceById(pid, variables);
	}

	@Override
	public List<HistoricTaskInstance> getHistoricTaskInstanceListByInstanceId(String iid) {
		//查询流程执行过程
		List<HistoricTaskInstance> list = processEngine.getHistoryService()
				.createHistoricTaskInstanceQuery()
				.processInstanceId(iid)
				.orderByTaskCreateTime()
				.asc()
				.list();
		return list;
	}

	@Override
	public ProcessEngine getProcessEngine() {
		return processEngine;
		
	}

	@Override
	public List<HistoricActivityInstance> getHisActInstListByProcessInstanceId(String iid) {
		List<HistoricActivityInstance> list = historyService
				.createHistoricActivityInstanceQuery()
				.processInstanceId(iid)
				.orderByHistoricActivityInstanceStartTime()
				.asc()
				.list();
		LogUtil.info(" get HistoricActivityInstance list : " + list);
		return list;
	}

	@Override
	public NativeHistoricProcessInstanceQuery createHisProcInstDefIdNameLikeQuery(PlanPageVo vo) {
		
		StringBuffer wheresql = new StringBuffer() ;
		
		if(vo.getProcessKey() != null && vo.getProcessKey().trim().length() > 0 ) {
			wheresql.append(" and t.proc_def_id_ like #{procDefId} ");
		}
		if(vo.getProcessInstanceId() != null && vo.getProcessInstanceId().trim().length() > 0) {
			wheresql.append(" and t.proc_inst_id_ = #{processInstanceId}");
		}
		if(vo.getStartTime() != null ) {
			wheresql.append(" and t.start_time_ >= cast( #{startTime} as Date) ");
		}
		if(vo.getEndTime() != null ) {
			wheresql.append(" and t.start_time_ <= cast( #{endTime} as Date) ");
		}
		
		NativeHistoricProcessInstanceQuery query = historyService
				.createNativeHistoricProcessInstanceQuery()
				.sql("select * from  ACT_HI_PROCINST t where 1 = 1  " + wheresql.toString() + " order by t.start_time_ desc")
				.parameter("procDefId", "%" + vo.getProcessKey() + "%")
				.parameter("processInstanceId", vo.getProcessInstanceId())
				.parameter("startTime", vo.getStartTime())
				.parameter("endTime", vo.getEndTime());
		
		return query;
	}

	@Override
	public ProcessDefinitionQuery createProcessDefinitionQuery() {
		return repositoryService.createProcessDefinitionQuery();
	}


}
