package com.bandweaver.tunnel.controller.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.itf.StoreTypeService;
import com.bandweaver.tunnel.common.biz.pojo.StoreType;
import com.bandweaver.tunnel.common.biz.vo.StoreTypeVo;
import com.bandweaver.tunnel.common.platform.constant.StatusCodeEnum;
import com.bandweaver.tunnel.common.platform.util.CommonUtil;
import com.bandweaver.tunnel.common.platform.util.DataTypeUtil;
import com.github.pagehelper.PageInfo;

/**管舱类型管理
 * @author shaosen
 * @date 2018年9月17日
 */
@Controller
@ResponseBody
public class StoreTypeController extends BaseController<StoreType>{
	
	@Autowired
	private StoreTypeService storeTypeService;
	
	/**添加管舱类型
	 * @param storeType
	 * @return   
	 * @author shaosen
	 * @Date 2018年9月17日
	 */
	@RequestMapping(value="store-type",method=RequestMethod.POST)
	public JSONObject add(@RequestBody StoreType storeType) {
		storeTypeService.insert(storeType);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}
	
	/**检查名称是否重复 
	 * @param name
	 * @return   true:可添加 false:不可添加
	 * @author shaosen
	 * @Date 2018年10月9日
	 */
	@RequestMapping(value="store-type/ajax/{name}",method=RequestMethod.GET)
	public JSONObject checkName(@PathVariable String name) {
		StoreType storeType = storeTypeService.getByName(name);
		return success(storeType == null ? true : false);
	}
	
	/**通过主键ID获取管舱类型 
	 * @param id
	 * @return   
	 * @author shaosen
	 * @Date 2018年9月17日
	 */
	@RequestMapping(value="store-type/{id}",method=RequestMethod.GET)
	public JSONObject getById(Integer id) {
		StoreType storeType = storeTypeService.getById(id);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, storeType);
	}
	
	/**获取所有管舱类型 
	 * @return   
	 * @author shaosen
	 * @Date 2018年9月17日
	 */
	@RequestMapping(value="store-type/list",method=RequestMethod.GET)
	public JSONObject getList() {
		List<StoreType> list = storeTypeService.getList();
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, list);
	}
	
	/**修改管舱类型 
	 * @param storeType
	 * @return   
	 * @author shaosen
	 * @Date 2018年9月17日
	 */
	@RequestMapping(value="store-type",method=RequestMethod.PUT)
	public JSONObject update(@RequestBody StoreType storeType) {
		storeTypeService.update(storeType);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}


	/**
	 * 删除
	 */
	@RequestMapping(value="store-type/{id}",method=RequestMethod.DELETE)
	@Override
	public JSONObject delete(@PathVariable Integer id) {
		storeTypeService.delete(id);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}


	/**
	 * 批量删除
	 */
	@Override
	@RequestMapping(value="store-type/batch/{ids}",method=RequestMethod.DELETE)
	public JSONObject deleteBatch(@PathVariable String ids) {
		List<Integer> list = convertStringToList(ids);
		storeTypeService.deleteBatch(list);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}
	
	
	/**分页查询 
	 * @param name 类型名称(支持模糊查询)
	 * @param startTime
	 * @param endTime
	 * @param pageNum 必须
	 * @param pageSize 必须
	 * @return   {"msg":"请求成功","code":"200","data":{"total":4,"list":[{"id":1,"name":"燃气仓","crtTime":1535611490000},{"id":2,"name":"综合仓","crtTime":1535611490000},{"id":3,"name":"电力仓","crtTime":1535611490000},{"id":4,"name":"污水仓","crtTime":1535611490000}],"pageNum":1,"pageSize":5,"size":4,"startRow":1,"endRow":4,"pages":1,"prePage":0,"nextPage":0,"isFirstPage":true,"isLastPage":true,"hasPreviousPage":false,"hasNextPage":false,"navigatePages":8,"navigatepageNums":[1],"navigateFirstPage":1,"navigateLastPage":1,"lastPage":1,"firstPage":1}}
	 * @author shaosen
	 * @Date 2018年9月27日
	 */
	@RequestMapping(value="store-type/datagrid",method=RequestMethod.POST)
	public JSONObject dataGrid(@RequestBody StoreTypeVo vo) {
		PageInfo<StoreType> pageInfo = storeTypeService.dataGrid(vo);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, pageInfo);
	}
	
} 
