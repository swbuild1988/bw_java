package com.bandweaver.tunnel.dao.common;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bandweaver.tunnel.common.biz.dto.AreaDto;
import com.bandweaver.tunnel.common.biz.pojo.Area;
import com.bandweaver.tunnel.common.biz.vo.AreaVo;

public interface AreaMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Area record);

    int insertSelective(Area record);

    Area selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Area record);

    int updateByPrimaryKey(Area record);

	/**
	 * @Description: 批量添加
	 * @param @param list   
	 * @return void  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月11日
	 */
	void addAreaBatch(List<Area> list);

	/**
	 * @Description: 获取所有区域列表
	 * @param @return   
	 * @return List<AreaDto>  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月11日
	 */
	List<AreaDto> getList();



	/**
	 * @Description: 条件查询列表
	 * @param @param areaVo
	 * @param @return   
	 * @return List<AreaDto>  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月11日
	 */
	List<AreaDto> getAreasByCondition(AreaVo areaVo);

	/**
	 * @Description: 通过id查询dto
	 * @param @param id
	 * @param @return   
	 * @return AreaDto  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月14日
	 */
	AreaDto getAreasById(@Param("id")Integer id);

	void deleteBatch(List<Integer> list);

	Area getByName(String name);

	Area getAreaByTunnelAndSN(Integer tunnelId, String sn);

	List<Area> getAreasByTunnelId(Integer tunnelId);
}