package com.bandweaver.tunnel.common.biz.itf;

import java.util.List;

import com.bandweaver.tunnel.common.biz.dto.SectionDto;
import com.bandweaver.tunnel.common.biz.dto.TunnelDto;
import com.bandweaver.tunnel.common.biz.dto.TunnelSimpleDto;
import com.bandweaver.tunnel.common.biz.pojo.Tunnel;
import com.bandweaver.tunnel.common.biz.vo.TunnelVo;
import com.github.pagehelper.PageInfo;

public interface TunnelService {
	
	
    /**
     * @Description: 获取所有管廊列表（id,name）
     * @param @return   
     * @return List<TunnelSimpleDto>  
     * @throws
     * @author shaosen
     * @date 2018年6月27日
     */
    List<TunnelSimpleDto> getList();

    /**
     * @Description: 获取所有管廊列表(详细)
     * @param @return   
     * @return List<TunnelDto>  
     * @throws
     * @author shaosen
     * @date 2018年6月27日
     */
    List<TunnelDto> getDtoList();
	/**
	 * @Description: 通过id获取name
	 * @param @param id
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月11日
	 */
	String getNameById(Integer id);

	/**
	 * @Description: 通过id获取dto
	 * @param @param id
	 * @param @return   
	 * @return TunnelDto  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月27日
	 */
	TunnelDto getDtoById(Integer id);


	/**查询管廊下的所有section 
	 * @param id 管廊id
	 * @return   
	 * @author shaosen
	 * @Date 2018年8月16日
	 */
	List<SectionDto> getSectionList(Integer id);

	void add(Tunnel tunnel);
	
	void update(Tunnel tunnel);
	
	void delete(Integer id);

	PageInfo<TunnelDto> dataGrid(TunnelVo vo);

	void deleteBatch(List<Integer> id_list);

	Tunnel getByName(String name);

	Tunnel getBySN(String sn);
}
