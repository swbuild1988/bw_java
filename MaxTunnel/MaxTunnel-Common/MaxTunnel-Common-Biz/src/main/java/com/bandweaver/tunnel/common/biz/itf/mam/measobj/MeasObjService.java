package com.bandweaver.tunnel.common.biz.itf.mam.measobj;

import com.bandweaver.tunnel.common.biz.dto.mam.MeasObjDto;
import com.bandweaver.tunnel.common.biz.pojo.mam.MeasValueAI;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObj;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObjAI;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObjDI;
import com.bandweaver.tunnel.common.biz.vo.mam.MeasObjVo;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface MeasObjService {
    int add(MeasObj measObj);

    MeasObj get(Integer id);

    List<MeasObj> getList();

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
	 * 更新
	 * @param obj
	 */
	void updateObj(MeasObj obj);

	/**
	 * @Description: 条件查询
	 * @param @param obj
	 * @param @return   
	 * @return List<MeasObj>  
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
	List<MeasObj> getMeasObjByLongitudeAndLatitudeAndHeight(String longitude, String latitude, String height);

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

	void addTestData(List<MeasObj> objList, List<MeasObjAI> aiList,List<MeasObjDI> diList,List<MeasValueAI> aiValList);

	/**
	 * @Description: 分页查询
	 * @param @param vo
	 * @param @return   
	 * @return PageInfo<MeasObjDto>  
	 * @throws
	 * @author shaosen
	 * @date 2018年7月18日
	 */
	PageInfo<MeasObjDto> dataGrid(MeasObjVo vo);

	List<Integer> getIdsByTunnelId(Integer id);

	List<MeasObjDto> getMeasObjBySectionId(Integer id);

	/**根据measObjID和dataType类型查询cv值 
	 * @param objId
	 * @param datatypeId   
	 * @author shaosen
	 * @return 
	 * @Date 2018年8月28日
	 */
	double getMeasObjCVByIdAndDataType(Integer objId, Integer datatypeId);

	List<MeasObj> getMeasObjByTargetVal(String targetValue);

	List<MeasObj> getMeasObjsByTargetValAndVars(String targetValue,Integer sectionId);
	

	
	
	
}
