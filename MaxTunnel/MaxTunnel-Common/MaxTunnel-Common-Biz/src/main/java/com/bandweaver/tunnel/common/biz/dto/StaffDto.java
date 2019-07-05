package com.bandweaver.tunnel.common.biz.dto;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.constant.OutsideEnum;
import com.bandweaver.tunnel.common.biz.pojo.Department;
import com.bandweaver.tunnel.common.biz.pojo.Position;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * ClassName: StaffDto
 *
 * @author shaosen
 * @Description: 员工
 * @date 2018年6月26日
 */
public class StaffDto implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    private String account;

    private String sex;

    private String telphone;

    private Date hireDate;

    @JsonIgnore
    private Integer deptId;

    private Department dept;

    @JsonIgnore
    private Integer positionId;

    private Position position;

    private JSONObject acctInfo;

    private Date crtTime;

    //身份证号
    private String identityNO;
    //外来人员
    private Integer outside;
    private String outsideName;

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

    public Department getDept() {
        return dept;
    }

    public void setDept(Department dept) {
        this.dept = dept;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }


    public JSONObject getAcctInfo() {
        return acctInfo;
    }

    public void setAcctInfo(JSONObject acctInfo) {
        this.acctInfo = acctInfo;
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

	public String getOutsideName() {
		if(this.getOutside() == null) return null;
		OutsideEnum e = OutsideEnum.getEnum(this.getOutside());
		return e == null ? null : e.getName();
	}

}