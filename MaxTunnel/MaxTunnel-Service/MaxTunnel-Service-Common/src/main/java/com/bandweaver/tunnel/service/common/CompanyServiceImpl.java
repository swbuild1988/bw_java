package com.bandweaver.tunnel.service.common;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bandweaver.tunnel.common.biz.itf.CompanyService;
import com.bandweaver.tunnel.common.biz.pojo.Company;
import com.bandweaver.tunnel.common.biz.vo.CompanyVo;
import com.bandweaver.tunnel.dao.common.CompanyMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	private CompanyMapper companyMapper;

	@Override
	public List<Company> getList() {
		return companyMapper.getList();
	}

	@Override
	public Company getCompanyById(Integer id) {
		return companyMapper.selectByPrimaryKey(id);
	}

	@Transactional
	@Override
	public void deleteById(Integer id) {
		companyMapper.deleteByPrimaryKey(id);
		//删除中间表数据
		companyMapper.deleteFromOtherTable(id);
	}

	@Override
	public void update(Company company) {
		companyMapper.updateByPrimaryKeySelective(company);
	}

	@Override
	public void add(Company company) {
		company.setCrtTime(new Date());
		companyMapper.insertSelective(company);
	}

	@Override
	public PageInfo<Company> dataGrid(CompanyVo vo) {
		PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
		List<Company> list = getByCondition(vo);
		return new PageInfo<>(list);
	}
	
	public List<Company> getByCondition(CompanyVo vo){
		return companyMapper.getByCondition(vo);
	}

	@Override
	public void deleteBatch(List<Integer> list) {
		companyMapper.deleteBatch(list);
	}
}
 