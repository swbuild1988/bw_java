package com.bandweaver.tunnel.common.biz.itf;

import java.util.List;

import com.bandweaver.tunnel.common.biz.dto.StaffDto;
import com.bandweaver.tunnel.common.biz.pojo.Staff;
import com.bandweaver.tunnel.common.biz.vo.StaffVo;
import com.github.pagehelper.PageInfo;

public interface StaffService {

	/**
	 * @Description: 根据id查询
	 * @param @param id
	 * @param @return   
	 * @return StaffDto  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月26日
	 */
	StaffDto getDtoById(Integer id);
	
	/**
	 * @Description: 条件查询
	 * @param @param vo
	 * @param @return   
	 * @return List<StaffDto>  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月26日
	 */
	List<StaffDto> getDtoListByCondition(StaffVo vo);

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
	 * @Description: 修改员工信息
	 * @param @param staff   
	 * @return void  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月26日
	 */
	void update(Staff staff);

	/**
	 * @Description: 添加
	 * @param @param staff   
	 * @return void  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月26日
	 */
	void add(Staff staff);

	/**
	 * @Description: 所有dto列表
	 * @param @return   
	 * @return List<StaffDto>  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月26日
	 */
	List<StaffDto> getDtoList();

	/**
	 * @Description: 根据公司id和部门id查询所有员工
	 * @param @param companyId
	 * @param @param deptId
	 * @param @return   
	 * @return List<StaffDto>  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月26日
	 */
	List<StaffDto> getStaffsByCompanyIdAndDeptId(Integer companyId, Integer deptId);

	void deleteBatch(List<Integer> list);

	PageInfo<StaffDto> dataGrid(StaffVo vo);

}
