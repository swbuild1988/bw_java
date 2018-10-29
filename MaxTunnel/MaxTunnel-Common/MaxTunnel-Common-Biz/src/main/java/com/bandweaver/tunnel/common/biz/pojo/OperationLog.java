package com.bandweaver.tunnel.common.biz.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * ClassName: OperationLog
 * @Description: 操作日志
 * @author shaosen
 * @date 2018年5月18日
 */
public class OperationLog implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -1542198672321577997L;

	private String id;

    private String moduleType;	//模块类型

    private String reqIp;	//请求发起地址

    private int reqId;	//请求人id
    
    private String reqUser;	//请求人姓名

    private String method;

    private String params;
    
    private String result;//操作结果
    
    private String desc;	//日志描述

    private Date crtTime;
    
    

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getModuleType() {
		return moduleType;
	}

	public void setModuleType(String moduleType) {
		this.moduleType = moduleType;
	}

	public int getReqId() {
		return reqId;
	}

	public void setReqId(int reqId) {
		this.reqId = reqId;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }


    public String getReqIp() {
        return reqIp;
    }

    public void setReqIp(String reqIp) {
        this.reqIp = reqIp == null ? null : reqIp.trim();
    }

    public String getReqUser() {
        return reqUser;
    }

    public void setReqUser(String reqUser) {
        this.reqUser = reqUser == null ? null : reqUser.trim();
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method == null ? null : method.trim();
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params == null ? null : params.trim();
    }

	public Date getCrtTime() {
		return crtTime;
	}

	public void setCrtTime(Date crtTime) {
		this.crtTime = crtTime;
	}

	
	
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "OperationLog [id=" + id + ", moduleType=" + moduleType + ", reqIp=" + reqIp + ", reqId=" + reqId
				+ ", reqUser=" + reqUser + ", method=" + method + ", params=" + params + ", result=" + result
				+ ", desc=" + desc + ", crtTime=" + crtTime + "]";
	}

	
  
}