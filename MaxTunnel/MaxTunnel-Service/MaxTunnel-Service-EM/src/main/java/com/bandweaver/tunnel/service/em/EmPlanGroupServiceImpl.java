package com.bandweaver.tunnel.service.em;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bandweaver.tunnel.common.biz.dto.em.EmPlanGroupDto;
import com.bandweaver.tunnel.common.biz.dto.em.EmPlanMemberDto;
import com.bandweaver.tunnel.common.biz.itf.em.EmPlanGroupService;
import com.bandweaver.tunnel.common.biz.pojo.em.EmPlanGroup;
import com.bandweaver.tunnel.common.biz.pojo.em.EmPlanMember;
import com.bandweaver.tunnel.common.biz.vo.em.EmPlanGroupVo;
import com.bandweaver.tunnel.common.biz.vo.em.EmPlanMemberVo;
import com.bandweaver.tunnel.dao.em.EmPlanGroupMapper;
import com.bandweaver.tunnel.dao.em.EmPlanMemberMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class EmPlanGroupServiceImpl implements EmPlanGroupService {
	
	@Autowired
	private EmPlanGroupMapper emPlanGroupMapper;
	@Autowired
	private EmPlanMemberMapper emPlanMemberMapper;

	@Transactional
	@Override
	public int deleteByPrimaryKey(Integer id) {
		EmPlanMemberVo vo = new EmPlanMemberVo();
		vo.setGroupId(id);
		List<EmPlanMemberDto> list = emPlanMemberMapper.getByCondition(vo);
		for(EmPlanMemberDto dto : list) {
			emPlanMemberMapper.deleteByPrimaryKey(dto.getId());
		}
		return emPlanGroupMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insertSelective(EmPlanGroup record) {
		record.setTime(new Date());
		return emPlanGroupMapper.insertSelective(record);
	}

	@Override
	public EmPlanGroup selectByPrimaryKey(Integer id) {
		return emPlanGroupMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(EmPlanGroup record) {
		return emPlanGroupMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public EmPlanGroupDto getById(Integer id) {
		return emPlanGroupMapper.getById(id);
	}

	@Override
	public List<EmPlanGroupDto> getByCondition(EmPlanGroupVo vo) {
		return emPlanGroupMapper.getByCondition(vo);
	}

	@Override
	public PageInfo<EmPlanGroupDto> dataGrid(EmPlanGroupVo vo) {
		PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
		List<EmPlanGroupDto> list = emPlanGroupMapper.getByCondition(vo);
		return new PageInfo<>(list);
	}


}
