package com.bandweaver.tunnel.dao.oam;

import java.util.List;

import com.bandweaver.tunnel.common.biz.dto.oam.ReqHistoryDto;
import com.bandweaver.tunnel.common.biz.pojo.oam.ReqHistory;
import com.bandweaver.tunnel.common.biz.vo.oam.ReqHistoryVo;

public interface ReqHistoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ReqHistory record);

    int insertSelective(ReqHistory record);

    ReqHistory selectByPrimaryKey(Integer id);

//	ReqHistory selectByProcessDefinition(String processDefinitionId);

    int updateByPrimaryKeySelective(ReqHistory record);

    int updateByPrimaryKey(ReqHistory record);

	/**
	 * 批量添加
	 * @param  list   
	 * @return void  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月27日
	 */
	void addBatch(List<ReqHistory> list);

	/**
	 * 通过id获取dto
	 * @param  id
	 * @return ReqHistoryDto  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月27日
	 */
	ReqHistoryDto getDtoById(Integer id);

	/**
	 * 条件查询
	 * @param  vo
	 * @param    
	 * @return List<ReqHistoryDto>  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月27日
	 */
	List<ReqHistoryDto> getDtoListByCondition(ReqHistoryVo vo);

	/**
	 * 通过流程实例id查询
	 * @param  processInstanceId
	 * @param @return   
	 * @return ReqHistory  
	 * @throws
	 * @author shaosen
	 * @Date 2018年8月7日
	 */
	ReqHistory getReqHistoryByProcessInstanceId(String processInstanceId);
	
	ReqHistoryDto getReqHistoryDtoByProcessInstanceId(String processInstanceId);

}