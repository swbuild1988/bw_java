package com.bandweaver.tunnel.service.common.module;

import com.bandweaver.tunnel.service.common.mq.MqModuleCenter;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bandweaver.tunnel.common.biz.itf.ModuleCenterInterface;
import com.bandweaver.tunnel.common.biz.itf.ScheduleJobService;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.DateUtil;

@Service
public class COMModuleCenter implements ModuleCenterInterface {

    @Autowired
    private ScheduleJobService scheduleJobService;
    @Autowired
    private MqModuleCenter mqModuleCenter;

    @Override
    public void start() {

        try {
            scheduleJobService.init();
            mqModuleCenter.start();
        } catch (ClassNotFoundException e) {
            LogUtil.error(e.toString());
        } catch (SchedulerException e) {
            LogUtil.error(e.toString());
        }
    }

    @Override
    public void stop() {
    }

}
