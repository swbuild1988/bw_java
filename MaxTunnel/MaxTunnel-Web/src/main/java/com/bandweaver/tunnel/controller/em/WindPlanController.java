package com.bandweaver.tunnel.controller.em;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.constant.ProcessTypeEnum;
import com.bandweaver.tunnel.common.biz.constant.em.FirePlanNodeEnum;
import com.bandweaver.tunnel.common.biz.itf.ActivitiService;
import com.bandweaver.tunnel.common.biz.itf.em.WindPlanService;
import com.bandweaver.tunnel.common.platform.constant.StatusCodeEnum;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.CommonUtil;
import com.bandweaver.tunnel.common.platform.util.PropertiesUtil;

/**
 * 通风预案
 * @author ya.liu
 * @date 2018年9月22日
 */
@Controller
@ResponseBody
public class WindPlanController {
	
	private static final String VALUE = "yes";
	
	@Autowired
	private ActivitiService activitiService;
	@Autowired
	private WindPlanService windPlanService;
	
	/**
	 * 通风预案流程部署（只需执行一次即可，如果执行多次，则使用最新版本）
	 * @param    
	 * @return {"msg":"请求成功","code":"200","data":{}}  
	 * @author ya.liu
	 * @Date 2018年9月22日
	 */
	@RequestMapping(value="windplans/deploy",method=RequestMethod.GET)
	public JSONObject deploy() {
		activitiService.deploy((String)PropertiesUtil.getValue(ProcessTypeEnum.WIND_PLAN.getBpmnPath()), 
				(String)PropertiesUtil.getValue(ProcessTypeEnum.WIND_PLAN.getBpmnPath()), 
				(String)PropertiesUtil.getValue(ProcessTypeEnum.WIND_PLAN.getName()));
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}
	
	/**
	 * 通风预案演示
	 * 说明：每次节点任务完成时，会发送消息到mq队列，消息格式为：{"val":[{"node":"开启声光报警","status":"完成"},{"node":"调用摄像头","status":"完成"},{"node":"值班人员确认","status":"完成"},{"node":"打开风机","status":"完成"},{"node":"打开风阀","status":"完成"},{"node":"打开百叶","status":"完成"},{"node":"启动干粉灭火","status":"完成"},{"node":"通知相关单位","status":"完成"}],"end":true,"key":"消防预案执行流程"}
	 * end字段：false代表流程未结束，true代表流程结束
	 * @param     
	 * @return   {"msg":"请求成功","code":"200","data":{}}
	 * @throws InterruptedException
	 * @author ya.liu
	 * @Date 2018年9月22日
	 */
	@RequestMapping(value="windplans/demo",method=RequestMethod.GET)
	public JSONObject MonitoringTemperature() throws InterruptedException {
		LogUtil.info("温湿度过高或氧气较低或有害气体过浓");
		
		//获取最新流程
		ProcessDefinition processDefinition = activitiService.getLastestProcessDefinition((String)PropertiesUtil.getValue(ProcessTypeEnum.WIND_PLAN.getBpmnPath()));
		
		//启动流程
		ProcessInstance instance = activitiService.startProcessInstanceById(processDefinition.getId());
		
		//查询流程并完成节点任务
		queryAndCompleteTask(instance.getId());
		queryAndCompleteTask(instance.getId());
		queryTaskStatus(instance.getId(),null);
		
		LogUtil.info("流程实例ID："+instance.getId());
		LogUtil.info("等待值班人员确认...");
		
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}
	

	
	private void queryAndCompleteTask(String iid) {
		Task task = activitiService.getActiveTaskByProcessIntance(iid);
		assertNotNull(task);
		queryTaskStatus(iid,null);//查询流程状态
		activitiService.completeTask(task.getId());
	}


	private void queryTaskStatus(String iid,String value) {
		
		// 通过流程实例id获取历史任务节点
		List<HistoricTaskInstance> list = activitiService.getHistoricTaskInstanceListByInstanceId(iid);
		List<JSONObject> result = new ArrayList<>();
		boolean flag = false;
		for (HistoricTaskInstance historicTaskInstance : list) {
			JSONObject json = new JSONObject();
			json.put("node", historicTaskInstance.getName());
			json.put("status", historicTaskInstance.getEndTime()==null ? "进行中" : "完成");
			result.add(json);
			
			//如果是确认，判断最后一个节点任务（通知相关单位）是否完成，如果完成，则设置flag=true
			if(historicTaskInstance.getName().equals(FirePlanNodeEnum.inform_relatedunit.getName())&&historicTaskInstance.getEndTime()!=null) {
				flag = true;
			}
		}
		
		//如果值班人员取消，根据value值设置flag=true
		if(value != null && !VALUE.equals(value)) {
			flag =true;
		}
		
		JSONObject json = new JSONObject();
		json.put("key", "消防预案执行流程");
		json.put("val", result);
		json.put("end", flag);
		json.put("iid", iid);
		windPlanService.sendMsg(json);
		
	}

	
	/**
	 * 值班人员确认
	 * @param  iid 流程实例id
	 * @param  value 值：yes 或 no
	 * @param     
	 * @return {"msg":"请求成功","code":"200","data":{}}  
	 * @throws InterruptedException
	 * @author shaosen
	 * @Date 2018年7月31日
	 */
	@RequestMapping(value="windplans/{iid}/{value}",method=RequestMethod.GET)
	public JSONObject comfirm(@PathVariable("iid") String iid,@PathVariable("value") String value) throws InterruptedException {
		//查询任务
		Task task = activitiService.getActiveTaskByProcessIntance(iid);
		assertNotNull(task);
		
		//值班人员确认
		Map<String,Object> variables = new HashMap<>();
		variables.put("result", value);
		LogUtil.info(VALUE.equals(value)? "警报确认" : "警报取消");
		activitiService.completeTask(task.getId(), variables);
		
		if(VALUE.equals(value)) {
			int i = 0;
			while(i<3) {
				queryAndCompleteTask(iid);
				i++;
			}
		}
		
		queryTaskStatus(iid,value);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
		
	}
}
