package com.bandweaver.tunnel.server.init;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.DateUtil;
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
    	long beginTime = System.currentTimeMillis();
        mamModuleCenter.start();
        comModuleCenter.start();
        long endTime = System.currentTimeMillis();
        
        LogUtil.info(	"*********************************\n"
						+ "描述：初始化结束\n"
						+ "项目名：太原市综合管廊管理平台\n"
						+ "版本号：V100B03\n"
						+ "耗时：" + (endTime - beginTime) +"ms\n"
						+ "*********************************");
    }
}
