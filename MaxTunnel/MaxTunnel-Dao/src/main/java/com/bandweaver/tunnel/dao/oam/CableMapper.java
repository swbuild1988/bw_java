package com.bandweaver.tunnel.dao.oam;

import java.util.List;

import com.bandweaver.tunnel.common.biz.dto.oam.CableDto;
import com.bandweaver.tunnel.common.biz.pojo.oam.Cable;

public interface CableMapper {
    int deleteByPrimaryKey(String id);

    int insert(Cable record);

    int insertSelective(Cable record);

    Cable selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Cable record);

    int updateByPrimaryKey(Cable record);

	/**
	 * @Description: 通过sectionId获取管线列表
	 * @param @param id
	 * @param @return   
	 * @return List<CableDto>  
	 * @throws
	 * @author shaosen
	 * @date 2018年7月22日
	 */
	List<CableDto> getCableDtoListBySectionId(Integer id);

	void addBatch(List<Cable> list);

	/**
	 * 通过合同编号获取管线详情
	 * @param @param id
	 * @param @return   
	 * @return CableDto  
	 * @throws
	 * @author shaosen
	 * @Date 2018年7月29日
	 */
	CableDto getCableDetByContractId(String id);

	void deleteByContractId(String id);
}