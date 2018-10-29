package com.bandweaver.tunnel.dao.oam;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bandweaver.tunnel.common.biz.dto.oam.CustomerDto;
import com.bandweaver.tunnel.common.biz.pojo.oam.Customer;
import com.bandweaver.tunnel.common.biz.vo.oam.CustomerVo;

public interface CustomerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Customer record);

    int insertSelective(Customer record);

    Customer selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Customer record);

    int updateByPrimaryKey(Customer record);

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
	CustomerDto getDtoById(@Param("id")Integer id);

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