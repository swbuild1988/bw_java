package com.bandweaver.tunnel.dao.oam;

import java.util.List;

import com.bandweaver.tunnel.common.biz.dto.oam.ConsumeDto;
import com.bandweaver.tunnel.common.biz.pojo.oam.Consume;
import com.bandweaver.tunnel.common.biz.vo.oam.ConsumeDataVo;

public interface ConsumeMapper {
    
	int insert(Consume consume);
	
	int update(Consume consume);
	
	int delete(Integer id);
	
	int addBatch(List<Consume> list);
	
	int deleteBatch(List<Integer> list);
	
	Consume getConsumeById(Integer id);
	
	ConsumeDto getConsumeDtoById(Integer id);
	
	int addBatchWithObjectId(List<Consume> list);
	
	List<ConsumeDto> getConsumeDtos();
	
	List<ConsumeDto> getConsumesByCondition(ConsumeDataVo vo);
}