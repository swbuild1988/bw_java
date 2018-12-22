package com.bandweaver.tunnel.controller.omm;

import java.util.ArrayList;
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
import com.bandweaver.tunnel.common.biz.dto.omm.CheckingPointDto;
import com.bandweaver.tunnel.common.biz.itf.omm.CheckingPointService;
import com.bandweaver.tunnel.common.biz.pojo.omm.CheckingPoint;
import com.bandweaver.tunnel.common.biz.vo.omm.CheckingPointVo;
import com.bandweaver.tunnel.common.platform.constant.StatusCodeEnum;
import com.bandweaver.tunnel.common.platform.util.CommonUtil;
import com.bandweaver.tunnel.common.platform.util.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * ClassName: CheckingPointController
 * @Description: 巡检点管理
 * @author shaosen
 * @date 2018年6月4日
 */
@Controller
@ResponseBody
public class CheckingPointController {

	@Autowired
	private CheckingPointService checkingPointService;
	
	
	/**
	 * @Description: 添加巡检点
	 * @param @param checkingPoint
	 * @param @return   
	 * @return JSONObject  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月4日
	 */
	@RequestMapping(value="/checkingpoints",method=RequestMethod.POST)
	public JSONObject addCheckingPoint(@RequestBody CheckingPoint checkingPoint) {
		checkingPointService.addCheckingPoint(checkingPoint);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}
	
	/**
	 * @Description: 添加测试数据
	 * @param @return   
	 * @return JSONObject  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月12日
	 */
	@RequestMapping(value="/checkingpoints/batch",method=RequestMethod.GET)
	public JSONObject addTestData() {
		
		List<CheckingPoint> list = new ArrayList<>();
		for (int i = 0; i < 200; i++) {
			CheckingPoint pojo = new CheckingPoint();
			pojo.setName("巡检点-"+i);
			pojo.setPosition("位置-"+i);
			pojo.setTunnelId((int)(Math.random()*6)+1);
			pojo.setCrtTime(DateUtil.getFrontDay(new Date(), i));
			list.add(pojo);
		}
		checkingPointService.addCheckingPointBatch(list);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}
	
	
	
	/**
	 * @Description: 获取所有巡检点列表
	 * @param @return   
	 * @return JSONObject  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月6日
	 */
	@RequestMapping(value="/checkingpoints",method=RequestMethod.GET)
	public JSONObject getAllCheckingPointList() {
		List<CheckingPointDto> dtoList = checkingPointService.getAllCheckingPointList();
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, dtoList);
	}
	
	/**
	 * @Description: 分页查询
	 * @param @param dto
	 * 				查询字段，可以为巡检点id，管廊id，时间，巡检点名称（支持模糊查询）
	 * @param @return   
	 * @return JSONObject  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月4日
	 */
	@RequestMapping(value="/checkingpoints/datagrid",method=RequestMethod.POST)
	public JSONObject dataGrid(@RequestBody CheckingPointVo vo) {
		PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
		List<CheckingPointDto> dtoList = checkingPointService.getCheckingPointListByConditon(vo);
		PageInfo<CheckingPointDto> pageInfo = new PageInfo<>(dtoList);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, pageInfo);
	}
	
	
	/**
	 * @Description: 根据主键id删除巡检点
	 * @param @param id
	 * @param @return   
	 * @return JSONObject  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月4日
	 */
	@RequestMapping(value="/checkingpoints/{id}",method=RequestMethod.DELETE)
	public JSONObject deleteCheckingPointById(@PathVariable Integer id) {
		checkingPointService.deleteCheckingPointById(id);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}
	
	/**
	 * @Description: 根据主键id查询巡检点
	 * @param @param id
	 * @param @return   
	 * @return JSONObject  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月6日
	 */
	@RequestMapping(value="/checkingpoints/{id}",method=RequestMethod.GET)
	public JSONObject getCheckingPointById(@PathVariable Integer id) {
		CheckingPointDto dto = checkingPointService.getCheckingPointById(id);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,dto);
	}
	
	
	/**
	 * @Description: 通过管廊id获取所有巡检点
	 * @param @param id
	 * @param @return   
	 * @return JSONObject  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月12日
	 */
	@RequestMapping(value="tunnels/{id}/checkingpoints",method=RequestMethod.GET)
	public JSONObject getCheckingPointByTunnelId(@PathVariable Integer id) {
		CheckingPointVo vo = new CheckingPointVo();
		vo.setTunnelId(id);
		List<CheckingPointDto> list = checkingPointService.getCheckingPointListByConditon(vo);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,list);

	}
	
}
