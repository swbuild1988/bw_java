package com.bandweaver.tunnel.controller.common;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
import com.bandweaver.tunnel.common.biz.itf.common.UserService;
import com.bandweaver.tunnel.common.biz.pojo.Staff;
import com.bandweaver.tunnel.common.biz.pojo.common.User;
import com.bandweaver.tunnel.common.biz.vo.StaffVo;
import com.bandweaver.tunnel.common.platform.constant.Constants;
import com.bandweaver.tunnel.common.platform.constant.StatusCodeEnum;
import com.bandweaver.tunnel.common.platform.exception.BandWeaverException;
import com.bandweaver.tunnel.common.platform.log.DescEnum;
import com.bandweaver.tunnel.common.platform.log.WriteLog;
import com.bandweaver.tunnel.common.platform.util.CommonUtil;
import com.bandweaver.tunnel.common.platform.util.DataTypeUtil;
import com.bandweaver.tunnel.common.platform.util.PropertiesUtil;
import com.bandweaver.tunnel.common.platform.util.Sha256;
import com.bandweaver.tunnel.common.platform.util.StringTools;
import com.github.pagehelper.PageInfo;


/**
 * 员工管理
 * @author shaosen
 * @date 2018年6月26日
 */
@Controller
@ResponseBody
public class StaffController {

	
	@Autowired
	private StaffService staffService;
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private UserService userService;
	
	
	/**
	 * 添加员工（同时根据员工姓名生成一条登陆账户，账号就为姓名的汉语拼音，初始密码统一为Aa123456
	 * 如添加员工张三，会自动生成一个zhangsan账号，需要注意的是：如果汉字为多音字，则生成的账号是随机的，如“重"是多音字，账号为为chong或zhong其中之一，需要手动修改账号信息）
	 * @param  name 姓名
	 * @param  sex 性别，字符串
	 * @param  telphone 联系方式
	 * @param  hireDate 入职时间
	 * @param  deptId 部门id
	 * @param  positionId 职位id
	 * @param outside 外来人员标识，1为内部员工，2为外来人员
	 * @param	identityNO 身份证号
	 * @return {"msg":"请求成功","code":"200","data":{}}  
	 * @author shaosen
	 * @date 2018年6月26日
	 */
	@WriteLog(DescEnum.ADD_USER)
	@RequestMapping(value="staffs",method=RequestMethod.POST)
	public JSONObject add(@RequestBody Staff staff) {
		staffService.add(staff);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}
	
