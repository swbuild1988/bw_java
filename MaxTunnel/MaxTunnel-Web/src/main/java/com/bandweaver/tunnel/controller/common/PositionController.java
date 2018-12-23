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
import com.bandweaver.tunnel.common.biz.dto.PositionDto;
import com.bandweaver.tunnel.common.biz.itf.PositionService;
import com.bandweaver.tunnel.common.biz.pojo.Position;
import com.bandweaver.tunnel.common.biz.vo.PositionVo;
import com.bandweaver.tunnel.common.platform.constant.StatusCodeEnum;
import com.bandweaver.tunnel.common.platform.util.CommonUtil;
import com.github.pagehelper.PageInfo;

/**
 *  职位管理
 * @author shaosen
 * @date 2018年6月26日
 */
@Controller
@ResponseBody
public class PositionController extends BaseController<Position>{

	@Autowired
    private PositionService positionService;
	
	 
    /**获取所有职位列表
     * @param    
     * @return {"msg":"请求成功","code":"200","data":[{"id":1,"name":"总经理","crtTime":1529596800000},{"id":2,"name":"产品总监","crtTime":1529596800000},{"id":3,"name":"主管","crtTime":1529596800000},{"id":4,"name":"普通员工","crtTime":1529596800000},{"id":5,"name":"技术经理","crtTime":1529251200000}]}  
     * @author shaosen
     * @date 2018年6月25日
     */
    @RequestMapping(value="positions",method=RequestMethod.GET)
    public JSONObject getPositionList() {
    	List<Position> list = positionService.getList();
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,list);
    }
    
    /**通过职位id获取职位信息
     * @param  id 职位id
     * @return {"msg":"请求成功","code":"200","data":{"id":1,"name":"总经理","crtTime":1529596800000}}  
     * @author shaosen
     * @date 2018年6月25日
     */
    @RequestMapping(value="positions/{id}",method=RequestMethod.GET)
    public JSONObject getById(@PathVariable Integer id) {
    	Position position = positionService.getPositionById(id);
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,position);
    }
    
    
    /**添加职位
     * @param  name 职位名称
     * @return  {"msg":"请求成功","code":"200","data":{}}
     * @author shaosen
     * @Date 2018年7月26日
     */
    @RequestMapping(value="positions",method=RequestMethod.POST)
    public JSONObject add(@RequestBody Position pojo) {
    	positionService.add(pojo);
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }
    
    
    /**删除
     * @param  id 职位id
     * @return  {"msg":"请求成功","code":"200","data":{}}  
     * @author shaosen
     * @Date 2018年7月26日
     */
    @RequestMapping(value="positions/{id}",method=RequestMethod.DELETE)
    public JSONObject delete(@PathVariable Integer id) {
    	positionService.delete(id);
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }
    
    /**
     * 批量删除
     */
	@Override
	@RequestMapping(value="positions/batch/{ids}",method=RequestMethod.DELETE)
	public JSONObject deleteBatch(@PathVariable String ids) {
		List<Integer> list = convertStringToList(ids);
		positionService.deleteBatch(list);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}

    
    /**更新 
     * @param position
     * @return   
     * @author shaosen
     * @Date 2018年9月20日
     */
    @RequestMapping(value="positions",method=RequestMethod.PUT)
    public JSONObject update(@RequestBody Position position) {
    	positionService.update(position);
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);

    }
    
    
    /**分页查询 
     * @param name 支持模糊查询
     * @param startTime
     * @param endTime
     * @param pageNum
     * @param pageSize
     * @return   
     * @author shaosen
     * @Date 2018年9月20日
     */
    @RequestMapping(value="positions/datagrid",method=RequestMethod.POST)
    public JSONObject dataGrid(@RequestBody PositionVo vo ) {
    	PageInfo<PositionDto> pageInfo = positionService.dataGrid(vo);
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, pageInfo);
    }


    
}
