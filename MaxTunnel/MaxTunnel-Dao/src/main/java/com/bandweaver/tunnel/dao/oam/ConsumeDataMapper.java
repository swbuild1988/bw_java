package com.bandweaver.tunnel.dao.oam;

import java.util.Date;
import java.util.List;

import com.bandweaver.tunnel.common.biz.pojo.oam.ConsumeData;
import com.bandweaver.tunnel.common.biz.vo.oam.ConsumeDataVo;

public interface ConsumeDataMapper {
    
	int insert(ConsumeData consumeData);
	
	int addBatch(List<ConsumeData> list);
	
	int deleteBatch(Date date);
	
	List<ConsumeData> getListByIdAndTime(ConsumeDataVo vo);
}