package com.bandweaver.tunnel.common.biz.itf.common;

import com.bandweaver.tunnel.common.biz.pojo.xml.Config;

public interface XMLService {

    /**
     * 获取配置文件
     *
     * @return
     */
    Config getXMLAllInfo();

    /**
     * 设置配置文件
     *
     * @param config
     */
    void setXMLInfo(Config config);
}
