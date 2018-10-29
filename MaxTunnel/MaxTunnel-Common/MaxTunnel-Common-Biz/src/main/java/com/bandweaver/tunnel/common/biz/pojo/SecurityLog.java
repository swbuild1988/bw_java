package com.bandweaver.tunnel.common.biz.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * ClassName: SecurityLog
 * @Description: 安全日志 记录登录用户角色和权限
 * @author shaosen
 * @date 2018年5月18日
 */
public class SecurityLog implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 5014105642118581525L;

	private String id;

    private String method;

    private String reqUser;

    private String reqRole;

    private String reqPermission;

    private Date crtTime;

    private String startTime;
    
    private String endTime;
    
    
    public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method == null ? null : method.trim();
    }

    public String getReqUser() {
        return reqUser;
    }

    public void setReqUser(String reqUser) {
        this.reqUser = reqUser == null ? null : reqUser.trim();
    }

    public String getReqRole() {
        return reqRole;
    }

    public void setReqRole(String reqRole) {
        this.reqRole = reqRole == null ? null : reqRole.trim();
    }

    public String getReqPermission() {
        return reqPermission;
    }

    public void setReqPermission(String reqPermission) {
        this.reqPermission = reqPermission == null ? null : reqPermission.trim();
    }

    public Date getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(Date crtTime) {
        this.crtTime = crtTime;
    }

	@Override
	public String toString() {
		return "SecurityLog [id=" + id + ", method=" + method + ", reqUser=" + reqUser + ", reqRole=" + reqRole
				+ ", reqPermission=" + reqPermission + ", crtTime=" + crtTime + ", startTime=" + startTime
				+ ", endTime=" + endTime + "]";
	}
    
    
}