package com.bandweaver.tunnel.controller.mam;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.itf.mam.substation.SubstationService;
import com.bandweaver.tunnel.common.biz.pojo.mam.substation.Substation;
import com.bandweaver.tunnel.common.biz.vo.mam.substation.SubstationVo;
import com.bandweaver.tunnel.common.platform.util.CommonUtil;
import com.github.pagehelper.PageInfo;

/**变电站管理
 * @author liuya
 * @date 2019年4月18日
 */
@Controller
@ResponseBody
public class SubstationController {
	@Autowired
	private SubstationService substationService;
	
	/**
	 * 添加
	 * @param tunnelId
	 * @param invId  进线电压id
	 * @param inaId  进线电流id
	 * @param outvId  出线电压id
	 * @param outaId  出线电流id
	 * @param powerId  实时功率id
	 * @param eleRadius  供电半径
	 * @param powerFactor  功率因数
	 * @param compensationFactor  功率补偿因数
	 * @param position  位置信息
	 * @param time  创建时间
	 * @return
	 * @author ya.liu
	 * @Date 2019年4月18日
	 */
	@RequiresPermissions("substations:add")
	@RequestMapping(value = "substations", method = RequestMethod.POST)
	public JSONObject add(@RequestBody Substation substation) {
		substationService.insertSelective(substation);
		
		return CommonUtil.success();
	}
	
	/**
	 * 修改
	 * @param id
	 * @param tunnelId
	 * @param invId  进线电压id
	 * @param inaId  进线电流id
	 * @param outvId  出线电压id
	 * @param outaId  出线电流id
	 * @param powerId  实时功率id
	 * @param eleRadius  供电半径
	 * @param powerFactor  功率因数
	 * @param compensationFactor  功率补偿因数
	 * @param position  位置信息
	 * @param time  创建时间
	 * @return
	 * @author ya.liu
	 * @Date 2019年4月18日
	 */
	@RequiresPermissions("substations:update")
	@RequestMapping(value = "substations", method = RequestMethod.PUT)
	public JSONObject update(@RequestBody Substation substation) {
		substationService.updateByPrimaryKeySelective(substation);
		
		return CommonUtil.success();
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 * @author ya.liu
	 * @Date 2019年4月18日
	 */
	@RequiresPermissions("substations:delete")
	@RequestMapping(value = "substations/{id}", method = RequestMethod.DELETE)
	public JSONObject delete(@PathVariable("id") Integer id) {
		substationService.deleteByPrimaryKey(id);
		
		return CommonUtil.success();
	}
	
	/**
	 * 通过id获取信息
	 * @param id
	 * @return
	 * @author ya.liu
	 * @Date 2019年4月18日
	 */
	@RequestMapping(value = "substations/{id}", method = RequestMethod.GET)
	public JSONObject getSubstationDto(@PathVariable("id") Integer id) {
		Substation s = substationService.getDtoById(id);
		
		return CommonUtil.success(s);
	}
	
	/**
	 * 条件获取列表，不分页
	 * @param id
	 * @param tunnelId
	 * @param invId  进线电压id
	 * @param inaId  进线电流id
	 * @param outvId  出线电压id
	 * @param outaId  出线电流id
	 * @param powerId  实时功率id
	 * @param startTime
	 * @param endTime
	 * @return
	 * @author ya.liu
	 * @Date 2019年4月22日
	 */
	@RequestMapping(value = "substations/condition", method = RequestMethod.POST)
	public JSONObject getSubstations(@RequestBody SubstationVo vo) {
		List<Substation> list = substationService.getListByCondition(vo);
		
		return CommonUtil.success(list);
	}
	
	/**
	 * 条件获取列表，分页
	 * @param id
	 * @param tunnelId
	 * @param invId  进线电压id
	 * @param inaId  进线电流id
	 * @param outvId  出线电压id
	 * @param outaId  出线电流id
	 * @param powerId  实时功率id
	 * @param startTime
	 * @param endTime
	 * @param pageSize
	 * @param pageNum
	 * @return
	 * @author ya.liu
	 * @Date 2019年4月22日
	 */
	@RequiresPermissions("substations:list")
	@RequestMapping(value = "substations/datagrid", method = RequestMethod.POST)
	public JSONObject dataGrid(@RequestBody SubstationVo vo) {
		PageInfo<Substation> list = substationService.dataGrid(vo);
		
		return CommonUtil.success(list);
	}
}
