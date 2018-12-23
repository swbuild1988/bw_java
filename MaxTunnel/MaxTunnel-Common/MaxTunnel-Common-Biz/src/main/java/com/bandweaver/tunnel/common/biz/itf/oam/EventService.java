package com.bandweaver.tunnel.common.biz.itf.oam;

import java.util.List;

import com.bandweaver.tunnel.common.biz.dto.oam.EventDto;
import com.bandweaver.tunnel.common.biz.pojo.oam.Event;
import com.bandweaver.tunnel.common.biz.vo.oam.EventVo;
import com.github.pagehelper.PageInfo;

public interface EventService {
	
	void insert(Event event);
	
	void addEvent(Event event);
	
	void updateByPrimaryKey(Event event);
	
	List<EventDto> getList();
	
	EventDto getDtoById(Integer id);
	
	PageInfo<EventDto> dataGrid(EventVo vo);
	
	void deleteByPrimaryKey(Integer id);
	
	List<EventDto> getDtoListByDay(EventVo vo);
}
