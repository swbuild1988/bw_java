package com.bandweaver.tunnel.common.biz.pojo.em;

import java.io.Serializable;
import java.util.Date;

public class ObjectBind implements Serializable {
	private static final long serialVersionUID = 2177986868116748238L;

	private Integer id;

    private Integer objectId;

    private Integer bindId;

    private Integer type;

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

    public Integer getBindId() {
        return bindId;
    }

    public void setBindId(Integer bindId) {
        this.bindId = bindId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(Date crtTime) {
        this.crtTime = crtTime;
    }

	@Override
	public String toString() {
		return "ObjectBind [id=" + id + ", objectId=" + objectId + ", bindId=" + bindId + ", type=" + type
				+ ", crtTime=" + crtTime + "]";
	}
    
    
}