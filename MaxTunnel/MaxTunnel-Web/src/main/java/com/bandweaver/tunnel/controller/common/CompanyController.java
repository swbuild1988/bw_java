package com.bandweaver.tunnel.controller.common;

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
import com.bandweaver.tunnel.common.biz.itf.CompanyService;
import com.bandweaver.tunnel.common.biz.pojo.Company;
import com.bandweaver.tunnel.common.biz.vo.CompanyVo;
import com.bandweaver.tunnel.common.platform.constant.StatusCodeEnum;
import com.bandweaver.tunnel.common.platform.util.CommonUtil;
import com.bandweaver.tunnel.common.platform.util.DataTypeUtil;
import com.github.pagehelper.PageInfo;

/**
 *  公司管理
 * @author shaosen
 * @date 2018年6月26日
 */
@Controller
@ResponseBody
public class CompanyController extends BaseController<Company> {

	
	 @Autowired
	 private CompanyService companyService;
	 
	 /**添加公司信息 
	 * @param name 名称
	 * @return   
	 * @author shaosen
	 * @Date 2018年9月19日
	 */
	@RequestMapping(value="companies",method=RequestMethod.POST)
	 public JSONObject add(@RequestBody Company company) {
		 companyService.add(company);
		 return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	 }
	
	 /**
     *  获取所有公司列表
     * @param    
     * @return {"msg":"请求成功","code":"200","data":[{"id":1,"name":"波汇科技","crtTime":1529596800000},{"id":2,"name":"中国移动","crtTime":1529596800000},{"id":3,"name":"中国石化","crtTime":1529596800000},{"id":4,"name":"中国电信","crtTime":1529596800000},{"id":5,"name":"中建一局","crtTime":1529596800000}]}  
     * @author shaosen
     * @date 2018年6月25日
     */
    @RequestMapping(value="companies",method=RequestMethod.GET)
    public JSONObject getCompanyList() {
    	List<Company> list = companyService.getList();
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,list);
    }
    
    /**
     *  通过公司id获取公司信息
     * @param  id 公司id
     * @param    
     * @return {"msg":"请求成功","code":"200","data":{"id":1,"name":"波汇科技","crtTime":1529596800000}}  
     * @throws
     * @author shaosen
     * @date 2018年6月25日
     */
    @RequestMapping(value="companies/{id}",method=RequestMethod.GET)
    public JSONObject getById(@PathVariable Integer id) {
    	Company company = companyService.getCompanyById(id);
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,company);
    }
    
    /**
     * 删除
     * @param  id 公司id
     * @param    
     * @return   {"msg":"请求成功","code":"200","data":{}}
     * @throws
     * @author shaosen
     * @date 2018年6月26日
     */
    @RequestMapping(value="companies/{id}",method=RequestMethod.DELETE)
    public JSONObject delete(@PathVariable Integer id) {
    	companyService.deleteById(id);
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }
    
    
    /**
     * 批量删除
     */
    @Override
    @RequestMapping(value="companies/batch/{ids}",method=RequestMethod.DELETE)
	public JSONObject deleteBatch(@PathVariable String ids) {
    	List<Integer> list = convertStringToList(ids);
		companyService.deleteBatch(list);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}
    
    /**通过主键更新 
     * @param company
     * @return   
     * @author shaosen
     * @Date 2018年9月19日
     */
    @RequestMapping(value="companies",method=RequestMethod.PUT)
    public JSONObject update(@RequestBody Company company ) {
    	companyService.update(company);
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }
    
    
    /**公司分页查询 
     * @param name 公司名称（支持模糊查询）
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param pageNum 必须
     * @param pageSize 必须
     * @return   {"msg":"请求成功","code":"200","data":{"total":5,"list":[{"id":1,"name":"波汇科技","crtTime":1529596800000},{"id":2,"name":"中国移动","crtTime":1529596800000},{"id":3,"name":"中国石化","crtTime":1529596800000},{"id":4,"name":"中国电信","crtTime":1529596800000},{"id":5,"name":"中建一局","crtTime":1529596800000}],"pageNum":1,"pageSize":5,"size":5,"startRow":1,"endRow":5,"pages":1,"prePage":0,"nextPage":0,"isFirstPage":true,"isLastPage":true,"hasPreviousPage":false,"hasNextPage":false,"navigatePages":8,"navigatepageNums":[1],"navigateFirstPage":1,"navigateLastPage":1,"firstPage":1,"lastPage":1}}
     * @author shaosen
     * @Date 2018年9月19日
     */
    @RequestMapping(value="companies/datagrid",method=RequestMethod.POST)
    public JSONObject dataGrid(@RequestBody CompanyVo vo) {
    	PageInfo<Company> pageInfo = companyService.dataGrid(vo);
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, pageInfo);
    }

     
}
