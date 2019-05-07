package com.bandweaver.tunnel.common.biz.dto;


import java.util.List;

import com.bandweaver.tunnel.common.biz.constant.BankEnum;
import com.bandweaver.tunnel.common.biz.pojo.Company;
import com.bandweaver.tunnel.common.biz.pojo.oam.Customer;

public class CompanyDto extends Company {
	
	private static final long serialVersionUID = 1L;
	
	private String bankName;
	private List<Customer> customers;

	public String getBankName() {
		BankEnum bank = BankEnum.getEnum(super.getBank());
		return bank == null ? null : bank.getName();
	}

	public List<Customer> getCustomers() {
		return customers;
	}

	@Override
	public String toString() {
		return "CompanyDto [bankName=" + bankName + ", customers=" + customers + "]";
	}
	
}
