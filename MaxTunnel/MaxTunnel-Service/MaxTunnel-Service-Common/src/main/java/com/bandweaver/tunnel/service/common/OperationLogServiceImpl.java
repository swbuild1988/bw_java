package com.bandweaver.tunnel.service.common;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bandweaver.tunnel.common.biz.itf.OperationLogService;
import com.bandweaver.tunnel.common.biz.pojo.OperationLog;
import com.bandweaver.tunnel.common.biz.vo.OperationLogVo;
import com.bandweaver.tunnel.dao.common.OperationLogMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class OperationLogServiceImpl implements OperationLogService {

	@Autowired
	private OperationLogMapper operationLogMapper;

	@Override
	public int save(OperationLog optlog) {
		
		return operationLogMapper.insert(optlog);
	}


	@Override
	public void updateById(String result,String id) {
		operationLogMapper.updateById(result,id);
	}

	@Override
	public PageInfo<OperationLog> dataGrid(OperationLogVo vo) {
		PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
		List<OperationLog> list = operationLogMapper.dataGrid(vo);
		return list.isEmpty() ? null : new PageInfo<>(list);
	}


	@Override
	public void delete(String id) {
		operationLogMapper.deleteByPrimaryKey(id);
	}
	
	
	
}
