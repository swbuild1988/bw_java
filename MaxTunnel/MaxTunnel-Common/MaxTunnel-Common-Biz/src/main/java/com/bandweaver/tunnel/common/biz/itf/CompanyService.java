package com.bandweaver.tunnel.common.biz.itf;

import java.util.List;

import com.bandweaver.tunnel.common.biz.pojo.Company;
import com.bandweaver.tunnel.common.biz.vo.CompanyVo;
import com.github.pagehelper.PageInfo;

public interface CompanyService {

	List<Company> getList();

	Company getCompanyById(Integer id);

	void deleteById(Integer id);

	void update(Company company);

	void add(Company company);

	PageInfo<Company> dataGrid(CompanyVo vo);

	void deleteBatch(List<Integer> list);

}
