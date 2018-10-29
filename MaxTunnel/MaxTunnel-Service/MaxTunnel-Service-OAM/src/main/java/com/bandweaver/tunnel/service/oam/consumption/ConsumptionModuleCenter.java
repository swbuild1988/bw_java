package com.bandweaver.tunnel.service.oam.consumption;

import com.bandweaver.tunnel.common.biz.itf.ModuleCenterInterface;

public class ConsumptionModuleCenter implements ModuleCenterInterface {


    private static class SingletonHolder{
        public static ConsumptionModuleCenter instance = new ConsumptionModuleCenter();
    }

    private ConsumptionModuleCenter(){}

    public static ConsumptionModuleCenter getInstance(){
        return SingletonHolder.instance;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
