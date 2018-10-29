package com.bandweaver.tunnel.common.biz.itf;

import java.util.List;

import com.bandweaver.tunnel.common.biz.dto.DepartmentDto;
import com.bandweaver.tunnel.common.biz.pojo.Department;
import com.bandweaver.tunnel.common.biz.vo.DepartmentVo;
import com.github.pagehelper.PageInfo;

public interface DepartmentService {

	/**
	 * @Description: 根据id查询
	 * @param @param id
	 * @param @return   
	 * @return DepartmentDto  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月26日
	 */
	DepartmentDto getDtoById(Integer id);

	/**
	 * @Description: 查询所有
	 * @param @return   
	 * @return List<DepartmentDto>  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月26日
	 */
	List<DepartmentDto> getDtoList();

	/**
	 * @Description:条件查询
	 * @param @param vo
	 * @param @return   
	 * @return List<DepartmentDto>  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月26日
	 */
	List<DepartmentDto> getDtoListByCondition(DepartmentVo vo);

	/**
	 * @Description: 根据id删除
	 * @param @param id   
	 * @return void  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月26日
	 */
	void deleteById(Integer id);

	/**
	 * @Description: 修改
	 * @param @param department   
	 * @return void  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月26日
	 */
	void update(Department department);

	/**
	 * @Description: 添加
	 * @param @param department   
	 * @return void  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月26日
	 */
	void add(Department department);

	/**
	 * @Description: 根据公司id查询部门列表
	 * @param @param companyId
	 * @param @return   
	 * @return List<DepartmentDto>  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月26日
	 */
	List<DepartmentDto> getDepartmentsByCompanyId(Integer companyId);

	PageInfo<DepartmentDto> dataGrid(DepartmentVo vo);

	void deleteBatch(List<Integer> list);
}
