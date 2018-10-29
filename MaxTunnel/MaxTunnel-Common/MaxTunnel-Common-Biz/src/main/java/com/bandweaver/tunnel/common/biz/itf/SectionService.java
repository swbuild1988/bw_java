package com.bandweaver.tunnel.common.biz.itf;

import java.util.List;

import com.bandweaver.tunnel.common.biz.dto.SectionDto;
import com.bandweaver.tunnel.common.biz.dto.TunnelSimpleDto;
import com.bandweaver.tunnel.common.biz.pojo.Section;
import com.bandweaver.tunnel.common.biz.vo.SectionVo;
import com.github.pagehelper.PageInfo;

public interface SectionService {

	/**
	 * @Description: 批量添加
	 * @param @param list   
	 * @return void  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月21日
	 */
	void addBatch(List<Section> list);

	/**
	 * @Description: 条件查询dtoList
	 * @param @param vo
	 * @param @return   
	 * @return List<SectionDto>  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月21日
	 */
	List<SectionDto> getSectionsByCondition(SectionVo vo);

	List<SectionDto> getAllSections();

	/**
	 * @Description: 通过id查询dto
	 * @param @param id
	 * @param @return   
	 * @return SectionDto  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月21日
	 */
	SectionDto getSectionById(Integer id);

	/**
	 * @Description: 通过storeId查询dto列表
	 * @param @param id
	 * @param @return   
	 * @return List<SectionDto>  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月21日
	 */
	List<SectionDto> getSectionsByStoreId(Integer id);

	/**
	 * @Description: 通过areaId查询dto列表
	 * @param @param id
	 * @param @return   
	 * @return List<SectionDto>  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月21日
	 */
	List<SectionDto> getSectionsByAreaId(Integer id);

	/**
	 * @Description: 根据sectionId查询所在的管廊信息
	 * @param @param id
	 * @param @return   
	 * @return TunnelDto  
	 * @throws
	 * @author shaosen
	 * @date 2018年7月9日
	 */
	TunnelSimpleDto getTunnelBySectionId(Integer id);

	void add(Section section);

	PageInfo<SectionDto> dataGrid(SectionVo vo);

	List<Section> getSectionListByStoreAndArea(SectionVo vo);

	Section getSectionByStoreAndArea(Integer storeId, Integer areaId);

	void update(Section record);

	/**
	 * 根据 GPS 获得所属的管廊
	 * @param longitude
	 * @param latitude
	 * @param height
	 * @return
	 */
	SectionDto getSectionByGPS(double longitude, double latitude, double height);

	void delete(Integer id);

	void deleteBatch(List<Integer> list);

	Section getByName(String name);

}
