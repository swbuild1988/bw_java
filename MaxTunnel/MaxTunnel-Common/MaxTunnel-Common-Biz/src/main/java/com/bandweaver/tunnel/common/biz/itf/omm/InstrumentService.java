package com.bandweaver.tunnel.common.biz.itf.omm;

import java.util.List;

import com.bandweaver.tunnel.common.biz.dto.CommonDto;
import com.bandweaver.tunnel.common.biz.dto.omm.InstrumentDto;
import com.bandweaver.tunnel.common.biz.pojo.ListPageUtil;
import com.bandweaver.tunnel.common.biz.pojo.omm.Instrument;
import com.bandweaver.tunnel.common.biz.vo.omm.InstrumentVo;

public interface InstrumentService {


    int update(Instrument instrument);

    int addBatch(List<Instrument> list);
    
    int addBatchBySpare(List<Instrument> list);
    
    ListPageUtil<InstrumentDto> dataGride(InstrumentVo vo);
    
    List<InstrumentDto> getInstrumentDtoByCondition(InstrumentVo vo);
    
    InstrumentDto getInstrumentDtoById(Integer id);

    int deleteByIds(List<Integer> list);
    
    List<CommonDto> getCountGroupByTypeId();
}
