package com.bandweaver.tunnel.common.biz.itf;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.NativeHistoricProcessInstanceQuery;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import com.bandweaver.tunnel.common.biz.constant.ProcessTypeEnum;
import com.bandweaver.tunnel.common.biz.pojo.ProcessBase;
import com.bandweaver.tunnel.common.biz.vo.MyTaskVo;
import com.bandweaver.tunnel.common.biz.vo.em.PlanPageVo;

public interface ActivitiService {

	/**
	 * @Description: 发布流程
	 * @param @param  bpmn路径
	 * @param @param  png路径
	 * @param @param  流程名称
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author shaosen
	 * @date 2018年7月10日
	 */
	String deploy(String bpmnPath, String pngPath, String processName);


	/**
	 *
	 * @param processTypeEnum
	 * @return
	 */
	String startProcessInstance(ProcessTypeEnum processTypeEnum);

	/**
	 *
	 * @param processTypeEnum
	 * @param var
	 * @return
	 */
	String startProcessInstance(ProcessTypeEnum processTypeEnum, int... var);

	/**
	 * 进行下一个任务
	 * @param processIntanceId
	 * @return
	 */
	int completeTaskByProcessIntance(String processIntanceId);

	/**
	 * 进行下一个任务
	 * @param processIntanceId
	 * @param map
	 * @return
	 */
	int completeTaskByProcessIntance(String processIntanceId, Map<String, Object> map);

	/**
	 * 根据流程获取活动的任务
	 * @param processIntanceId
	 * @return
	 */
	List<Task> getActiveTasksByProcessIntance(String processIntanceId);

	/**
	 * 获取用户的所有任务
	 * @param userId
	 * @return
	 */
	List<Task> getActiveTasksByUser(int userId);

	/**
	 * 获取分页用户任务
	 * @param vo
	 * @return
	 */
	List<Task> getActiveTasksByVO(MyTaskVo vo);

	/**
	 * 根据任务获得流程类型
	 * @param task
	 * @return
	 */
	ProcessTypeEnum getProcessType(Task task);

	/**
	 * 根据历史任务实例获得流程类型
	 * @param task
	 * @return
	 */
	ProcessTypeEnum getHistoryProcessType(HistoricTaskInstance task);
	
	/**
	 * 根据流程实例获得流程类型
	 * @param processInstanceId
	 * @return
	 */
	ProcessTypeEnum getProcessTypeByProcessInstance(String processInstanceId);



	/**
	 * 获取流程实例的当前单个任务
	 * @param processInstanceId
	 * @return
	 */
	Task getActiveTaskByProcessIntance(String processInstanceId);

	/**
	 * 根据任务获取流程实例
	 * @param taskId
	 * @return
	 */
	ProcessInstance getProcessInstanceByTask(String taskId);

	/**
	 * 完结任务
	 * @param taskId
	 */
	void completeTask(String taskId);

	/**
	 * 完结任务并配以参数
	 * @param taskId
	 * @param map
	 */
	void completeTask(String taskId, Map<String, Object> map);

	/**
	 * 完结任务，配参数，并加上批注
	 * @param taskId
	 * @param map
	 * @param comment
	 */
	void completeTask(String taskId, Map<String, Object> map, String comment);

	/**
	 * 添加任务备注
	 * @param taskId
	 * @param processIntanceId
	 * @param message
	 */
	void addTaskComment(String taskId, String processIntanceId, String message);

	/**
	 * 获得任务备注
	 * @param taskId
	 * @return
	 */
	String getTaskComment(String taskId);

	/**
	 * 根据任务id，获取最基本的流程信息（我们表中存储的）
	 * @param taskId
	 * @return
	 */
	ProcessBase getProcessBaseByTask(String taskId);
	
	/**
	 * 根据历史任务实例，获取最基本的流程信息（我们表中存储的）
	 * @param taskId
	 * @return
	 */
	ProcessBase getProcessBaseByHistoryTask(ProcessTypeEnum processTypeEnum,HistoricTaskInstance task);

