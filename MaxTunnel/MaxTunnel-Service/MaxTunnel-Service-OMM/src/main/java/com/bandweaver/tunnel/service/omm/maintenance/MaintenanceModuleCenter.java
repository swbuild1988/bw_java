package com.bandweaver.tunnel.service.omm.maintenance;

import com.bandweaver.tunnel.common.biz.itf.ModuleCenterInterface;

public class MaintenanceModuleCenter implements ModuleCenterInterface {


    private static class SingletonHolder{
        public static MaintenanceModuleCenter instance = new MaintenanceModuleCenter();
    }

    private MaintenanceModuleCenter(){}

    public static MaintenanceModuleCenter getInstance(){
        return SingletonHolder.instance;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}