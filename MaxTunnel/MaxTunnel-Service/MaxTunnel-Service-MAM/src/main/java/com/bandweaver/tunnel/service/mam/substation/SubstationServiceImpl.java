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
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class SubstationServiceImpl implements SubstationService {
	@Autowired
	private SubstationMapper substationMapper;

	@Override
	public SubstationDto selectByPrimaryKey(Integer id) {
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
	public List<SubstationDto> getListByCondition(SubstationVo vo) {
		return substationMapper.getListByCondition(vo);
	}

	@Override
	public SubstationDto getDtoById(Integer id) {
		return substationMapper.getDtoById(id);
	}

	@Override
	public PageInfo<SubstationDto> dataGrid(SubstationVo vo) {
		PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
		List<SubstationDto> list = getListByCondition(vo);
		return new PageInfo<>(list);
	}

}
