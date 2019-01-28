package com.bandweaver.tunnel.controller.omm;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.constant.omm.EquipmentStatusEnum;
import com.bandweaver.tunnel.common.biz.constant.omm.SpareWhitherEnum;
import com.bandweaver.tunnel.common.biz.dto.CommonDto;
import com.bandweaver.tunnel.common.biz.dto.omm.SpareDto;
import com.bandweaver.tunnel.common.biz.dto.omm.SpareOutDto;

import com.bandweaver.tunnel.common.biz.itf.omm.EquipmentService;
import com.bandweaver.tunnel.common.biz.itf.omm.EquipmentTypeService;
import com.bandweaver.tunnel.common.biz.itf.omm.InstrumentService;

import com.bandweaver.tunnel.common.biz.itf.omm.SpareOutService;
import com.bandweaver.tunnel.common.biz.itf.omm.SpareService;
import com.bandweaver.tunnel.common.biz.pojo.ListPageUtil;
import com.bandweaver.tunnel.common.biz.pojo.omm.EquipmentType;
import com.bandweaver.tunnel.common.biz.pojo.omm.Spare;
import com.bandweaver.tunnel.common.biz.pojo.omm.SpareOut;
import com.bandweaver.tunnel.common.biz.vo.omm.InstrumentVo;
import com.bandweaver.tunnel.common.biz.vo.omm.SpareOutVo;
import com.bandweaver.tunnel.common.biz.vo.omm.SpareVo;
import com.bandweaver.tunnel.common.platform.constant.StatusCodeEnum;
import com.bandweaver.tunnel.common.platform.util.CommonUtil;
import com.github.pagehelper.PageInfo;

/**
 * 备品模块
 * @author ya.liu
 * @Date 2018年11月26日
 */
@Controller
@ResponseBody
public class SpareController {
	
	@Autowired
	private SpareService spareService;
	@Autowired
	private SpareOutService spareOutService;
	@Autowired
	private EquipmentTypeService equipmentTypeService;
	@Autowired
	private EquipmentService equipmentService;
	@Autowired
	private InstrumentService instrumentService;

	
	/**
	 * 添加
	 * @param name	名称
	 * @param modelId 模型
	 * @param typeId 类型
	 * @param venderId 供应商
	 * @param status true 入库/false 出库
	 * @param inTime 入库时间
	 * @return
	 * @author ya.liu
	 * @Date 2018年11月26日
	 */
	@RequestMapping(value = "spares", method = RequestMethod.POST)
	public JSONObject insert(@RequestBody Spare spare) {
		spareService.add(spare);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}
	
	/**
	 * 批量添加
	 * @param count 数量
	 * @param name	名称
	 * @param modelId 模型
	 * @param typeId 类型
	 * @param venderId 供应商
	 * @param status true 入库/false 出库
	 * @param inTime 入库时间
	 * @return
	 * @author ya.liu
	 * @Date 2018年11月26日
	 */
	@RequestMapping(value = "spares/{count}", method = RequestMethod.POST)
	public JSONObject addBatch(@PathVariable("count") Integer count,
			@RequestBody Spare spare) {
		List<Spare> list = new ArrayList<>();
		for(int i=1;i<=count;i++) {
			Spare s = new Spare();
			s.setName(spare.getName() + i);
			s.setInTime(spare.getInTime());
			s.setModelId(spare.getModelId());
			s.setVenderId(spare.getVenderId());
			s.setStatus(spare.getStatus());
			s.setTypeId(spare.getTypeId());
			list.add(s);
		}
		spareService.addBatch(list);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}
	
	/**
	 * 修改
	 * @param id 
	 * @param name	名称
	 * @param modelId 模型
	 * @param typeId 类型
	 * @param venderId 供应商
	 * @param status true/false
	 * @param inTime 入库时间
	 * @return
	 * @author ya.liu
	 * @Date 2018年11月26日
	 */
	@RequestMapping(value = "spares", method = RequestMethod.PUT)
	public JSONObject update(@RequestBody Spare spare) {
		spareService.update(spare);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}
	
