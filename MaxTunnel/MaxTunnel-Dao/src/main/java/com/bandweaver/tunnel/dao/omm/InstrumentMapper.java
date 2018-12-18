package com.bandweaver.tunnel.dao.omm;


import com.bandweaver.tunnel.common.biz.dto.CommonDto;
import com.bandweaver.tunnel.common.biz.dto.omm.InstrumentDto;
import com.bandweaver.tunnel.common.biz.pojo.omm.Instrument;
import com.bandweaver.tunnel.common.biz.vo.omm.InstrumentVo;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface InstrumentMapper {


    int update(Instrument instrument);
    
    int updateStatus(@Param("status") Boolean status,
    		@Param("list") List<Integer> list);
    
    int updateStatusAndUseStatus(@Param("in") Instrument in,
    		@Param("list") List<Integer> list);

    int addBatch(List<Instrument> list);
    
    int addBatchBySpare(List<Instrument> list);
    /**
     * 条件查询列表
     * @param vo
     * @return
     * @author ya.liu
     * @Date 2018年11月30日
     */
    List<InstrumentDto> getInstrumentDtoByCondition(InstrumentVo vo);
    
    InstrumentDto getInstrumentDtoById(Integer id);

    int deleteByIds(List<Integer> list);
    
    List<CommonDto> getCountGroupByTypeId();
}
