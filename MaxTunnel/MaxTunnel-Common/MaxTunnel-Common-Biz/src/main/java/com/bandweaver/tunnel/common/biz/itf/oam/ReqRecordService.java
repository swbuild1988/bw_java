package com.bandweaver.tunnel.common.biz.itf.oam;

import java.util.List;

import com.bandweaver.tunnel.common.biz.dto.oam.ReqRecordDto;
import com.bandweaver.tunnel.common.biz.pojo.oam.ReqRecord;
import com.bandweaver.tunnel.common.biz.vo.oam.ReqRecordVo;

public interface ReqRecordService {

	int insertSelective(ReqRecord record);
	
	int updateByIdSelective(ReqRecord record);
	
	List<ReqRecord> getListByCondition(ReqRecordVo vo);
	
	List<ReqRecordDto> getDtoListByCondition(ReqRecordVo vo);
	
	ReqRecordDto selectById(Integer id);
	
	int deleteById(Integer id);
}
