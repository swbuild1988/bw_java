package com.bandweaver.tunnel.dao.oam;

import java.util.List;

import com.bandweaver.tunnel.common.biz.dto.oam.EventDto;
import com.bandweaver.tunnel.common.biz.pojo.oam.Event;
import com.bandweaver.tunnel.common.biz.vo.oam.EventVo;

public interface EventMapper {

	void insert(Event event);
	
	void addEvent(Event event);
	
	void updateByPrimaryKey(Event event);
	
	List<EventDto> getList();
	
	EventDto getDtoById(Integer id);
	
	List<EventDto> getDtoListByCondition(EventVo vo);
	
	void deleteByPrimaryKey(Integer id);
	
	List<EventDto> getDtoListByDay(EventVo vo);
}
