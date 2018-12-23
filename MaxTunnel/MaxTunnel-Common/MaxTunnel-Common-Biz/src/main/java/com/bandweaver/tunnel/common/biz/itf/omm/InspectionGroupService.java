package com.bandweaver.tunnel.common.biz.itf.omm;

import com.bandweaver.tunnel.common.biz.dto.omm.InspectionGroupDto;
import com.bandweaver.tunnel.common.biz.pojo.omm.InspectionGroup;

import java.util.List;

public interface InspectionGroupService {
    Integer add(InspectionGroup inspectionGroup);
    Integer addRelation(Integer groupId, Integer userId);

    InspectionGroupDto getInspectionGroupDto(Integer id);

    List<InspectionGroup> getAllInspectionGroups();

    List<InspectionGroupDto> getAllInspectionGroupDtos();
}
