package com.bandweaver.tunnel.service.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bandweaver.tunnel.common.biz.constant.ProcessStatusEnum;
import com.bandweaver.tunnel.common.biz.dto.MyTaskDto;
import com.bandweaver.tunnel.common.biz.itf.ActivitiService;
import com.bandweaver.tunnel.common.biz.itf.MyTaskService;
import com.bandweaver.tunnel.common.biz.vo.MyTaskVo;
import com.bandweaver.tunnel.dao.common.MyTaskMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
@Service
public class MyTaskServiceImpl implements MyTaskService {

	@Autowired
	private MyTaskMapper myTaskMapper;
	@Autowired
	private ActivitiService activitiService;

	@Override
	public PageInfo<MyTaskDto> taskDataGrid(MyTaskVo vo) {
		
		//获取当前登录用户
		vo.setApproverId(activitiService.getStaffId());
		//只获取未处理的数据
		vo.setStatus(ProcessStatusEnum.WAIT.getValue());
		
		PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
		List<MyTaskDto> list = getDtoListByCondition(vo);
		return new PageInfo<>(list);
	}

	@Override
	public List<MyTaskDto> getDtoListByCondition(MyTaskVo vo) {
		return myTaskMapper.getDtoListByCondition(vo);
	}

	@Override
	public PageInfo<MyTaskDto> applyDataGrid(MyTaskVo vo) {
		//获取当前登录用户
		vo.setRequestStaffId(activitiService.getStaffId());
		
		PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
		List<MyTaskDto> list = getDtoListByCondition(vo);
		return new PageInfo<>(list);
	}

	@Override
	public int getMsgCount(Integer staffId,Integer status) {
		return myTaskMapper.getMsgCount(staffId,status);
	}
}
