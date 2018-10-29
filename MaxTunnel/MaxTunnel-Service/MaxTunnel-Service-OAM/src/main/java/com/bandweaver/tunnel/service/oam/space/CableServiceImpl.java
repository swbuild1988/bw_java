package com.bandweaver.tunnel.service.oam.space;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bandweaver.tunnel.common.biz.dto.oam.CableDto;
import com.bandweaver.tunnel.common.biz.itf.oam.CableService;
import com.bandweaver.tunnel.common.biz.pojo.oam.Cable;
import com.bandweaver.tunnel.dao.oam.CableMapper;

@Service
public class CableServiceImpl implements CableService {

	@Autowired
	private CableMapper cableMapper;

	@Override
	public List<CableDto> getCableDtoListBySectionId(Integer id) {
		return cableMapper.getCableDtoListBySectionId(id);
	}

	@Override
	public void addBatch(List<Cable> list) {
		cableMapper.addBatch(list);
	}
}
