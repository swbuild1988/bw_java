package com.bandweaver.tunnel.service.common;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bandweaver.tunnel.common.biz.dto.StaffDto;
import com.bandweaver.tunnel.common.biz.itf.StaffService;
import com.bandweaver.tunnel.common.biz.pojo.Staff;
import com.bandweaver.tunnel.common.biz.vo.StaffVo;
import com.bandweaver.tunnel.dao.common.StaffMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
@Service
public class StaffServiceImpl implements StaffService {

	
	@Autowired
	private StaffMapper staffMapper;

	@Override
	public StaffDto getDtoById(Integer id) {
		return staffMapper.getDtoById(id);
	}

	@Override
	public List<StaffDto> getDtoListByCondition(StaffVo vo) {
		return staffMapper.getDtoListByCondition(vo);
	}

	@Override
	public void deleteById(Integer id) {
		staffMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void update(Staff staff) {
		staffMapper.updateByPrimaryKeySelective(staff);
	}

	@Override
	public void add(Staff staff) {
		staff.setCrtTime(new Date());
		staffMapper.insert(staff);
	}

	@Override
	public List<StaffDto> getDtoList() {
		return getDtoListByCondition(new StaffVo());
	}

	@Override
	public List<StaffDto> getStaffsByCompanyIdAndDeptId(Integer companyId, Integer deptId) {
		return staffMapper.getStaffsByCompanyIdAndDeptId(companyId,deptId);
	}

	@Override
	public void deleteBatch(List<Integer> list) {
		staffMapper.deleteBatch(list);
	}

	@Override
	public PageInfo<StaffDto> dataGrid(StaffVo vo) {
		PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
		List<StaffDto> list = getDtoListByCondition(vo);
		return new PageInfo<>(list);
	}
	
	
}
