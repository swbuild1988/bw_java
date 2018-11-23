package com.bandweaver.tunnel.common.biz.itf.em;

import java.util.List;

import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.impl.persistence.entity.TaskEntity;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.dto.em.EmPlanDto;
import com.bandweaver.tunnel.common.biz.pojo.em.EmPlan;
import com.bandweaver.tunnel.common.biz.vo.em.EmPlanVo;
import com.github.pagehelper.PageInfo;

public interface EmPlanService {

	void doBusiness(ActivitiEvent activitiEvent, TaskEntity taskEntity);

	/**下一个任务 
	 * @param iid   流程实例id
	 * @author shaosen
	 * @Date 2018年10月16日
	 */
	void nextTask(String processInstanceId,Integer objectId);

	/**发送消息到mq队列 
	 * @param emPlan
	 * @param processInstanceId 流程实例ID
	 * @param isFinished  流程是否结束
	 * @author shaosen
	 * @Date 2018年10月16日
	 */
	void sendMsg(EmPlan emPlan, String processInstanceId ,Integer objectId);

	/**启动预案 
	 * @param sectionId
	 * @param processValue   
	 * @author shaosen
	 * @Date 2018年10月17日
	 */
	void start(Integer sectionId, Integer processValue,Integer objectId);

	void add(EmPlan record);

	void delete(Integer id);

	void update(EmPlan record);

	EmPlanDto getById(Integer id);

	void deleteBatch(List<Integer> list);

	PageInfo<EmPlanDto> dataGrid(EmPlanVo vo);

	List<JSONObject> getNodeListByProcessKey(String processKey);


}
