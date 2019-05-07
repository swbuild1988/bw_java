package com.bandweaver.tunnel.dao.oam;

import java.util.List;


import com.bandweaver.tunnel.common.biz.pojo.oam.Customer;
import com.bandweaver.tunnel.common.biz.vo.oam.CustomerVo;

public interface CustomerMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(Customer record);

    Customer selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Customer record);

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
	 * @Description: 条件查询 dto列表
	 * @param @param vo
	 * @param @return   
	 * @return List<CustomerDto>  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月26日
	 */
	List<Customer> getListByCondition(CustomerVo vo);

	void deleteBatch(List<Integer> list);
}