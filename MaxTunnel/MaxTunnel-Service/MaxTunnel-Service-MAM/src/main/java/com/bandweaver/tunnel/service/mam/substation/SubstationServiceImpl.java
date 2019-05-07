package com.bandweaver.tunnel.service.mam.substation;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bandweaver.tunnel.common.biz.dto.mam.substation.SubstationDto;
import com.bandweaver.tunnel.common.biz.itf.mam.substation.SubstationService;
import com.bandweaver.tunnel.common.biz.pojo.mam.substation.Substation;
import com.bandweaver.tunnel.common.biz.vo.mam.substation.SubstationVo;
import com.bandweaver.tunnel.dao.mam.SubstationMapper;
import com.bandweaver.tunnel.service.mam.measobj.MeasObjModuleCenter;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class SubstationServiceImpl implements SubstationService {
	@Autowired
	private SubstationMapper substationMapper;
	@Autowired
	private MeasObjModuleCenter measObjModuleCenter;

	@Override
	public Substation selectByPrimaryKey(Integer id) {
		return substationMapper.selectByPrimaryKey(id);
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {
		return substationMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insertSelective(Substation substation) {
		if(substation.getTime() == null) substation.setTime(new Date());
		return substationMapper.insertSelective(substation);
	}

	@Override
	public int updateByPrimaryKeySelective(Substation substation) {
		return substationMapper.updateByPrimaryKeySelective(substation);
	}

	@Override
	public List<Substation> getListByCondition(SubstationVo vo) {
		return substationMapper.getListByCondition(vo);
	}

	@Override
	public SubstationDto getDtoById(Integer id) {
		SubstationDto dto = substationMapper.getDtoById(id);
		dto.setInv(measObjModuleCenter.getMeasObjAI(dto.getInvId()));
		dto.setIna(measObjModuleCenter.getMeasObjAI(dto.getInaId()));
		dto.setOutv(measObjModuleCenter.getMeasObjAI(dto.getOutvId()));
		dto.setOuta(measObjModuleCenter.getMeasObjAI(dto.getOutaId()));
		dto.setPower(measObjModuleCenter.getMeasObjAI(dto.getPowerId()));
		return dto;
	}

	@Override
	public PageInfo<Substation> dataGrid(SubstationVo vo) {
		PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
		List<Substation> list = getListByCondition(vo);
		return new PageInfo<>(list);
	}

}
