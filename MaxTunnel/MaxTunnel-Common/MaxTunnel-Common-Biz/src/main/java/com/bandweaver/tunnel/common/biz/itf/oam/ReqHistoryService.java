package com.bandweaver.tunnel.common.biz.itf.oam;

import java.io.UnsupportedEncodingException;
import java.util.List;

import com.bandweaver.tunnel.common.biz.dto.oam.ReqHistoryDto;
import com.bandweaver.tunnel.common.biz.pojo.oam.ReqHistory;
import com.bandweaver.tunnel.common.biz.vo.oam.ReqHistoryVo;
import com.github.pagehelper.PageInfo;

public interface ReqHistoryService {

	/**
	 *  批量添加
	 * @param  list
	 * @return void
	 * @throws
	 * @author shaosen
	 * @date 2018年6月27日
	 */
	void addBatch(List<ReqHistory> list);

	/**
	 * 通过id查询
	 * @param  id
	 * @param 
	 * @return ReqHistoryDto
	 * @throws 
	 * @author shaosen
	 * @date 2018年6月27日
	 */
	ReqHistoryDto getDtoById(Integer id);

	/**
	 * 条件查询
	 * @param  vo
	 * @return List<ReqHistoryDto>
	 * @throws 
	 * @author shaosen
	 * @date 2018年6月27日
	 */
	List<ReqHistoryDto> getDtoListByCondition(ReqHistoryVo vo);

	/**
	 *  添加
	 * @param  history
	 * @return void
	 * @throws 
	 * @author shaosen
	 * @date 2018年6月27日
	 */
	void add(ReqHistory history);

	/**
	 * 删除
	 * @param  id
	 * @return void
	 * @author shaosen
	 * @date 2018年6月27日
	 */
	void deleteById(Integer id);

	/**
	 * 分页查询
	 * @param  vo
	 * @return PageInfo<ReqHistoryDto>
	 * @author shaosen
	 * @date 2018年6月27日
	 */
	PageInfo<ReqHistoryDto> dataGrid(ReqHistoryVo vo);

	/**
	 * 通过id更新
	 * @param  pojo 
	 * @author shaosen
	 * @date 2018年7月6日
	 */
	void updateById(ReqHistory pojo);

	/**
	 *  审批
	 * @param processInstanceId
	 * @param value
	 * @param remark
	 * @return void
	 * @throws UnsupportedEncodingException                            
	 * @author shaosen
	 * @date 2018年7月16日
	 */
	void auditRequest(String processInstanceId, int value, String remark) throws UnsupportedEncodingException;

	/**
	 * 确认出廊
	 * 
	 * @param id
	 * @param processInstanceId
	 * @param exitTime
	 * @return void
	 * @author shaosen
	 * @date 2018年7月16日
	 */
	void confirm(ReqHistory vo);

	/**
	 * 通过流程实例id查询
	 * 
	 * @param processInstanceId
	 * @param
	 * @return ReqHistory
	 * @author shaosen
	 * @Date 2018年8月7日
	 */
	ReqHistory getReqHistoryByProcessInstanceId(String processInstanceId);

	ReqHistoryDto getReqHistoryDtoByProcessInstanceId(String processInstanceId);

}
