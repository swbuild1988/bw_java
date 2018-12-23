package com.bandweaver.tunnel.service.common.activiti;

import com.bandweaver.tunnel.common.biz.itf.ActivitiService;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.SpringContextHolder;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;

public class ProcessCompletedListener implements ActivitiEventListener {
    @Override
    public void onEvent(ActivitiEvent activitiEvent) {
//        LogUtil.info("process completed: " + activitiEvent.getType().name());
//        LogUtil.info(activitiEvent);

        ActivitiService activitiService = SpringContextHolder.getBean("activitiService");
        activitiService.finishProcessBaseByProcessInstance(activitiEvent.getProcessInstanceId());
    }

    @Override
    public boolean isFailOnException() {
        return false;
    }
}
