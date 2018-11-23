package com.bandweaver.tunnel.service.mam.module;

import com.bandweaver.tunnel.common.biz.itf.ModuleCenterInterface;
import com.bandweaver.tunnel.service.mam.alarm.AlarmModuleCenter;
import com.bandweaver.tunnel.service.mam.data.DataModuleCenter;
import com.bandweaver.tunnel.service.mam.maxview.MaxviewConfigModuleCenter;
import com.bandweaver.tunnel.service.mam.measobj.MeasObjModuleCenter;
import com.bandweaver.tunnel.service.mam.robot.RobotModuleCenter;
import com.bandweaver.tunnel.service.mam.video.VideoModuleCenter;
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
    private MaxviewConfigModuleCenter maxviewConfigModuleCenter;


    @Override
    public void start() {
//    	alarmModuleCenter.start();
        DataModuleCenter.getInstance().start();
        measObjModuleCenter.start();
        videoModuleCenter.start();
        maxviewConfigModuleCenter.start();
        RobotModuleCenter.getInstance().start();
    }

    @Override
    public void stop() {
//    	alarmModuleCenter.stop();
        DataModuleCenter.getInstance().stop();
        measObjModuleCenter.stop();
        videoModuleCenter.stop();
        maxviewConfigModuleCenter.stop();
        RobotModuleCenter.getInstance().stop();
    }
}
