package com.bandweaver.tunnel.dao.common;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bandweaver.tunnel.common.biz.dto.DepartmentDto;
import com.bandweaver.tunnel.common.biz.pojo.Department;
import com.bandweaver.tunnel.common.biz.vo.DepartmentVo;

public interface DepartmentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Department record);

    int insertSelective(Department record);

    Department selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Department record);

    int updateByPrimaryKey(Department record);

	/**
	 * @Description: 根据id查询dto
	 * @param @param id
	 * @param @return   
	 * @return DepartmentDto  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月26日
	 */
	DepartmentDto getDtoById(Integer id);

	/**
	 * @Description: 条件查询
	 * @param @param vo
	 * @param @return   
	 * @return List<DepartmentDto>  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月26日
	 */
	List<DepartmentDto> getDtoListByCondition(DepartmentVo vo);

	/**
	 * @Description: 根据公司id查询部门列表
	 * @param @param companyId
	 * @param @return   
	 * @return List<DepartmentDto>  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月26日
	 */
	List<DepartmentDto> getDepartmentsByCompanyId(@Param("companyId")Integer companyId);

	/**
	 * @Description: 删除中间表数据
	 * @param @param id   
	 * @return void  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月26日
	 */
	void deleteFromOtherTable(@Param("id")Integer id);

	void deleteBatch(List<Integer> list);
}