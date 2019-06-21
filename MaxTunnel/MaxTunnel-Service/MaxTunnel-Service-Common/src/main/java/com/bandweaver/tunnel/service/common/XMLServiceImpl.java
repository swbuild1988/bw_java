package com.bandweaver.tunnel.service.common;

import com.bandweaver.tunnel.common.biz.itf.common.XMLService;
import com.bandweaver.tunnel.common.biz.pojo.xml.Config;
import com.bandweaver.tunnel.common.platform.util.XMLUtil;
import org.springframework.stereotype.Service;

@Service
public class XMLServiceImpl implements XMLService {

    @Override
    public Config getXMLAllInfo() {
        String path = this.getClass().getClassLoader().getResource("config.xml").getPath();
        Config config = (Config) XMLUtil.convertXmlFileToObject(Config.class, path);

        if(config == null) {
            config = new Config();
        }
        config.init();
        XMLUtil.convertToXml(config, path);
        return config;
    }

    @Override
    public void setXMLInfo(Config config) {
        String path = this.getClass().getClassLoader().getResource("config.xml").getPath();

        XMLUtil.convertToXml(config, path);
    }
}
