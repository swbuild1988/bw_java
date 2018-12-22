package com.bandweaver.tunnel.dao.omm;

import com.bandweaver.tunnel.common.biz.dto.omm.InspectionGroupDto;
import com.bandweaver.tunnel.common.biz.pojo.omm.InspectionGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InspectionGroupMapper {
    Integer add(InspectionGroup inspectionGroup);

    Integer addRelation(@Param("groupId") Integer groupId, @Param("userId") Integer userId);

    InspectionGroupDto getInspectionGroupDto(Integer id);

    List<InspectionGroup> getAllInspectionGroups();

    List<InspectionGroupDto> getAllInspectionGroupDtos();
}
