package com.bandweaver.tunnel.common.biz.pojo.omm;

import java.io.Serializable;
import java.util.Date;

/**
 * ClassName: EquipmentVender
 * @Description: 设备供应商
 * @author shaosen
 * @date 2018年6月4日
 */
public class EquipmentVender implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -7442664194174919319L;

	private Short id;

    private String name;

    private Date crtTime;

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Date getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(Date crtTime) {
        this.crtTime = crtTime;
    }
}