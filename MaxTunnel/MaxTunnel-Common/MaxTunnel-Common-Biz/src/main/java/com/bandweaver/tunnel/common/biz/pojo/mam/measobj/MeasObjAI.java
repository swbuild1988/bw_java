package com.bandweaver.tunnel.common.biz.pojo.mam.measobj;

import com.alibaba.fastjson.JSONObject;

import java.util.Date;

public class MeasObjAI extends MeasObj {

    public static MeasObjAI fromMeasObj(MeasObj obj){
        MeasObjAI tmp = new MeasObjAI();
        String str = JSONObject.toJSONString(obj);
        tmp = (MeasObjAI)JSONObject.parseObject(str, MeasObjAI.class);
        return tmp;
    }

    private Date refreshTime;
    private Double CV;

    public Double getCV() {
        return CV;
    }

    public void setCV(Double CV) {
        this.CV = CV;
    }

    public Date getRefreshTime() {
        return refreshTime;
    }

    public void setRefreshTime(Date refreshTime) {
        this.refreshTime = refreshTime;
    }

}
