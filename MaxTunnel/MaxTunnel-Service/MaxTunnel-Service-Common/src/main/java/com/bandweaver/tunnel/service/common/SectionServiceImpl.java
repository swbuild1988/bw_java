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

	@Override
	public SectionDto getSectionDtoByGPS(double longitude, double latitude, double height) {
		// 获取舱的高度范围
		double min = -4.1, mid = 1.1, max = 5.1;
		
		// 确定所在舱的高度
		if(height > mid && height <= max )
			height = 3;
		else if(height >= min && height <= mid) 
			height = -0.75;
		else return null;
		//获取所有的section列表
		List<SectionDto> scList = getAllSections();
		LogUtil.info("sections:" + scList.size());
		
		SectionDto resultDto = null;
		for (SectionDto sectionDto : scList) {
			//获取起始经纬度高度
			String startPoint = sectionDto.getStartPoint();
			String endPoint = sectionDto.getEndPoint();
			if(startPoint == null || startPoint.trim().length() == 0) { continue; }
			if(endPoint == null || endPoint.trim().length() == 0) { continue; }
			// 经纬高数组
			List<Double> startList = getListFromString(startPoint);
			List<Double> endList = getListFromString(endPoint);
			// 筛选高度，确定所在舱范围
			if(!startList.get(2).equals(height)) continue;
			
			// 获取所在舱的区域范围
			List<java.awt.geom.Point2D.Double> pts = new ArrayList<java.awt.geom.Point2D.Double>();
			pts.add(new java.awt.geom.Point2D.Double(startList.get(0) + 1.11E-5, startList.get(1) + 5.0E-5));
			pts.add(new java.awt.geom.Point2D.Double(startList.get(0) - 1.11E-5, startList.get(1) - 5.0E-5));
			pts.add(new java.awt.geom.Point2D.Double(endList.get(0) + 1.11E-5, endList.get(1) + 5.0E-5));
			pts.add(new java.awt.geom.Point2D.Double(endList.get(0) - 1.11E-5, endList.get(1) - 5.0E-5));
			
			if(IsPtInPoly(longitude, latitude, pts)) {
				LogUtil.info("section: " + sectionDto);
				resultDto = sectionDto;
			}
		}
		return resultDto;
	}
		
	public List<Section> getSectionListByParentId(Integer sectionId) {
		return sectionMapper.getSectionListByParentId(sectionId); 

	}

	/**
	 * 判断点是否在多边形内
	 * @param point 检测点
	 * @param pts   多边形的顶点
	 * @return      点在多边形内返回true,否则返回false
	 */
	public static boolean IsPtInPoly(double lon,double lat, List<java.awt.geom.Point2D.Double> pts){
	    
	    int N = pts.size();
	    boolean boundOrVertex = true; //如果点位于多边形的顶点或边上，也算做点在多边形内，直接返回true
	    int intersectCount = 0;//cross points count of x 
	    double precision = 2e-10; //浮点类型计算时候与0比较时候的容差
	    java.awt.geom.Point2D.Double p1, p2;//neighbour bound vertices
	    java.awt.geom.Point2D.Double p = new java.awt.geom.Point2D.Double(lon, lat);; //当前点
	    
	    p1 = pts.get(0);//left vertex        
	    for(int i = 1; i <= N; ++i){//check all rays            
	        if(p.equals(p1)){
	            return boundOrVertex;//p is an vertex
	        }
	        
	        p2 = pts.get(i % N);//right vertex            
	        if(p.x < Math.min(p1.x, p2.x) || p.x > Math.max(p1.x, p2.x)){//ray is outside of our interests                
	            p1 = p2; 
	            continue;//next ray left point
	        }
	        
	        if(p.x > Math.min(p1.x, p2.x) && p.x < Math.max(p1.x, p2.x)){//ray is crossing over by the algorithm (common part of)
	            if(p.y <= Math.max(p1.y, p2.y)){//x is before of ray                    
	                if(p1.x == p2.x && p.y >= Math.min(p1.y, p2.y)){//overlies on a horizontal ray
	                    return boundOrVertex;
	                }
	                
	                if(p1.y == p2.y){//ray is vertical                        
	                    if(p1.y == p.y){//overlies on a vertical ray
	                        return boundOrVertex;
	                    }else{//before ray
	                        ++intersectCount;
	                    } 
	                }else{//cross point on the left side                        
	                    double xinters = (p.x - p1.x) * (p2.y - p1.y) / (p2.x - p1.x) + p1.y;//cross point of y                        
	                    if(Math.abs(p.y - xinters) < precision){//overlies on a ray
	                        return boundOrVertex;
	                    }
	                    
	                    if(p.y < xinters){//before ray
	                        ++intersectCount;
	                    } 
	                }
	            }
	        }else{//special case when ray is crossing through the vertex                
	            if(p.x == p2.x && p.y <= p2.y){//p crossing over p2                    
	            	java.awt.geom.Point2D.Double p3 = pts.get((i+1) % N); //next vertex                    
	                if(p.x >= Math.min(p1.x, p3.x) && p.x <= Math.max(p1.x, p3.x)){//p.x lies between p1.x & p3.x
	                    ++intersectCount;
	                }else{
	                    intersectCount += 2;
	                }
	            }
	        }            
	        p1 = p2;//next ray left point
	    }
	    
	    if(intersectCount % 2 == 0){//偶数在多边形外
	        return false;
	    } else { //奇数在多边形内
	        return true;
	    }
	    
	}
}
