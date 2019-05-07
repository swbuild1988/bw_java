package com.bandweaver.tunnel.controller.oam;

import java.util.ArrayList;
import java.util.Date;
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
import com.bandweaver.tunnel.common.biz.itf.CompanyService;
import com.bandweaver.tunnel.common.biz.itf.oam.CustomerService;
import com.bandweaver.tunnel.common.biz.pojo.Company;
import com.bandweaver.tunnel.common.biz.pojo.oam.Customer;
import com.bandweaver.tunnel.common.platform.constant.StatusCodeEnum;
import com.bandweaver.tunnel.common.platform.log.DescEnum;
import com.bandweaver.tunnel.common.platform.log.WriteLog;
import com.bandweaver.tunnel.common.platform.util.CommonUtil;
import com.bandweaver.tunnel.common.platform.util.DateUtil;


/**
 * 客户管理
 * @author shaosen
 * @date 2018年7月26日
 */
@Controller
@ResponseBody
public class CustomerController {

	
	@Autowired
	private CustomerService customerService;
	@Autowired
	private CompanyService companyService;
	
	
	/**
	 * 添加客户测试数据
	 * @return JSONObject  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月14日
	 */
	@Deprecated
	@RequestMapping(value="customers/batch",method=RequestMethod.GET)
	public JSONObject addCustomerTestData() {
		List<Customer> list = new ArrayList<>();
		List<Company> companyList = companyService.getList();
		for(int i = 0; i < companyList.size(); i ++ ) {
			Customer customer = new Customer();
			customer.setCompanyId(companyList.get(i).getId());
			customer.setContact("联系人"+i);
			customer.setTel("1372222888"+i);
			customer.setCrtTime(DateUtil.getFrontDay(new Date(), i));
			list.add(customer);
		}
		
		//customerService.addBatch(list);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);

	}
	
	/**
	 * 添加客户
	 * @param  companyId 公司id
	 * @param  contact 联系人名称 必须
	 * @param  tel 手机号
	 * @param  duty 职责
	 * @param  tel2 电话
	 * @param  mail 
	 * @param  type 类型：一般联系人/紧急联系人
	 * @return {"msg":"请求成功","code":"200","data":{}}  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月19日
	 */
	@RequiresPermissions("customer:add")
	@WriteLog(value=DescEnum.OAM_ADD_CUSTOMER)
	@RequestMapping(value="/customers",method=RequestMethod.POST)
	public JSONObject add(@RequestBody Customer customer) {
		customerService.add(customer);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}
	

	
	/**
	 * 修改
	 * @param  customer 参考添加接口
	 * @return {"msg":"请求成功","code":"200","data":{}}    
	 * @throws
	 * @author shaosen
	 * @date 2018年6月26日
	 */
	@RequiresPermissions("customer:update")
	@WriteLog(DescEnum.OAM_UPDATE_CUSTOMER)
	@RequestMapping(value="/customers",method=RequestMethod.PUT)
	public JSONObject update(@RequestBody Customer customer) {
		customerService.update(customer);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}
	
	
	/**
	 *删除
	 * @param  id 客户id
	 * @return  {"msg":"请求成功","code":"200","data":{}}      
	 * @throws
	 * @author shaosen
	 * @date 2018年6月26日
	 */
	@RequiresPermissions("customer:delete")
	@WriteLog(DescEnum.OAM_DELETE_CUSTOMER)
	@RequestMapping(value="/customers/{id}",method=RequestMethod.DELETE)
	public JSONObject delete(@PathVariable Integer id) {
		customerService.deleteById(id);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}
	
	/**
	 * 批量删除
	 */
	@RequiresPermissions("customer:delete")
	@RequestMapping(value="/customers/batch/{ids}",method=RequestMethod.DELETE)
	public JSONObject deleteBatch(@PathVariable("ids") String ids) {
		List<Integer> list = CommonUtil.convertStringToList(ids);
		customerService.deleteBatch(list);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}

}
