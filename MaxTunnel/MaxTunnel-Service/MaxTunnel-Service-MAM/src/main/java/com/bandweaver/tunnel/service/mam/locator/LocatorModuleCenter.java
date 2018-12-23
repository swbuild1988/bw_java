package com.bandweaver.tunnel.service.mam.locator;

import com.bandweaver.tunnel.common.biz.itf.ModuleCenterInterface;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import org.springframework.stereotype.Service;

@Service
public class LocatorModuleCenter implements ModuleCenterInterface {
    @Override
    public void start() {
        LogUtil.info("定位系统开启");
    }

    @Override
    public void stop() {
        LogUtil.info("定位系统关闭");
    }
}
