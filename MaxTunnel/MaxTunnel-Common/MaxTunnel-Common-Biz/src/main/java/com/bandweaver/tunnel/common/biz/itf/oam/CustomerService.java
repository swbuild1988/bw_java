package com.bandweaver.tunnel.common.biz.itf.oam;

import java.util.List;

import com.bandweaver.tunnel.common.biz.pojo.oam.Customer;
import com.bandweaver.tunnel.common.biz.vo.oam.CustomerVo;

public interface CustomerService {

	/**
	 * @Description: 添加客户
	 * @param @param customer   
	 * @return void  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月26日
	 */
	void add(Customer customer);
	
	/**
	 * @Description: 获取所有客户列表
	 * @param @return   
	 * @return List<CustomerDto>  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月26日
	 */
	List<Customer> getList();

	/**
	 * @Description: 条件查询
	 * @param @param vo
	 * @param @return   
	 * @return List<CustomerDto>  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月26日
	 */
	List<Customer> getListByCondition(CustomerVo vo);

	/**
	 * @Description: 修改客户信息
	 * @param @param customer   
	 * @return void  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月26日
	 */
	void update(Customer customer);

	/**
	 * @Description: 删除
	 * @param @param id   
	 * @return void  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月26日
	 */
	void deleteById(Integer id);

	void deleteBatch(List<Integer> list);

}
