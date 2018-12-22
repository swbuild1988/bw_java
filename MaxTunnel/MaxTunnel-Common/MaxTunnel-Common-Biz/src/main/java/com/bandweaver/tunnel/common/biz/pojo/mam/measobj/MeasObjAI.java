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
    private Double cv;

    public Double getCv() {
        return cv;
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
