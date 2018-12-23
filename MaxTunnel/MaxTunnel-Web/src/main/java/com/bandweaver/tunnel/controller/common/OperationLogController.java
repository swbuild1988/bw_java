package com.bandweaver.tunnel.controller.common;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.itf.OperationLogService;
import com.bandweaver.tunnel.common.biz.pojo.OperationLog;
import com.bandweaver.tunnel.common.biz.vo.OperationLogVo;
import com.bandweaver.tunnel.common.platform.constant.StatusCodeEnum;
import com.bandweaver.tunnel.common.platform.util.CommonUtil;
import com.github.pagehelper.PageInfo;

/**操作日志管理
 * @author shaosen
 * @date 2018年7月2日
 */
@Controller
@ResponseBody
public class OperationLogController extends BaseController<OperationLog> {

	@Autowired
	private OperationLogService operationLogService;
	
	
	/**分页查询
	 * @param  moduleType 模块类型
	 * @param	reqUser	    请求人姓名,支持模糊查询
	 * @param	result 	    操作结果 ：成功；失败
	 * @param	beginTime
	 * @param	endTime
	 * @param	pageNum
	 * @param	pageSize				
	 * @param							
	 * @return JSONObject  
	 * @author shaosen
	 * @date 2018年7月2日
	 */
	@RequestMapping(value="operationlogs/datagrid",method=RequestMethod.POST)
	public JSONObject dataGrid(@RequestBody OperationLogVo vo) {
		PageInfo<OperationLog>  pageInfo = operationLogService.dataGrid(vo);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, pageInfo);
	}
	
	/**删除
	 * @param  id
	 * @return JSONObject  
	 * @author shaosen
	 * @date 2018年7月3日
	 */
	@RequestMapping(value="operationlogs/{id}",method=RequestMethod.DELETE)
	public JSONObject delete(@PathVariable String id ) {
		operationLogService.delete(id);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}

	/**
	 * 批量删除
	 */
	@Override
	@RequestMapping(value="operationlogs/batch/{ids}",method=RequestMethod.DELETE)
	public JSONObject deleteBatch(@PathVariable String ids) throws Exception {
		String[] arr = ids.split(",");
		for (String id : arr) {
			operationLogService.delete(id);
		}
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}
	
	@Override
	public JSONObject add(OperationLog pojo) throws Exception {
		return null;
	}

	@Override
	public JSONObject delete(Integer id) throws Exception {
		return null;
	}

	@Override
	public JSONObject update(OperationLog pojo) throws Exception {
		return null;
	}

	@Override
	public JSONObject getById(Integer id) throws Exception {
		return null;
	}
}
