package com.bandweaver.tunnel.server.init;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.bandweaver.tunnel.service.common.module.COMModuleCenter;
import com.bandweaver.tunnel.service.em.EMModuleCenter;
import com.bandweaver.tunnel.service.mam.module.MAMModuleCenter;
import com.bandweaver.tunnel.service.oam.module.OAMModuleCenter;
import com.bandweaver.tunnel.service.omm.module.OMMModuleCenter;

@Component("StartupListener")
public class StartupListener implements ApplicationContextAware {
    @Autowired
    private MAMModuleCenter mamModuleCenter;
    @Autowired
    private COMModuleCenter comModuleCenter;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("start init");

        mamModuleCenter.start();
        comModuleCenter.start();
    }
}
