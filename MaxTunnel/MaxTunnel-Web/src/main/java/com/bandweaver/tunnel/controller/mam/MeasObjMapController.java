package com.bandweaver.tunnel.controller.mam;

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
import com.bandweaver.tunnel.common.biz.constant.mam.DataType;
import com.bandweaver.tunnel.common.biz.itf.mam.mapping.MeasObjMapService;
import com.bandweaver.tunnel.common.biz.pojo.mam.mapping.MeasObjMap;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObj;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObjDI;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObjSI;
import com.bandweaver.tunnel.common.biz.vo.mam.MeasObjMapVo;
import com.bandweaver.tunnel.common.platform.constant.StatusCodeEnum;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.CommonUtil;
import com.bandweaver.tunnel.controller.common.BaseController;
import com.bandweaver.tunnel.service.mam.measobj.MeasObjModuleCenter;
import com.github.pagehelper.PageInfo;

/**开关量状态量映射管理
 * @author shaosen
 * @date 2018年9月17日
 */
@Controller
@ResponseBody
public class MeasObjMapController extends BaseController<MeasObjMap>{

	@Autowired
	private MeasObjMapService measObjMapService;
	
	
	/**根据objectId和inputValue获取映射对象 
	 * @param objectId 监测对象ID
	 * @param inputValue 输入量
	 * @return   
	 * @author shaosen
	 * @Date 2018年9月17日
	 */
	@RequestMapping(value="measobjs-map/{objectId}/inputvalue/{inputvalue}",method=RequestMethod.GET)
	public JSONObject getMaxViewMeasObj(@PathVariable("objectId") Integer objectId,
			@PathVariable("inputvalue") Integer inputValue) {
		
		MeasObjMap result = measObjMapService.getMaxViewMeasObj(objectId,inputValue);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,result);
	}
	
	
	/**通过主键id获取 
	 * @param id 主键
	 * @return   
	 * @author shaosen
	 * @Date 2018年9月17日
	 */
	@RequestMapping(value="measobjs-map/{id}",method=RequestMethod.GET)
	public JSONObject getById(@PathVariable Integer id) {
		MeasObjMap measObjMap = measObjMapService.getById(id);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, measObjMap);
	}
	
	/**添加映射关系
	 * @param objectId
	 * @param inputValue
	 * @param objectId2
	 * @param outputValue
	 * @return   
	 * @author shaosen
	 * @Date 2018年9月19日
	 */
	@RequestMapping(value="measobjs-map",method=RequestMethod.POST)
	public JSONObject add(@RequestBody MeasObjMap measObjMap) {
		measObjMapService.add(measObjMap);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}


	/**删除
	 * @param id 
	 * @return   
	 * @author shaosen
	 * @Date 2018年9月19日
	 */
	@Override
	@RequestMapping(value="measobjs-map/{id}",method=RequestMethod.DELETE)
	public JSONObject delete(@PathVariable Integer id) throws Exception {
		measObjMapService.delete(id);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}

	/**批量删除
	 * @param ids  1,2,3
	 * @return   
	 * @author shaosen
	 * @Date 2018年9月19日
	 */
	@Override
	@RequestMapping(value="measobjs-map/batch/{ids}",method=RequestMethod.DELETE)
	public JSONObject deleteBatch(@PathVariable String ids) throws Exception {
		List<Integer> list = convertStringToList(ids);
		measObjMapService.deleteBatch(list);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}

	/**更新
	 * @param pojo 参考添加接口
	 * @return   
	 * @author shaosen
	 * @Date 2018年9月19日
	 */
	@Override
	@RequestMapping(value="measobjs-map",method=RequestMethod.PUT)
	public JSONObject update(@RequestBody MeasObjMap pojo) throws Exception {
		measObjMapService.update(pojo);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}
	
	
	/**分页查询 
	 * @param pageNum 必须
	 * @param pageSize 必须
	 * @param startTime 可选
	 * @param endTime 可选
	 * @param objectId 可选 监测对象id
	 * @param objectId2 可选 对应的maxview的监测对象id
	 * @return   {"msg":"请求成功","code":"200","data":{"total":2,"list":[{"id":1,"objectId":400,"inputValue":1,"objectId2":401,"outputValue":1,"crtTime":1542297600000},{"id":2,"objectId":400,"inputValue":0,"objectId2":401,"outputValue":0,"crtTime":1542297600000}],"pageNum":1,"pageSize":5,"size":2,"startRow":1,"endRow":2,"pages":1,"prePage":0,"nextPage":0,"isFirstPage":true,"isLastPage":true,"hasPreviousPage":false,"hasNextPage":false,"navigatePages":8,"navigatepageNums":[1],"navigateFirstPage":1,"navigateLastPage":1,"lastPage":1,"firstPage":1}}
	 * @author shaosen
	 * @Date 2018年11月16日
	 */
	@RequestMapping(value="measobj-map/datagrid",method=RequestMethod.POST)
	public JSONObject dataGrid(@RequestBody MeasObjMapVo vo) {
		PageInfo<MeasObjMap> pageInfo = measObjMapService.dataGrid(vo);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, pageInfo);
	}
	
}
