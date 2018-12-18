package com.bandweaver.tunnel.common.biz.pojo.mam.measobj;

import java.util.Date;

import com.alibaba.fastjson.JSONObject;

public class MeasObjSI extends MeasObj {

    public static MeasObjSI fromMeasObj(MeasObj obj){
        MeasObjSI tmp = new MeasObjSI();
        String str = JSONObject.toJSONString(obj);
        tmp = (MeasObjSI)JSONObject.parseObject(str, MeasObjSI.class);
        return tmp;
    }
    
    private Date refreshTime;
    private Integer cv;
    
	public Date getRefreshTime() {
		return refreshTime;
	}
	public void setRefreshTime(Date refreshTime) {
		this.refreshTime = refreshTime;
	}
	public Integer getCv() {
		return cv;
	}
	public void setCv(Integer cV) {
		cv = cV;
	}
    
    
}
