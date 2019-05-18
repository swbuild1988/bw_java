package com.bandweaver.tunnel.dao.omm;

import com.bandweaver.tunnel.common.biz.dto.omm.InspectionGroupDto;
import com.bandweaver.tunnel.common.biz.pojo.omm.InspectionGroup;
import com.bandweaver.tunnel.common.biz.vo.omm.InspectionVo;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InspectionGroupMapper {
    Integer add(InspectionGroup inspectionGroup);
    
    Integer update(InspectionGroup inspectionGroup);
    
    Integer delete(Integer id);

    Integer addRelation(@Param("groupId") Integer groupId, @Param("userId") Integer userId);

    Integer deleteRelation(@Param("groupId") Integer groupId, @Param("userId") Integer userId);
    
    InspectionGroupDto getInspectionGroupDto(Integer id);

    List<InspectionGroup> getInspectionGroupsByCondition(InspectionVo vo);

    List<InspectionGroupDto> getAllInspectionGroupDtos();
}
