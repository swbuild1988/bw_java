package com.bandweaver.tunnel.common.biz.dto.omm;

import java.util.Date;

import com.bandweaver.tunnel.common.biz.constant.omm.SpareWhitherEnum;

public class SpareOutDto extends SpareDto{
	
	private Integer staffId;
	private String staffName;
    
    private Integer userId;
    private String userName;
    
    private Date outTime;
    
    private String describe;
    
    private Integer whither;
    private String whitherName;

	public Integer getStaffId() {
		return staffId;
	}

	public void setStaffId(Integer staffId) {
		this.staffId = staffId;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getOutTime() {
		return outTime;
	}

	public void setOutTime(Date outTime) {
		this.outTime = outTime;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public Integer getWhither() {
		return whither;
	}

	public void setWhither(Integer whither) {
		this.whither = whither;
	}

	public String getWhitherName() {
		return SpareWhitherEnum.getEnum(whither).getName();
	}

	public void setWhitherName(String whitherName) {
		this.whitherName = whitherName;
	}

	@Override
	public String toString() {
		return "SpareOutDto [staffId=" + staffId + ", staffName=" + staffName
				+ ", userId=" + userId + ", userName=" + userName 
				+ ", outTime=" + outTime + ", describe=" + describe 
				+ ", whither=" + whither + "]";
	}
    
	
}
