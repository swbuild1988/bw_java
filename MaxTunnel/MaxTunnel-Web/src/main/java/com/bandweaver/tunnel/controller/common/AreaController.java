package com.bandweaver.tunnel.controller.common;

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
import com.bandweaver.tunnel.common.biz.dto.AreaDto;
import com.bandweaver.tunnel.common.biz.dto.TunnelSimpleDto;
import com.bandweaver.tunnel.common.biz.itf.AreaService;
import com.bandweaver.tunnel.common.biz.itf.TunnelService;
import com.bandweaver.tunnel.common.biz.pojo.Area;
import com.bandweaver.tunnel.common.biz.vo.AreaVo;
import com.bandweaver.tunnel.common.platform.constant.StatusCodeEnum;
import com.bandweaver.tunnel.common.platform.util.CommonUtil;
import com.bandweaver.tunnel.common.platform.util.DataTypeUtil;
import com.bandweaver.tunnel.common.platform.util.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 管廊区域管理
 * @author shaosen
 * @date 2018年7月26日
 */
@Controller
@ResponseBody
public class AreaController extends BaseController<Area>{

	@Autowired
	private AreaService areaService;
	
	/**添加区域
	 * @param 	name  区域名称
	 * @param   tunnelId 管廊id
	 * @param   camera 相机视角（字符串）
	 * @return {"msg":"请求成功","code":"200","data":{}}  
	 * @author shaosen
	 * @date 2018年6月11日
	 */
	@RequestMapping(value="areas",method=RequestMethod.POST)
	public JSONObject add(@RequestBody Area area) {
		areaService.addArea(area);
		return success();
	}

	/**添加区域
	 */
	@RequestMapping(value="areas/multi",method=RequestMethod.POST)
	public JSONObject addMulti(@RequestBody List<Area> areas) {
		for (Area area : areas) {
			area.setCrtTime(new Date());
		}
		areaService.addAreaBatch(areas);
		return success();
	}
	
	/**检查名称是否重复 
	 * @param name
	 * @return   true:可添加 false:重复不可添加
	 * @author shaosen
	 * @Date 2018年10月9日
	 */
	@RequestMapping(value="areas/ajax/{name}",method=RequestMethod.GET)
	public JSONObject checkName(@PathVariable String name) {
		Area area = areaService.getByName(name);
		return success(area == null ? true : false);
	}
	
	
	/**更新 
	 * @param area 参考添加接口
	 * @return    {"msg":"请求成功","code":"200","data":{}}  
	 * @author shaosen
	 * @Date 2018年9月19日
	 */
	@RequestMapping(value="areas",method=RequestMethod.PUT)
	public JSONObject update(@RequestBody Area area) {
		areaService.update(area);
		return success();
	}
	
	/**删除 
	 * @param id
	 * @return    {"msg":"请求成功","code":"200","data":{}}  
	 * @author shaosen
	 * @Date 2018年9月19日
	 */
	@RequestMapping(value="areas/{id}",method=RequestMethod.DELETE)
	public JSONObject delete(@PathVariable Integer id) {
		areaService.delete(id);
		return success();
	}
	
	/**通过区域id获取区域
	 * @param  id 区域id
	 * @return {"msg":"请求成功","code":"200","data":{"id":1,"name":"区域1","location":"位置1","tunnel":{"id":1,"name":"凤岭北路"},"camera":"1111-2222-333","crtTime":1532499849000}}  
	 * @author shaosen
	 * @date 2018年6月11日
	 */
	@RequestMapping(value="areas/{id}",method=RequestMethod.GET)
	public JSONObject getById(@PathVariable Integer id) {
		AreaVo areaVo = new AreaVo();
		areaVo.setId(id);
		List<AreaDto> list = areaService.getAreasByCondition(areaVo);
		if(list.size()>0) {
			return success(list.get(0));
		}
		return success();
	}
	
	/**根据管廊id查询区域列表
	 * @param  id 管廊id
	 * @return {"msg":"请求成功","code":"200","data":[{"id":1,"name":"区域1","location":"位置1","tunnel":{"id":1,"name":"凤岭北路"},"camera":"1111-2222-333","crtTime":1532499849000},{"id":12,"name":"凤岭北路-区域-0","location":"凤岭北路-区域-0-位置","tunnel":{"id":1,"name":"凤岭北路"},"camera":null,"crtTime":1529903396000},{"id":13,"name":"凤岭北路-区域-1","location":"凤岭北路-区域-1-位置","tunnel":{"id":1,"name":"凤岭北路"},"camera":null,"crtTime":1529816996000},{"id":14,"name":"凤岭北路-区域-2","location":"凤岭北路-区域-2-位置","tunnel":{"id":1,"name":"凤岭北路"},"camera":null,"crtTime":1529730596000},{"id":15,"name":"凤岭北路-区域-3","location":"凤岭北路-区域-3-位置","tunnel":{"id":1,"name":"凤岭北路"},"camera":null,"crtTime":1529644196000},{"id":16,"name":"凤岭北路-区域-4","location":"凤岭北路-区域-4-位置","tunnel":{"id":1,"name":"凤岭北路"},"camera":null,"crtTime":1529557796000}]}  
	 * @author shaosen
	 * @date 2018年6月11日
	 */
	@RequestMapping(value="tunnels/{id}/areas",method=RequestMethod.GET)
	public JSONObject getAreasByTunnelId(@PathVariable Integer id) {
		AreaVo areaVo = new AreaVo();
		areaVo.setTunnelId(id);
		List<AreaDto> list = areaService.getAreasByCondition(areaVo);
		return success(list);
	}
	
	
	/**
	 * 区域分页查询
	 * @param  name 区域名称（支持模糊查询）
	 * @param  tunnelId 管廊id
	 * @param  startTime
	 * @param  endTime
	 * @param  pageNum 必须
	 * @param  pageSize 必须
	 * @param    
	 * @return {"msg":"请求成功","code":"200","data":{"total":26,"list":[{"id":1,"name":"区域1","location":"位置1","tunnel":{"id":1,"name":"凤岭北路"},"camera":"1111-2222-333","crtTime":1532499849000},{"id":2,"name":"长虹路-区域-0","location":"长虹路-区域-0-位置","tunnel":{"id":2,"name":"长虹路"},"camera":null,"crtTime":1529903396000}],"pageNum":1,"pageSize":2,"size":2,"startRow":1,"endRow":2,"pages":13,"prePage":0,"nextPage":2,"isFirstPage":true,"isLastPage":false,"hasPreviousPage":false,"hasNextPage":true,"navigatePages":8,"navigatepageNums":[1,2,3,4,5,6,7,8],"navigateFirstPage":1,"navigateLastPage":8,"firstPage":1,"lastPage":8}}  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月11日
	 */
	@RequestMapping(value="areas/datagrid",method=RequestMethod.POST)
	public JSONObject dataGrid(@RequestBody AreaVo areaVo) {
		PageInfo<AreaDto> pageInfo = areaService.dataGrid(areaVo);
		return success(pageInfo);
	}


	/**
	 * 批量删除
	 */
	@Override
	@RequestMapping(value="areas/batch/{ids}",method=RequestMethod.DELETE)
	public JSONObject deleteBatch(@PathVariable String ids) {
		List<Integer> list = convertStringToList(ids);	
		areaService.deleteBatch(list);
		return success();
	}

	
}
