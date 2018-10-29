package com.bandweaver.tunnel.service.omm.equipment;

import com.bandweaver.tunnel.common.biz.itf.ModuleCenterInterface;

public class EquipmentModuleCenter implements ModuleCenterInterface {


    private static class SingletonHolder{
        public static EquipmentModuleCenter instance = new EquipmentModuleCenter();
    }

    private EquipmentModuleCenter(){}

    public static EquipmentModuleCenter getInstance(){
        return SingletonHolder.instance;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}