package com.bandweaver.tunnel.common.biz.pojo;

import java.io.Serializable;
import java.util.List;

import com.bandweaver.tunnel.common.biz.pojo.oam.Customer;

public class Company_Customer implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Company company;

    private List<Customer> list;

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public List<Customer> getList() {
		return list;
	}

	public void setList(List<Customer> list) {
		this.list = list;
	}
    
}