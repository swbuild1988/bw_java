package com.bandweaver.tunnel.service.oam.customer;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bandweaver.tunnel.common.biz.dto.oam.CustomerDto;
import com.bandweaver.tunnel.common.biz.itf.oam.CustomerService;
import com.bandweaver.tunnel.common.biz.pojo.oam.Customer;
import com.bandweaver.tunnel.common.biz.vo.oam.CustomerVo;
import com.bandweaver.tunnel.dao.oam.CustomerMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class CustomerServiceImpl implements CustomerService {

	
	@Autowired
	private CustomerMapper customerMapper;

	@Override
	public void addBatch(List<Customer> list) {
		customerMapper.addBatch(list);
	}

	@Override
	public void add(Customer customer) {
		customer.setCrtTime(new Date());
		customerMapper.insertSelective(customer);
	}

	@Override
	public List<CustomerDto> getList() {
		return customerMapper.getList();
	}

	@Override
	public CustomerDto getDtoById(Integer id) {
		return customerMapper.getDtoById(id);
	}

	@Override
	public List<CustomerDto> getDtoListByCondition(CustomerVo vo) {
		return customerMapper.getDtoListByCondition(vo);
	}

	@Override
	public PageInfo<CustomerDto> dataGrid(CustomerVo vo) {
		PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
		List<CustomerDto> list = customerMapper.getDtoListByCondition(vo);
		return new PageInfo<>(list);
	}

	@Override
	public void update(Customer customer) {
		customerMapper.updateByPrimaryKeySelective(customer);
	}

	@Override
	public void deleteById(Integer id) {
		customerMapper.deleteByPrimaryKey(id);
	}

	@Override
	public CustomerDto checkCustomer(CustomerVo vo) {
		return customerMapper.checkCustomer(vo);
	}

	@Override
	public void deleteBatch(List<Integer> list) {
		customerMapper.deleteBatch(list);
	}
	
	
}
