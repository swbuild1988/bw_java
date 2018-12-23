package com.bandweaver.tunnel.common.biz.itf.oam;

import java.util.List;

import com.bandweaver.tunnel.common.biz.dto.oam.ConsumeDto;
import com.bandweaver.tunnel.common.biz.pojo.oam.Consume;
import com.bandweaver.tunnel.common.biz.vo.oam.ConsumeDataVo;

public interface ConsumeService {

	int insert(Consume consume);
	
	int update(Consume consume);
	
	int delete(Integer id);
	
	int addBatch(List<Consume> list);
	
	int addBatchWithObjectId(List<Consume> list);
	
	int deleteBatch(List<Integer> list);
	
	Consume getConsumeById(Integer id);
	
	ConsumeDto getConsumeDtoById(Integer id);
	
	List<ConsumeDto> getConsumes();
	
	List<ConsumeDto> getConsumesByCondition(ConsumeDataVo vo);
}
