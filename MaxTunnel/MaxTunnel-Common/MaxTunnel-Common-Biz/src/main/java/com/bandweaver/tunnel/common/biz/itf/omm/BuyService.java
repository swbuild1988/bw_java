package com.bandweaver.tunnel.common.biz.itf.omm;

import java.util.List;

import com.bandweaver.tunnel.common.biz.dto.omm.BuyDto;
import com.bandweaver.tunnel.common.biz.pojo.ListPageUtil;
import com.bandweaver.tunnel.common.biz.pojo.omm.Buy;
import com.bandweaver.tunnel.common.biz.vo.omm.BuyVo;

public interface BuyService {

int add(Buy buy);
	
	int update(Buy buy);
	
	int delete(Integer id);
	
	BuyDto getDtoById(Integer id);
	
	List<BuyDto> selectByCondition(BuyVo vo);
	
	ListPageUtil<BuyDto> dataGrid(BuyVo vo);
}
