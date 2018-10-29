package com.bandweaver.tunnel.common.biz.pojo.mam.measobj;

import java.util.Date;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.constant.mam.DataType;
import com.bandweaver.tunnel.common.biz.constant.mam.ObjectType;

public class MeasObjDI extends MeasObj {

    public static MeasObjDI fromMeasObj(MeasObj obj){
        MeasObjDI tmp = new MeasObjDI();
        String str = JSONObject.toJSONString(obj);
        tmp = (MeasObjDI)JSONObject.parseObject(str, MeasObjDI.class);
        return tmp;
    }
    
    private String datatypeName;
    private String objtypeName;
    private Date refreshTime;
    private boolean CV;
    
    
	public Date getRefreshTime() {
		return refreshTime;
	}
	public void setRefreshTime(Date refreshTime) {
		this.refreshTime = refreshTime;
	}
	
	public boolean getCV() {
		return CV;
	}
	public void setCV(boolean CV) {
		this.CV = CV;
	}
	public String getDatatypeName() {
		return DataType.getEnum(this.getDatatypeId()).getName();
	}
	public void setDatatypeName(String datatypeName) {
		this.datatypeName = datatypeName;
	}
	public String getObjtypeName() {
		return ObjectType.getEnum(this.getObjtypeId()).getName();
	}
	public void setObjtypeName(String objtypeName) {
		this.objtypeName = objtypeName;
	}
	
	
    
    
}
