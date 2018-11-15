package com.bandweaver.tunnel.controller.common;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.constant.MonitorTypeEnum;
import com.bandweaver.tunnel.common.biz.constant.mam.DataType;
import com.bandweaver.tunnel.common.biz.constant.mam.ObjectType;
import com.bandweaver.tunnel.common.biz.dto.SectionDto;
import com.bandweaver.tunnel.common.biz.dto.StoreDto;
import com.bandweaver.tunnel.common.biz.dto.TunnelDto;
import com.bandweaver.tunnel.common.biz.dto.TunnelSimpleDto;
import com.bandweaver.tunnel.common.biz.dto.mam.MeasObjAIParam;
import com.bandweaver.tunnel.common.biz.dto.mam.MeasObjDto;
import com.bandweaver.tunnel.common.biz.itf.SectionService;
import com.bandweaver.tunnel.common.biz.itf.StoreService;
import com.bandweaver.tunnel.common.biz.itf.StoreTypeService;
import com.bandweaver.tunnel.common.biz.itf.TunnelService;
import com.bandweaver.tunnel.common.biz.itf.mam.measobj.MeasObjService;
import com.bandweaver.tunnel.common.biz.pojo.StoreType;
import com.bandweaver.tunnel.common.biz.pojo.Tunnel;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObj;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObjAI;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObjDI;
import com.bandweaver.tunnel.common.biz.vo.BaseVo;
import com.bandweaver.tunnel.common.biz.vo.TunnelVo;
import com.bandweaver.tunnel.common.biz.vo.mam.MeasObjVo;
import com.bandweaver.tunnel.common.platform.constant.StatusCodeEnum;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.CommonUtil;
import com.bandweaver.tunnel.common.platform.util.DataTypeUtil;
import com.bandweaver.tunnel.common.platform.util.PropertiesUtil;
import com.bandweaver.tunnel.service.mam.measobj.MeasObjModuleCenter;
import com.github.pagehelper.PageInfo;

/**
 * 管廊管理
 * 
 * @author shaosen
 * @date 2018年7月26日
 */
@Controller
@ResponseBody
public class TunnelController extends BaseController<Tunnel> {
	@Autowired
	private TunnelService tunnelService;
	@Autowired
	private StoreService storeService;
	@Autowired
	private SectionService sectionService;
	@Autowired
	private MeasObjModuleCenter measObjModuleCenter;
	@Autowired
	private MeasObjService measObjService;

	//*********************************************************************************************
	//****************************************增删改查START******************************************
	//*********************************************************************************************
	
	/**添加管廊
	 * @param name             管廊名称
	 * @param length           管廊长度（数值）
	 * @param responsibilityId 负责人id（员工表）
	 * @param constructId      建筑单位id（公司表）
	 * @param operationId      运营单位（公司表）
	 * @param camera           相机视角（字符串）
	 * @return {"msg":"请求成功","code":"200","data":{}}
	 * @author shaosen
	 * @date 2018年7月25日
	 */
	@RequestMapping(value = "tunnels", method = RequestMethod.POST)
	public JSONObject add(@RequestBody Tunnel tunnel) {
		tunnelService.add(tunnel);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}
	
	/**检查名称是否重复 
	 * @param name 管廊名称
	 * @return   ture：可以添加  false:重复不可添加
	 * @author shaosen
	 * @Date 2018年10月9日
	 */
	@RequestMapping(value="tunnels/ajax/{name}",method=RequestMethod.GET)
	public JSONObject checkName(@PathVariable String name) {
		LogUtil.info("name:" + name);
		Tunnel tunnel = tunnelService.getByName(name);
		LogUtil.info("Get tunnel:" + tunnel);
		return success(tunnel == null ? true : false);
	}
	
	

