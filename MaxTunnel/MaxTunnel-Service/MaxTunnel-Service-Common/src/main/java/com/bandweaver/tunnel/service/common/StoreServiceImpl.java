package com.bandweaver.tunnel.service.common;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bandweaver.tunnel.common.biz.dto.AreaDto;
import com.bandweaver.tunnel.common.biz.dto.StoreDto;
import com.bandweaver.tunnel.common.biz.itf.StoreService;
import com.bandweaver.tunnel.common.biz.pojo.Area;
import com.bandweaver.tunnel.common.biz.pojo.Section;
import com.bandweaver.tunnel.common.biz.pojo.Store;
import com.bandweaver.tunnel.common.biz.vo.StoreVo;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.dao.common.AreaMapper;
import com.bandweaver.tunnel.dao.common.SectionMapper;
import com.bandweaver.tunnel.dao.common.StoreMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class StoreServiceImpl implements StoreService {

	@Autowired
	private StoreMapper storeMapper;
	@Autowired
	private SectionMapper sectionMapper;
	@Autowired
	private AreaMapper areaMapper;

	@Override
	public void addBatch(List<Store> list) {
		storeMapper.addBatch(list);
	}

	@Override
	public List<Store> getList() {
		return storeMapper.getList();
	}

	@Override
	public List<StoreDto> getStoresByCondition(StoreVo vo) {
		return storeMapper.getStoresByCondition(vo);
	}

	@Override
	public List<StoreDto> getStoresByTunnelId(Integer id) {
		StoreVo vo = new StoreVo();
		vo.setTunnelId(id);
		return storeMapper.getStoresByCondition(vo);
	}

	@Override
	public StoreDto getStoreById(Integer id) {
		StoreVo vo = new StoreVo();
		vo.setId(id);
		List<StoreDto> list = storeMapper.getStoresByCondition(vo);
		return list.isEmpty() ? null : list.get(0);
	}

	@Override
	@Transactional
	public void add(Store store) {
		LogUtil.debug("insert befor:" + store);
		store.setCrtTime(new Date());
		storeMapper.insert(store);
		LogUtil.debug("insert after:" + store);
		
		String store_name = store.getName();
		List<Area> area_list = areaMapper.getAreasByTunnelId(store.getTunnelId());
		if(area_list == null) return;
		for (Area area : area_list) {
			String section_name = area.getName() + "-" + store_name ;
			//添加区段信息
			Section section = new Section();
			section.setName(section_name);
			section.setTunnelId(store.getTunnelId());
			section.setStoreId(store.getId());
			section.setAreaId(area.getId());
			section.setCrtTime(new Date());
			LogUtil.debug("create section:" + section );
			sectionMapper.insertSelective(section);
		}
		
	}

	@Override
	public PageInfo<StoreDto> dataGrid(StoreVo vo) {
		PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
		List<StoreDto> list = getStoresByCondition(vo);
		return new PageInfo<>(list);
	}

	@Override
	public void update(Store store) {
		storeMapper.updateByPrimaryKeySelective(store);
	}

	@Override
	public void delete(Integer id) {
		storeMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void deleteBatch(List<Integer> list) {
		storeMapper.deleteBatch(list);
	}

	@Override
	public Store getByName(String name) {
		return storeMapper.getByName(name);
	}

	@Override
	public Store getStoreByTunnelAndSn(Integer tunnelId, String sn) {
		return storeMapper.getStoreByTunnelAndSN(tunnelId, sn);
	}
}
