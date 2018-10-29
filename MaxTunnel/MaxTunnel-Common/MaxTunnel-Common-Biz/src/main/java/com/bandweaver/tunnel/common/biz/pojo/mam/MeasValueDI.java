package com.bandweaver.tunnel.common.biz.pojo.mam;

import java.io.Serializable;
import java.util.Date;

public class MeasValueDI implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -799260672947924884L;
	private Date time;
    private Integer objectId;
    private boolean CV;

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

    public boolean getCV() {
        return CV;
    }

    public void setCV(boolean CV) {
        this.CV = CV;
    }

    @Override
    public String toString() {
        return "MeasValueDI{" +
                "time=" + time +
                ", objectId=" + objectId +
                ", CV=" + CV +
                '}';
    }
}
