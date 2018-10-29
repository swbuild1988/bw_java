package com.bandweaver.tunnel.common.biz.pojo.omm;

import java.io.Serializable;
import java.util.Date;

/**
 * ClassName: EquipmentModel
 * @Description: 设备型号
 * @author shaosen
 * @date 2018年6月4日
 */
public class EquipmentModel implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -2200012969471031912L;

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