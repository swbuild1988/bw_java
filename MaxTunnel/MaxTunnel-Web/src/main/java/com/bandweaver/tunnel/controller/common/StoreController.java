package com.bandweaver.tunnel.controller.common;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.dto.StoreDto;
import com.bandweaver.tunnel.common.biz.dto.TunnelSimpleDto;
import com.bandweaver.tunnel.common.biz.itf.AreaService;
import com.bandweaver.tunnel.common.biz.itf.SectionService;
import com.bandweaver.tunnel.common.biz.itf.StoreService;
import com.bandweaver.tunnel.common.biz.itf.StoreTypeService;
import com.bandweaver.tunnel.common.biz.itf.TunnelService;
import com.bandweaver.tunnel.common.biz.pojo.Store;
import com.bandweaver.tunnel.common.biz.pojo.StoreType;
import com.bandweaver.tunnel.common.biz.pojo.Tunnel;
import com.bandweaver.tunnel.common.biz.vo.StoreVo;
import com.bandweaver.tunnel.common.platform.constant.StatusCodeEnum;
import com.bandweaver.tunnel.common.platform.exception.BandWeaverException;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.CommonUtil;
import com.bandweaver.tunnel.common.platform.util.DataTypeUtil;
import com.bandweaver.tunnel.common.platform.util.DateUtil;
import com.github.pagehelper.PageInfo;

/**
 *  管舱管理
 * @author shaosen
 * @date 2018年6月14日
 */
@Controller
@ResponseBody
public class StoreController {

	@Autowired
	private StoreService storeService;
	@Autowired
	private StoreTypeService storeTypeService;
	@Autowired
	private TunnelService tunnelService;
	@Autowired
	private AreaService areaService;
	@Autowired
	private SectionService sectionService;
	

	/** 添加管舱
	 * @param  name 管舱名称
	 * @param  tunnelId 管廊id
	 * @param  storeTypeId 管舱类型id
	 * @param  sn 
	 * @return {"msg":"请求成功","code":"200","data":{}}  
	 * @author shaosen
	 * @date 2018年7月25日
	 */
	@RequiresPermissions("store:add")
	@RequestMapping(value="stores",method=RequestMethod.POST)
	public JSONObject add(@RequestBody Store st) {
//		StoreVo v = new StoreVo();
//		v.setTunnelId(st.getTunnelId());
//		v.setStoreTypeId(st.getStoreTypeId());
//		if(storeService.getStoresByCondition(v).size() > 0) {
//			return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
//		}
		Store store = storeService.add(st);
		
		// 获取该store所属storeType对象
				StoreType type = storeTypeService.getById(store.getStoreTypeId());
				// 获取storeType的父storeType对象
				StoreType type1 = storeTypeService.getBySN(type.getParent());
				// 获取该store的父store对象
				StoreVo vo = new StoreVo();
				vo.setTunnelId(store.getTunnelId());
				vo.setStoreTypeId(type1.getId());
				List<StoreDto> list = storeService.getStoresByCondition(vo);
				// 修改该store的parentId
				if(list.size() > 0) {
					store.setParentId(list.get(0).getId());
					storeService.update(store);
				}
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}

	

	/** 批量添加管舱
	 * @param  name 管舱名称
	 * @param  tunnelId 管廊id
	 * @param  storeTypeId 管舱类型id
	 * @param sn
	 * @return {"msg":"请求成功","code":"200","data":{}}
	 * @author shaosen
	 * @date 2018年7月25日
	 */
	@RequiresPermissions("store:add")
	@RequestMapping(value="stores/multi",method=RequestMethod.POST)
	public JSONObject addMulti(@RequestBody List<Store> stores) {
		int i = 0;
		for (Store store : stores) {
//			StoreVo vo = new StoreVo();
//			vo.setTunnelId(store.getTunnelId());
//			vo.setStoreTypeId(store.getStoreTypeId());
//			if(storeService.getStoresByCondition(vo).size() > 0) continue;
			storeService.add(store);
			i++;
		}
		LogUtil.info("本次共批量生成[" + i + "]条store");
		List<StoreDto> list = storeService.getStoresByTunnelId(stores.get(0).getTunnelId());
		LogUtil.info("管廊id为" + stores.get(0).getTunnelId() + " 下共有store:" + list.size() + "条。");
		for(StoreDto dto : list) {
			// 获取该store所属storeType对象
			StoreType type = storeTypeService.getById(dto.getStoreTypeId());
			// 获取storeType的父storeType对象
			StoreType type1 = storeTypeService.getBySN(type.getParent());
			// 获取该store的父store对象
			StoreVo vo = new StoreVo();
			vo.setTunnelId(dto.getTunnelId());
			vo.setStoreTypeId(type1.getId());
			List<StoreDto> ls = storeService.getStoresByCondition(vo);
			// 修改该store的parentId
			Store store = new Store();
			store.setId(dto.getId());
			if(ls.size() > 0) {
				store.setParentId(ls.get(0).getId());
				storeService.update(store);
			}
		}
		LogUtil.info("store添加完成");
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}

	/**检查名称是否重复 
	 * @param name
	 * @return   true:可添加 false:不可添加
	 * @author shaosen
	 * @Date 2018年10月9日
	 */
	@RequestMapping(value="stores/ajax/{name}",method=RequestMethod.GET)
	public JSONObject checkName(@PathVariable String name) {
		Store store = storeService.getByName(name);
		return CommonUtil.success(store == null ? true : false);
	}
	
