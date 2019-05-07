package com.bandweaver.tunnel.service.em;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bandweaver.tunnel.common.biz.dto.em.EmPlanMemberDto;
import com.bandweaver.tunnel.common.biz.itf.em.EmPlanMemberService;
import com.bandweaver.tunnel.common.biz.pojo.em.EmPlanGroup;
import com.bandweaver.tunnel.common.biz.pojo.em.EmPlanMember;
import com.bandweaver.tunnel.common.biz.vo.em.EmPlanMemberVo;
import com.bandweaver.tunnel.dao.em.EmPlanGroupMapper;
import com.bandweaver.tunnel.dao.em.EmPlanMemberMapper;

@Service
public class EmPlanMemberServiceImpl implements EmPlanMemberService {
	
	@Autowired
	private EmPlanGroupMapper emPlanGroupMapper;
	@Autowired
	private EmPlanMemberMapper emPlanMemberMapper;

	@Override
	public int deleteByPrimaryKey(Integer id) {
		return emPlanMemberMapper.deleteByPrimaryKey(id);
	}

	@Transactional
	@Override
	public int insertSelective(EmPlanMember record, Integer role) {
		record.setTime(new Date());
		isGroupOwner(record,role);
		return emPlanMemberMapper.insertSelective(record);
	}

	@Override
	public EmPlanMember selectByPrimaryKey(Integer id) {
		return emPlanMemberMapper.selectByPrimaryKey(id);
	}

	@Transactional
	@Override
	public int updateByPrimaryKeySelective(EmPlanMember record, Integer role) {
		isGroupOwner(record,role);
		return emPlanMemberMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public EmPlanMemberDto getById(Integer id) {
		return emPlanMemberMapper.getById(id);
	}

	@Override
	public List<EmPlanMemberDto> getByCondition(EmPlanMemberVo vo) {
		return emPlanMemberMapper.getByCondition(vo);
	}

	/**
	 * 判断是否是组长
	 * @param record
	 * @param role
	 * @author ya.liu
	 * @Date 2019年4月24日
	 */
	private void isGroupOwner(EmPlanMember record, Integer role) {
		if(role == 1) {
			EmPlanGroup e = new EmPlanGroup();
			e.setId(record.getGroupId());
			e.setStaffId(record.getStaffId());
			emPlanGroupMapper.updateByPrimaryKeySelective(e);
		}
	}
}
