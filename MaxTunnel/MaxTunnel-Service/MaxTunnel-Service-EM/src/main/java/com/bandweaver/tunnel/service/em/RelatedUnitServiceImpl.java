package com.bandweaver.tunnel.service.em;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bandweaver.tunnel.common.biz.dto.em.RelatedUnitDto;
import com.bandweaver.tunnel.common.biz.itf.em.RelatedUnitService;
import com.bandweaver.tunnel.common.biz.pojo.em.RelatedUnit;
import com.bandweaver.tunnel.common.biz.vo.em.RelatedUnitVo;
import com.bandweaver.tunnel.dao.em.RelatedUnitMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
@Service
public class RelatedUnitServiceImpl implements RelatedUnitService {

	@Autowired
	private RelatedUnitMapper relatedUnitMapper;

	@Override
	public void add(RelatedUnit pojo) {
		pojo.setCrtTime(new Date());
		doBusi(pojo);
		relatedUnitMapper.insert(pojo);
	}

	@Override
	public RelatedUnitDto getDtoById(Integer id) {
		return relatedUnitMapper.getDtoById(id);
	}

	@Override
	public PageInfo<RelatedUnitDto> dataGrid(RelatedUnitVo vo) {
		if(vo.getSectionIds()!=null && vo.getSectionIds().trim()!="") {
			vo.setSectionIds("[" + vo.getSectionIds() + "]");
		}
		PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
		List<RelatedUnitDto> list = relatedUnitMapper.getDtoListByCondition(vo);
		return new PageInfo<>(list);
	}

	@Override
	public List<RelatedUnitDto> getDtoListByCondition(RelatedUnitVo vo) {
		List<RelatedUnitDto> list = relatedUnitMapper.getDtoListByCondition(vo);
		return list == null ? Collections.emptyList() : list ;
	}

	@Override
	public void delete(Integer id) {
		relatedUnitMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void update(RelatedUnit pojo) {
		doBusi(pojo);
		relatedUnitMapper.updateByPrimaryKeySelective(pojo);
	}

	/**
	 * 
	 * 把1,2,3,4转化成[1],[2],[3],[4]格式
	 * @param @param pojo   
	 * @return void  
	 * @throws
	 * @author shaosen
	 * @Date 2018年8月7日
	 */
	private void doBusi(RelatedUnit pojo) {
		String str = pojo.getSectionIds();
		String[] arr = str.split(",");
		
		String id = "";
		StringBuffer buffer = new StringBuffer();
		for (int i = 0 ; i < arr.length ; i ++) {
			if(i == arr.length-1) {
				id = "[" + arr[i] + "]";
			}else {
				id = "[" + arr[i] + "],";
			}
			buffer.append(id);
		}
		pojo.setSectionIds(buffer.toString());
	}

	@Override
	public List<RelatedUnitDto> getDtoListBySectionId(Integer id) {
		RelatedUnitVo vo = new RelatedUnitVo();
		//如果id拼接方式为1,2,3,15 进行模糊查询的时候，如果如果传入参数id=1，那么15也会查询出来，为了避免这种情况发生
		//把每个id用[]包装，即[1],[2],[3],[15],参数用[ 和 ]包装，即查询出唯一结果
		vo.setSectionIds("[" + String.valueOf(id.intValue()) + "]");
		List<RelatedUnitDto> list = getDtoListByCondition(vo);
		
		//对sectionIds做处理
		if(list != null && list.size()>0) {
			for (RelatedUnitDto relatedUnitDto : list) {
				String ids = relatedUnitDto.getSectionIds().replaceAll("\\[", "");
				ids = ids.replaceAll("\\]", "");
				relatedUnitDto.setSectionIds(ids);
			}
		}
		return list;
	}

	@Override
	public List<RelatedUnitDto> getDtoListBySecionIdAndUnitType(Integer id, Integer unitType) {
		RelatedUnitVo vo = new RelatedUnitVo();
		//如果id拼接方式为1,2,3,15 进行模糊查询的时候，如果如果传入参数id=1，那么15也会查询出来，为了避免这种情况发生
		//把每个id用[]包装，即[1],[2],[3],[15],参数用[ 和 ]包装，即查询出唯一结果
		vo.setSectionIds("[" + String.valueOf(id.intValue()) + "]");
		vo.setUnitType(unitType);
		List<RelatedUnitDto> list = getDtoListByCondition(vo);
		
		//对sectionIds做处理
		if(list != null && list.size()>0) {
			for (RelatedUnitDto relatedUnitDto : list) {
				String ids = relatedUnitDto.getSectionIds().replaceAll("\\[", "");
				ids = ids.replaceAll("\\]", "");
				relatedUnitDto.setSectionIds(ids);
			}
		}
		return list;
	}
}
