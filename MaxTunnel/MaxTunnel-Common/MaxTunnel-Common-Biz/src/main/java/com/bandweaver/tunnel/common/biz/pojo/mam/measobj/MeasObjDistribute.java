package com.bandweaver.tunnel.common.biz.pojo.mam.measobj;

import com.alibaba.fastjson.JSONObject;

import java.util.Arrays;
import java.util.Date;

public class MeasObjDistribute extends MeasObj {

    public static MeasObjDistribute fromMeasObj(MeasObj obj){
        MeasObjDistribute tmp = new MeasObjDistribute();
        String str = JSONObject.toJSONString(obj);
        tmp = (MeasObjDistribute)JSONObject.parseObject(str, MeasObjDistribute.class);
        return tmp;
    }

    private Date refreshTime;
    private Double spacePrecision;
    private String dcv;

    public Date getRefreshTime() {
        return refreshTime;
    }

    public void setRefreshTime(Date refreshTime) {
        this.refreshTime = refreshTime;
    }

    public Double getSpacePrecision() {
        return spacePrecision;
    }

    public void setSpacePrecision(Double spacePrecision) {
        this.spacePrecision = spacePrecision;
    }

    public String getDcv() {
        return dcv;
    }

    public void setDcv(String dcv) {
        this.dcv = dcv;
    }

}
