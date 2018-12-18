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
import com.bandweaver.tunnel.common.biz.dto.CommonDto;
import com.bandweaver.tunnel.common.biz.dto.omm.SpareDto;
import com.bandweaver.tunnel.common.biz.dto.omm.SpareOutDto;
import com.bandweaver.tunnel.common.biz.itf.omm.SpareOutService;
import com.bandweaver.tunnel.common.biz.itf.omm.SpareService;
import com.bandweaver.tunnel.common.biz.pojo.ListPageUtil;
import com.bandweaver.tunnel.common.biz.pojo.omm.Spare;
import com.bandweaver.tunnel.common.biz.pojo.omm.SpareOut;
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
		List<SpareDto> list = spareService.getSpareDto();
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
		List<SpareDto> list = spareService.getSpareDtoByStatus();
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
		List<CommonDto> listDto = spareService.getCountGroupByTypeId(true);
		List<JSONObject> list = new ArrayList<>();
		for(CommonDto dto : listDto) {
			JSONObject obj = new JSONObject();
			obj.put("key", dto.getName());
			obj.put("val", dto.getCount());
			list.add(obj);
		}
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,list);
	}
	
	/**
	 * 批量添加备品出库
	 * @param ids "1,2,3"
	 * @param staffId 取用人id
	 * @param userId 操作员id
	 * @param outTime 出库时间
	 * @param whither 备品去向
	 * @param describe 描述
	 * @return
	 * @author ya.liu
	 * @Date 2018年11月28日
	 */
	@RequestMapping(value = "spare-outs/{ids}", method = RequestMethod.POST)
	public JSONObject addBatch(@PathVariable("ids") String ids,
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
		spareOutService.addBatch(list);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}
	
	/**
	 * 更新
	 * @param id 备品id
	 * @param staffId 取用人id
	 * @param userId 操作员id
	 * @param outTime 出库时间
	 * @param whither 备品去向
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
		List<CommonDto> list = spareService.getCountGroupByTypeId(false);
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
		List<CommonDto> listDto = spareService.getCountGroupByTypeId(false);
		List<JSONObject> list = new ArrayList<>();
		for(CommonDto dto : listDto) {
			JSONObject obj = new JSONObject();
			obj.put("key", dto.getName());
			obj.put("val", dto.getCount());
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
	public JSONObject get() {
		List<CommonDto> listIn = spareService.getCountGroupByTypeId(true);
		List<CommonDto> listOut = spareService.getCountGroupByTypeId(false);
		List<JSONObject> list = new ArrayList<>();
		for(CommonDto dtoIn : listIn) {
			JSONObject objType = new JSONObject();
			objType.put("key", dtoIn.getName());
			List<JSONObject> listType = new ArrayList<>();
			JSONObject objIn = new JSONObject();
			objIn.put("key", "在库");
			objIn.put("val", dtoIn.getCount());
			listType.add(objIn);
			for(CommonDto dtoOut : listOut) {
				if(dtoIn.getName().equals(dtoOut.getName())) {
					JSONObject objOut = new JSONObject();
					objOut.put("key", "出库");
					objOut.put("val", dtoOut.getCount());
					listType.add(objOut);
				}
			}
			objType.put("val", listType);
			list.add(objType);
		}
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,list);
	}
}
