package com.bandweaver.tunnel.controller.mam;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.w3c.dom.ls.LSInput;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.itf.mam.maxview.MaxviewConfigService;
import com.bandweaver.tunnel.common.biz.pojo.mam.MaxviewConfig;
import com.bandweaver.tunnel.common.biz.vo.mam.MaxviewConfigVo;
import com.bandweaver.tunnel.common.platform.constant.StatusCodeEnum;
import com.bandweaver.tunnel.common.platform.util.CommonUtil;
import com.bandweaver.tunnel.common.platform.util.DataTypeUtil;
import com.github.pagehelper.PageInfo;

/**maxview终端管理
 * @author shaosen
 * @date 2018年11月15日
 */
@Controller
@ResponseBody
public class MaxviewConfigController {

	
	@Autowired
	private MaxviewConfigService maxviewConfigService;
	
	/**测试心跳 
	 * @return 
	 * @author shaosen
	 * @Date 2018年11月16日
	 */
	@RequestMapping(value="maxview/heart-beat",method=RequestMethod.GET)
	public JSONObject heartBeat() {
		maxviewConfigService.heartBeat(1);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}
	
	
	/**开关控制 
	 * @param obj
	 * @param inputVal
	 * @return  {"msg":"请求成功","code":"200","data":false or true} 
	 * @author shaosen
	 * @Date 2018年11月16日
	 */
	@RequestMapping(value = "maxview/action/obj/{obj}/inputval/{inputVal}" ,method= RequestMethod.GET)
	public JSONObject doAction(@PathVariable("obj")Integer obj,@PathVariable("inputVal")Integer inputVal) {
		boolean result = maxviewConfigService.doAction(obj, inputVal);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,result);
	}
	
	/**添加终端 
	 * @param name 终端名称
	 * @param ip 终端ip
	 * @param port 终端端口
	 * @param username 登录账号
	 * @param password 登录密码
	 * @return  
	 * @author shaosen
	 * @Date 2018年11月16日
	 */
	@RequestMapping(value = "maxview",method=RequestMethod.POST)
	public JSONObject add(@RequestBody MaxviewConfig config) {
		maxviewConfigService.add(config);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}	
	
	
	/** 更新配置 
	 * @param config 参考添加接口
	 * @return   
	 * @author shaosen
	 * @Date 2018年11月16日
	 */
	@RequestMapping(value = "maxview",method=RequestMethod.PUT)
	public JSONObject update(@RequestBody MaxviewConfig config) {
		maxviewConfigService.update(config);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}
	
	/**删除 
	 * @param id 主键
	 * @return   
	 * @author shaosen
	 * @Date 2018年11月16日
	 */
	@RequestMapping(value = "maxview/{id}",method=RequestMethod.DELETE)
	public JSONObject delete(@PathVariable Integer id) {
		maxviewConfigService.delete(id);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}
	
	/**批量删除 
	 * @param ids 1,2,3,4
	 * @return   
	 * @author shaosen
	 * @Date 2018年11月16日
	 */
	@RequestMapping(value = "maxview/batch/{ids}",method=RequestMethod.DELETE)
	public JSONObject deleteBatch(@PathVariable String ids) {
		
		List<Integer> idList = new ArrayList<>();
		String[] arr = ids.split(",");
		for (String str : arr) {
			idList.add(DataTypeUtil.toInteger(str));
		}
		maxviewConfigService.deleteBatch(idList);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}
	
	/**根据主键查询 
	 * @param id
	 * @return  {"msg":"请求成功","code":"200","data":{"id":1,"name":"终端1","ip":"192.168.6.14","port":"80","username":"admin","password":"14e24dc8cf60446f56eb163a7b85bce8a53d3f766badf15e86a1af857d9a9012","crtTime":1542211200000}} 
	 * @author shaosen
	 * @Date 2018年11月16日
	 */
	@RequestMapping(value = "maxview/{id}",method=RequestMethod.GET)
	public JSONObject getById(@PathVariable Integer id) {
		MaxviewConfig config = maxviewConfigService.getById(id);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,config);
	}
	
	/**
	 * 分页查询 
	 * @param pageNum 必须
	 * @param pageSize 必须
	 * @param startTime 可选
	 * @param endTime 可选 
	 * @param name 可选，终端名称，支持模糊查询
	 * @return   {"msg":"请求成功","code":"200","data":{"total":1,"list":[{"id":1,"name":"终端1","ip":"192.168.6.14","port":"80","username":"admin","password":"14e24dc8cf60446f56eb163a7b85bce8a53d3f766badf15e86a1af857d9a9012","crtTime":1542211200000}],"pageNum":1,"pageSize":5,"size":1,"startRow":1,"endRow":1,"pages":1,"prePage":0,"nextPage":0,"isFirstPage":true,"isLastPage":true,"hasPreviousPage":false,"hasNextPage":false,"navigatePages":8,"navigatepageNums":[1],"navigateFirstPage":1,"navigateLastPage":1,"firstPage":1,"lastPage":1}}
	 * @author shaosen
	 * @Date 2018年11月16日
	 */
	@RequestMapping(value = "maxview/datagrid",method=RequestMethod.POST)
	public JSONObject dataGrid(@RequestBody MaxviewConfigVo vo) {
		PageInfo<MaxviewConfig> pageInfo = maxviewConfigService.dataGrid(vo);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, pageInfo);
	}
	
	
	/**获取所有终端 
	 * @return  {"msg":"请求成功","code":"200","data":[{"id":1,"name":"终端1","ip":"192.168.6.14","port":"80","username":"admin","password":"14e24dc8cf60446f56eb163a7b85bce8a53d3f766badf15e86a1af857d9a9012","crtTime":1542211200000}]} 
	 * @author shaosen
	 * @Date 2018年11月21日
	 */
	@RequestMapping(value = "maxview/list",method=RequestMethod.GET)
	public JSONObject getAll() {
		List<MaxviewConfig> list = maxviewConfigService.getAll();
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,list);
	}
	
}
