package com.bandweaver.tunnel.common.biz.pojo.mam.measobj;

import com.alibaba.fastjson.JSONObject;

import java.util.Date;

public class MeasObjSO extends MeasObj {

    public static MeasObjSO fromMeasObj(MeasObj obj){
        MeasObjSO tmp = new MeasObjSO();
        String str = JSONObject.toJSONString(obj);
        tmp = (MeasObjSO)JSONObject.parseObject(str, MeasObjSO.class);
        return tmp;
    }

    private Date refreshTime;
    private String CV;

    public String getCV() {
        return CV;
    }

    public void setCV(String CV) {
        this.CV = CV;
    }

    public Date getRefreshTime() {
        return refreshTime;
    }

    public void setRefreshTime(Date refreshTime) {
        this.refreshTime = refreshTime;
    }

    @Override
    public String toString() {
        return "MeasObjSO{" +
                "refreshTime=" + refreshTime +
                ", CV=" + CV +
                "} " + super.toString();
    }
}
