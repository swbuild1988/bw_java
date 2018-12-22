package com.bandweaver.tunnel.common.biz.dto.oam;

import java.io.Serializable;
import java.util.Date;

import com.bandweaver.tunnel.common.biz.constant.oam.PayTypeEnum;
import com.bandweaver.tunnel.common.biz.pojo.Company;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * ClassName: Customer
 * @Description: 客户
 * @author shaosen
 * @date 2018年6月26日
 */
public class CustomerDto implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

	@JsonIgnore
    private Integer companyId;
    
    private Company company;

    private String contact;

    private String tel;
    
    private String contact2;
    
    private String tel2;
    
    private String contact3;
    
    private String tel3;

    private Date crtTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }


    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact == null ? null : contact.trim();
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel == null ? null : tel.trim();
    }

    public String getContact2() {
		return contact2;
	}

	public void setContact2(String contact2) {
		this.contact2 = contact2;
	}

	public String getTel2() {
		return tel2;
	}

	public void setTel2(String tel2) {
		this.tel2 = tel2;
	}

	public String getContact3() {
		return contact3;
	}

	public void setContact3(String contact3) {
		this.contact3 = contact3;
	}

	public String getTel3() {
		return tel3;
	}

	public void setTel3(String tel3) {
		this.tel3 = tel3;
	}

	public Date getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(Date crtTime) {
        this.crtTime = crtTime;
    }

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	@Override
	public String toString() {
		return "CustomerDto [id=" + id + ", companyId=" + companyId + ", company=" + company + ", contact=" + contact
				+ ", tel=" + tel + ", contact2=" + contact2 + ", tel2=" + tel2 + ", contact3=" + contact3 + ", tel3="
				+ tel3 + ", crtTime=" + crtTime + "]";
	}

    
    
}