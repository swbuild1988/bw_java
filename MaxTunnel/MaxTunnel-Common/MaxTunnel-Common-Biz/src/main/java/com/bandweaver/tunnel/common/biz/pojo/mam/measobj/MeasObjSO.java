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
    private String cv;

    public String getCv() {
        return cv;
    }

    public void setCv(String CV) {
        this.cv = CV;
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
                ", CV=" + cv +
                "} " + super.toString();
    }
}
