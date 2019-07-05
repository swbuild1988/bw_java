package com.bandweaver.tunnel.service.oam.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bandweaver.tunnel.common.biz.dto.oam.ReqRecordDto;
import com.bandweaver.tunnel.common.biz.itf.oam.ReqRecordService;
import com.bandweaver.tunnel.common.biz.pojo.oam.ReqRecord;
import com.bandweaver.tunnel.common.biz.vo.oam.ReqRecordVo;
import com.bandweaver.tunnel.dao.oam.ReqRecordMapper;

@Service
public class ReqRecordServiceImpl implements ReqRecordService {
	@Autowired
	private ReqRecordMapper reqRecordMapper;

	@Override
	public int insertSelective(ReqRecord record) {
		return reqRecordMapper.insertSelective(record);
	}

	@Override
	public int updateByIdSelective(ReqRecord record) {
		return reqRecordMapper.updateByIdSelective(record);
	}

	@Override
	public List<ReqRecord> getListByCondition(ReqRecordVo vo) {
		return reqRecordMapper.getListByCondition(vo);
	}

	@Override
	public List<ReqRecordDto> getDtoListByCondition(ReqRecordVo vo) {
		return reqRecordMapper.getDtoListByCondition(vo);
	}

	@Override
	public ReqRecordDto selectById(Integer id) {
		return reqRecordMapper.selectById(id);
	}

	@Override
	public int deleteById(Integer id) {
		return reqRecordMapper.deleteById(id);
	}

}