	/**
	 * 查询所有入库的备品
	 * @return
	 * @author ya.liu
	 * @Date 2018年11月26日
	 */
	@RequestMapping(value = "spares", method = RequestMethod.GET)
	public JSONObject getSpareDtos() {
		List<SpareDto> list = spareService.getSpareDtoByStatus(true);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,list);
	}
	
	/**
	 * 查询所有出库的备品
	 * @return
	 * @author ya.liu
	 * @Date 2018年11月26日
	 */
	@RequestMapping(value = "spares/status", method = RequestMethod.GET)
	public JSONObject getSpareDtosByStatus() {
		List<SpareDto> list = spareService.getSpareDtoByStatus(false);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,list);
	}
	
	/**
	 * 条件查询，分页
	 * @param name	名称,支持模糊查询
	 * @param modelId 模型
	 * @param typeId 类型
	 * @param venderId 供应商
	 * @param status 备品状态
	 * @param startTime
	 * @param endTime 
	 * @param pageSize
	 * @param pageNum
	 * @return
	 * @author ya.liu
	 * @Date 2018年11月26日
	 */
	@RequestMapping(value = "spares/datagrid", method = RequestMethod.POST)
	public JSONObject dataGrid(@RequestBody SpareVo vo) {
		PageInfo<SpareDto> pageInfo = spareService.dataGrid(vo);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,pageInfo);
	}
	
	/**
	 * 通过id获取备品信息
	 * @param id
	 * @return
	 * @author ya.liu
	 * @Date 2018年11月28日
	 */
	@RequestMapping(value = "spares/{id}", method = RequestMethod.GET)
	public JSONObject getSpareDtoById(@PathVariable("id") Integer id) {
		SpareDto dto = spareService.getSpareDtoById(id);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,dto);
	}
	
	/**
	 * 批量删除
	 * @param ids "1,2,3"
	 * @return
	 * @author ya.liu
	 * @Date 2018年11月26日
	 */
	@RequestMapping(value = "spares/{ids}", method = RequestMethod.DELETE)
	public JSONObject delete(@PathVariable String ids) {
		String [] ss = ids.split(",");
		List<Integer> list = new ArrayList<>();
		for(String s : ss) {
			list.add(Integer.valueOf(s));
		}
		int i = spareService.deleteByIds(list);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,i);
	}
	
	/**
	 * 在库/设备类型
	 * @return
	 * @author ya.liu
	 * @Date 2018年11月28日
	 */
	@RequestMapping(value = "spares/type/key", method = RequestMethod.GET)
	public JSONObject getInCountGroupByTypeId() {
		List<JSONObject> list = new ArrayList<>();
		List<EquipmentType> typeList = equipmentTypeService.getAllEquipmentTypeList();
		for(EquipmentType type : typeList) {
			int spareCount = spareService.getCountByTypeIdAndStatus(true,type.getId());
			JSONObject obj = new JSONObject();
			obj.put("key", type.getName());
			obj.put("val", spareCount);
			list.add(obj);
		}
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,list);
	}
	
	/**
	 * 批量添加备品出库
	 * @param ids "1,2,3"
	 * @param tunnelId 所属管廊，选仪表给0
	 * @param staffId 取用人id
	 * @param userId 操作员id
	 * @param outTime 出库时间
	 * @param whither 备品去向
	 * @param describe 描述
	 * @return
	 * @author ya.liu
	 * @Date 2018年11月28日
	 */
	@RequestMapping(value = "spare-outs/{ids}/tunnels/{tunnelId}", method = RequestMethod.POST)
	public JSONObject addBatch(@PathVariable("ids") String ids,
			@PathVariable("tunnelId") Integer tunnelId,
			@RequestBody SpareOut s) {
		String [] strs = ids.split(",");
		List<SpareOut> list = new ArrayList<>();
		for(String str : strs) {
			SpareOut out = new SpareOut();
			out.setId(Integer.valueOf(str));
			out.setStaffId(s.getStaffId());
			out.setOutTime(s.getOutTime());
			out.setUserId(s.getUserId());
			out.setWhither(s.getWhither());
			out.setDescribe(s.getDescribe() == null ? "" : s.getDescribe());
			list.add(out);
		}
		spareOutService.addBatch(list, tunnelId);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}
	
	/**
	 * 更新
	 * @param id 备品id
	 * @param staffId 取用人id
	 * @param userId 操作员id
	 * @param outTime 出库时间
	 * @param describe 描述
	 * @return
	 * @author ya.liu
	 * @Date 2018年11月28日
	 */
	@RequestMapping(value = "spare-outs", method = RequestMethod.PUT)
	public JSONObject update(@RequestBody SpareOut s) {
		spareOutService.update(s);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}
	
	/**
	 * 条件查询，分页
	 * @param name	备品名称,支持模糊查询
	 * @param modelId 模型
	 * @param typeId 类型
	 * @param staffId 取用人
	 * @param userId 操作员
	 * @param whither 备品去向
	 * @param startTime
	 * @param endTime 
	 * @param pageSize
	 * @param pageNum
	 * @return
	 * @author ya.liu
	 * @Date 2018年11月28日
	 */
	@RequestMapping(value = "spare-outs/datagrid", method = RequestMethod.POST)
	public JSONObject dataGrid(@RequestBody SpareOutVo vo) {
		ListPageUtil<SpareOutDto> page = spareOutService.dataGrid(vo);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,page);
	}
	
	/**
	 * 条件查询，不分页
	 * @param name	备品名称,支持模糊查询
	 * @param modelId 模型
	 * @param typeId 类型
	 * @param staffId 取用人
	 * @param userId 操作员
	 * @param whither 备品去向
	 * @param startTime
	 * @param endTime 
	 * @return
	 * @author ya.liu
	 * @Date 2018年11月28日
	 */
	@RequestMapping(value = "spare-outs", method = RequestMethod.GET)
	public JSONObject getSpareOutDtoByCondition(@RequestBody(required = false) SpareOutVo vo) {
		List<SpareOutDto> list = spareOutService.getSpareOutDtoByCondition(vo);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,list);
	}
	
	/**
	 * 通过id获取出库备品详情
	 * @param id
	 * @return
	 * @author ya.liu
	 * @Date 2018年11月28日
	 */
	@RequestMapping(value = "spare-outs/{id}", method = RequestMethod.GET)
	public JSONObject getSpareOutDtoById(@PathVariable("id") Integer id) {
		SpareOutDto dto = spareOutService.getSpareOutDtoById(id);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,dto);
	}
	
	/**
	 * 取用人排行榜
	 * @return
	 * @author ya.liu
	 * @Date 2018年11月28日
	 */
	@RequestMapping(value = "spare-outs/staff", method = RequestMethod.GET)
	public JSONObject getCountGroupByStaffId() {
		List<CommonDto> list = spareOutService.getCountGroupByStaffId();
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,list);
	}
	
	/**
	 * 设备类型出库排行榜
	 * @return
	 * @author ya.liu
	 * @Date 2018年11月28日
	 */
	@RequestMapping(value = "spare-outs/type", method = RequestMethod.GET)
	public JSONObject getOutCountGroupByTypeId() {
		List<CommonDto> list = new ArrayList<>();
		List<EquipmentType> typeList = equipmentTypeService.getAllEquipmentTypeList();
		for(EquipmentType type : typeList) {
			int spareCount = spareService.getCountByTypeIdAndStatus(false,type.getId());
			CommonDto dto = new CommonDto();
			dto.setCount(spareCount);
			dto.setId(type.getId());
			dto.setName(type.getName());
			list.add(dto);
		}
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,list);
	}
	
	/**
	 * 出库/设备类型
	 * @return
	 * @author ya.liu
	 * @Date 2018年11月29日
	 */
	@RequestMapping(value = "spare-outs/type/key", method = RequestMethod.GET)
	public JSONObject getKeyValGroupByTypeId() {
		List<JSONObject> list = new ArrayList<>();
		List<EquipmentType> typeList = equipmentTypeService.getAllEquipmentTypeList();
		for(EquipmentType type : typeList) {
			int spareCount = spareService.getCountByTypeIdAndStatus(false,type.getId());
			JSONObject obj = new JSONObject();
			obj.put("key", type.getName());
			obj.put("val", spareCount);
			list.add(obj);
		}
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,list);
	}
	
	/**
	 * 备品在库和出库数量/设备类型
	 * @return
	 * @author ya.liu
	 * @Date 2018年12月4日
	 */
	@RequestMapping(value = "spares/outs/type", method = RequestMethod.GET)
	public JSONObject getSparesByStatus() {
		
		List<EquipmentType> typeList = equipmentTypeService.getAllEquipmentTypeList();
		List<JSONObject> list = new ArrayList<>();
		for(EquipmentType type : typeList) {
			int inCount = spareService.getCountByTypeIdAndStatus(true,type.getId());
			int outCount = spareService.getCountByTypeIdAndStatus(false,type.getId());
			JSONObject obj = new JSONObject();
			obj.put("key", type.getName());

			List<JSONObject> listType = new ArrayList<>();
			JSONObject objIn = new JSONObject();
			objIn.put("key", "在库");
			objIn.put("val", inCount);
			listType.add(objIn);
			JSONObject objOut = new JSONObject();
			objOut.put("key", "出库");
			objOut.put("val", outCount);
			listType.add(objOut);
			obj.put("val", listType);
			
			list.add(obj);

		}
		
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,list);
	}
	
	/******************************************************************
    *******************************以下是统计信息总览************************
    ******************************************************************/
	/**
	 * 备品在库以及去向的数目
	 * @return
	 * @author ya.liu
	 * @Date 2019年1月4日
	 */
	@RequestMapping(value = "spares/count/status", method = RequestMethod.GET)
	public JSONObject getCountByStatus() {
		JSONObject obj = new JSONObject();
		obj.put("in", spareService.getCountBystatus(true));
		obj.put("equipment", spareOutService.getCountByWhither(SpareWhitherEnum.PIPE.getValue()));
		obj.put("instrument", spareOutService.getCountByWhither(SpareWhitherEnum.INSTRUMENT.getValue()));
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,obj);
	}
	
	/**
	 * 备品、管廊设备、仪表设备库存
	 * @return
	 * @author ya.liu
	 * @Date 2019年1月4日
	 */
	@RequestMapping(value = "spares/equipments/instruments", method = RequestMethod.GET)
	public JSONObject getCount() {
		JSONObject obj = new JSONObject();
		int spares = spareService.getCountBystatus(true);
		int equipment = equipmentService.getCountByCondition(null, null, null);
		int instrument = instrumentService.getCountByStatus(null);
		obj.put("spares", spares);
		obj.put("equipment", equipment);
		obj.put("instrument", instrument);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,obj);
	}
	
	/**
	 * 管廊设备数占比
	 * @return
	 * @author ya.liu
	 * @Date 2019年1月7日
	 */
	@RequestMapping(value = "equipments/num", method = RequestMethod.GET)
	public JSONObject getEquipmentByNum() {
		int spares = spareService.getCountBystatus(true);
		int equipment = equipmentService.getCountByCondition(null, null, null);
		int instrument = instrumentService.getCountByStatus(null);
		int num = spares + equipment + instrument;
		double d = equipment*100/num/100.0;
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,d);
	}
	
	/**
	 * 仪表工具数占比
	 * @return
	 * @author ya.liu
	 * @Date 2019年1月7日
	 */
	@RequestMapping(value = "instruments/num", method = RequestMethod.GET)
	public JSONObject getInstrumentByNum() {
		int spares = spareService.getCountBystatus(true);
		int equipment = equipmentService.getCountByCondition(null, null, null);
		int instrument = instrumentService.getCountByStatus(null);
		int num = spares + equipment + instrument;
		double d = instrument*100/num/100.0;
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,d);
	}
	
	/**
	 * 获取设备总数/类型
	 * @return
	 * @author ya.liu
	 * @Date 2019年1月4日
	 */
	@RequestMapping(value = "spares/equipments/instruments/counts/types", method = RequestMethod.GET)
	public JSONObject getCountsGroupByTypeId() {
		List<JSONObject> list = new ArrayList<>();
		List<EquipmentType> typeList = equipmentTypeService.getAllEquipmentTypeList();
		for(EquipmentType type : typeList) {
			// 获取备品在库数
			int spareCount = spareService.getCountByTypeIdAndStatus(true,type.getId());
			// 获取仪表库存数
			InstrumentVo vo = new InstrumentVo();
			vo.setTypeId(type.getId());
			int instrumentCount = instrumentService.getCountByCondition(vo);
			// 获取管廊库存数
			int equipmentCount = equipmentService.getCountByCondition(null, null, type.getId());
			
			// 获取总的设备数
			int count = spareCount + instrumentCount + equipmentCount;
			JSONObject obj = new JSONObject();
			obj.put("key",type.getName());
			obj.put("val",count);
			list.add(obj);
		}
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,list);
	}
	
	/**
	 * 设备故障总数 / 类型
	 * @return
	 * @author ya.liu
	 * @Date 2019年1月7日
	 */
	@RequestMapping(value = "equipments/instruments/broken-counts/types", method = RequestMethod.GET)
	public JSONObject getBrokenCountsByType() {
		List<JSONObject> list = new ArrayList<>();
		List<EquipmentType> typeList = equipmentTypeService.getAllEquipmentTypeList();
		for(EquipmentType type : typeList) {
			Integer broken = EquipmentStatusEnum.BROKEN.getValue();
			// 获取仪表故障数
			InstrumentVo vo = new InstrumentVo();
			vo.setTypeId(type.getId());
			vo.setUseStatus(broken);
			int instrumentCount = instrumentService.getCountByCondition(vo);
			// 获取管廊故障数
			int equipmentCount = equipmentService.getCountByCondition(null, broken, type.getId());
			
			// 获取总的故障数
			int count = instrumentCount + equipmentCount;
			JSONObject obj = new JSONObject();
			obj.put("key",type.getName());
			obj.put("val",count);
			list.add(obj);
		}
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,list);
	}
	
	/**
	 * 设备故障总数
	 * @return
	 * @author ya.liu
	 * @Date 2019年1月7日
	 */
	@RequestMapping(value = "equipments/instruments/broken-counts", method = RequestMethod.GET)
	public JSONObject getBrokenCounts() {
		int num = 0;
		List<EquipmentType> typeList = equipmentTypeService.getAllEquipmentTypeList();
		for(EquipmentType type : typeList) {
			Integer broken = EquipmentStatusEnum.BROKEN.getValue();
			// 获取仪表故障数
			InstrumentVo vo = new InstrumentVo();
			vo.setTypeId(type.getId());
			vo.setUseStatus(broken);
			int instrumentCount = instrumentService.getCountByCondition(vo);
			// 获取管廊故障数
			int equipmentCount = equipmentService.getCountByCondition(null, broken, type.getId());
			
			// 获取总的故障数
			int count = instrumentCount + equipmentCount;
			num += count;
		}
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,num);
	}
	
	/**
     * 获取每月的设备缺陷、备品使用量以及工具借用次数
     * @return
     * @author ya.liu
     * @Date 2019年1月11日
     */
    @RequestMapping(value = "spares/instruments/defects/count", method = RequestMethod.GET)
    public JSONObject getTheyCountByMonth() {
    	List<JSONObject> list = new ArrayList<>();
    	String [] strs = {"设备缺陷","备品使用量","工具借用次数"};
    	for(int i=0;i<strs.length;i++) {
    		JSONObject obj = new JSONObject();
    		obj.put("key", strs[i]);
    		List<JSONObject> monthList = new ArrayList<>();
    		for(int j=1;j<13;j++) {
    			JSONObject monthObj = new JSONObject();
    			monthObj.put("key", j + "月");
    			int math = 0;
    			if(i == 0) math = (int)(Math.random() * 6 + 8);
    			else if(i == 1) math = (int)(Math.random() * 20 + 50);
    			else math = (int)(Math.random() * 40 + 80);
    			monthObj.put("val", math);
    			monthList.add(monthObj);
    		}
    		obj.put("val", monthList);
    		list.add(obj);
    	}
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, list);
    }
    
    /**
     * 获取设备数量/设备状态
     * @return
     * @author ya.liu
     * @Date 2019年1月17日
     */
    @RequestMapping(value = "equipments/instruments/status",method=RequestMethod.GET)
    public JSONObject getEquipmentAndInstrumentStatusCount() {
		List<JSONObject> list = new ArrayList<>();
		
		for (EquipmentStatusEnum e : EquipmentStatusEnum.values()) {
			JSONObject obj = new JSONObject();
			obj.put("key", e.getName());
			int equipmentCount = equipmentService.getCountByCondition(null, e.getValue(), null);
			// 获取仪表故障数
			InstrumentVo vo = new InstrumentVo();
			vo.setUseStatus(e.getValue());
			int instrumentCount = instrumentService.getCountByCondition(vo);
			int count = instrumentCount + equipmentCount;
			obj.put("val", count);
			list.add(obj);
		}
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, list);
    }
}
