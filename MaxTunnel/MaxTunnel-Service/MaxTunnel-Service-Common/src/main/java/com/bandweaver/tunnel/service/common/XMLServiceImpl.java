package com.bandweaver.tunnel.service.common;

import com.bandweaver.tunnel.common.biz.itf.common.XMLService;
import com.bandweaver.tunnel.common.biz.pojo.xml.Config;
import com.bandweaver.tunnel.common.platform.util.XMLUtil;
import org.springframework.stereotype.Service;

@Service
public class XMLServiceImpl implements XMLService {


    private Config config = null;

    @Override
    public Config getXMLAllInfo() {

        if (config == null) {

            String path = this.getClass().getClassLoader().getResource("config.xml").getPath();
            config = (Config) XMLUtil.convertXmlFileToObject(Config.class, path);

            if (config == null) {
                config = new Config();
            }
            boolean f = config.init();
            if (f) XMLUtil.convertToXml(config, path);
        }

        return config;
    }

    @Override
    public void setXMLInfo(Config config) {
        String path = this.getClass().getClassLoader().getResource("config.xml").getPath();

        XMLUtil.convertToXml(config, path);
    }
}
