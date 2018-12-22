package com.bandweaver.tunnel.service.common;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bandweaver.tunnel.common.biz.dto.AreaDto;
import com.bandweaver.tunnel.common.biz.itf.AreaService;
import com.bandweaver.tunnel.common.biz.pojo.Area;
import com.bandweaver.tunnel.common.biz.pojo.Section;
import com.bandweaver.tunnel.common.biz.pojo.Store;
import com.bandweaver.tunnel.common.biz.vo.AreaVo;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.dao.common.AreaMapper;
import com.bandweaver.tunnel.dao.common.SectionMapper;
import com.bandweaver.tunnel.dao.common.StoreMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
@Service
public class AreaServiceImpl implements AreaService {

	@Autowired
	private AreaMapper areaMapper;
	@Autowired
	private StoreMapper storeMapper;
	@Autowired
	private SectionMapper sectionMapper;

	@Override
	@Transactional
	public void addArea(Area area) {
		LogUtil.debug("insert before:" + area);
		area.setCrtTime(new Date());
		areaMapper.insertSelective(area);
		LogUtil.debug("insert after:" + area);
		
		String area_name = area.getName();
		List<Store> store_list = storeMapper.getStoresByTunnelId(area.getTunnelId());
		if(store_list == null) return;
		for (Store store : store_list) {
			String section_name = area_name + "-" + store.getName();
			
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
	public void addAreaBatch(List<Area> list) {
		areaMapper.addAreaBatch(list);
	}

	@Override
	public List<AreaDto> getList() {
		return areaMapper.getList();
	}

	@Override
	public List<AreaDto> getAreasByCondition(AreaVo areaVo) {
		return areaMapper.getAreasByCondition(areaVo);
	}

	@Override
	public AreaDto getAreasById(Integer id) {
		return areaMapper.getAreasById(id);
	}

	@Override
	public List<AreaDto> getAreasByTunnelId(Integer id) {
		AreaVo vo = new AreaVo();
		vo.setTunnelId(id);
		return areaMapper.getAreasByCondition(vo);
	}

	@Override
	public PageInfo<AreaDto> dataGrid(AreaVo vo) {
		PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
		List<AreaDto> list = getAreasByCondition(vo);
		return new PageInfo<>(list);
	}

	@Override
	public void update(Area area) {
		areaMapper.updateByPrimaryKeySelective(area);
	}

	@Override
	public void delete(Integer id) {
		areaMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void deleteBatch(List<Integer> list) {
		areaMapper.deleteBatch(list);
	}

	@Override
	public Area getByName(String name) {
		return areaMapper.getByName(name);
	}

	@Override
	public Area getByTunnelAndSN(Integer tunnelId, String sn) {
		return areaMapper.getAreaByTunnelAndSN(tunnelId, sn);
	}
}
