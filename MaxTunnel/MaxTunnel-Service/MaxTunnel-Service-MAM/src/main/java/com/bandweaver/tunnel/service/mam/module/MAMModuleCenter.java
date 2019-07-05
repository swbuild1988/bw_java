package com.bandweaver.tunnel.service.mam.module;

import com.bandweaver.tunnel.common.biz.itf.ModuleCenterInterface;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.service.mam.alarm.AlarmModuleCenter;
import com.bandweaver.tunnel.service.mam.data.DataModuleCenter;
import com.bandweaver.tunnel.service.mam.maxview.SubSystemModuleCenter;
import com.bandweaver.tunnel.service.mam.measobj.MeasObjModuleCenter;
import com.bandweaver.tunnel.service.mam.robot.RobotModuleCenter;
import com.bandweaver.tunnel.service.mam.video.VideoModuleCenter;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MAMModuleCenter implements ModuleCenterInterface {
    @Autowired
    private MeasObjModuleCenter measObjModuleCenter;
    @Autowired
    private VideoModuleCenter videoModuleCenter;
    @Autowired
    private AlarmModuleCenter alarmModuleCenter;
    @Autowired
    private SubSystemModuleCenter subSystemModuleCenter;


    @Override
    public void start() {
//    	alarmModuleCenter.start();
        measObjModuleCenter.start();
        videoModuleCenter.start();
        subSystemModuleCenter.start();
    }

    @Override
    @PreDestroy
    public void stop() {

        // GC垃圾回收
//    	alarmModuleCenter.stop();
        measObjModuleCenter.stop();
        videoModuleCenter.stop();
        subSystemModuleCenter.stop();

        LogUtil.info("GC垃圾回收完成");
    }
}
