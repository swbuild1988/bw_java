package com.bandweaver.tunnel.service.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bandweaver.tunnel.common.biz.dto.CompanyDto;
import com.bandweaver.tunnel.common.biz.itf.CompanyService;
import com.bandweaver.tunnel.common.biz.pojo.Company;
import com.bandweaver.tunnel.common.biz.pojo.oam.Customer;
import com.bandweaver.tunnel.common.biz.vo.CompanyVo;
import com.bandweaver.tunnel.common.biz.vo.oam.CustomerVo;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.dao.common.CompanyMapper;
import com.bandweaver.tunnel.dao.oam.CustomerMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	private CompanyMapper companyMapper;
	@Autowired
	private CustomerMapper customerMapper;

	@Override
	public List<Company> getList() {
		return companyMapper.getList();
	}

	@Override
	public CompanyDto getCompanyById(Integer id) {
		return companyMapper.getDtoById(id);
	}

	@Transactional
	@Override
	public void deleteById(Integer id) {
		CustomerVo vo = new CustomerVo();
		vo.setCompanyId(id);
		List<Customer> customers = customerMapper.getListByCondition(vo);
		List<Integer> list = new ArrayList<>();
		for(Customer c : customers) {
			list.add(c.getId());
		}
		// 删除客户表中数据
		if(list != null && list.size() > 0)
			customerMapper.deleteBatch(list);
		// 删除企业信息
		companyMapper.deleteByPrimaryKey(id);
		// 删除中间表数据
		companyMapper.deleteFromOtherTable(id);
	}

	@Override
	public void update(Company company, List<Customer> list) {
		companyMapper.updateByPrimaryKeySelective(company);
		for(Customer c : list) {
			customerMapper.updateByPrimaryKeySelective(c);
		}
	}

	@Override
	@Transactional
	public void add(Company company, List<Customer> list) {
		company.setCrtTime(new Date());
		companyMapper.insertSelective(company);
		for(Customer c : list) {
			c.setCrtTime(new Date());
			c.setCompanyId(company.getId());
			customerMapper.insertSelective(c);
		}
	}

	@Override
	public PageInfo<Company> dataGrid(CompanyVo vo) {
		PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
		List<Company> list = companyMapper.getByCondition(vo);
		return new PageInfo<>(list);
	}
	
	public List<Company> getByCondition(CompanyVo vo){
		return companyMapper.getByCondition(vo);
	}

}
 