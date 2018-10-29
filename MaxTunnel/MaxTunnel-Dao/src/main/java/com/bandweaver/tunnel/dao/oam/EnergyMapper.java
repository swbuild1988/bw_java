package com.bandweaver.tunnel.dao.oam;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bandweaver.tunnel.common.biz.dto.oam.EnergyDto;
import com.bandweaver.tunnel.common.biz.dto.oam.EnergySimpleDto;
import com.bandweaver.tunnel.common.biz.pojo.oam.Energy;
import com.bandweaver.tunnel.common.biz.vo.oam.EnergyVo;

public interface EnergyMapper {
    Integer deleteByPrimaryKey(Integer id);

    Integer insert(Energy record);

    Integer insertSelective(Energy record);

    Energy selectByPrimaryKey(Integer id);

    Integer updateByPrimaryKeySelective(Energy record);

    Integer updateByPrimaryKey(Energy record);

	/**
	 * @Description: 通过id获取管廊总能耗
	 * @param @param id
	 * @param @return   
	 * @return Double  
	 * @throws
	 * @author shaosen
	 * @date 2018年5月31日
	 */
	Double getTotalValueByTunnelId(@Param("id")Integer id);


	/**
	 * @Description: 获取所有管廊历史总能耗
	 * @param @return   
	 * @return Double  
	 * @throws
	 * @author shaosen
	 * @date 2018年5月31日
	 */
	Double getAllTunnelValue();

	/**
	 * @Description:  根据时间获取管廊总能耗
	 * @param @param startTime
	 * @param @param endTime
	 * @param @return   
	 * @return Double  
	 * @throws
	 * @author shaosen
	 * @date 2018年5月31日
	 */
	Double getSumByTime(@Param("startTime")Date startTime,@Param("endTime") Date endTime);

	/**
	 * @Description: 根据起止日期条件查询各管廊能耗列表
	 * @param @param startTime
	 * @param @param endTime
	 * @param @return   
	 * @return List<EnergyDto>  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月1日
	 */
	List<EnergyDto> getTunnelEngergyConsumptionListByTime(@Param("startTime")Date startTime,@Param("endTime") Date endTime);

	/**
	 * @Description: 添加测试数据
	 * @param @param list   
	 * @return void  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月8日
	 */
	void addEnergyBatch(List<Energy> list);

	Double getEnergyByTunnelIdAndTime(@Param("id")Integer id, @Param("startTime")Date startTime, @Param("endTime")Date endTime);

	/**
	 * @Description: 根据管廊id获取指定日期内的能耗数据
	 * @param @param tunnelId
	 * @param @param startTime
	 * @param @param endTime
	 * @param @return   
	 * @return List<Energy>  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月11日
	 */
	List<EnergySimpleDto> getEnergiesByTunnelIdAndTimeCondition(@Param("tunnelId")Integer tunnelId, 
			@Param("startTime")Date startTime, 
			@Param("endTime")Date endTime);

	/**
	 * @Description: 条件查询
	 * @param @param vo
	 * @param @return   
	 * @return List<EnergySimpleDto>  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月13日
	 */
	List<EnergySimpleDto> getEnergiesByCondition(EnergyVo vo);



}