package com.bandweaver.tunnel.controller.common;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.dto.AreaDto;
import com.bandweaver.tunnel.common.biz.itf.AreaService;
import com.bandweaver.tunnel.common.biz.pojo.Area;
import com.bandweaver.tunnel.common.biz.vo.AreaVo;
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
	@Override
	@RequiresPermissions("area:add")
	@RequestMapping(value="areas",method=RequestMethod.POST)
	public JSONObject add(@RequestBody Area area) {
		areaService.addArea(area);
		return success();
	}

	@Override
	public JSONObject delete(Integer id) throws Exception {
		return null;
	}

	/**添加区域
	 */
	@RequiresPermissions("area:add")
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
	@Override
	@RequiresPermissions("area:update")
	@RequestMapping(value="areas",method=RequestMethod.PUT)
	public JSONObject update(@RequestBody Area area) {
		areaService.update(area);
		return success();
	}
	
	/**通过区域id获取区域
	 * @param  id 区域id
	 * @return {"msg":"请求成功","code":"200","data":{"id":1,"name":"区域1","location":"位置1","tunnel":{"id":1,"name":"凤岭北路"},"camera":"1111-2222-333","crtTime":1532499849000}}  
	 * @author shaosen
	 * @date 2018年6月11日
	 */
	@Override
	@RequestMapping(value="areas/{id}",method=RequestMethod.GET)
	public JSONObject getById(@PathVariable Integer id) {
		AreaDto dto = areaService.getAreasById(id);
		return success(dto);
	}
	
	/**根据管廊id查询区域列表
	 * @param  id 管廊id
	 * @return {"msg":"请求成功","code":"200","data":[{"id":1,"name":"区域1","location":"位置1","tunnel":{"id":1,"name":"凤岭北路"},"camera":"1111-2222-333","crtTime":1532499849000},{"id":12,"name":"凤岭北路-区域-0","location":"凤岭北路-区域-0-位置","tunnel":{"id":1,"name":"凤岭北路"},"camera":null,"crtTime":1529903396000},{"id":13,"name":"凤岭北路-区域-1","location":"凤岭北路-区域-1-位置","tunnel":{"id":1,"name":"凤岭北路"},"camera":null,"crtTime":1529816996000},{"id":14,"name":"凤岭北路-区域-2","location":"凤岭北路-区域-2-位置","tunnel":{"id":1,"name":"凤岭北路"},"camera":null,"crtTime":1529730596000},{"id":15,"name":"凤岭北路-区域-3","location":"凤岭北路-区域-3-位置","tunnel":{"id":1,"name":"凤岭北路"},"camera":null,"crtTime":1529644196000},{"id":16,"name":"凤岭北路-区域-4","location":"凤岭北路-区域-4-位置","tunnel":{"id":1,"name":"凤岭北路"},"camera":null,"crtTime":1529557796000}]}  
	 * @author shaosen
	 * @date 2018年6月11日
	 */
	@RequiresPermissions("area:list")
	@RequestMapping(value="tunnels/{id}/areas",method=RequestMethod.GET)
	public JSONObject getAreasByTunnelId(@PathVariable Integer id) {
		List<AreaDto> list = areaService.getAreasByTunnelId(id);
		//orderby sn
		list = list.stream().sorted(Comparator.comparing(AreaDto::getSn)).collect(Collectors.toList());
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
	@RequiresPermissions("area:list")
	@RequestMapping(value="areas/datagrid",method=RequestMethod.POST)
	public JSONObject dataGrid(@RequestBody AreaVo areaVo) {
		PageInfo<AreaDto> pageInfo = areaService.dataGrid(areaVo);
		return success(pageInfo);
	}


	/**
	 * 批量删除
	 */
	@RequiresPermissions("area:delete")
	@Override
	@RequestMapping(value="areas/batch/{ids}",method=RequestMethod.DELETE)
	public JSONObject deleteBatch(@PathVariable String ids) {
		List<Integer> list = convertStringToList(ids);	
		areaService.deleteBatch(list);
		return success();
	}

}
