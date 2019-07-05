package com.bandweaver.tunnel.common.biz.dto;


import java.util.List;

import com.bandweaver.tunnel.common.biz.pojo.Company;
import com.bandweaver.tunnel.common.biz.pojo.oam.Customer;

public class CompanyDto extends Company {
	
	private static final long serialVersionUID = 1L;
	
	private List<Customer> customers;

	public List<Customer> getCustomers() {
		return customers;
	}

	@Override
	public String toString() {
		return "CompanyDto [customers=" + customers + "]";
	}
	
}
