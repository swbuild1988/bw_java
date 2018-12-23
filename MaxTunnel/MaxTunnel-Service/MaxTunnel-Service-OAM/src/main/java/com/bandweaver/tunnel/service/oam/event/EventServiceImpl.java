package com.bandweaver.tunnel.service.oam.event;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bandweaver.tunnel.common.biz.dto.oam.EventDto;
import com.bandweaver.tunnel.common.biz.itf.oam.EventService;
import com.bandweaver.tunnel.common.biz.pojo.oam.Event;
import com.bandweaver.tunnel.common.biz.vo.oam.EventVo;
import com.bandweaver.tunnel.dao.oam.EventMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class EventServiceImpl implements EventService {

	@Autowired
	private EventMapper eventMapper;
	
	@Override
	public void insert(Event event) {
		eventMapper.insert(event);
	}

	@Override
	public void addEvent(Event event) {
		eventMapper.addEvent(event);
		
	}

	@Override
	public void updateByPrimaryKey(Event event) {
		eventMapper.updateByPrimaryKey(event);
		
	}


	@Override
	public List<EventDto> getList() {
		return eventMapper.getList();
	}

	@Override
	public EventDto getDtoById(Integer id) {
		return eventMapper.getDtoById(id);
	}

	@Override
	public PageInfo<EventDto> dataGrid(EventVo vo) {
		PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
		List<EventDto> list = eventMapper.getDtoListByCondition(vo);
		return new PageInfo<>(list);
	}

	@Override
	public void deleteByPrimaryKey(Integer id) {
		eventMapper.deleteByPrimaryKey(id);
	}

	@Override
	public List<EventDto> getDtoListByDay(EventVo vo) {
		return eventMapper.getDtoListByDay(vo);
	}

}
