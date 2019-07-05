package com.bandweaver.tunnel.common.biz.itf;

import java.util.List;

import com.bandweaver.tunnel.common.biz.dto.CompanyDto;
import com.bandweaver.tunnel.common.biz.pojo.Company;
import com.bandweaver.tunnel.common.biz.pojo.oam.Customer;
import com.bandweaver.tunnel.common.biz.vo.CompanyVo;
import com.github.pagehelper.PageInfo;

public interface CompanyService {

	List<Company> getList();

	CompanyDto getCompanyById(Integer id);

	void deleteById(Integer id);

	void update(Company company, List<Customer> list);

	void add(Company company, List<Customer> list);

	PageInfo<Company> dataGrid(CompanyVo vo);

}
