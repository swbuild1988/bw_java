package com.bandweaver.tunnel.common.biz.itf.oam;

import java.util.List;

import com.bandweaver.tunnel.common.biz.dto.oam.CableDto;
import com.bandweaver.tunnel.common.biz.pojo.oam.Cable;

public interface CableService {

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

	/**
	 * @Description: 批量添加
	 * @param @param list   
	 * @return void  
	 * @throws
	 * @author shaosen
	 * @date 2018年7月22日
	 */
	void addBatch(List<Cable> list);

}
