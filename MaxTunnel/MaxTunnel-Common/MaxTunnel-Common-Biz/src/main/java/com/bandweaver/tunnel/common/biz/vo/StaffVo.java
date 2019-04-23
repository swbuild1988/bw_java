package com.bandweaver.tunnel.common.biz.vo;

import java.io.Serializable;
import java.util.Date;

public class StaffVo extends BaseVo {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String name;
	private String account;
	private String sex;
	private String telphone;
	private Date hireDate;
<<<<<<< HEAD

	private Integer deptId;

	private Integer positionId;

	private Date crtTime;
=======

	private Integer deptId;

	private Integer positionId;

	private Date crtTime;

	private String identityNO;

	private Integer outside;
>>>>>>> bf512039ff8442b3d1853c03de35f9d29734072e

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
<<<<<<< HEAD
	}

	public Date getCrtTime() {
		return crtTime;
	}

	public void setCrtTime(Date crtTime) {
		this.crtTime = crtTime;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	public Date getHireDate() {
		return hireDate;
	}

	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}
	
	
=======
	}

	public Date getCrtTime() {
		return crtTime;
	}

	public void setCrtTime(Date crtTime) {
		this.crtTime = crtTime;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	public Date getHireDate() {
		return hireDate;
	}

	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}

	public String getIdentityNO() {
		return identityNO;
	}

	public void setIdentityNO(String identityNO) {
		this.identityNO = identityNO;
	}

	public Integer getOutside() {
		return outside;
	}

	public void setOutside(Integer outside) {
		this.outside = outside;
	}
>>>>>>> bf512039ff8442b3d1853c03de35f9d29734072e

}