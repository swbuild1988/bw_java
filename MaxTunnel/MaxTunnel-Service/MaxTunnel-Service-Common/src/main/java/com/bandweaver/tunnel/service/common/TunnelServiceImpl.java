package com.bandweaver.tunnel.service.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bandweaver.tunnel.common.biz.dto.SectionDto;
import com.bandweaver.tunnel.common.biz.dto.StoreDto;
import com.bandweaver.tunnel.common.biz.dto.TunnelDto;
import com.bandweaver.tunnel.common.biz.dto.TunnelSimpleDto;
import com.bandweaver.tunnel.common.biz.itf.SectionService;
import com.bandweaver.tunnel.common.biz.itf.StoreService;
import com.bandweaver.tunnel.common.biz.itf.TunnelService;
import com.bandweaver.tunnel.common.biz.pojo.Tunnel;
import com.bandweaver.tunnel.common.biz.pojo.common.TunnelRun;
import com.bandweaver.tunnel.common.biz.vo.TunnelVo;
import com.bandweaver.tunnel.dao.common.SectionMapper;
import com.bandweaver.tunnel.dao.common.StoreMapper;
import com.bandweaver.tunnel.dao.common.TunnelMapper;
import com.bandweaver.tunnel.dao.common.TunnelRunMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class TunnelServiceImpl implements TunnelService {
    @Autowired
    private TunnelMapper tunnelMapper;
    @Autowired
    private StoreService storeService;
    @Autowired
    private SectionService sectionService;
    @Autowired
    private TunnelRunMapper tunnelRunMapper;
    

    @Override
    public List<TunnelSimpleDto> getList() {
    	List<TunnelSimpleDto> list = tunnelMapper.getList();
    	return list == null ? Collections.emptyList() : list;
    }

	@Override
	public String getNameById(Integer id) {
		return tunnelMapper.getNameById(id);
	}

	@Override
	public TunnelDto getDtoById(Integer id) {
		return tunnelMapper.getDtoById(id);
	}

	@Override
	public List<TunnelDto> getDtoList() {
		return tunnelMapper.getDtoList();
	}



	@Override
	public List<SectionDto> getSectionList(Integer id) {
		List<SectionDto> list = new ArrayList<>();
    	List<StoreDto> storeList = storeService.getStoresByTunnelId(id);
    	for (StoreDto storeDto : storeList) {
			List<SectionDto> sectionList = sectionService.getSectionsByStoreId(storeDto.getId());
			list.addAll(sectionList);
		}
    	return list;
	}
	
	@Override
	public void add(Tunnel tunnel) {
		tunnel.setCrtTime(new Date());
		tunnelMapper.insertSelective(tunnel);
	}

	@Override
	public void update(Tunnel tunnel) {
		tunnelMapper.updateByPrimaryKeySelective(tunnel);
	}

	@Override
	public void delete(Integer id) {
		tunnelMapper.deleteByPrimaryKey(id);
		
	}

	@Override
	public PageInfo<TunnelDto> dataGrid(TunnelVo vo) {
		PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
		List<TunnelDto> list = tunnelMapper.getByCondition(vo);
		return new PageInfo<>(list);
	}

	@Override
	public void deleteBatch(List<Integer> list) {
		
		tunnelMapper.deleteBatch(list);
	}

	@Override
	public Tunnel getByName(String name) {
		return tunnelMapper.getByName(name);
	}

	@Override
	public Tunnel getBySN(String sn) {
		return tunnelMapper.getBySN(sn);
	}

	@Override
	public TunnelRun getTunnelRunInfo() {
		return tunnelRunMapper.getRunInfo();
	}

	@Override
	public void updateTunnelRunInfo(TunnelRun runInfo) {
		runInfo.setRunDays(runInfo.getRunDays() + 1);
		runInfo.setSafeDyas(runInfo.getSafeDyas() + 1);
		tunnelRunMapper.updateByPrimaryKeySelective(runInfo);
	}

	@Override
	public void addTunnelRun(TunnelRun tr) {
		tunnelRunMapper.insertSelective(tr);
	}
}
