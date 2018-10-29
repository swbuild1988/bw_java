package com.bandweaver.tunnel.common.biz.pojo.mam;

import java.awt.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

public class MeasValueDistribute implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -1319784159140830404L;
	private Date time;
    private Integer objectId;
    private Double spacePrecision;
    private String dcv;

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getObjectId() {
        return objectId;
    }

    public void setObjectId(Integer objectId) {
        this.objectId = objectId;
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

    @Override
	public String toString() {
		return "MeasValueDistribute [time=" + time + ", objectId=" + objectId + ", spacePrecision=" + spacePrecision
				+ ", dcv=" + dcv + "]";
	}
}
