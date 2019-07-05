package com.bandweaver.tunnel.service.omm.inspection;

import com.bandweaver.tunnel.common.biz.dto.omm.InspectionGroupDto;
import com.bandweaver.tunnel.common.biz.itf.omm.InspectionGroupService;
import com.bandweaver.tunnel.common.biz.pojo.Staff;
import com.bandweaver.tunnel.common.biz.pojo.omm.InspectionGroup;
import com.bandweaver.tunnel.common.biz.vo.omm.InspectionVo;
import com.bandweaver.tunnel.dao.common.StaffMapper;
import com.bandweaver.tunnel.dao.omm.InspectionGroupMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InspectionGroupServiceImpl implements InspectionGroupService{
    @Autowired
    private InspectionGroupMapper inspectionGroupMapper;
    @Autowired
    private StaffMapper staffMapper;

    @Transactional
    @Override
    public Integer add(InspectionGroup inspectionGroup) {
    	Staff user = staffMapper.selectByPrimaryKey(inspectionGroup.getLeaderId());
    	if(user != null) inspectionGroup.setLeaderName(user.getName());
    	// 创建群
    	int i = inspectionGroupMapper.add(inspectionGroup);
    	// 添加成员
    	addRelation(inspectionGroup.getId(), inspectionGroup.getLeaderId());
        return i;
    }

    @Override
    public Integer addRelation(Integer groupId, Integer userId) {
        return inspectionGroupMapper.addRelation(groupId, userId);
    }

    @Override
    public InspectionGroupDto getInspectionGroupDto(Integer id) {
        return inspectionGroupMapper.getInspectionGroupDto(id);
    }

    @Override
    public PageInfo<InspectionGroup> dataGraid(InspectionVo vo) {
    	PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
    	List<InspectionGroup> list = inspectionGroupMapper.getInspectionGroupsByCondition(vo);
        return new PageInfo<>(list);
    }

    @Override
    public List<InspectionGroupDto> getAllInspectionGroupDtos() {
        return inspectionGroupMapper.getAllInspectionGroupDtos();
    }

    @Transactional
	@Override
	public Integer update(InspectionGroup inspectionGroup) {
		Staff user = staffMapper.selectByPrimaryKey(inspectionGroup.getLeaderId());
    	if(user != null) inspectionGroup.setLeaderName(user.getName());
    	InspectionGroupDto dto = getInspectionGroupDto(inspectionGroup.getId());
    	if(dto != null) {
    		List<Staff> list = dto.getMembers();
    		boolean flag = true;
    		for(Staff staff : list) {
    			if(inspectionGroup.getLeaderId().equals(staff.getId())) flag = false;
    		}
    		if(flag) addRelation(inspectionGroup.getId(), inspectionGroup.getLeaderId());
    	}
		return inspectionGroupMapper.update(inspectionGroup);
	}

	@Transactional
	@Override
	public Integer delete(Integer id) {
		inspectionGroupMapper.deleteRelation(id, null);
		return inspectionGroupMapper.delete(id);
	}

	@Override
	public Integer deleteRelation(Integer groupId, Integer userId) {
		inspectionGroupMapper.deleteRelation(groupId, userId);
		return null;
	}
}
