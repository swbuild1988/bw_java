package com.bandweaver.tunnel.service.common;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bandweaver.tunnel.common.biz.dto.StaffDto;
import com.bandweaver.tunnel.common.biz.itf.StaffService;
import com.bandweaver.tunnel.common.biz.pojo.Staff;
import com.bandweaver.tunnel.common.biz.pojo.common.User;
import com.bandweaver.tunnel.common.biz.vo.StaffVo;
import com.bandweaver.tunnel.common.platform.constant.Constants;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.PinyinUtil;
import com.bandweaver.tunnel.common.platform.util.PropertiesUtil;
import com.bandweaver.tunnel.common.platform.util.Sha256;
import com.bandweaver.tunnel.dao.common.StaffMapper;
import com.bandweaver.tunnel.dao.common.UserMapper;
import com.github.pagehelper.Constant;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
@Service
public class StaffServiceImpl implements StaffService {

	
	@Autowired
	private StaffMapper staffMapper;
	@Autowired
	private UserMapper userMapper;

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
	@Transactional
	public void add(Staff staff) {
		staff.setCrtTime(new Date());
		staffMapper.insert(staff);
		
		//员工表和账号表为一对一关系，他们的id也相同
		String account = PinyinUtil.ToPinyin(staff.getName());//将员工姓名转换为拼音作为其登陆账号
		//保证账号唯一，如果有姓名相同的员工，账号后加数字区分
		List<Staff> list = staffMapper.getByName(staff.getName());
		LogUtil.info(list);
		if(list != null && list.size() > 1) {
			int suffix = list.size();
			account = account + suffix;
		}
		
		String initPassword = (String) PropertiesUtil.getValue(Constants.INIT_PASSWORD);
		User user = new User();
		user.setId(staff.getId());
		user.setName(account);
		user.setPassword(Sha256.getSHA256StrJava(initPassword));//设置初始密码
		user.setCrtTime(staff.getCrtTime());
		userMapper.insert(user);
		LogUtil.info(user.getName());
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
	@Transactional
	public void deleteBatch(List<Integer> list) {
		staffMapper.deleteBatch(list);
		userMapper.deleteBatch(list);
	}

	@Override
	public PageInfo<StaffDto> dataGrid(StaffVo vo) {
		PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
		List<StaffDto> list = getDtoListByCondition(vo);
		return new PageInfo<>(list);
	}
	
	
}
