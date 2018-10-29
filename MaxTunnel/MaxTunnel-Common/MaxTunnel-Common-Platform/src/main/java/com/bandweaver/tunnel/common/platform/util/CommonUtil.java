package com.bandweaver.tunnel.common.platform.util;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.platform.constant.Constants;
import com.bandweaver.tunnel.common.platform.constant.StatusCodeEnum;

public class CommonUtil {

    /**
     * 返回格式都是以（code,msg,data）形式返回
     * @param code
     * @param msg
     * @param data
     * @return
     */
    public static JSONObject returnJson(String code, String msg, Object data){
        JSONObject resultJson = new JSONObject();
        resultJson.put("code", code);
        resultJson.put("msg", msg);
        resultJson.put("data", data);
        return resultJson;
    }

    /**
     *
     * @param statusCodeEnum
     * @return
     */
    public static JSONObject returnStatusJson(StatusCodeEnum statusCodeEnum){
        return returnJson(statusCodeEnum.getCode(), statusCodeEnum.getMsg(), new JSONObject());
    }

    /**
     *
     * @param statusCodeEnum
     * @param obj
     * @return
     */
    public static JSONObject returnStatusJson(StatusCodeEnum statusCodeEnum, Object obj){
        return returnJson(statusCodeEnum.getCode(), statusCodeEnum.getMsg(), obj);
    }
}
