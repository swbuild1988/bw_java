package com.bandweaver.tunnel.common.biz.itf.omm;

import com.bandweaver.tunnel.common.biz.dto.omm.InspectionGroupDto;
import com.bandweaver.tunnel.common.biz.pojo.omm.InspectionGroup;
import com.bandweaver.tunnel.common.biz.vo.omm.InspectionVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface InspectionGroupService {
	
    Integer add(InspectionGroup inspectionGroup);
    
    Integer update(InspectionGroup inspectionGroup);
    
    Integer delete(Integer id);
    
    Integer addRelation(Integer groupId, Integer userId);
    
    Integer deleteRelation(Integer groupId, Integer userId);

    InspectionGroupDto getInspectionGroupDto(Integer id);

    PageInfo<InspectionGroup> dataGraid(InspectionVo vo);

    List<InspectionGroupDto> getAllInspectionGroupDtos();
}
