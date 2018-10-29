package com.bandweaver.tunnel.controller.mam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.dto.mam.report.MeasObjReportDto;
import com.bandweaver.tunnel.common.biz.itf.mam.report.MeasObjReportService;
import com.bandweaver.tunnel.common.biz.vo.mam.report.MeasObjReportVo;
import com.bandweaver.tunnel.common.platform.constant.StatusCodeEnum;
import com.bandweaver.tunnel.common.platform.util.CommonUtil;
import com.github.pagehelper.PageInfo;

/**监测对象数据统计
 * @author shaosen
 * @date 2018年9月15日
 */
@Controller
@ResponseBody
public class MeasObjReportController {

	@Autowired
	private MeasObjReportService measObjReportService;
	
	/**监测对象数据统计分页查询 
	 * @param objectId 监测对象id
	 * @param objectType 监测对象类型 枚举
	 * @param timeType 时间类型 枚举
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @param pageNum 
	 * @param pageSize 
	 * @return  {"msg":"请求成功","code":"200","data":{"total":92,"list":[{"id":1,"objectId":1024,"objectTypeName":"甲烷","timeTypeName":"时","sTime":1536976800000,"eTime":1536980399000,"maxValue":0.0,"minValue":0.0,"avgValue":0.0},{"id":9,"objectId":1041,"objectTypeName":"湿度","timeTypeName":"时","sTime":1536976800000,"eTime":1536980399000,"maxValue":0.0,"minValue":0.0,"avgValue":0.0},{"id":8,"objectId":1040,"objectTypeName":"湿度","timeTypeName":"时","sTime":1536976800000,"eTime":1536980399000,"maxValue":0.0,"minValue":0.0,"avgValue":0.0},{"id":4,"objectId":1031,"objectTypeName":"硫化氢","timeTypeName":"时","sTime":1536976800000,"eTime":1536980399000,"maxValue":0.0,"minValue":0.0,"avgValue":0.0},{"id":3,"objectId":1029,"objectTypeName":"甲烷","timeTypeName":"时","sTime":1536976800000,"eTime":1536980399000,"maxValue":0.0,"minValue":0.0,"avgValue":0.0}],"pageNum":1,"pageSize":5,"size":5,"startRow":1,"endRow":5,"pages":19,"prePage":0,"nextPage":2,"isFirstPage":true,"isLastPage":false,"hasPreviousPage":false,"hasNextPage":true,"navigatePages":8,"navigatepageNums":[1,2,3,4,5,6,7,8],"navigateFirstPage":1,"navigateLastPage":8,"lastPage":8,"firstPage":1}} 
	 * @author shaosen
	 * @Date 2018年9月15日
	 */
	@RequestMapping(value="measobjs-report/datagrid",method=RequestMethod.POST)
	public JSONObject dataGrid(@RequestBody MeasObjReportVo vo) {
		PageInfo<MeasObjReportDto> pageInfo = measObjReportService.dataGrid(vo);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,pageInfo);
	}
	
	
}
