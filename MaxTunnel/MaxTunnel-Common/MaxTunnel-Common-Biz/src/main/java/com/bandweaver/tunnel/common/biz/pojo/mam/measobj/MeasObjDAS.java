package com.bandweaver.tunnel.common.biz.pojo.mam.measobj;

import com.alibaba.fastjson.JSONObject;

public class MeasObjDAS extends MeasObj {
    public static MeasObjDAS fromMeasObj(MeasObj obj){
        MeasObjDAS tmp = new MeasObjDAS();
        String str = JSONObject.toJSONString(obj);
        tmp = (MeasObjDAS)JSONObject.parseObject(str, MeasObjDAS.class);
        return tmp;
    }
}
