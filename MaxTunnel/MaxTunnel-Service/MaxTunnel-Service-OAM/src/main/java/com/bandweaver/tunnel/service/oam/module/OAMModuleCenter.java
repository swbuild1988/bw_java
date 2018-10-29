package com.bandweaver.tunnel.service.oam.module;

import com.bandweaver.tunnel.common.biz.itf.ModuleCenterInterface;
import com.bandweaver.tunnel.service.oam.consumption.ConsumptionModuleCenter;
import com.bandweaver.tunnel.service.oam.space.SpaceModuleCenter;

public class OAMModuleCenter implements ModuleCenterInterface {

    private static class SingletonHolder{
        public static OAMModuleCenter instance = new OAMModuleCenter();
    }

    private OAMModuleCenter(){}

    public static OAMModuleCenter getInstance(){
        return SingletonHolder.instance;
    }

    @Override
    public void start() {
        ConsumptionModuleCenter.getInstance().start();
        SpaceModuleCenter.getInstance().start();
    }

    @Override
    public void stop() {

    }
}
