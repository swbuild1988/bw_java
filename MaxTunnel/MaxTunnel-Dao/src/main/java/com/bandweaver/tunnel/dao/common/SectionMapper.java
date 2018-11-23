package com.bandweaver.tunnel.dao.common;

import java.util.List;

import com.bandweaver.tunnel.common.biz.dto.SectionDto;
import com.bandweaver.tunnel.common.biz.dto.TunnelSimpleDto;
import com.bandweaver.tunnel.common.biz.pojo.Section;
import com.bandweaver.tunnel.common.biz.vo.SectionVo;

public interface SectionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Section record);

    int insertSelective(Section record);

    Section selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Section record);

    int updateByPrimaryKey(Section record);

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
	 * @Description: 条件查询
	 * @param @param vo
	 * @param @return   
	 * @return List<SectionDto>  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月21日
	 */
	List<SectionDto> getSectionsByCondition(SectionVo vo);

	/**
	 * @Description: 根据sectionid查询所在管廊信息
	 * @param @param id
	 * @param @return   
	 * @return TunnelDto  
	 * @throws
	 * @author shaosen
	 * @date 2018年7月9日
	 */
	TunnelSimpleDto getTunnelBySectionId(Integer id);

	List<Section> getSectionListByStoreAndArea(SectionVo vo);

	List<SectionDto> getAllSections();

	void deleteBatch(List<Integer> list);

	Section getByName(String name);

	SectionDto getDtoById(Integer id);
}