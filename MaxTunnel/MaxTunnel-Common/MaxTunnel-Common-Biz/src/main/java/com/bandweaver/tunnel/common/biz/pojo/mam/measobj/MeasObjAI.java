package com.bandweaver.tunnel.common.biz.pojo.mam.measobj;

import com.alibaba.fastjson.JSONObject;

import java.text.DecimalFormat;
import java.util.Date;

public class MeasObjAI extends MeasObj {

    private int decimal;

    public static MeasObjAI fromMeasObj(MeasObj obj) {
        MeasObjAI tmp = new MeasObjAI();
        String str = JSONObject.toJSONString(obj);
        tmp = (MeasObjAI) JSONObject.parseObject(str, MeasObjAI.class);
        return tmp;
    }

    private Date refreshTime;
    private Double cv;

    public MeasObjAI() {
        this.decimal = 2;
    }

    public void setDecimal(int decimal) {
        this.decimal = decimal;
    }

    public Double getCv() {
        if (cv == null) return 0.0;

        int num = decimal;
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
