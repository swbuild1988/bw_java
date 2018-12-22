package com.bandweaver.tunnel.common.platform.config;

import com.bandweaver.tunnel.common.platform.log.LogUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.*;
import java.util.HashMap;

public class SystemConfigService {

    private static final String SYSPARAS_FILENAME = "config/system-config.xml";

    public HashMap<String, HashMap<String, String>> SYSPARAS;

    private static class SingletonClass{
        public static SystemConfigService instance = new SystemConfigService();
    }

    private SystemConfigService(){
        LoadSysParas();
    }

    public static SystemConfigService getInstance(){
        return SingletonClass.instance;
    }

    private void LoadSysParas() {
        SYSPARAS = new HashMap<String, HashMap<String, String>>();

        // 开始读取数据
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(SYSPARAS_FILENAME);

            // 获取所有的Category
            NodeList categorys = document.getElementsByTagName("Category");
            for (int i = 0; i < categorys.getLength(); i++) {
                Element category = (Element)categorys.item(i);
                // 获得name属性
                String categoryName = category.getAttribute("name");
                // 获得子属性
                HashMap<String, String> categoryParas = new HashMap<String, String>();
                NodeList childNodes = category.getChildNodes();
                for (int j = 0; j < childNodes.getLength(); j++) {
                    Element child = (Element)childNodes.item(j);
                    String childName = child.getAttribute("name");
                    String childValue = child.getAttribute("value");
                    categoryParas.put(childName,childValue);
                }
                SYSPARAS.put(categoryName, categoryParas);
            }

        } catch (Exception e) {
            LogUtil.error(e.getMessage());
        }
    }

    public String getParamValue(String categoryName, String paraName){

        if (SYSPARAS.containsKey(categoryName)){
            if (SYSPARAS.get(categoryName).containsKey(paraName)){
                return SYSPARAS.get(categoryName).get(paraName);
            }
        }

        return  "";
    }
}
