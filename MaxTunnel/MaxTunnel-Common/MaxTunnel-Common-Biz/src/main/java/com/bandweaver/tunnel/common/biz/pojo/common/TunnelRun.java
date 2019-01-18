package com.bandweaver.tunnel.common.biz.pojo.common;

import java.io.Serializable;

public class TunnelRun implements Serializable{
	private static final long serialVersionUID = 1L;

	private Integer id;

    private Integer runDays;

    private Integer safeDyas;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRunDays() {
        return runDays;
    }

    public void setRunDays(Integer runDays) {
        this.runDays = runDays;
    }

    public Integer getSafeDyas() {
        return safeDyas;
    }

    public void setSafeDyas(Integer safeDyas) {
        this.safeDyas = safeDyas;
    }

	@Override
	public String toString() {
		return "TunnelRun [id=" + id + ", runDays=" + runDays + ", safeDyas=" + safeDyas + "]";
	}
    
    
}