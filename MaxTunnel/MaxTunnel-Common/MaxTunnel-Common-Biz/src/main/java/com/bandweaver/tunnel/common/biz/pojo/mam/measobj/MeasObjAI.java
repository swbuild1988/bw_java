package com.bandweaver.tunnel.common.biz.pojo.mam.measobj;

import com.alibaba.fastjson.JSONObject;

import java.text.DecimalFormat;
import java.util.Date;

public class MeasObjAI extends MeasObj {

    public static MeasObjAI fromMeasObj(MeasObj obj) {
        MeasObjAI tmp = new MeasObjAI();
        String str = JSONObject.toJSONString(obj);
        tmp = (MeasObjAI) JSONObject.parseObject(str, MeasObjAI.class);
        return tmp;
    }

    private Date refreshTime;
    private Double cv;

    public Double getCv() {
        return this.cv;
    }

    public Double getCv(Integer decimal) {
        if (cv == null) return 0.0;
        if (decimal == null) return this.cv;

        int num = decimal.intValue();
        String s = "#.";
        for (int i = 0; i < num; i++) {
            s += "#";
        }
        DecimalFormat df = new DecimalFormat(s);
        return Double.parseDouble(df.format(cv.doubleValue()));
    }

    public void setCv(Double CV) {
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
        return "MeasObjAI [refreshTime=" + refreshTime + ", CV=" + cv + "]";
    }


}
