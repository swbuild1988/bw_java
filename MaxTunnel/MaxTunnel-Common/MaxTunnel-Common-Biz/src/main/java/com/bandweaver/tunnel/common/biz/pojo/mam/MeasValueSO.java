package com.bandweaver.tunnel.common.biz.pojo.mam;

import java.io.Serializable;
import java.util.Date;

public class MeasValueSO implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 4465292270005739448L;
	private Date time;
    private int objectId;
    private String cv;

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getObjectId() {
        return objectId;
    }

    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }

    public String getCv() {
        return cv;
    }

    public void setCv(String CV) {
        this.cv = CV;
    }

    @Override
	public String toString() {
		return "MeasValueSO [time=" + time + ", objectId=" + objectId + ", cv=" + cv + "]";
	}
}
