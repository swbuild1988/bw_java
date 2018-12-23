package com.bandweaver.tunnel.service.omm.instrument;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bandweaver.tunnel.common.biz.dto.CommonDto;
import com.bandweaver.tunnel.common.biz.dto.omm.InstrumentDto;
import com.bandweaver.tunnel.common.biz.dto.omm.InstrumentRecordDto;
import com.bandweaver.tunnel.common.biz.itf.omm.InstrumentService;
import com.bandweaver.tunnel.common.biz.pojo.ListPageUtil;
import com.bandweaver.tunnel.common.biz.pojo.omm.Instrument;
import com.bandweaver.tunnel.common.biz.vo.omm.InstrumentRecordVo;
import com.bandweaver.tunnel.common.biz.vo.omm.InstrumentVo;
import com.bandweaver.tunnel.dao.omm.InstrumentMapper;
import com.bandweaver.tunnel.dao.omm.InstrumentRecordMapper;

@Service
public class InstrumentServiceImpl implements InstrumentService {

	@Autowired
	private InstrumentMapper instrumentMapper;
	@Autowired
	private InstrumentRecordMapper instrumentRecordMapper;
	
	@Override
	public int update(Instrument instrument) {
		return instrumentMapper.update(instrument);
	}

	@Override
	public int addBatch(List<Instrument> list) {
		return instrumentMapper.addBatch(list);
	}

	@Override
	public int addBatchBySpare(List<Instrument> list) {
		return instrumentMapper.addBatchBySpare(list);
	}

	@Override
	public ListPageUtil<InstrumentDto> dataGride(InstrumentVo vo) {
		List<InstrumentDto> list = instrumentMapper.getInstrumentDtoByCondition(vo);
		ListPageUtil<InstrumentDto> page = new ListPageUtil<>(list, vo.getPageNum(), vo.getPageSize());
		return page;
	}

	@Override
	public List<InstrumentDto> getInstrumentDtoByCondition(InstrumentVo vo) {
		List<InstrumentDto> list = instrumentMapper.getInstrumentDtoByCondition(vo);
		return list;
	}

	@Override
	public InstrumentDto getInstrumentDtoById(Integer id) {
		return instrumentMapper.getInstrumentDtoById(id);
	}

	@Override
	@Transactional
	public int deleteByIds(List<Integer> list) {
		List<Integer> listRecord = new ArrayList<>();
		for(Integer in : list) {
			InstrumentRecordVo vo = new InstrumentRecordVo();
			vo.setInstrumentId(in);
			List<InstrumentRecordDto> listDto = instrumentRecordMapper.getInstrumentRecordDtoByCondition(vo);
			for (InstrumentRecordDto dto : listDto) {
				listRecord.add(dto.getId());
			}
		}
		//删除仪表借出记录
		instrumentRecordMapper.deleteByIds(listRecord);
		return instrumentMapper.deleteByIds(list);
	}

	@Override
	public List<CommonDto> getCountGroupByTypeId(InstrumentVo vo) {
		return instrumentMapper.getCountGroupByTypeId(vo);
	}

	
}
