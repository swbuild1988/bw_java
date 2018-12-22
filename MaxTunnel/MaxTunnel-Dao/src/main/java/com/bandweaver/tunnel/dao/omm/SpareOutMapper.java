package com.bandweaver.tunnel.dao.omm;



import com.bandweaver.tunnel.common.biz.dto.CommonDto;
import com.bandweaver.tunnel.common.biz.dto.omm.SpareOutDto;
import com.bandweaver.tunnel.common.biz.pojo.omm.SpareOut;
import com.bandweaver.tunnel.common.biz.vo.omm.SpareOutVo;

import java.util.List;

public interface SpareOutMapper {

    int add(SpareOut s);

    int update(SpareOut s);

    int addBatch(List<SpareOut> list);
    
    List<SpareOutDto> getSpareOutDtoByCondition(SpareOutVo vo);
    
    SpareOutDto getSpareOutDtoById(Integer id);

    int deleteByIds(List<Integer> list);
    
    List<CommonDto> getCountGroupByStaffId();
    
    List<CommonDto> getCountGroupByTypeId();
    
}
