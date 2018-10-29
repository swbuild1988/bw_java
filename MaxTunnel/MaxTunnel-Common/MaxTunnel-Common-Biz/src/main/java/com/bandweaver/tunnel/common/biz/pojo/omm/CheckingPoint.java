package com.bandweaver.tunnel.common.biz.pojo.omm;

import java.io.Serializable;
import java.util.Date;

/**
 * ClassName: CheckingPoint
 * @Description: 巡检点
 * @author shaosen
 * @date 2018年6月4日
 */
public class CheckingPoint implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4786536730517299550L;
	/**id自增*/
    private Integer id;
    /**名称*/
    private String name;
    /**位置*/
    private String position;
    /**所属管廊id*/
    private Integer tunnelId;
    /**创建日期*/
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position == null ? null : position.trim();
    }

    public Integer getTunnelId() {
        return tunnelId;
    }

    public void setTunnelId(Integer tunnelId) {
        this.tunnelId = tunnelId;
    }

    public Date getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(Date crtTime) {
        this.crtTime = crtTime;
    }
}