package com.bandweaver.tunnel.service.common;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bandweaver.tunnel.common.biz.dto.PositionDto;
import com.bandweaver.tunnel.common.biz.itf.PositionService;
import com.bandweaver.tunnel.common.biz.pojo.Position;
import com.bandweaver.tunnel.common.biz.vo.PositionVo;
import com.bandweaver.tunnel.dao.common.PositionMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
@Service
public class PositionServiceImpl implements PositionService {

	@Autowired
	private PositionMapper positionMapper;

	@Override
	public List<Position> getList() {
		return positionMapper.getList();
	}

	@Override
	public Position getPositionById(Integer id) {
		return positionMapper.selectByPrimaryKey(id);
	}

	@Override
	public void add(Position pojo) {
		pojo.setCrtTime(new Date());
		positionMapper.insert(pojo);
	}

	@Override
	public void delete(Integer id) {
		positionMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void update(Position position) {
		positionMapper.updateByPrimaryKeySelective(position);
		
	}

	@Override
	public PageInfo<PositionDto> dataGrid(PositionVo vo) {
		PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
		List<PositionDto> list = getDtoListByCondition(vo);
		return new PageInfo<>(list);
	}

	
	private List<PositionDto> getDtoListByCondition(PositionVo vo) {
		return positionMapper.getDtoListByCondition(vo);
	}

	@Override
	public void deleteBatch(List<Integer> list) {
		positionMapper.deleteBatch(list);
	}
	
	
}
