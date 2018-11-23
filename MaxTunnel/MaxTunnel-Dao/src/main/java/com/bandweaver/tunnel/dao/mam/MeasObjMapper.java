package com.bandweaver.tunnel.dao.mam;

import com.bandweaver.tunnel.common.biz.dto.mam.MeasObjDto;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObj;
import com.bandweaver.tunnel.common.biz.vo.mam.MeasObjVo;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface MeasObjMapper {
    Integer deleteByPrimaryKey(Integer id);

    Integer insert(MeasObj record);

    Integer insertSelective(MeasObj record);

    MeasObj selectByPrimaryKey(Integer id);

    Integer updateByPrimaryKeySelective(MeasObj record);

    Integer updateByPrimaryKey(MeasObj record);

	/**
	 * @Description: 批量添加
	 * @param @param list   
	 * @return void  
	 * @throws
	 * @author shaosen
	 * @date 2018年5月30日
	 */
	void addObjBatch(List<MeasObj> list);

	/**
	 * @Description: 条件查询
	 * @param @param obj
	 * @param @return   
	 * @return List<MeasObjDto>  
	 * @throws
	 * @author shaosen
	 * @date 2018年5月30日
	 */
	List<MeasObjDto> getMeasObjByCondition(MeasObjVo vo);

	/**
	 * @Description: 根据经纬度高度查询
	 * @param @param longitude
	 * @param @param latitude
	 * @param @param height
	 * @param @return   
	 * @return List<MeasObj>  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月5日
	 */
	List<MeasObj> getMeasObjByLongitudeAndLatitudeAndHeight(@Param("longitude")String longitude,@Param("latitude") String latitude, @Param("height")String height);

	/**
	 * @Description: 获取所有obj
	 * @param @return   
	 * @return List<MeasObj>  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月7日
	 */
	List<MeasObj> getAllMeasObjList();

	/**
	 * @Description: 根据ids获取list
	 * @param @param ids
	 * @param @return   
	 * @return List<MeasObj>  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月8日
	 */
	List<MeasObj> getListByIds(List<Integer> ids);

	List<Integer> getIdsByTunnelId(Integer id);

	List<MeasObj> getListBySectionIDAndObjectTypeID(@Param("sectionId")Integer sectionId, @Param("objectTypeId")Integer objectTypeId);
}
