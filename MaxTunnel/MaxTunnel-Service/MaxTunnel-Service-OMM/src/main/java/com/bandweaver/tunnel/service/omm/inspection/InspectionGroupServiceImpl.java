package com.bandweaver.tunnel.service.omm.inspection;

import com.bandweaver.tunnel.common.biz.dto.omm.InspectionGroupDto;
import com.bandweaver.tunnel.common.biz.itf.omm.InspectionGroupService;
import com.bandweaver.tunnel.common.biz.pojo.omm.InspectionGroup;
import com.bandweaver.tunnel.dao.omm.InspectionGroupMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InspectionGroupServiceImpl implements InspectionGroupService{
    @Autowired
    private InspectionGroupMapper inspectionGroupMapper;

    @Override
    public Integer add(InspectionGroup inspectionGroup) {
        return inspectionGroupMapper.add(inspectionGroup);
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
    public List<InspectionGroup> getAllInspectionGroups() {
        return inspectionGroupMapper.getAllInspectionGroups();
    }

    @Override
    public List<InspectionGroupDto> getAllInspectionGroupDtos() {
        return inspectionGroupMapper.getAllInspectionGroupDtos();
    }
}