	/**
	 * 批量添加外来访客
	 * @param name 姓名
	 * @param telphone 联系方式
	 * @param hireDate 入职时间
	 * @param outside 外来人员标识，1为内部员工，2为外来人员
	 * @param identityNO 身份证号
	 * @return
	 * @author ya.liu
	 * @Date 2019年3月1日
	 */
	@RequestMapping(value="staffs/add-batch",method=RequestMethod.POST)
	public JSONObject add(@RequestBody List<Staff> list) {
		for(Staff staff : list) {
			staffService.add(staff);
		}
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}
	
	
	/**根据id查询员工信息
	 * @param  id 员工id
	 * @return   
	 * @author shaosen
	 * @date 2018年6月26日
	 */
	@RequestMapping(value="staffs/{id}",method=RequestMethod.GET)
	public JSONObject getById(@PathVariable Integer id) {
		StaffDto dto = staffService.getDtoById(id);
		if(!StringTools.isNullOrEmpty(dto)) {
			dto.setAcctInfo(userService.getUserDet(dto.getId()));
		}
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, dto);
	}
	
	
	/**
	 *批量删除
	 */
	@RequestMapping(value="staffs/batch/{ids}",method=RequestMethod.DELETE)
	public JSONObject deleteBatch(@PathVariable String ids) {
		List<Integer> list = CommonUtil.convertStringToList(ids);
		staffService.deleteBatch(list);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}
	
	/**修改员工信息
	 * @param  staff 参考添加接口,注意：员工姓名不可更改,当接收到的code为20009时，表示修改的登录用户名已被占用，需弹框提醒用户
	 * @param    
	 * @return {"msg":"请求成功","code":"200","data":{}}  
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
	 * @return   
	 * @author shaosen
	 * @date 2018年6月26日
	 */
	@RequestMapping(value="staffs",method=RequestMethod.GET)
	public JSONObject getList() {
		List<StaffDto> list = staffService.getDtoList();
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,list);
	}
	
	/**
	 * 不分页，条件获取员工信息
	 * @param name 姓名（支持模糊查询）
	 * @param account 账号，精确查找
	 * @param sex 性别
	 * @param deptId 部门id
	 * @param positionId 职位id
	 * @param outside 外来人员标识
	 * @param identityNO 身份证号
	 * @param telphone 手机号
	 * @return
	 * @author ya.liu
	 * @Date 2019年2月28日
	 */
	@RequestMapping(value="staffs/conditions",method=RequestMethod.POST)
	public JSONObject getListByConditions(@RequestBody StaffVo vo) {
		List<StaffDto> list = staffService.getDtoListByCondition(vo);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,list);
	}
	
	/**
	 * 根据公司id和部门id查询所有员工
	 * @param  companyId 公司id
	 * @param  deptId 部门id
	 * @param    
	 * @return {"msg":"请求成功","code":"200","data":[{"id":1,"name":"张三","dept":{"id":1,"name":"产品部","crtTime":1529942400000},"position":{"id":1,"name":"总经理","crtTime":1529596800000},"accountId":1,"crtTime":1529942400000},{"id":5,"name":"王二","dept":{"id":1,"name":"产品部","crtTime":1529942400000},"position":{"id":3,"name":"主管","crtTime":1529596800000},"accountId":5,"crtTime":1529942400000}]}  
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
	 * @param account 账号，精确查找
	 * @param sex 性别
	 * @param deptId 部门id
	 * @param positionId 职位id
	 * @param outside 外来人员标识
	 * @param startTime
	 * @param endTime
	 * @param pageNum
	 * @param pageSize
	 * @return   
	 * @author shaosen
	 * @Date 2018年9月28日
	 */
	@RequestMapping(value="staffs/datagrid",method=RequestMethod.POST)
	public JSONObject dataGrid(@RequestBody JSONObject reqJson) {
		CommonUtil.hasAllRequired(reqJson, "pageNum,pageSize");
		StaffVo vo = CommonUtil.parse2Obj(reqJson, StaffVo.class);
		PageInfo<StaffDto> pageInfo = staffService.dataGrid(vo);
		List<StaffDto> list = pageInfo.getList();
		for (StaffDto staffDto : list) {
			staffDto.setAcctInfo(userService.getUserDet(staffDto.getId()));
		}
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, pageInfo);
	}

	
	/**重置密码 
	 * @param id 账号/员工id
	 * @return   
	 * @author shaosen
	 * @Date 2018年12月1日
	 */
	@WriteLog(DescEnum.RESET_PW)
	@RequestMapping(value="users/init-pw/{id}",method=RequestMethod.GET)
	public JSONObject resetPw(@PathVariable Integer id) {
		
		String initPassword = PropertiesUtil.getString(Constants.INIT_PASSWORD);
		User user = new User();
		user.setId(id);
		user.setPassword(Sha256.getSHA256StrJava(initPassword));
		userService.updateByPrimaryKeySelective(user);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}
	
	
	/**修改登录密码 
	 * @param id 主键id
	 * @param oldPw 旧密码（sha256加密之后的密码）
	 * @param newPw 新密码（sha256加密之后的密码）
	 * @return   
	 * @author shaosen
	 * @Date 2018年12月1日
	 */
	@WriteLog(DescEnum.CHANGE_PW)
	@RequestMapping(value="users/pw",method=RequestMethod.POST)
	public JSONObject changePw(@RequestBody Map<String, String> map) {
		
		Integer id = DataTypeUtil.toInteger(map.get("id"));
		String oldPw = DataTypeUtil.toString(map.get("oldPw"));
		String newPw = DataTypeUtil.toString(map.get("newPw"));
//		String reptPw = DataTypeUtil.toString(map.get("reptPw"));//前端做校验
		
		User user = userService.selectByPrimaryKey(id);
		if(user == null)
//			return CommonUtil.returnStatusJson(StatusCodeEnum.E_20006);//用户名不存在
			throw new BandWeaverException("用户名不存在");
		
		if(!user.getPassword().equals(oldPw))
//			return CommonUtil.returnStatusJson(StatusCodeEnum.E_20007);//密码错误
			throw new BandWeaverException("原密码错误");
		
		User upt = new User();
		upt.setId(id);
		upt.setPassword(newPw);
		userService.updateByPrimaryKeySelective(upt);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}
	
}
 