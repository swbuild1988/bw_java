package com.bandweaver.tunnel.dao.omm;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bandweaver.tunnel.common.biz.dto.omm.CheckingPointDto;
import com.bandweaver.tunnel.common.biz.pojo.omm.CheckingPoint;
import com.bandweaver.tunnel.common.biz.vo.omm.CheckingPointVo;

public interface CheckingPointMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CheckingPoint record);

    int insertSelective(CheckingPoint record);

    CheckingPoint selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CheckingPoint record);

    int updateByPrimaryKey(CheckingPoint record);

	/**
	 * @Description: 条件查询
	 * @param @param dto
	 * @param @return   
	 * @return List<CheckingPointDto>  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月4日
	 */
	List<CheckingPointDto> getCheckingPointListByConditon(CheckingPointVo vo);

	/**
	 * @Description: 根据id查询巡检点
	 * @param @param id
	 * @param @return   
	 * @return CheckingPointDto  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月6日
	 */
	CheckingPointDto getCheckingPointById(@Param("id")Integer id);

	/**
	 * @Description: 批量添加
	 * @param @param list   
	 * @return void  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月12日
	 */
	void addCheckingPointBatch(List<CheckingPoint> list);
}