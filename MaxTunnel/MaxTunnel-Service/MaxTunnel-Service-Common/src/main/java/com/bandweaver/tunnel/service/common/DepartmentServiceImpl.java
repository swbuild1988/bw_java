package com.bandweaver.tunnel.service.common;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bandweaver.tunnel.common.biz.dto.DepartmentDto;
import com.bandweaver.tunnel.common.biz.itf.DepartmentService;
import com.bandweaver.tunnel.common.biz.pojo.Department;
import com.bandweaver.tunnel.common.biz.vo.DepartmentVo;
import com.bandweaver.tunnel.dao.common.DepartmentMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
@Service
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	private DepartmentMapper departmentMapper;

	@Override
	public DepartmentDto getDtoById(Integer id) {
		return departmentMapper.getDtoById(id);
	}

	@Override
	public List<DepartmentDto> getDtoList() {
		return departmentMapper.getDtoListByCondition(new DepartmentVo());
	}

	@Override
	public List<DepartmentDto> getDtoListByCondition(DepartmentVo vo) {
		return departmentMapper.getDtoListByCondition(vo);
	}

	@Transactional
	@Override
	public void deleteById(Integer id) {
		departmentMapper.deleteByPrimaryKey(id);
		//删除中间表数据
		departmentMapper.deleteFromOtherTable(id);
	}

	@Override
	public void update(Department department) {
		departmentMapper.updateByPrimaryKeySelective(department);
	}

	@Override
	public void add(Department department) {
		department.setCrtTime(new Date());
		departmentMapper.insert(department);
	}

	@Override
	public List<DepartmentDto> getDepartmentsByCompanyId(Integer companyId) {
		return departmentMapper.getDepartmentsByCompanyId(companyId);
	}

	@Override
	public PageInfo<DepartmentDto> dataGrid(DepartmentVo vo) {
		PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
		List<DepartmentDto> list = getDtoListByCondition(vo);
		return new PageInfo<>(list);
	}

	@Override
	public void deleteBatch(List<Integer> list) {
		departmentMapper.deleteBatch(list);
	}
}
