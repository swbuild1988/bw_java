package com.bandweaver.tunnel.service.mam.robot;

import com.bandweaver.tunnel.common.biz.itf.ModuleCenterInterface;

public class RobotModuleCenter implements ModuleCenterInterface {


    private static class SingletonHolder{
        public static RobotModuleCenter instance = new RobotModuleCenter();
    }

    private RobotModuleCenter(){}

    public static RobotModuleCenter getInstance(){
        return SingletonHolder.instance;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
