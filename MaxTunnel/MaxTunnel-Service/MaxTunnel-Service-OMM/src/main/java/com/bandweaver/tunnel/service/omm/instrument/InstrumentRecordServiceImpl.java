package com.bandweaver.tunnel.service.omm.instrument;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bandweaver.tunnel.common.biz.constant.omm.EquipmentStatusEnum;
import com.bandweaver.tunnel.common.biz.dto.omm.InstrumentRecordDto;
import com.bandweaver.tunnel.common.biz.itf.omm.InstrumentRecordService;
import com.bandweaver.tunnel.common.biz.pojo.ListPageUtil;
import com.bandweaver.tunnel.common.biz.pojo.omm.Instrument;
import com.bandweaver.tunnel.common.biz.pojo.omm.InstrumentRecord;
import com.bandweaver.tunnel.common.biz.vo.omm.InstrumentRecordVo;
import com.bandweaver.tunnel.dao.omm.InstrumentMapper;
import com.bandweaver.tunnel.dao.omm.InstrumentRecordMapper;

@Service
@Transactional
public class InstrumentRecordServiceImpl implements InstrumentRecordService {

	@Autowired
	private InstrumentRecordMapper instrumentRecordMapper;
	@Autowired
	private InstrumentMapper instrumentMapper;
	
	@Override
	public int addBatch(List<InstrumentRecord> list) {
		if(list.size() < 1) return 0;
		List<Integer> ls = new ArrayList<>();
		for(InstrumentRecord in : list) {
			ls.add(in.getInstrumentId());
		}
		//仪表工具出库
		Instrument in = new Instrument();
		in.setStatus(false);
		instrumentMapper.updateStatus(false, ls);
		return instrumentRecordMapper.addBatch(list);
	}

	@Override
	@Transactional
	public int updateReturnTime(Integer useStatus,InstrumentRecord record, List<Integer> list) {
		List<Integer> ls = new ArrayList<>();
		for(Integer i : list) {
			InstrumentRecordDto dto = instrumentRecordMapper.getInstrumentRecordDtoById(i);
			ls.add(dto.getInstrumentId());
		}
		//仪表工具入库
		Instrument in = new Instrument();
		in.setStatus(true);
		in.setUseStatus(useStatus);
		instrumentMapper.updateStatusAndUseStatus(in, ls);
		if(record.getRemark() == null) record.setRemark("");
		return instrumentRecordMapper.updateReturnTime(record, list);
	}

	@Override
	public List<InstrumentRecordDto> getInstrumentRecordDtoByCondition(InstrumentRecordVo vo) {
		return instrumentRecordMapper.getInstrumentRecordDtoByCondition(vo);
	}

	@Override
	public ListPageUtil<InstrumentRecordDto> dataGride(InstrumentRecordVo vo) {
		List<InstrumentRecordDto> list = instrumentRecordMapper.getInstrumentRecordDtoByCondition(vo);
		ListPageUtil<InstrumentRecordDto> page = new ListPageUtil<>(list, vo.getPageNum(), vo.getPageSize());
		return page;
	}

	@Override
	public InstrumentRecordDto getInstrumentRecordDtoById(Integer id) {
		return instrumentRecordMapper.getInstrumentRecordDtoById(id);
	}

	@Override
	public int deleteByIds(List<Integer> list) {
		return instrumentRecordMapper.deleteByIds(list);
	}
	
}