	/**获取管廊列表(简单列表)
	 * @return {"msg":"请求成功","code":"200","data":[{"id":1,"name":"古城大街"},{"id":2,"name":"实验路"},{"id":3,"name":"经三路"},{"id":4,"name":"经二路"},{"id":5,"name":"纬三路"}]}
	 * @author shaosen
	 * @date 2018年6月7日
	 */
	@RequestMapping(value = "tunnels", method = RequestMethod.GET)
	public JSONObject getList() {
		List<TunnelSimpleDto> tunnels = tunnelService.getList();
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, tunnels);
	}

	/**通过id获取管廊信息（包含负责人，建筑单位，运营单位信息）
	 * @param id 管廊id
	 * @return {"msg":"请求成功","code":"200","data":{"id":1,"name":"古城大街","length":5000.0,"responsibility":{"id":1,"name":"张三","deptId":1,"positionId":1,"accountId":1,"crtTime":1529942400000},"construct":{"id":5,"name":"中建一局","crtTime":1529596800000},"operation":{"id":1,"name":"波汇科技","crtTime":1529596800000},"camera":null,"crtTime":1535611490000}}
	 * @author shaosen
	 * @date 2018年6月27日
	 */
	@RequestMapping(value = "tunnels/{id}", method = RequestMethod.GET)
	public JSONObject getById(@PathVariable Integer id) {
		TunnelDto dto = tunnelService.getDtoById(id);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, dto);
	}
	
	
	
	/**通过主键更新
	 * @param tunnel 参数参考添加接口
	 * @return   {"msg":"请求成功","code":"200","data":{}}
	 * @author shaosen
	 * @Date 2018年9月19日
	 */
	@RequestMapping(value="tunnels",method=RequestMethod.PUT)
	public JSONObject update(@RequestBody Tunnel tunnel) {
		tunnelService.update(tunnel);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}

	/**通过主键删除 
	 * @param id
	 * @return  {"msg":"请求成功","code":"200","data":{}}
	 * @author shaosen
	 * @Date 2018年9月19日
	 */
	@RequestMapping(value="tunnels/{id}",method=RequestMethod.DELETE)
	public JSONObject delete(@PathVariable Integer id) {
		tunnelService.delete(id);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}
	
	/**批量删除 
	 * @param ids id通过逗号拼接的字符串
	 * @return   
	 * @author shaosen
	 * @Date 2018年9月21日
	 */
	@RequestMapping(value="tunnels/batch/{ids}",method=RequestMethod.DELETE)
	public JSONObject deleteBatch(@PathVariable String ids) {
		
		LogUtil.info("ids : " + ids );
		String[] arr = ids.split(",");
		List<Integer> id_list = new ArrayList<>();
		for (String str : arr) {
			id_list.add(DataTypeUtil.toInteger(str));
		}
		tunnelService.deleteBatch(id_list);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}
	
	
	
	/**管廊分页查询 
	 * @param name 管廊名称（支持模糊查询）
	 * @param responsibilityId 负责人id
	 * @param constructId 建筑单位id
	 * @param operationId 营运单位id
	 * @param startTime
	 * @param endTime
	 * @param pageNum 必须
	 * @param pageSize 必须
	 * @return   {"msg":"请求成功","code":"200","data":{"total":5,"list":[{"id":2,"name":"实验路","length":1000.0,"responsibility":{"id":1,"name":"张三","deptId":1,"positionId":1,"accountId":1,"crtTime":1529942400000},"construct":{"id":5,"name":"中建一局","crtTime":1529596800000},"operation":{"id":1,"name":"波汇科技","crtTime":1529596800000},"camera":null,"crtTime":1535611491000},{"id":5,"name":"纬三路","length":5000.0,"responsibility":{"id":1,"name":"张三","deptId":1,"positionId":1,"accountId":1,"crtTime":1529942400000},"construct":{"id":5,"name":"中建一局","crtTime":1529596800000},"operation":{"id":1,"name":"波汇科技","crtTime":1529596800000},"camera":null,"crtTime":1535611491000},{"id":4,"name":"经二路","length":1000.0,"responsibility":{"id":1,"name":"张三","deptId":1,"positionId":1,"accountId":1,"crtTime":1529942400000},"construct":{"id":5,"name":"中建一局","crtTime":1529596800000},"operation":{"id":1,"name":"波汇科技","crtTime":1529596800000},"camera":null,"crtTime":1535611491000},{"id":3,"name":"经三路","length":1000.0,"responsibility":{"id":1,"name":"张三","deptId":1,"positionId":1,"accountId":1,"crtTime":1529942400000},"construct":{"id":5,"name":"中建一局","crtTime":1529596800000},"operation":{"id":1,"name":"波汇科技","crtTime":1529596800000},"camera":null,"crtTime":1535611491000},{"id":1,"name":"古城大街","length":5000.0,"responsibility":{"id":1,"name":"张三","deptId":1,"positionId":1,"accountId":1,"crtTime":1529942400000},"construct":{"id":5,"name":"中建一局","crtTime":1529596800000},"operation":{"id":1,"name":"波汇科技","crtTime":1529596800000},"camera":null,"crtTime":1535611490000}],"pageNum":1,"pageSize":5,"size":5,"startRow":1,"endRow":5,"pages":1,"prePage":0,"nextPage":0,"isFirstPage":true,"isLastPage":true,"hasPreviousPage":false,"hasNextPage":false,"navigatePages":8,"navigatepageNums":[1],"navigateFirstPage":1,"navigateLastPage":1,"firstPage":1,"lastPage":1}}
	 * @author shaosen
	 * @Date 2018年9月19日
	 */
	@RequestMapping(value="tunnels/datagrid",method=RequestMethod.POST)
	public JSONObject dataGrid(@RequestBody TunnelVo vo) {
		PageInfo<TunnelDto> pageInfo = tunnelService.dataGrid(vo);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, pageInfo);
	}
	
	//*********************************************************************************************
	//****************************************增删改查END******************************************
	//*********************************************************************************************
	
	
	/**获取树形结构
	 * @param
	 * @return {"msg":"请求成功","code":"200","data":[{"name":"古城大街","id":1,"camera":null,"list":[{"name":"燃气仓","id":1,"camera":null,"list":[{"name":"11区-燃气仓","id":11,"camera":null,"list":null},{"name":"21区-燃气仓","id":21,"camera":null,"list":null},{"name":"20区-燃气仓","id":20,"camera":null,"list":null},{"name":"19区-燃气仓","id":19,"camera":null,"list":null},{"name":"5区-燃气仓","id":5,"camera":null,"list":null},{"name":"6区-燃气仓","id":6,"camera":null,"list":null},{"name":"7区-燃气仓","id":7,"camera":null,"list":null},{"name":"8区-燃气仓","id":8,"camera":null,"list":null},{"name":"9区-燃气仓","id":9,"camera":null,"list":null},{"name":"10区-燃气仓","id":10,"camera":null,"list":null},{"name":"22区-燃气仓","id":22,"camera":null,"list":null},{"name":"12区-燃气仓","id":12,"camera":null,"list":null},{"name":"13区-燃气仓","id":13,"camera":null,"list":null},{"name":"14区-燃气仓","id":14,"camera":null,"list":null},{"name":"15区-燃气仓","id":15,"camera":null,"list":null},{"name":"16区-燃气仓","id":16,"camera":null,"list":null},{"name":"17区-燃气仓","id":17,"camera":null,"list":null},{"name":"18区-燃气仓","id":18,"camera":null,"list":null},{"name":"4区-燃气仓","id":4,"camera":null,"list":null},{"name":"3区-燃气仓","id":3,"camera":null,"list":null},{"name":"2区-燃气仓","id":2,"camera":null,"list":null},{"name":"1区-燃气仓","id":1,"camera":null,"list":null}]},{"name":"污水仓","id":4,"camera":null,"list":[{"name":"1区-污水仓","id":23,"camera":null,"list":null},{"name":"22区-污水仓","id":44,"camera":null,"list":null},{"name":"3区-污水仓","id":25,"camera":null,"list":null},{"name":"4区-污水仓","id":26,"camera":null,"list":null},{"name":"5区-污水仓","id":27,"camera":null,"list":null},{"name":"6区-污水仓","id":28,"camera":null,"list":null},{"name":"7区-污水仓","id":29,"camera":null,"list":null},{"name":"8区-污水仓","id":30,"camera":null,"list":null},{"name":"9区-污水仓","id":31,"camera":null,"list":null},{"name":"10区-污水仓","id":32,"camera":null,"list":null},{"name":"11区-污水仓","id":33,"camera":null,"list":null},{"name":"12区-污水仓","id":34,"camera":null,"list":null},{"name":"13区-污水仓","id":35,"camera":null,"list":null},{"name":"14区-污水仓","id":36,"camera":null,"list":null},{"name":"15区-污水仓","id":37,"camera":null,"list":null},{"name":"16区-污水仓","id":38,"camera":null,"list":null},{"name":"17区-污水仓","id":39,"camera":null,"list":null},{"name":"18区-污水仓","id":40,"camera":null,"list":null},{"name":"19区-污水仓","id":41,"camera":null,"list":null},{"name":"20区-污水仓","id":42,"camera":null,"list":null},{"name":"21区-污水仓","id":43,"camera":null,"list":null},{"name":"2区-污水仓","id":24,"camera":null,"list":null}]},{"name":"电力仓","id":3,"camera":null,"list":[{"name":"1区-电力仓","id":45,"camera":null,"list":null},{"name":"22区-电力仓","id":66,"camera":null,"list":null},{"name":"3区-电力仓","id":47,"camera":null,"list":null},{"name":"4区-电力仓","id":48,"camera":null,"list":null},{"name":"5区-电力仓","id":49,"camera":null,"list":null},{"name":"6区-电力仓","id":50,"camera":null,"list":null},{"name":"7区-电力仓","id":51,"camera":null,"list":null},{"name":"8区-电力仓","id":52,"camera":null,"list":null},{"name":"9区-电力仓","id":53,"camera":null,"list":null},{"name":"10区-电力仓","id":54,"camera":null,"list":null},{"name":"11区-电力仓","id":55,"camera":null,"list":null},{"name":"12区-电力仓","id":56,"camera":null,"list":null},{"name":"13区-电力仓","id":57,"camera":null,"list":null},{"name":"14区-电力仓","id":58,"camera":null,"list":null},{"name":"15区-电力仓","id":59,"camera":null,"list":null},{"name":"16区-电力仓","id":60,"camera":null,"list":null},{"name":"17区-电力仓","id":61,"camera":null,"list":null},{"name":"18区-电力仓","id":62,"camera":null,"list":null},{"name":"19区-电力仓","id":63,"camera":null,"list":null},{"name":"20区-电力仓","id":64,"camera":null,"list":null},{"name":"21区-电力仓","id":65,"camera":null,"list":null},{"name":"2区-电力仓","id":46,"camera":null,"list":null}]},{"name":"综合仓","id":2,"camera":null,"list":[{"name":"1区-综合仓","id":67,"camera":null,"list":null},{"name":"22区-综合仓","id":88,"camera":null,"list":null},{"name":"3区-综合仓","id":69,"camera":null,"list":null},{"name":"4区-综合仓","id":70,"camera":null,"list":null},{"name":"5区-综合仓","id":71,"camera":null,"list":null},{"name":"6区-综合仓","id":72,"camera":null,"list":null},{"name":"7区-综合仓","id":73,"camera":null,"list":null},{"name":"8区-综合仓","id":74,"camera":null,"list":null},{"name":"9区-综合仓","id":75,"camera":null,"list":null},{"name":"10区-综合仓","id":76,"camera":null,"list":null},{"name":"11区-综合仓","id":77,"camera":null,"list":null},{"name":"12区-综合仓","id":78,"camera":null,"list":null},{"name":"13区-综合仓","id":79,"camera":null,"list":null},{"name":"14区-综合仓","id":80,"camera":null,"list":null},{"name":"15区-综合仓","id":81,"camera":null,"list":null},{"name":"16区-综合仓","id":82,"camera":null,"list":null},{"name":"17区-综合仓","id":83,"camera":null,"list":null},{"name":"18区-综合仓","id":84,"camera":null,"list":null},{"name":"19区-综合仓","id":85,"camera":null,"list":null},{"name":"20区-综合仓","id":86,"camera":null,"list":null},{"name":"21区-综合仓","id":87,"camera":null,"list":null},{"name":"2区-综合仓","id":68,"camera":null,"list":null}]}]},{"name":"实验路","id":2,"camera":null,"list":[{"name":"燃气仓","id":5,"camera":null,"list":[{"name":"1区-燃气仓","id":89,"camera":null,"list":null},{"name":"2区-燃气仓","id":90,"camera":null,"list":null},{"name":"6区-燃气仓","id":94,"camera":null,"list":null},{"name":"4区-燃气仓","id":92,"camera":null,"list":null},{"name":"5区-燃气仓","id":93,"camera":null,"list":null},{"name":"3区-燃气仓","id":91,"camera":null,"list":null}]},{"name":"污水仓","id":8,"camera":null,"list":[{"name":"1区-污水仓","id":95,"camera":null,"list":null},{"name":"2区-污水仓","id":96,"camera":null,"list":null},{"name":"6区-污水仓","id":100,"camera":null,"list":null},{"name":"4区-污水仓","id":98,"camera":null,"list":null},{"name":"5区-污水仓","id":99,"camera":null,"list":null},{"name":"3区-污水仓","id":97,"camera":null,"list":null}]},{"name":"电力仓","id":7,"camera":null,"list":[{"name":"1区-电力仓","id":101,"camera":null,"list":null},{"name":"2区-电力仓","id":102,"camera":null,"list":null},{"name":"6区-电力仓","id":106,"camera":null,"list":null},{"name":"4区-电力仓","id":104,"camera":null,"list":null},{"name":"5区-电力仓","id":105,"camera":null,"list":null},{"name":"3区-电力仓","id":103,"camera":null,"list":null}]},{"name":"综合仓","id":6,"camera":null,"list":[{"name":"1区-综合仓","id":107,"camera":null,"list":null},{"name":"2区-综合仓","id":108,"camera":null,"list":null},{"name":"6区-综合仓","id":112,"camera":null,"list":null},{"name":"4区-综合仓","id":110,"camera":null,"list":null},{"name":"5区-综合仓","id":111,"camera":null,"list":null},{"name":"3区-综合仓","id":109,"camera":null,"list":null}]}]},{"name":"经三路","id":3,"camera":null,"list":[{"name":"燃气仓","id":9,"camera":null,"list":[{"name":"1区-燃气仓","id":113,"camera":null,"list":null},{"name":"2区-燃气仓","id":114,"camera":null,"list":null},{"name":"6区-燃气仓","id":118,"camera":null,"list":null},{"name":"4区-燃气仓","id":116,"camera":null,"list":null},{"name":"5区-燃气仓","id":117,"camera":null,"list":null},{"name":"3区-燃气仓","id":115,"camera":null,"list":null}]},{"name":"污水仓","id":12,"camera":null,"list":[{"name":"1区-污水仓","id":119,"camera":null,"list":null},{"name":"2区-污水仓","id":120,"camera":null,"list":null},{"name":"6区-污水仓","id":124,"camera":null,"list":null},{"name":"4区-污水仓","id":122,"camera":null,"list":null},{"name":"5区-污水仓","id":123,"camera":null,"list":null},{"name":"3区-污水仓","id":121,"camera":null,"list":null}]},{"name":"电力仓","id":11,"camera":null,"list":[{"name":"1区-电力仓","id":125,"camera":null,"list":null},{"name":"2区-电力仓","id":126,"camera":null,"list":null},{"name":"6区-电力仓","id":130,"camera":null,"list":null},{"name":"4区-电力仓","id":128,"camera":null,"list":null},{"name":"5区-电力仓","id":129,"camera":null,"list":null},{"name":"3区-电力仓","id":127,"camera":null,"list":null}]},{"name":"综合仓","id":10,"camera":null,"list":[{"name":"1区-综合仓","id":131,"camera":null,"list":null},{"name":"2区-综合仓","id":132,"camera":null,"list":null},{"name":"6区-综合仓","id":136,"camera":null,"list":null},{"name":"4区-综合仓","id":134,"camera":null,"list":null},{"name":"5区-综合仓","id":135,"camera":null,"list":null},{"name":"3区-综合仓","id":133,"camera":null,"list":null}]}]},{"name":"经二路","id":4,"camera":null,"list":[{"name":"燃气仓","id":13,"camera":null,"list":[{"name":"1区-燃气仓","id":137,"camera":null,"list":null},{"name":"2区-燃气仓","id":138,"camera":null,"list":null},{"name":"6区-燃气仓","id":142,"camera":null,"list":null},{"name":"4区-燃气仓","id":140,"camera":null,"list":null},{"name":"5区-燃气仓","id":141,"camera":null,"list":null},{"name":"3区-燃气仓","id":139,"camera":null,"list":null}]},{"name":"污水仓","id":16,"camera":null,"list":[{"name":"1区-污水仓","id":143,"camera":null,"list":null},{"name":"2区-污水仓","id":144,"camera":null,"list":null},{"name":"6区-污水仓","id":148,"camera":null,"list":null},{"name":"4区-污水仓","id":146,"camera":null,"list":null},{"name":"5区-污水仓","id":147,"camera":null,"list":null},{"name":"3区-污水仓","id":145,"camera":null,"list":null}]},{"name":"电力仓","id":15,"camera":null,"list":[{"name":"1区-电力仓","id":149,"camera":null,"list":null},{"name":"2区-电力仓","id":150,"camera":null,"list":null},{"name":"6区-电力仓","id":154,"camera":null,"list":null},{"name":"4区-电力仓","id":152,"camera":null,"list":null},{"name":"5区-电力仓","id":153,"camera":null,"list":null},{"name":"3区-电力仓","id":151,"camera":null,"list":null}]},{"name":"综合仓","id":14,"camera":null,"list":[{"name":"1区-综合仓","id":155,"camera":null,"list":null},{"name":"2区-综合仓","id":156,"camera":null,"list":null},{"name":"6区-综合仓","id":160,"camera":null,"list":null},{"name":"4区-综合仓","id":158,"camera":null,"list":null},{"name":"5区-综合仓","id":159,"camera":null,"list":null},{"name":"3区-综合仓","id":157,"camera":null,"list":null}]}]},{"name":"纬三路","id":5,"camera":null,"list":[{"name":"燃气仓","id":17,"camera":null,"list":[{"name":"20区-燃气仓","id":180,"camera":null,"list":null},{"name":"19区-燃气仓","id":179,"camera":null,"list":null},{"name":"22区-燃气仓","id":182,"camera":null,"list":null},{"name":"1区-燃气仓","id":161,"camera":null,"list":null},{"name":"2区-燃气仓","id":162,"camera":null,"list":null},{"name":"3区-燃气仓","id":163,"camera":null,"list":null},{"name":"4区-燃气仓","id":164,"camera":null,"list":null},{"name":"5区-燃气仓","id":165,"camera":null,"list":null},{"name":"6区-燃气仓","id":166,"camera":null,"list":null},{"name":"7区-燃气仓","id":167,"camera":null,"list":null},{"name":"8区-燃气仓","id":168,"camera":null,"list":null},{"name":"9区-燃气仓","id":169,"camera":null,"list":null},{"name":"10区-燃气仓","id":170,"camera":null,"list":null},{"name":"11区-燃气仓","id":171,"camera":null,"list":null},{"name":"12区-燃气仓","id":172,"camera":null,"list":null},{"name":"13区-燃气仓","id":173,"camera":null,"list":null},{"name":"14区-燃气仓","id":174,"camera":null,"list":null},{"name":"15区-燃气仓","id":175,"camera":null,"list":null},{"name":"16区-燃气仓","id":176,"camera":null,"list":null},{"name":"17区-燃气仓","id":177,"camera":null,"list":null},{"name":"18区-燃气仓","id":178,"camera":null,"list":null},{"name":"21区-燃气仓","id":181,"camera":null,"list":null}]},{"name":"污水仓","id":20,"camera":null,"list":[{"name":"1区-污水仓","id":183,"camera":null,"list":null},{"name":"22区-污水仓","id":204,"camera":null,"list":null},{"name":"3区-污水仓","id":185,"camera":null,"list":null},{"name":"4区-污水仓","id":186,"camera":null,"list":null},{"name":"5区-污水仓","id":187,"camera":null,"list":null},{"name":"6区-污水仓","id":188,"camera":null,"list":null},{"name":"7区-污水仓","id":189,"camera":null,"list":null},{"name":"8区-污水仓","id":190,"camera":null,"list":null},{"name":"9区-污水仓","id":191,"camera":null,"list":null},{"name":"10区-污水仓","id":192,"camera":null,"list":null},{"name":"11区-污水仓","id":193,"camera":null,"list":null},{"name":"12区-污水仓","id":194,"camera":null,"list":null},{"name":"13区-污水仓","id":195,"camera":null,"list":null},{"name":"14区-污水仓","id":196,"camera":null,"list":null},{"name":"15区-污水仓","id":197,"camera":null,"list":null},{"name":"16区-污水仓","id":198,"camera":null,"list":null},{"name":"17区-污水仓","id":199,"camera":null,"list":null},{"name":"18区-污水仓","id":200,"camera":null,"list":null},{"name":"19区-污水仓","id":201,"camera":null,"list":null},{"name":"20区-污水仓","id":202,"camera":null,"list":null},{"name":"21区-污水仓","id":203,"camera":null,"list":null},{"name":"2区-污水仓","id":184,"camera":null,"list":null}]},{"name":"电力仓","id":19,"camera":null,"list":[{"name":"1区-电力仓","id":205,"camera":null,"list":null},{"name":"22区-电力仓","id":226,"camera":null,"list":null},{"name":"3区-电力仓","id":207,"camera":null,"list":null},{"name":"4区-电力仓","id":208,"camera":null,"list":null},{"name":"5区-电力仓","id":209,"camera":null,"list":null},{"name":"6区-电力仓","id":210,"camera":null,"list":null},{"name":"7区-电力仓","id":211,"camera":null,"list":null},{"name":"8区-电力仓","id":212,"camera":null,"list":null},{"name":"9区-电力仓","id":213,"camera":null,"list":null},{"name":"10区-电力仓","id":214,"camera":null,"list":null},{"name":"11区-电力仓","id":215,"camera":null,"list":null},{"name":"12区-电力仓","id":216,"camera":null,"list":null},{"name":"13区-电力仓","id":217,"camera":null,"list":null},{"name":"14区-电力仓","id":218,"camera":null,"list":null},{"name":"15区-电力仓","id":219,"camera":null,"list":null},{"name":"16区-电力仓","id":220,"camera":null,"list":null},{"name":"17区-电力仓","id":221,"camera":null,"list":null},{"name":"18区-电力仓","id":222,"camera":null,"list":null},{"name":"19区-电力仓","id":223,"camera":null,"list":null},{"name":"20区-电力仓","id":224,"camera":null,"list":null},{"name":"21区-电力仓","id":225,"camera":null,"list":null},{"name":"2区-电力仓","id":206,"camera":null,"list":null}]},{"name":"综合仓","id":18,"camera":null,"list":[{"name":"1区-综合仓","id":227,"camera":null,"list":null},{"name":"22区-综合仓","id":248,"camera":null,"list":null},{"name":"3区-综合仓","id":229,"camera":null,"list":null},{"name":"4区-综合仓","id":230,"camera":null,"list":null},{"name":"5区-综合仓","id":231,"camera":null,"list":null},{"name":"6区-综合仓","id":232,"camera":null,"list":null},{"name":"7区-综合仓","id":233,"camera":null,"list":null},{"name":"8区-综合仓","id":234,"camera":null,"list":null},{"name":"9区-综合仓","id":235,"camera":null,"list":null},{"name":"10区-综合仓","id":236,"camera":null,"list":null},{"name":"11区-综合仓","id":237,"camera":null,"list":null},{"name":"12区-综合仓","id":238,"camera":null,"list":null},{"name":"13区-综合仓","id":239,"camera":null,"list":null},{"name":"14区-综合仓","id":240,"camera":null,"list":null},{"name":"15区-综合仓","id":241,"camera":null,"list":null},{"name":"16区-综合仓","id":242,"camera":null,"list":null},{"name":"17区-综合仓","id":243,"camera":null,"list":null},{"name":"18区-综合仓","id":244,"camera":null,"list":null},{"name":"19区-综合仓","id":245,"camera":null,"list":null},{"name":"20区-综合仓","id":246,"camera":null,"list":null},{"name":"21区-综合仓","id":247,"camera":null,"list":null},{"name":"2区-综合仓","id":228,"camera":null,"list":null}]}]}]}
	 * @author shaosen
	 * @date 2018年7月25日
	 */
	@RequestMapping(value = "tunnels/tree", method = RequestMethod.GET)
	public JSONObject getTree() {

		List<TunnelDto> dtoList = tunnelService.getDtoList();
		List<JSONObject> listOne = new ArrayList<>();
		for (TunnelDto dto : dtoList) {
			JSONObject jsonOne = new JSONObject();
			jsonOne.put("name", dto.getName());
			jsonOne.put("id", dto.getId());
			jsonOne.put("camera", dto.getCamera());
			// 查询下面有多少仓
			List<StoreDto> storeList = storeService.getStoresByTunnelId(dto.getId());
			List<JSONObject> listTwo = new ArrayList<>();
			for (StoreDto storeDto : storeList) {
				JSONObject jsonTwo = new JSONObject();
				jsonTwo.put("name", storeDto.getName());
				jsonTwo.put("id", storeDto.getId());
				jsonTwo.put("camera", storeDto.getCamera());
				// 查询下面有多少仓段
				List<SectionDto> sectionList = sectionService.getSectionsByStoreId(storeDto.getId());
				List<JSONObject> listThree = new ArrayList<>();
				for (SectionDto sectionDto : sectionList) {
					JSONObject jsonThree = new JSONObject();
					jsonThree.put("name", sectionDto.getName());
					jsonThree.put("id", sectionDto.getId());
					jsonThree.put("camera", sectionDto.getCamera());
					jsonThree.put("list", null);
					listThree.add(jsonThree);
				}

				jsonTwo.put("list", listThree);
				listTwo.add(jsonTwo);
			}
			jsonOne.put("list", listTwo);
			listOne.add(jsonOne);
		}

		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, listOne);

	}

	/**
	 * 获取指定管廊下共有多少管仓
	 * 
	 * @param id 管廊id
	 * @return {"msg":"请求成功","code":"200","data":{"val":6,"key":"管仓数"}}
	 * @throws @author shaosen
	 * @date 2018年6月29日
	 */
	@RequestMapping(value = "tunnels/{id}/stores/count", method = RequestMethod.GET)
	public JSONObject getStoresCountByTunnelId(@PathVariable Integer id) {

		List<StoreDto> storeList = storeService.getStoresByTunnelId(id);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("key", "管仓数");
		jsonObject.put("val", storeList.size());

		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, jsonObject);
	}

	/**
	 * 获取指定管廊下共有多少section(仓段)
	 * 
	 * @param id 管廊id
	 * @return {"msg":"请求成功","code":"200","data":{"val":16,"key":"仓段数"}}
	 * @throws @author shaosen
	 * @date 2018年6月29日
	 */
	@RequestMapping(value = "tunnels/{id}/sections/count", method = RequestMethod.GET)
	public JSONObject getSectionsCountByTunnelId(@PathVariable Integer id) {

		List<SectionDto> list = tunnelService.getSectionList(id);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("key", "仓段数");
		jsonObject.put("val", list.size());

		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, jsonObject);
	}

	/**
	 * 获取指定管廊下环境监控各数值极限值
	 * 
	 * @param id          管廊id
	 * @param monitorType 监测内容枚举，见接口文档
	 * @return {"msg":"请求成功","code":"200","data":[{"val":53.314596083560126,"location":"综合仓xxxx-综合仓仓段15","key":"温度"},{"val":174.66580412805067,"location":"综合仓xxxx-综合仓仓段14","key":"湿度"},{"val":54.55083161453624,"location":"综合仓xxxx-综合仓仓段12","key":"氧气"},{"val":117.71152117067555,"location":"电力仓xxxx-电力仓仓段12","key":"甲烷"},{"val":68.85693145973374,"location":"电力仓xxxx-电力仓仓段16","key":"一氧化碳"}]}
	 * @throws @author shaosen
	 * @date 2018年6月28日
	 */
	@RequestMapping(value = "tunnels/{id}/{monitorType}/max-cv", method = RequestMethod.GET)
	public JSONObject getMaxCVByTunnelId(@PathVariable("id") Integer id,
			@PathVariable("monitorType") Integer monitorType) {

		// 获取tunnelId=？的measobjId集合
		List<MeasObj> measObjList = new ArrayList<>();
		List<Integer> idList = measObjService.getIdsByTunnelId(id);

		// 在缓存中查询所有obj对象
		for (Integer i : idList) {
			MeasObj measObj = measObjModuleCenter.getMeasObj(i);
			measObjList.add(measObj);
		}

		List<MeasObj> objList = new ArrayList<>();
		MonitorTypeEnum mt = MonitorTypeEnum.getEnum(monitorType);

		// 根据不同监测内容进行处理
		if (mt != null) {
			switch (mt) {
			case Environmental:
				for (MeasObj measObj : measObjList) {
					if (DataType.AI.getValue() == measObj.getDatatypeId()) {
						objList.add(measObj);
					}
				}
				break;
			case structure:
				// TODO
				break;
			case Security:
				// TODO
				break;

			default:
				break;
			}
		}

		// 分别查询温度，湿度等的极限值
		if (objList != null && objList.size() > 0) {
			List<MeasObjAIParam> paramList = new ArrayList<>();
			MeasObjAIParam temperature = new MeasObjAIParam(0.00);
			MeasObjAIParam humidity = new MeasObjAIParam(0.00);
			MeasObjAIParam oxygen = new MeasObjAIParam(10000.00);
			MeasObjAIParam h2s = new MeasObjAIParam(0.00);
			MeasObjAIParam ch4 = new MeasObjAIParam(0.00);
			MeasObjAIParam co = new MeasObjAIParam(0.00);

			for (MeasObj measObj : objList) {
				ObjectType objectType = ObjectType.getEnum(measObj.getObjtypeId().intValue());

				switch (objectType) {
				case TEMPERATURE:// 温度
					temperature = getMaxValue(measObj, temperature);
					break;
				case HUMIDITY:// 湿度
					humidity = getMaxValue(measObj, humidity);
					break;
				case OXYGEN:// 氧气
					oxygen = getMinValue(measObj, oxygen);
					break;
				case H2S:// 硫化氢
					h2s = getMaxValue(measObj, h2s);
					break;
				case CH4:// 甲烷
					ch4 = getMaxValue(measObj, ch4);
					break;
				case CO:// 一氧化碳
					co = getMaxValue(measObj, co);
					break;

				default:
					break;
				}
			}

			paramList.add(temperature);
			paramList.add(humidity);
			paramList.add(oxygen);
			paramList.add(h2s);
			paramList.add(ch4);
			paramList.add(co);
			List<JSONObject> list = getJsonObjList(paramList);
			return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, list);
		}

		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);

	}

	/** convert to Json */
	private List<JSONObject> getJsonObjList(List<MeasObjAIParam> paramList) {
		List<JSONObject> list = new ArrayList<>();

		// 判断监测对象是否存在，如果不存在跳过
		for (MeasObjAIParam param : paramList) {
			if (param.getObjTypeName() == null)
				continue;
			JSONObject jsonobj = new JSONObject();
			jsonobj.put("key", param.getObjTypeName());
			jsonobj.put("val", param.getCV());
			SectionDto dto = sectionService.getSectionById(param.getSectionId());
			jsonobj.put("location", dto.getName()); // “电力仓-区域1 ”类似于这种的名字
			list.add(jsonobj);
		}
		return list;
	}

	/** 求最大值 */
	private MeasObjAIParam getMaxValue(MeasObj measObj, MeasObjAIParam param) {
		Double cv = measObjModuleCenter.getMeasObjAI(measObj.getId()).getCV();
		if (cv.doubleValue() >= param.getCV().doubleValue()) {
			param.setCV(cv);
		}
		param.setObjId(measObj.getId());
		param.setSectionId(measObj.getSectionId());
		param.setObjTypeName(ObjectType.getEnum(measObj.getObjtypeId()).getName());
		return param;
	}

	/** 求最小值 */
	private MeasObjAIParam getMinValue(MeasObj measObj, MeasObjAIParam param) {
		Double cv = measObjModuleCenter.getMeasObjAI(measObj.getId()).getCV();
		if (cv.doubleValue() <= param.getCV().doubleValue()) {
			param.setCV(cv);
		}
		param.setObjId(measObj.getId());
		param.setSectionId(measObj.getSectionId());
		param.setObjTypeName(ObjectType.getEnum(measObj.getObjtypeId()).getName());
		return param;
	}

	/**
	 * 获取具体某个section的各种环境监测对象数值
	 * 
	 * @param id 舱段id
	 * @return {"msg":"请求成功","code":"200","data":[{"objId":1005,"sectionId":4,"objTypeName":"甲烷","cv":37.799710155275704}]}
	 * @throws @author shaosen
	 * @date 2018年6月29日
	 */
	@RequestMapping(value = "tunnels/stores/sections/{id}/ai/cv", method = RequestMethod.GET)
	public JSONObject getAICVBySectionId(@PathVariable Integer id) {

		List<MeasObj> list = new ArrayList<>();
		// 从缓存中读取obj数据
		List<MeasObj> objList = measObjModuleCenter.getMeasObjs();
		// 获取指定section的obj集合
		for (MeasObj measObj : objList) {
			if (measObj.getSectionId().intValue() == id.intValue()) {
				list.add(measObj);
			}
		}

		List<MeasObjAIParam> aiList = new ArrayList<>();
		for (MeasObj measObj : list) {
			DataType dataType = DataType.getEnum(measObj.getDatatypeId());

			switch (dataType) {
			case AI:
				aiList = getCV(measObj, aiList);
				break;
			case DI:
				break;
			case SI:
				break;
			case DISTRIBUTE:
				break;
			case VIDEO:
				break;
			case DAS:
				break;
			default:
				break;
			}

		}

		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, aiList);
	}

	private List<MeasObjAIParam> getCV(MeasObj measObj, List<MeasObjAIParam> list) {
		MeasObjAIParam aiParam = new MeasObjAIParam();
		Double cv = measObjModuleCenter.getMeasObjAI(measObj.getId()).getCV();
		aiParam.setObjId(measObj.getId());
		aiParam.setCV(cv);
		aiParam.setObjTypeName(ObjectType.getEnum(measObj.getObjtypeId()).getName());
		aiParam.setSectionId(measObj.getSectionId());
		list.add(aiParam);
		return list;
	}

	/**
	 * 获取环境监测各指标的最大值（用来显示百分比）
	 * 
	 * @return {"msg":"请求成功","code":"200","data":[{"val":1000.0,"key":"温度"},{"val":1000.0,"key":"湿度"},{"val":1000.0,"key":"氧气"},{"val":1000.0,"key":"硫化氢"},{"val":1000.0,"key":"甲烷"},{"val":1000.0,"key":"一氧化碳"}]}
	 * @throws @author shaosen
	 * @date 2018年7月3日
	 */
	@RequestMapping(value = "sections/ai/standard-cv", method = RequestMethod.GET)
	public JSONObject getAIStandardCV() {

		List<JSONObject> list = new ArrayList<>();
		for (ObjectType type : ObjectType.values()) {
			Double value = DataTypeUtil.toDouble(PropertiesUtil.getValue("ai." + type.name().toLowerCase()));
			if (value == null)
				continue;
			JSONObject json = new JSONObject();
			json.put("key", type.getName());
			json.put("val", value);
			list.add(json);
		}
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, list);

	}

	@RequestMapping(value = "tunnels/trees", method = RequestMethod.GET)
	public JSONObject getTrees() {

		List<TunnelDto> dtoList = tunnelService.getDtoList();
		List<JSONObject> listOne = new ArrayList<>();
		for (TunnelDto dto : dtoList) {
			JSONObject jsonOne = new JSONObject();
			jsonOne.put("name", dto.getName());
			jsonOne.put("id", dto.getId());
			// 查询下面有多少仓
			List<StoreDto> storeList = storeService.getStoresByTunnelId(dto.getId());
			List<JSONObject> listTwo = new ArrayList<>();
			for (StoreDto storeDto : storeList) {
				JSONObject jsonTwo = new JSONObject();
				jsonTwo.put("name", storeDto.getName());
				jsonTwo.put("id", storeDto.getId());
				// 查询下面有多少仓段
				List<SectionDto> sectionList = sectionService.getSectionsByStoreId(storeDto.getId());
				List<JSONObject> listThree = new ArrayList<>();
				for (SectionDto sectionDto : sectionList) {
					JSONObject jsonThree = new JSONObject();
					jsonThree.put("name", sectionDto.getName());
					jsonThree.put("id", sectionDto.getId());
					listThree.add(jsonThree);
				}

				jsonTwo.put("list", listThree);
				listTwo.add(jsonTwo);
			}
			jsonOne.put("list", listTwo);
			listOne.add(jsonOne);
		}

		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, listOne);

	}
	
}
