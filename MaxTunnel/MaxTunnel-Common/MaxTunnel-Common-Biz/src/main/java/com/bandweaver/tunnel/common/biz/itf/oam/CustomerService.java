package com.bandweaver.tunnel.common.biz.itf.oam;

import java.util.List;

import com.bandweaver.tunnel.common.biz.dto.oam.CustomerDto;
import com.bandweaver.tunnel.common.biz.pojo.oam.Customer;
import com.bandweaver.tunnel.common.biz.vo.oam.CustomerVo;
import com.github.pagehelper.PageInfo;

public interface CustomerService {

	/**
	 * @Description: 批量添加
	 * @param @param list   
	 * @return void  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月26日
	 */
	void addBatch(List<Customer> list);

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
	List<CustomerDto> getList();

	/**
	 * @Description: 通过id查询dto
	 * @param @param id
	 * @param @return   
	 * @return CustomerDto  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月26日
	 */
	CustomerDto getDtoById(Integer id);
	
	/**
	 * @Description: 条件查询 dto列表
	 * @param @param vo
	 * @param @return   
	 * @return List<CustomerDto>  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月26日
	 */
	List<CustomerDto> getDtoListByCondition(CustomerVo vo);

	/**
	 * @Description: 分页
	 * @param @param vo
	 * @param @return   
	 * @return PageInfo<CustomerDto>  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月26日
	 */
	PageInfo<CustomerDto> dataGrid(CustomerVo vo);

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

	/**
	 * @Description: 检查客户是否已经添加
	 * @param @param vo
	 * @param @return   
	 * @return CustomerDto  
	 * @throws
	 * @author shaosen
	 * @date 2018年7月2日
	 */
	CustomerDto checkCustomer(CustomerVo vo);

	void deleteBatch(List<Integer> list);

}
