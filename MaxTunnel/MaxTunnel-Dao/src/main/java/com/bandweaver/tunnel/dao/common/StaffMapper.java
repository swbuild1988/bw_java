package com.bandweaver.tunnel.dao.common;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bandweaver.tunnel.common.biz.dto.StaffDto;
import com.bandweaver.tunnel.common.biz.pojo.Staff;
import com.bandweaver.tunnel.common.biz.vo.StaffVo;

public interface StaffMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Staff record);

    int insertSelective(Staff record);

    Staff selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Staff record);

    int updateByPrimaryKey(Staff record);

	/**
	 * @Description: 根据id查询dto
	 * @param @param id
	 * @param @return   
	 * @return StaffDto  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月26日
	 */
	StaffDto getDtoById(@Param("id")Integer id);

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
	 * @Description: 根据公司id和部门id查询所有员工
	 * @param @param companyId
	 * @param @param deptId
	 * @param @return   
	 * @return List<StaffDto>  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月26日
	 */
	List<StaffDto> getStaffsByCompanyIdAndDeptId(@Param("companyId")Integer companyId, @Param("deptId")Integer deptId);

	void deleteBatch(List<Integer> list);
}