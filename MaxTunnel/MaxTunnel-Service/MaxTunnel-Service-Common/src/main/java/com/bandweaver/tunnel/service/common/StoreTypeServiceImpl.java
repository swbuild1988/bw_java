package com.bandweaver.tunnel.service.common;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bandweaver.tunnel.common.biz.itf.StoreTypeService;
import com.bandweaver.tunnel.common.biz.pojo.StoreType;
import com.bandweaver.tunnel.common.biz.vo.StoreTypeVo;
import com.bandweaver.tunnel.dao.common.StoreTypeMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
@Service
public class StoreTypeServiceImpl implements StoreTypeService {

	@Autowired
	private StoreTypeMapper storeTypeMapper;

	@Override
	public int insert(StoreType storeType) {
		storeType.setCrtTime(new Date());
		return storeTypeMapper.insertSelective(storeType);
	}

	@Override
	public List<StoreType> getList() {
		return storeTypeMapper.getList();
	}

	@Override
	public StoreType getById(Integer id) {
		return storeTypeMapper.selectByPrimaryKey(id);
	}

	@Override
	public void update(StoreType storeType) {
		storeTypeMapper.updateByPrimaryKeySelective(storeType);
	}

	@Override
	public void delete(Integer id) {
		storeTypeMapper.deleteByPrimaryKey(id);
		
	}

	@Override
	public void deleteBatch(List<Integer> list) {
		storeTypeMapper.deleteBatch(list);
		
	}

	@Override
	public PageInfo<StoreType> dataGrid(StoreTypeVo vo) {
		PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
		List<StoreType> list = getBycondition(vo);
		return new PageInfo<>(list);
	}

	private List<StoreType> getBycondition(StoreTypeVo vo) {
		return storeTypeMapper.getBycondition(vo);
	}

	@Override
	public StoreType getByName(String name) {
		return storeTypeMapper.getByName(name);
	}

	@Override
	public StoreType getBySN(String sn) {
		return storeTypeMapper.getBySN(sn);
	}
}
