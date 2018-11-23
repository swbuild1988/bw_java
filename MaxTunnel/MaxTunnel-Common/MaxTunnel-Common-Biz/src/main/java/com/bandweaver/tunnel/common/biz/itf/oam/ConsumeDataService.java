package com.bandweaver.tunnel.common.biz.itf.oam;

import java.util.Date;
import java.util.List;

import com.bandweaver.tunnel.common.biz.pojo.oam.ConsumeData;
import com.bandweaver.tunnel.common.biz.vo.oam.ConsumeDataVo;

public interface ConsumeDataService {

	int insert(ConsumeData consumeData);
	
	int addBatch(List<ConsumeData> list);
	
	int deleteBatch(Date date);
	
	List<ConsumeData> getListByIdAndTime(ConsumeDataVo vo);
}
