package com.bandweaver.tunnel.common.biz.vo;

import java.io.Serializable;
import java.util.Date;

public class StaffVo extends BaseVo implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

    private String name;

    private Integer deptId;

    private Integer positionId;

    private Integer accountId;
    
    private Date crtTime;
    
    

    public StaffVo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public StaffVo(Integer id, String name, Integer deptId, Integer positionId, Integer accountId, Date crtTime) {
		super();
		this.id = id;
		this.name = name;
		this.deptId = deptId;
		this.positionId = positionId;
		this.accountId = accountId;
		this.crtTime = crtTime;
	}

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

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public Integer getPositionId() {
        return positionId;
    }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
    }

    public Date getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(Date crtTime) {
        this.crtTime = crtTime;
    }

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}
    
}