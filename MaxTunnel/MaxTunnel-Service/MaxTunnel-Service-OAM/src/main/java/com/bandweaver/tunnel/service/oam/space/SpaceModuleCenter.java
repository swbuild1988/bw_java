package com.bandweaver.tunnel.service.oam.space;

import com.bandweaver.tunnel.common.biz.itf.ModuleCenterInterface;

public class SpaceModuleCenter implements ModuleCenterInterface {


    private static class SingletonHolder{
        public static SpaceModuleCenter instance = new SpaceModuleCenter();
    }

    private SpaceModuleCenter(){}

    public static SpaceModuleCenter getInstance(){
        return SingletonHolder.instance;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
