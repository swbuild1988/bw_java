package com.bandweaver.tunnel.dao.omm;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bandweaver.tunnel.common.biz.dto.omm.InstrumentRecordDto;
import com.bandweaver.tunnel.common.biz.pojo.omm.InstrumentRecord;
import com.bandweaver.tunnel.common.biz.vo.omm.InstrumentRecordVo;

public interface InstrumentRecordMapper {

	int addBatch(List<InstrumentRecord> list);
	
	int updateReturnTime(@Param("record") InstrumentRecord record,
			@Param("list") List<Integer> list);
	
	List<InstrumentRecordDto> getInstrumentRecordDtoByCondition(InstrumentRecordVo vo);
	
	InstrumentRecordDto getInstrumentRecordDtoById(@Param("id") Integer id);
	
	int deleteByIds(List<Integer> list);
}
