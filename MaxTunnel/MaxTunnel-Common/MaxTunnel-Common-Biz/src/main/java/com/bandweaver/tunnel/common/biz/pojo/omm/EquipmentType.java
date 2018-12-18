package com.bandweaver.tunnel.common.biz.pojo.omm;

import java.io.Serializable;

/**
 * 设备类型模块
 * @author ya.liu
 * @Date 2018年11月27日
 */
public class EquipmentType implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -7442664194174919319L;

	private Integer id;

    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}