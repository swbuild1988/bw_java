package com.bandweaver.tunnel.common.biz.vo.omm;


public class SpareOutVo extends SpareVo{
	
	/** 借用人id */
    private Integer staffId;
    /** 操作人id */
    private Integer userId;
    /** 备品去向 */
    private Integer whither;
    
	public Integer getStaffId() {
		return staffId;
	}
	public void setStaffId(Integer staffId) {
		this.staffId = staffId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getWhither() {
		return whither;
	}
	public void setWhither(Integer whither) {
		this.whither = whither;
	}
	@Override
	public String toString() {
		return "SpareOutVo [staffId=" + staffId + ", userId=" + userId 
				+ ", whither=" + whither+ "]";
	}
    
}
