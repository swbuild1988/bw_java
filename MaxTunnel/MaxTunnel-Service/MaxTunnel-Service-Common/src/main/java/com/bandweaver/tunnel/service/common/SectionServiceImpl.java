package com.bandweaver.tunnel.service.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.DataTypeUtil;
import com.bandweaver.tunnel.common.platform.util.GPSUtil;
import com.bandweaver.tunnel.common.platform.util.MathUtil;
import javafx.geometry.Point2D;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bandweaver.tunnel.common.biz.dto.SectionDto;
import com.bandweaver.tunnel.common.biz.dto.TunnelSimpleDto;
import com.bandweaver.tunnel.common.biz.itf.SectionService;
import com.bandweaver.tunnel.common.biz.pojo.Section;
import com.bandweaver.tunnel.common.biz.vo.SectionVo;
import com.bandweaver.tunnel.dao.common.SectionMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
@Service
public class SectionServiceImpl implements SectionService {

	@Autowired
	private SectionMapper sectionMapper;

	@Override
	public void addBatch(List<Section> list) {
		sectionMapper.addBatch(list);
	}

	@Override
	public List<SectionDto> getSectionsByCondition(SectionVo vo) {
		return sectionMapper.getSectionsByCondition(vo);
	}

	@Override
	public List<SectionDto> getAllSections() {
		return sectionMapper.getAllSections();
	}

	@Override
	public SectionDto getSectionById(Integer id) {
		return sectionMapper.getDtoById(id);
	}

	@Override
	public List<SectionDto> getSectionsByStoreId(Integer id) {
		SectionVo vo = new SectionVo();
		vo.setStoreId(id);
		return sectionMapper.getSectionsByCondition(vo);
	}

	@Override
	public List<SectionDto> getSectionsByAreaId(Integer id) {
		SectionVo vo = new SectionVo();
		vo.setAreaId(id);
		return sectionMapper.getSectionsByCondition(vo);
	}

	@Override
	public TunnelSimpleDto getTunnelBySectionId(Integer id) {
		return sectionMapper.getTunnelBySectionId(id);
	}

	@Override
	public void add(Section section) {
		section.setCrtTime(new Date());
		sectionMapper.insert(section);
	}

	@Override
	public PageInfo<SectionDto> dataGrid(SectionVo vo) {
		PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
		List<SectionDto> list = getSectionsByCondition(vo);
		return new PageInfo<>(list);
	}

	@Override
	public List<Section> getSectionListByStoreAndArea(SectionVo vo) {
		return sectionMapper.getSectionListByStoreAndArea(vo);
	}

	@Override
	public Section getSectionByStoreAndArea(Integer storeId, Integer areaId) {
		SectionVo vo = new SectionVo();
		vo.setStoreId(storeId);
		vo.setAreaId(areaId);
		List<Section> list = getSectionListByStoreAndArea(vo);
		return list.size() > 0 ? list.get(0) : null;
	}

	@Override
	public void update(Section record) {
		sectionMapper.updateByPrimaryKeySelective(record);
	}

	/**
	 * 根据 GPS 获得所属的管廊
	 *
	 * @param longitude
	 * @param latitude
	 * @param height
	 * @return
	 */
	@Override
	public SectionDto getSectionByGPS(double longitude, double latitude, double height) {
		Point2D pOut = GPSUtil.MillierConvertion(longitude, latitude);

		//获取所有的section列表
		List<SectionDto> scList = getAllSections();
		LogUtil.info(" get section list size : " + scList.size());

		double dd = 0.00;
		boolean flag = true;
		SectionDto resultDto = null;
		Point2D p4 = null;
		for (SectionDto sectionDto : scList) {

			//获取起始经纬度高度
			String startPoint = sectionDto.getStartPoint();
			String endPoint = sectionDto.getEndPoint();
			if(startPoint == null || startPoint.trim().length() == 0) { continue; }
			if(endPoint == null || endPoint.trim().length() == 0) { continue; }

			List<Double> startList = getListFromString(startPoint);
			List<Double> endList = getListFromString(endPoint);

			double startLon = startList.get(0);
			double startLat = startList.get(1);
			double startHei = startList.get(2);
			//将经纬度转化成平面坐标
			Point2D p1 = GPSUtil.MillierConvertion(startLon, startLat);

			LogUtil.info("section " + sectionDto.getId() + " Start Point: " + p1.toString());

			double endLon = endList.get(0);
			double endLat = endList.get(1);
			//将经纬度转化成平面坐标
			Point2D p2 = GPSUtil.MillierConvertion(endLon, endLat);

            LogUtil.info("section " + sectionDto.getId() + " End Point: " + p2.toString());

			//获取到投影坐标
			p4 = MathUtil.getProjectivePoint(p1, p2, pOut);

			double l = p1.distance(p2);//length from p1 to p2
            LogUtil.info("the distance of start and end point: " + l);
			double l1 = p1.distance(p4);//length from p1 to p4
			double l2 = p2.distance(p4);//length from p2 to p4


			double sub1 = l1 + l2 - l;
			double sub2 = Math.abs(l1 - l2) - l ;
			if(sub1 == 0 || Math.ceil(sub1) == 0 || Math.floor(sub1) == 0 ) {
				LogUtil.info(" point maybe in the section : " + sectionDto.getId() );
				double distance = pOut.distance(p4);
				LogUtil.info(" distance : " + distance );

				//比较哪个距离更近
				if(flag) {dd = distance; flag = false;}
				dd = Math.min(distance,dd);
				if(Math.min(distance,dd) == distance && distance <= 2) {
					//判断高度
					if(Math.abs(height - startHei) <= 5) {
						resultDto = sectionDto;
						LogUtil.info(" get section : " + resultDto.getId());
					}
				}

			}else if(sub2 == 0 || Math.ceil(sub2) == 0 || Math.floor(sub2) == 0 ){
				continue;
			}else {
				LogUtil.info(" 不该出现的情况");
				continue;
			}
		}

		return resultDto;
	}


	private List<Double> getListFromString(String str) {
		List<String> strList = Arrays.asList(str.split(","));
		List<Double> dList = new ArrayList<>();
		for (String s : strList) {
			Double d = DataTypeUtil.toDouble(s);
			dList.add(d);
		}
		return dList;
	}

	@Override
	public void delete(Integer id) {
		sectionMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void deleteBatch(List<Integer> list) {
		sectionMapper.deleteBatch(list);
		
	}

	@Override
	public Section getByName(String name) {
		return sectionMapper.getByName(name);
	}

}
