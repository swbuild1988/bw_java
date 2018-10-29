package com.bandweaver.tunnel.common.platform.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;


/**
 * 发送短信工具类
 * @author shaosen
 * @date 2018年8月1日
 */
public class MessageUtil {

    public static void sendMessage(String tel) {
         String host = "http://yzx.market.alicloudapi.com";
            String path = "/yzx/sendSms";
            String method = "POST";
            String appcode = "这里填写你的appcode";
            Map<String, String> headers = new HashMap<String, String>();
            //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
            headers.put("Authorization", "APPCODE " + appcode);
            Map<String, String> querys = new HashMap<String, String>();
            
            querys.put("mobile", "这里填写你要发送的手机号码");
            
            querys.put("param", "code:1234");
            //querys.put("param", "这里填写你和商家定义的变量名称和变量值填写格式看上一行代码");
            
            querys.put("tpl_id", "这里填写你和商家商议的模板");
            
            Map<String, String> bodys = new HashMap<String, String>();


            try {
                /**
                * 重要提示如下:
                * HttpUtils请从
                * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
                * 下载
                *
                * 相应的依赖请参照
                * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
                */
                HttpResponse response = HttpUtil.doPost(host, path, method, headers, querys, bodys);
                System.out.println(response.toString());

                //获取response的body
                //System.out.println(EntityUtils.toString(response.getEntity()));
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
}
