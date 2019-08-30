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
import com.bandweaver.tunnel.common.biz.dto.DocTypeDto;
import com.bandweaver.tunnel.common.biz.itf.DocTypeService;
import com.bandweaver.tunnel.common.biz.pojo.DocType;
import com.bandweaver.tunnel.common.biz.vo.DocTypeVo;
import com.bandweaver.tunnel.common.platform.util.CommonUtil;

@ResponseBody
@Controller
public class DocTypeController {
	
	@Autowired
	private DocTypeService docTypeService;
	
	/**
	 * 添加
	 * @param name 名称
	 * @param parentId 添加子类时的父类id
	 * @return
	 * @author ya.liu
	 * @Date 2019年7月2日
	 */
	@RequestMapping(value = "doc-types", method = RequestMethod.POST)
	public JSONObject add(@RequestBody DocType type) {
		docTypeService.insertSelective(type);
		return CommonUtil.success();
	}
	
	/**
	 * 修改
	 * @param name 名称
	 * @param parentId
	 * @return
	 * @author ya.liu
	 * @Date 2019年7月2日
	 */
	@RequestMapping(value = "doc-types", method = RequestMethod.PUT)
	public JSONObject update(@RequestBody DocType type) {
		docTypeService.updateByPrimaryKeySelective(type);
		return CommonUtil.success();
	}
	
	/**
	 * 一级联动列表
	 * @return
	 * @author ya.liu
	 * @Date 2019年7月2日
	 */
	@RequestMapping(value = "doc-types/first", method = RequestMethod.GET)
	public JSONObject getDocTypeById() {
		List<DocType> types = docTypeService.getList();
		return CommonUtil.success(types);
	}
	
	/**
	 * 查看详情
	 * @param id
	 * @return
	 * @author ya.liu
	 * @Date 2019年7月2日
	 */
	@RequestMapping(value = "doc-types/{id}", method = RequestMethod.GET)
	public JSONObject getDtoById(@PathVariable("id") Integer id) {
		DocTypeDto type = docTypeService.getDtoById(id);
		return CommonUtil.success(type);
	}
	
	/**
	 * 树结构
	 * @param name 支持模糊查询
	 * @param startTime
	 * @param endTime
	 * @return
	 * @author ya.liu
	 * @Date 2019年7月2日
	 */
	@RequestMapping(value = "doc-types/condition", method = RequestMethod.POST)
	public JSONObject getDtosByCondition(@RequestBody(required = false) DocTypeVo vo) {
		List<DocTypeDto> types = docTypeService.getDtoByCondition(vo);
		return CommonUtil.success(types);
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 * @author ya.liu
	 * @Date 2019年7月2日
	 */
	@RequestMapping(value = "doc-types/{id}", method = RequestMethod.DELETE)
	public JSONObject delete(@PathVariable("id") Integer id) {
		docTypeService.deleteByPrimaryKey(id);
		return CommonUtil.success();
	}
	
}
