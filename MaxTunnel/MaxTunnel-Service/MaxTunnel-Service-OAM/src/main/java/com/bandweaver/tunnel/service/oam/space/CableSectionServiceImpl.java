package com.bandweaver.tunnel.service.oam.space;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bandweaver.tunnel.common.biz.itf.oam.CableSectionService;
import com.bandweaver.tunnel.common.biz.pojo.oam.CableSection;
import com.bandweaver.tunnel.dao.oam.CableSectionMapper;
@Service
public class CableSectionServiceImpl implements CableSectionService{

	@Autowired
	private CableSectionMapper cableSectionMapper;

	@Override
	public void addBatch(List<CableSection> list) {
		cableSectionMapper.addBatch(list);
	}

	@Override
	public int getCountBySectionId(Integer id) {
		return cableSectionMapper.getCountBySectionId(id);
	}
}
