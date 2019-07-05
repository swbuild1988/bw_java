package com.bandweaver.tunnel.service.oam.customer;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bandweaver.tunnel.common.biz.itf.oam.CustomerService;
import com.bandweaver.tunnel.common.biz.pojo.oam.Customer;
import com.bandweaver.tunnel.common.biz.vo.oam.CustomerVo;
import com.bandweaver.tunnel.dao.oam.CustomerMapper;

@Service
public class CustomerServiceImpl implements CustomerService {

	
	@Autowired
	private CustomerMapper customerMapper;

	@Override
	public void add(Customer customer) {
		customer.setCrtTime(new Date());
		customerMapper.insertSelective(customer);
	}

	@Override
	public List<Customer> getList() {
		return customerMapper.getList();
	}

	@Override
	public List<Customer> getListByCondition(CustomerVo vo) {
		return customerMapper.getListByCondition(vo);
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
	public void deleteBatch(List<Integer> list) {
		customerMapper.deleteBatch(list);
	}
	
}
