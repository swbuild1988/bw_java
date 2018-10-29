package com.bandweaver.tunnel.common.biz.vo.oam;

import java.io.Serializable;
import java.util.Date;

import com.bandweaver.tunnel.common.biz.vo.BaseVo;

/**
 * ClassName: Customer
 * @Description: 客户
 * @author shaosen
 * @date 2018年6月26日
 */
public class CustomerVo extends BaseVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

    private Integer companyId;

    private String contact;

    private String tel;

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

    public Date getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(Date crtTime) {
        this.crtTime = crtTime;
    }
    
}