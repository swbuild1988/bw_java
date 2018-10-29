package com.bandweaver.tunnel.service.common.activiti;

import com.bandweaver.tunnel.common.platform.log.LogUtil;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;

public class GlobalEventListener implements ActivitiEventListener {
    @Override
    public void onEvent(ActivitiEvent activitiEvent) {
        String eventType = activitiEvent.getType().name();
//        LogUtil.info("activit onEvent: " + eventType);
    }

    @Override
    public boolean isFailOnException() {
        return false;
    }
}
