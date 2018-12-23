package com.bandweaver.tunnel.controller.oam;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.dto.oam.EventDto;
import com.bandweaver.tunnel.common.biz.itf.oam.EventService;
import com.bandweaver.tunnel.common.biz.pojo.oam.Event;
import com.bandweaver.tunnel.common.biz.vo.oam.EventVo;
import com.bandweaver.tunnel.common.platform.constant.StatusCodeEnum;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.CommonUtil;
import com.github.pagehelper.PageInfo;

/**重大事件模块
 * @author ya.liu
 * @Date 2018年10月16日
 */
@Controller
@ResponseBody
public class EventController {
	
	@Autowired
	private EventService eventService;
	
	/**
	 * 通过id获取事件信息
	 * @param id
	 * @return
	 * @author ya.liu
	 * @Date 2018年10月16日
	 */
	@RequestMapping(value="events/{id}", method=RequestMethod.GET)
	public JSONObject getDtoById(@PathVariable("id")Integer id) {
		EventDto dto = eventService.getDtoById(id);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, dto);
	}
	
	/**
	 * 获取所有事件
	 * @return
	 * @author ya.liu
	 * @Date 2018年10月17日
	 */
	@RequestMapping(value = "events", method = RequestMethod.GET)
	public JSONObject getList() {
		List<EventDto> list = eventService.getList();
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, list);
	}
	
	/**
	 * 分页获取事件
	 * @param vo
	 * @return
	 * @author ya.liu
	 * @Date 2018年10月17日
	 */
	@RequestMapping(value = "events/datagrid", method = RequestMethod.POST)
	public JSONObject dataGrid(@RequestBody EventVo vo) {
		PageInfo<EventDto> pageInfo = eventService.dataGrid(vo);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, pageInfo);
	}
	
	/**
	 * 添加
	 * @param event
	 * @return
	 * @author ya.liu
	 * @Date 2018年10月17日
	 */
	@RequestMapping(value = "events", method = RequestMethod.POST)
	public JSONObject addEvent(@RequestBody Event event) {
		eventService.addEvent(event);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}
	
	/**
	 * 修改
	 * @param event
	 * @return
	 * @author ya.liu
	 * @Date 2018年10月17日
	 */
	@RequestMapping(value = "events", method = RequestMethod.PUT)
	public JSONObject updateByPrimaryKey(@RequestBody Event event) {
		eventService.updateByPrimaryKey(event);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 * @author ya.liu
	 * @Date 2018年10月17日
	 */
	@RequestMapping(value = "events/{id}", method = RequestMethod.DELETE)
	public JSONObject deleteByPrimaryKey(@PathVariable("id")Integer id) {
		eventService.deleteByPrimaryKey(id);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}
	
	@RequestMapping(value="events/day/{count}", method=RequestMethod.GET)
	public JSONObject getDtoByDay(@PathVariable("count")Integer count) {
		// 获取时间段
		Date startTime = new Date();
		Date endTime = new Date();
		endTime.setDate(startTime.getDate() + count);
		
		// 封装
		EventVo vo = new EventVo();
		vo.setStartTime(startTime);
		vo.setEndTime(endTime);
		
		List<EventDto> list = eventService.getDtoListByDay(vo);
		for(EventDto dto :list) {
			dto.setStaffName(dto.getStaff().getName());
		}
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, list);
	}
}