	/**
	 * 根据流程实例获取基础的流程信息
	 * @param processInstanceId
	 * @return
	 */
	ProcessBase getProcessBaseByProcessInstance(String processInstanceId);

	/**
	 * 根据流程中的任务名更新状态
	 * @param processInstanceId
	 */
	void updateProcessBaseStatusByProcessInstance(String processInstanceId);

	/**
	 * 更新流程状态
	 * @param processDefinitionId
	 * @param processInstanceId
	 * @param status
	 */
	void updateProcessBaseStatus(String processDefinitionId, String processInstanceId, String status);

	/**
	 * 更新流程状态
	 * @param processInstanceId
	 * @param status
	 */
	void updateProcessBaseStatusByProcessInstance(String processInstanceId, String status);

	/**
	 * 根据流程实例结束任务
	 * @param processInstanceId
	 */
	void finishProcessBaseByProcessInstance(String processInstanceId);


	/***********************************************上面为要留的代码*********************************************************************/


	/**
	 * @Description: 获取流程图图片
	 * @param  processInstanceId 流程实例id
	 * @param @return   
	 * @return InputStream  
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @throws
	 * @author shaosen
	 * @date 2018年7月10日
	 */
	void getImageByProcessInstance(String processInstanceId,HttpServletResponse response) throws FileNotFoundException, IOException;
	

	/**
	 * @Description: 获取任务列表
	 * @param @return   
	 * @return List<Task>  
	 * @throws
	 * @author shaosen
	 * @date 2018年7月11日
	 */
	List<Task> getTaskList(Integer staffId);

	/**
	 * @Description: 获取当前登录用户员工id
	 * @param @return   
	 * @return Integer  
	 * @throws
	 * @author shaosen
	 * @date 2018年7月11日
	 */
	Integer getStaffId();


	/**
	 * 获取最新发布的流程
	 * @param  bpmnPath bpmn路径
	 * @param    
	 * @return ProcessDefinition  
	 * @author shaosen
	 * @Date 2018年8月8日
	 */
	ProcessDefinition getLastestProcessDefinition(String bpmnPath);


	/**
	 * 通过流程定义id开启流程
	 * @param  pid 流程定义id
	 * @param    
	 * @return ProcessInstance  
	 * @author shaosen
	 * @Date 2018年8月8日
	 */
	ProcessInstance startProcessInstanceById(String pid);
	ProcessInstance startProcessInstanceById(String pid,Map<String, Object> variables);


	/**
	 * 通过流程实例id获取历史任务节点
	 * @param  iid 流程实例id
	 * @return List<HistoricTaskInstance>  
	 * @author shaosen
	 * @Date 2018年8月8日
	 */
	List<HistoricTaskInstance> getHistoricTaskInstanceListByInstanceId(String iid);


	
	/**
	 * 获取流程引擎    
	 * @return  
	 * ProcessEngine  
	 * @author shaosen
	 * @Date 2018年8月9日
	 */
	ProcessEngine getProcessEngine();


	/**
	 * 通过流程实例id查询历史活动表，获取流程实例对应的活动节点信息，并按节点开始时间升序排列
	 * @param iid 流程实例id
	 * @return  
	 * List<HistoricActivityInstance>  
	 * @author shaosen
	 * @Date 2018年8月10日
	 */
	List<HistoricActivityInstance> getHisActInstListByProcessInstanceId(String iid);


	/**
	 * 通过流程定义id模糊查询查询历史实例表（包含正在执行的实例），并按实例开始时间降序排列
	 * @param PlanPageVo 
	 * @return  
	 * NativeHistoricProcessInstanceQuery  
	 * @author shaosen
	 * @Date 2018年8月10日
	 */
	NativeHistoricProcessInstanceQuery createHisProcInstDefIdNameLikeQuery(PlanPageVo vo);


	/**
	 * 获取用户所有任务
	 * @param userId
	 * @return
	 * @author liuya
	 * @Date 2018年9月14日
	 */
	List<HistoricTaskInstance> getHistoryTasksByUser(int userId);


	List<HistoricTaskInstance> getAllActiveTasks();


	ProcessDefinitionQuery createProcessDefinitionQuery();



}
