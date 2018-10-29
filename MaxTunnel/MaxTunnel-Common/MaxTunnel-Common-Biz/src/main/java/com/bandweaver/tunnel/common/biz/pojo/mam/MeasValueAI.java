package com.bandweaver.tunnel.common.biz.pojo.mam;

import java.io.Serializable;
import java.util.Date;

public class MeasValueAI implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -7311778596672084688L;
	private Date time;
    private Integer objectId;
    private Double CV;

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

    public Double getCV() {
        return CV;
    }

    public void setCV(Double CV) {
        this.CV = CV;
    }

    @Override
    public String toString() {
        return "MeasValueAI{" +
                "time=" + time +
                ", objectId=" + objectId +
                ", CV=" + CV +
                '}';
    }
}