	/**更新 
	 * @param store 参考添加接口
	 * @return   {"msg":"请求成功","code":"200","data":{}}  
	 * @author shaosen
	 * @Date 2018年9月19日
	 */
	@RequiresPermissions("store:update")
	@RequestMapping(value="stores",method=RequestMethod.PUT)
	public JSONObject update(@RequestBody Store store) {
		storeService.update(store);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}
		

	/**
	 * 批量删除
	 */
	@RequiresPermissions("store:delete")
	@RequestMapping(value="stores/batch/{ids}",method=RequestMethod.DELETE)
	public JSONObject deleteBatch(@PathVariable String ids) {
		List<Integer> id_list = new ArrayList<>();
		String[] arr = ids.split(",");
		for (String str : arr) {
			id_list.add(DataTypeUtil.toInteger(str));
		}
		storeService.deleteBatch(id_list);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}
	
	/**
	 * 根据管廊id查询管仓列表
	 * @param  id 管廊id
	 * @return {"msg":"请求成功","code":"200","data":[{"id":1,"name":"燃气仓","tunnel":{"id":1,"name":"古城大街"},"storeType":{"id":1,"name":"燃气仓","crtTime":1535611490000},"camera":null,"crtTime":1535611490000},{"id":4,"name":"污水仓","tunnel":{"id":1,"name":"古城大街"},"storeType":{"id":4,"name":"污水仓","crtTime":1535611490000},"camera":null,"crtTime":1535611490000},{"id":3,"name":"电力仓","tunnel":{"id":1,"name":"古城大街"},"storeType":{"id":3,"name":"电力仓","crtTime":1535611490000},"camera":null,"crtTime":1535611490000},{"id":2,"name":"综合仓","tunnel":{"id":1,"name":"古城大街"},"storeType":{"id":2,"name":"综合仓","crtTime":1535611490000},"camera":null,"crtTime":1535611490000}]}  
	 * @author shaosen
	 * @date 2018年6月21日
	 */
	@RequiresPermissions("store:list")
	@RequestMapping(value="tunnels/{id}/stores",method=RequestMethod.GET)
	public JSONObject getStoresByTunnelId(@PathVariable Integer id) {
		List<StoreDto> list = storeService.getStoresByTunnelId(id);
		//orderby sn
		list = list.stream().sorted(Comparator.comparing(StoreDto::getSn)).collect(Collectors.toList());
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, list);
	}

	/**
	 * 根据id查询管仓
	 * @param  id 管舱id
	 * @return {"msg":"请求成功","code":"200","data":{"id":1,"name":"燃气仓","tunnel":{"id":1,"name":"古城大街"},"storeType":{"id":1,"name":"燃气仓","crtTime":1535611490000},"camera":null,"crtTime":1535611490000}}  
	 * @author shaosen
	 * @date 2018年6月21日
	 */
	@RequestMapping(value="tunnels/stores/{id}",method=RequestMethod.GET)
	public JSONObject getById(@PathVariable Integer id) {
		StoreDto dto = storeService.getStoreById(id);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, dto);
	}
	

	/**管舱列表分页查询
	 * 
	 * @param  name 管舱名称（支持模糊查询）
	 * @param  tunnelId 管廊id
	 * @param  storeTypeId 管舱类型id
	 * @param  startTime
	 * @param  endTime
	 * @param  pageNum 必须
	 * @param  pageSize 必须
	 * @return {"msg":"请求成功","code":"200","data":{"total":20,"list":[{"id":5,"name":"燃气仓","tunnel":{"id":2,"name":"实验路"},"storeType":{"id":1,"name":"燃气仓","crtTime":1535611490000},"camera":null,"crtTime":1535611491000},{"id":9,"name":"燃气仓","tunnel":{"id":3,"name":"经三路"},"storeType":{"id":1,"name":"燃气仓","crtTime":1535611490000},"camera":null,"crtTime":1535611491000},{"id":8,"name":"污水仓","tunnel":{"id":2,"name":"实验路"},"storeType":{"id":4,"name":"污水仓","crtTime":1535611490000},"camera":null,"crtTime":1535611491000},{"id":7,"name":"电力仓","tunnel":{"id":2,"name":"实验路"},"storeType":{"id":3,"name":"电力仓","crtTime":1535611490000},"camera":null,"crtTime":1535611491000},{"id":6,"name":"综合仓","tunnel":{"id":2,"name":"实验路"},"storeType":{"id":2,"name":"综合仓","crtTime":1535611490000},"camera":null,"crtTime":1535611491000}],"pageNum":1,"pageSize":5,"size":5,"startRow":1,"endRow":5,"pages":4,"prePage":0,"nextPage":2,"isFirstPage":true,"isLastPage":false,"hasPreviousPage":false,"hasNextPage":true,"navigatePages":8,"navigatepageNums":[1,2,3,4],"navigateFirstPage":1,"navigateLastPage":4,"lastPage":4,"firstPage":1}}  
	 * @author shaosen
	 * @Date 2018年7月26日
	 */
	@RequiresPermissions("store:list")
	@RequestMapping(value="tunnels/stores/datagrid",method=RequestMethod.POST)
	public JSONObject dataGrid(@RequestBody StoreVo vo) {
		PageInfo<StoreDto> pageInfo = storeService.dataGrid(vo);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, pageInfo);
	}


	
}
