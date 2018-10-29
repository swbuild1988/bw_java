package com.bandweaver.tunnel.service.omm.module;

import com.bandweaver.tunnel.common.biz.itf.ModuleCenterInterface;
import com.bandweaver.tunnel.service.omm.maintenance.MaintenanceModuleCenter;
import com.bandweaver.tunnel.service.omm.equipment.EquipmentModuleCenter;

public class OMMModuleCenter implements ModuleCenterInterface {

    private static class SingletonHolder{
        public static OMMModuleCenter instance = new OMMModuleCenter();
    }

    private OMMModuleCenter(){}

    public static OMMModuleCenter getInstance(){
        return SingletonHolder.instance;
    }

    @Override
    public void start() {
        MaintenanceModuleCenter.getInstance().start();
        EquipmentModuleCenter.getInstance().start();
    }

    @Override
    public void stop() {

    }
}
