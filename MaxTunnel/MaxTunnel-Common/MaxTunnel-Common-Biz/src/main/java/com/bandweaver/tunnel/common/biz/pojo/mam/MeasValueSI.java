package com.bandweaver.tunnel.common.biz.pojo.mam;

import java.io.Serializable;
import java.util.Date;

public class MeasValueSI implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 6043719715178047401L;
	private Date time;
    private Integer objectId;
    private Integer CV;

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

    public Integer getCV() {
        return CV;
    }

    public void setCV(Integer CV) {
        this.CV = CV;
    }

    @Override
    public String toString() {
        return "MeasValueSI{" +
                "time=" + time +
                ", objectId=" + objectId +
                ", CV=" + CV +
                '}';
    }
}
