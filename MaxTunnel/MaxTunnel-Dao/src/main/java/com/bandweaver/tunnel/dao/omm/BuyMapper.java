package com.bandweaver.tunnel.dao.omm;

import java.util.List;

import com.bandweaver.tunnel.common.biz.dto.omm.BuyDto;
import com.bandweaver.tunnel.common.biz.pojo.omm.Buy;
import com.bandweaver.tunnel.common.biz.vo.omm.BuyVo;

public interface BuyMapper {

	int add(Buy buy);
	
	int update(Buy buy);
	
	int delete(Integer id);
	
	BuyDto getDtoById(Integer id);
	
	List<BuyDto> selectByCondition(BuyVo vo);
}
