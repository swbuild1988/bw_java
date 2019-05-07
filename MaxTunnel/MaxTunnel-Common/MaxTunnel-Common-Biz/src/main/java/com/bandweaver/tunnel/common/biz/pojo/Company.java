package com.bandweaver.tunnel.common.biz.pojo;

import java.io.Serializable;
import java.util.Date;

public class Company implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 2091227829335125696L;

	private Integer id;

    private String name;

    private Date crtTime;
    
    private String creditNo;
    private String account;
    private Integer bank;
    private String address;
    private String phone;
    private String mail;
    
    private String inspectionNo; // 巡检周期
    private String inspectionTime; // 巡检时间
    private String inspectionInfo; // 巡检信息
    private String imgUrl; // 证书图片

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

	public String getCreditNo() {
		return creditNo;
	}

	public void setCreditNo(String creditNo) {
		this.creditNo = creditNo;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Integer getBank() {
		return bank;
	}

	public void setBank(Integer bank) {
		this.bank = bank;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getInspectionNo() {
		return inspectionNo;
	}

	public void setInspectionNo(String inspectionNo) {
		this.inspectionNo = inspectionNo;
	}

	public String getInspectionTime() {
		return inspectionTime;
	}

	public void setInspectionTime(String inspectionTime) {
		this.inspectionTime = inspectionTime;
	}

	public String getInspectionInfo() {
		return inspectionInfo;
	}

	public void setInspectionInfo(String inspectionInfo) {
		this.inspectionInfo = inspectionInfo;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
}