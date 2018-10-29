package com.bandweaver.tunnel.service.em;

import com.bandweaver.tunnel.common.biz.itf.ModuleCenterInterface;

public class EMModuleCenter implements ModuleCenterInterface {

    private static class SingletonHolder{
        public static EMModuleCenter instance = new EMModuleCenter();
    }

    private EMModuleCenter(){}

    public static EMModuleCenter getInstance(){
        return SingletonHolder.instance;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
