package com.bandweaver.tunnel.common.biz.pojo.mam.mapping;

import java.io.Serializable;
import java.util.Date;

public class MeasObjMap implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

    private Integer objectId;

    private Integer inputValue;

    private Integer objectId2;

    private Integer outputValue;
    
    private Date crtTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getObjectId() {
        return objectId;
    }

    public void setObjectId(Integer objectId) {
        this.objectId = objectId;
    }

    public Integer getInputValue() {
        return inputValue;
    }

    public void setInputValue(Integer inputValue) {
        this.inputValue = inputValue;
    }

    public Integer getObjectId2() {
        return objectId2;
    }

    public void setObjectId2(Integer objectId2) {
        this.objectId2 = objectId2;
    }

    public Integer getOutputValue() {
        return outputValue;
    }

    public void setOutputValue(Integer outputValue) {
        this.outputValue = outputValue;
    }

	public Date getCrtTime() {
		return crtTime;
	}

	public void setCrtTime(Date crtTime) {
		this.crtTime = crtTime;
	}

	@Override
	public String toString() {
		return "MeasObjMap [id=" + id + ", objectId=" + objectId + ", inputValue=" + inputValue + ", objectId2="
				+ objectId2 + ", outputValue=" + outputValue + ", crtTime=" + crtTime + "]";
	}
    
}