package com.bandweaver.tunnel.common.biz.itf.omm;

import java.util.List;

import com.bandweaver.tunnel.common.biz.dto.CommonDto;
import com.bandweaver.tunnel.common.biz.dto.omm.SpareOutDto;
import com.bandweaver.tunnel.common.biz.pojo.ListPageUtil;
import com.bandweaver.tunnel.common.biz.pojo.omm.SpareOut;
import com.bandweaver.tunnel.common.biz.vo.omm.SpareOutVo;

public interface SpareOutService {

    int update(SpareOut s);

    int addBatch(List<SpareOut> list, Integer tunnelId);
    
    List<SpareOutDto> getSpareOutDtoByCondition(SpareOutVo vo);
    
    ListPageUtil<SpareOutDto> dataGrid(SpareOutVo vo);
    
    SpareOutDto getSpareOutDtoById(Integer id);

    int deleteByIds(List<Integer> list);
    
    List<CommonDto> getCountGroupByStaffId();
    
    int getCountByWhither(Integer whither);
    
}
