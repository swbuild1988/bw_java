package com.bandweaver.tunnel.common.biz.itf.omm;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bandweaver.tunnel.common.biz.dto.omm.InstrumentRecordDto;
import com.bandweaver.tunnel.common.biz.pojo.ListPageUtil;
import com.bandweaver.tunnel.common.biz.pojo.omm.InstrumentRecord;
import com.bandweaver.tunnel.common.biz.vo.omm.InstrumentRecordVo;

public interface InstrumentRecordService {

	int addBatch(List<InstrumentRecord> list);
	
	int updateReturnTime(Integer useStatus, InstrumentRecord record, List<Integer> list);
	
	List<InstrumentRecordDto> getInstrumentRecordDtoByCondition(InstrumentRecordVo vo);
	
	ListPageUtil<InstrumentRecordDto> dataGride(InstrumentRecordVo vo);
	
	InstrumentRecordDto getInstrumentRecordDtoById(@Param("id") Integer id);
	
	int deleteByIds(List<Integer> list);
}
