package com.bandweaver.tunnel.common.biz.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * ClassName: StoreType
 * @Description: 管廊仓类型
 * @author shaosen
 * @date 2018年6月14日
 */
public class StoreType implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1850967355585687418L;

	private Integer id;

    private String name;

    private Date crtTime;

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
        this.name = name == null ? null : name.trim();
    }

    public Date getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(Date crtTime) {
        this.crtTime = crtTime;
    }
}