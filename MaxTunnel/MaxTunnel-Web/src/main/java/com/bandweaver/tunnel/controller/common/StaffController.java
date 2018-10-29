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
import com.bandweaver.tunnel.common.biz.dto.StaffDto;
import com.bandweaver.tunnel.common.biz.itf.CompanyService;
import com.bandweaver.tunnel.common.biz.itf.DepartmentService;
import com.bandweaver.tunnel.common.biz.itf.StaffService;
import com.bandweaver.tunnel.common.biz.pojo.Staff;
import com.bandweaver.tunnel.common.biz.vo.StaffVo;
import com.bandweaver.tunnel.common.platform.constant.StatusCodeEnum;
import com.bandweaver.tunnel.common.platform.util.CommonUtil;
import com.github.pagehelper.PageInfo;

/**
 * 员工管理
 * @author shaosen
 * @date 2018年6月26日
 */
@Controller
@ResponseBody
public class StaffController extends BaseController<Staff> {

	
	@Autowired
	private StaffService staffService;
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private CompanyService companyService;
	
	
	/**
	 * 根据id查询员工信息（包含职位信息和部门信息）
	 * @param  id 员工id
	 * @return {"msg":"请求成功","code":"200","data":{"id":1,"name":"张三","dept":{"id":1,"name":"产品部","crtTime":1529942400000},"position":{"id":1,"name":"总经理","crtTime":1529596800000},"accountId":1,"crtTime":1529942400000}}  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月26日
	 */
	@RequestMapping(value="staffs/{id}",method=RequestMethod.GET)
	public JSONObject getById(@PathVariable Integer id) {
		StaffDto dto = staffService.getDtoById(id);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, dto);
	}
	
	
	
	/**
	 * 添加员工
	 * @param  name 姓名
	 * @param  deptId 部门id（部门表）
	 * @param  positionId 职位id（职位表）
	 * @param  accountId 登录账号id
	 * @param    
	 * @return {"msg":"请求成功","code":"200","data":{}}  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月26日
	 */
	@RequestMapping(value="staffs",method=RequestMethod.POST)
	public JSONObject add(@RequestBody Staff staff) {
		staffService.add(staff);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}
	
	/**
	 * 删除
	 * @param  id 员工id
	 * @return {"msg":"请求成功","code":"200","data":{}}  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月26日
	 */
	@RequestMapping(value="staffs/{id}",method=RequestMethod.DELETE)
	public JSONObject delete(@PathVariable Integer id) {
		staffService.deleteById(id);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}
	
	/**
	 *批量删除
	 */
	@Override
	@RequestMapping(value="staffs/batch/{ids}",method=RequestMethod.DELETE)
	public JSONObject deleteBatch(@PathVariable String ids) {
		List<Integer> list = convertStringToList(ids);
		staffService.deleteBatch(list);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}
	
	/**
	 *  修改员工信息
	 * @param  staff 参考添加接口
	 * @param    
	 * @return {"msg":"请求成功","code":"200","data":{}}  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月26日
	 */
	@RequestMapping(value="staffs",method=RequestMethod.PUT)
	public JSONObject update(@RequestBody Staff staff) {
		staffService.update(staff);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}
	
	
	
	/**
	 * 获取所有员工列表
	 * @param    
	 * @return {"msg":"请求成功","code":"200","data":[{"id":1,"name":"张三","dept":{"id":1,"name":"产品部","crtTime":1529942400000},"position":{"id":1,"name":"总经理","crtTime":1529596800000},"accountId":1,"crtTime":1529942400000},{"id":2,"name":"李四","dept":{"id":2,"name":"研发部","crtTime":1529942400000},"position":{"id":2,"name":"产品总监","crtTime":1529596800000},"accountId":2,"crtTime":1529942400000},{"id":5,"name":"王二","dept":{"id":1,"name":"产品部","crtTime":1529942400000},"position":{"id":3,"name":"主管","crtTime":1529596800000},"accountId":5,"crtTime":1529942400000},{"id":4,"name":"赵六","dept":{"id":4,"name":"销售部","crtTime":1529942400000},"position":{"id":4,"name":"普通员工","crtTime":1529596800000},"accountId":4,"crtTime":1529942400000},{"id":3,"name":"王五","dept":{"id":3,"name":"运营部","crtTime":1529942400000},"position":{"id":3,"name":"主管","crtTime":1529596800000},"accountId":3,"crtTime":1529942400000}]}  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月26日
	 */
	@RequestMapping(value="staffs",method=RequestMethod.GET)
	public JSONObject getList() {
		List<StaffDto> list = staffService.getDtoList();
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,list);
	}
	
	
	/**
	 * 根据公司id和部门id查询所有员工
	 * @param  companyId 公司id
	 * @param  deptId 部门id
	 * @param    
	 * @return {"msg":"请求成功","code":"200","data":[{"id":1,"name":"张三","dept":{"id":1,"name":"产品部","crtTime":1529942400000},"position":{"id":1,"name":"总经理","crtTime":1529596800000},"accountId":1,"crtTime":1529942400000},{"id":5,"name":"王二","dept":{"id":1,"name":"产品部","crtTime":1529942400000},"position":{"id":3,"name":"主管","crtTime":1529596800000},"accountId":5,"crtTime":1529942400000}]}  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月26日
	 */
	@RequestMapping(value="companies/{companyId}/departments/{deptId}/staffs",method=RequestMethod.GET)
	public JSONObject getStaffsByCompanyIdAndDeptId(@PathVariable("companyId") Integer companyId,
			@PathVariable("deptId")Integer deptId) {
		List<StaffDto> list = staffService.getStaffsByCompanyIdAndDeptId(companyId,deptId);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,list);
	}

	/**分页查询 
	 * @param name 姓名（支持模糊查询）
	 * @param deptId 部门id
	 * @param positionId 职位id
	 * @param accountId 账号
	 * @param startTime
	 * @param endTime
	 * @param pageNum
	 * @param pageSize
	 * @return   {"msg":"请求成功","code":"200","data":{"total":5,"list":[{"id":1,"name":"张三","dept":{"id":1,"name":"产品部","crtTime":1529942400000},"position":{"id":1,"name":"总经理","crtTime":1529596800000},"accountId":1,"crtTime":1529942400000},{"id":2,"name":"李四","dept":{"id":2,"name":"研发部","crtTime":1529942400000},"position":{"id":2,"name":"产品总监","crtTime":1529596800000},"accountId":2,"crtTime":1529942400000}],"pageNum":1,"pageSize":2,"size":2,"startRow":1,"endRow":2,"pages":3,"prePage":0,"nextPage":2,"isFirstPage":true,"isLastPage":false,"hasPreviousPage":false,"hasNextPage":true,"navigatePages":8,"navigatepageNums":[1,2,3],"navigateFirstPage":1,"navigateLastPage":3,"firstPage":1,"lastPage":3}}
	 * @author shaosen
	 * @Date 2018年9月28日
	 */
	@RequestMapping(value="staffs/datagrid",method=RequestMethod.POST)
	public JSONObject dataGrid(@RequestBody StaffVo vo) {
		PageInfo<StaffDto> pageInfo = staffService.dataGrid(vo);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, pageInfo);
	}
	
}
 