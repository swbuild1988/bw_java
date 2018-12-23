package com.bandweaver.tunnel.controller.common;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.dto.DepartmentDto;
import com.bandweaver.tunnel.common.biz.itf.CompanyService;
import com.bandweaver.tunnel.common.biz.itf.DepartmentService;
import com.bandweaver.tunnel.common.biz.itf.StaffService;
import com.bandweaver.tunnel.common.biz.pojo.Department;
import com.bandweaver.tunnel.common.biz.vo.DepartmentVo;
import com.bandweaver.tunnel.common.platform.constant.StatusCodeEnum;
import com.bandweaver.tunnel.common.platform.util.CommonUtil;
import com.github.pagehelper.PageInfo;
/**
 *  部门管理
 * @author shaosen
 * @date 2018年6月26日
 */
@Controller
@ResponseBody
public class DepartmentController extends BaseController<Department>{

	
	@Autowired
	private StaffService staffService;
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private CompanyService companyService;
	
	
	/**
	 *  添加
	 * @param  name 部门名称
	 * @param    
	 * @return {"msg":"请求成功","code":"200","data":{}}  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月26日
	 */
	@RequestMapping(value="departments",method=RequestMethod.POST)
	public JSONObject add(@RequestBody Department department ) {
		departmentService.add(department);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}
	
	/**
	 * 根据id查询
	 * @param  id 部门id
	 * @param    
	 * @return {"msg":"请求成功","code":"200","data":{"id":1,"name":"产品部","crtTime":1529942400000}}  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月26日
	 */
	@RequestMapping(value="departments/{id}",method=RequestMethod.GET)
	public JSONObject getById(@PathVariable Integer id) {
		DepartmentDto dto = departmentService.getDtoById(id);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, dto);
	}
	

	/**
	 * 查询所有部门信息
	 * @param    
	 * @return  {"msg":"请求成功","code":"200","data":[{"id":1,"name":"产品部","crtTime":1529942400000},{"id":4,"name":"销售部","crtTime":1529942400000},{"id":3,"name":"运营部","crtTime":1529942400000},{"id":2,"name":"研发部","crtTime":1529942400000}]} 
	 * @throws
	 * @author shaosen
	 * @date 2018年6月26日
	 */
	@RequestMapping(value="departments",method=RequestMethod.GET)
	public JSONObject getDtoById() {
		List<DepartmentDto> list = departmentService.getDtoList();
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, list);
	}
	
	
	/**
	 * 删除
	 * @param  id 部门id
	 * @return {"msg":"请求成功","code":"200","data":{}}    
	 * @throws
	 * @author shaosen
	 * @date 2018年6月26日
	 */
	@RequestMapping(value="departments/{id}",method=RequestMethod.DELETE)
	public JSONObject delete(@PathVariable Integer id) {
		departmentService.deleteById(id);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}
	
	/**
	 * 批量删除
	 */
	@Override
	@RequestMapping(value="departments/batch/{ids}",method=RequestMethod.DELETE)
	public JSONObject deleteBatch(@PathVariable String ids) {
		List<Integer> list = convertStringToList(ids);
		departmentService.deleteBatch(list);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}
	
	/**
	 * 修改
	 * @param  参考添加接口
	 * @param    
	 * @return {"msg":"请求成功","code":"200","data":{}}      
	 * @throws
	 * @author shaosen
	 * @date 2018年6月26日
	 */
	@RequestMapping(value="departments",method=RequestMethod.PUT)
	public JSONObject update(@RequestBody Department department ) {
		departmentService.update(department);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}
	
	
	/**
	 *  根据公司id查询部门列表
	 * @param  companyId 公司id
	 * @param    
	 * @return {"msg":"请求成功","code":"200","data":[{"id":1,"name":"产品部","crtTime":1529942400000},{"id":2,"name":"研发部","crtTime":1529942400000},{"id":3,"name":"运营部","crtTime":1529942400000},{"id":4,"name":"销售部","crtTime":1529942400000}]}  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月26日
	 */
	@RequestMapping(value="companies/{companyId}/departments",method=RequestMethod.GET)
	public JSONObject getDepartmentsByCompanyId(@PathVariable("companyId") Integer companyId ) {
		List<DepartmentDto> list = departmentService.getDepartmentsByCompanyId(companyId);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,list);
	}
	
	/**部门分页查询 
	 * @param name 支持模糊查询
	 * @param startTime
	 * @param endTime
	 * @param pageNum
	 * @param pageSize
	 * @return   
	 * @author shaosen
	 * @Date 2018年9月20日
	 */
	@RequestMapping(value="departments/datagrid",method=RequestMethod.POST)
	public JSONObject dataGrid(@RequestBody DepartmentVo vo ) {
		PageInfo<DepartmentDto> pageInfo = departmentService.dataGrid(vo);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, pageInfo);
	}

	
}
