package com.bandweaver.tunnel.controller.oam;

import java.util.ArrayList;
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
import com.bandweaver.tunnel.common.biz.dto.oam.CustomerDto;
import com.bandweaver.tunnel.common.biz.itf.CompanyService;
import com.bandweaver.tunnel.common.biz.itf.oam.CustomerService;
import com.bandweaver.tunnel.common.biz.pojo.Company;
import com.bandweaver.tunnel.common.biz.pojo.oam.Customer;
import com.bandweaver.tunnel.common.biz.vo.oam.CustomerVo;
import com.bandweaver.tunnel.common.platform.constant.StatusCodeEnum;
import com.bandweaver.tunnel.common.platform.log.DescEnum;
import com.bandweaver.tunnel.common.platform.log.WriteLog;
import com.bandweaver.tunnel.common.platform.util.CommonUtil;
import com.bandweaver.tunnel.common.platform.util.DateUtil;
import com.bandweaver.tunnel.controller.common.BaseController;
import com.github.pagehelper.PageInfo;


/**
 * 客户管理
 * @author shaosen
 * @date 2018年7月26日
 */
@Controller
@ResponseBody
public class CustomerController extends BaseController<Customer> {

	
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
		
		customerService.addBatch(list);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);

	}
	
	/**
	 * 添加客户
	 * @param  companyId 公司id
	 * @param  contact 联系人名称 必须
	 * @param  tel 电话 必须
	 * @param  contact2 联系人名称2 可选
	 * @param  tel2 电话2 可选
	 * @param  contact3 联系人名称3 可选
	 * @param  tel3 电话3 可选
	 * @return {"msg":"请求成功","code":"200","data":{}}  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月19日
	 */
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
	@WriteLog(DescEnum.OAM_DELETE_CUSTOMER)
	@RequestMapping(value="/customers/{id}",method=RequestMethod.DELETE)
	public JSONObject delete(@PathVariable Integer id) {
		customerService.deleteById(id);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}
	
	/**
	 * 批量删除
	 */
	@Override
	@RequestMapping(value="/customers/batch/{ids}",method=RequestMethod.DELETE)
	public JSONObject deleteBatch(@PathVariable String ids) throws Exception {
		List<Integer> list = convertStringToList(ids);
		customerService.deleteBatch(list);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}

	
	
	/**
	 * 根据id获取客户信息(包含公司信息)
	 * @param  id 客户id
	 * @return {"msg":"请求成功","code":"200","data":{"id":2,"company":{"id":1,"name":"波汇科技","crtTime":1529596800000},"contact":"联系人0","tel":"13722228880","crtTime":1529979467000}}  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月15日
	 */
	@RequestMapping(value="/customers/{id}",method=RequestMethod.GET)
	public JSONObject getById(@PathVariable Integer id) {
		CustomerDto dto = customerService.getDtoById(id);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,dto);
	}

	
	
	/**
	 * 客户列表分页查询
	 * @param  companyId 公司id
	 * @param  contact 联系人（支持模糊查询）
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @param pageNum 必须
	 * @param pageSize 必须
	 * @return {"msg":"请求成功","code":"200","data":{"total":4,"list":[{"id":2,"company":{"id":1,"name":"波汇科技","crtTime":1529596800000},"contact":"联系人0","tel":"13722228880","crtTime":1529979467000},{"id":3,"company":{"id":2,"name":"中国移动","crtTime":1529596800000},"contact":"联系人1","tel":"13722228881","crtTime":1529893067000}],"pageNum":1,"pageSize":2,"size":2,"startRow":1,"endRow":2,"pages":2,"prePage":0,"nextPage":2,"isFirstPage":true,"isLastPage":false,"hasPreviousPage":false,"hasNextPage":true,"navigatePages":8,"navigatepageNums":[1,2],"navigateFirstPage":1,"navigateLastPage":2,"firstPage":1,"lastPage":2}}  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月19日
	 */
	@RequestMapping(value="/customers/datagrid",method=RequestMethod.POST)
	public JSONObject dataGrid(@RequestBody CustomerVo vo) {
		PageInfo<CustomerDto> pageInfo = customerService.dataGrid(vo);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,pageInfo);
	}


	
}
