package com.bandweaver.tunnel.service.mam.data;

import com.bandweaver.tunnel.common.biz.itf.ModuleCenterInterface;

public class DataModuleCenter implements ModuleCenterInterface {


    private static class SingletonHolder{
        public static DataModuleCenter instance = new DataModuleCenter();
    }

    private DataModuleCenter(){}

    public static DataModuleCenter getInstance(){
        return SingletonHolder.instance;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}