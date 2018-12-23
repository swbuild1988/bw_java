package com.bandweaver.tunnel.service.common.activiti;

import com.bandweaver.tunnel.common.biz.itf.ActivitiService;
import com.bandweaver.tunnel.common.biz.itf.em.EmPlanService;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.SpringContextHolder;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.delegate.event.impl.ActivitiEntityEventImpl;
import org.activiti.engine.impl.persistence.entity.TaskEntity;


public class TaskCreatedListener implements ActivitiEventListener {

    @Override
    public void onEvent(ActivitiEvent activitiEvent) {
        LogUtil.info("task created: " + activitiEvent.getType().name());
        LogUtil.info("excuted Id : " + activitiEvent.getExecutionId());

        ActivitiService activitiService = SpringContextHolder.getBean("activitiService");
        EmPlanService emPlanService = SpringContextHolder.getBean("emPlanService");

        // 获取任务实例
        ActivitiEntityEventImpl eventImpl=(ActivitiEntityEventImpl)activitiEvent;
        TaskEntity taskEntity=(TaskEntity)eventImpl.getEntity();

        LogUtil.info("task name: " + taskEntity.getName());
        LogUtil.info("taskDefinitionKey: " + taskEntity.getTaskDefinitionKey());
        // 更新状态
        activitiService.updateProcessBaseStatus(activitiEvent.getProcessDefinitionId(), activitiEvent.getProcessInstanceId(), taskEntity.getName());
        
        //应急预案处理
        emPlanService.doBusiness(activitiEvent,taskEntity);
        
    }

    @Override
    public boolean isFailOnException() {
        return false;
    }
}
