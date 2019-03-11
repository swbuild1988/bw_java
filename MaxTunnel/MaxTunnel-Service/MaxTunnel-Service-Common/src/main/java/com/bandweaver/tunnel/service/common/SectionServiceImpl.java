package com.bandweaver.tunnel.service.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.bandweaver.tunnel.common.biz.dto.*;
import com.bandweaver.tunnel.common.biz.dto.mam.Point3D;
import com.bandweaver.tunnel.common.biz.itf.AreaService;
import com.bandweaver.tunnel.common.biz.itf.StoreService;
import com.bandweaver.tunnel.common.biz.pojo.Area;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.DataTypeUtil;
import com.bandweaver.tunnel.common.platform.util.GPSUtil;
import com.bandweaver.tunnel.common.platform.util.MathUtil;
import com.bandweaver.tunnel.common.platform.util.PointUtil;
import com.bandweaver.tunnel.dao.common.TunnelMapper;
import javafx.geometry.Point2D;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    @Autowired
    private TunnelMapper tunnelMapper;

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
    public List<SectionDto> getSectionsByTunnel(int tunnelId) {
        SectionVo vo = new SectionVo();
        vo.setTunnelId(tunnelId);
        return sectionMapper.getSectionsByCondition(vo);
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
        List<SectionDto> list = sectionMapper.getSectionsByCondition(vo);
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

        SectionDto resultDto = null;
        Point2D p4 = null;
        for (SectionDto sectionDto : scList) {

            // 获取起点和终点的经纬度高
            String startPoint = sectionDto.getStartPoint();
            String endPoint = sectionDto.getEndPoint();
            if (startPoint == null || startPoint.trim().length() == 0) continue;
            if (endPoint == null || endPoint.trim().length() == 0) continue;

            List<Double> startList = getListFromString(startPoint);
            double startLon = startList.get(0);
            double startLat = startList.get(1);
            double startHei = startList.get(2);

            // 判断高度
            double heiLen = sectionDto.getStore().getHeight();
            if (height > startHei + heiLen / 2 || height < startHei - heiLen / 2) continue;

            // 将经纬度转化成平面坐标
            Point2D p1 = GPSUtil.MillierConvertion(startLon, startLat);

            //LogUtil.info("section " + sectionDto.getId() + " Start Point: " + p1.toString());

            List<Double> endList = getListFromString(endPoint);
            double endLon = endList.get(0);
            double endLat = endList.get(1);
            // 将经纬度转化成平面坐标
            Point2D p2 = GPSUtil.MillierConvertion(endLon, endLat);

            // 获取到投影坐标
            p4 = MathUtil.getProjectivePoint(p1, p2, pOut);

            // 判断投影坐标是否在线段上
            double l = p1.distance(p2);//length from p1 to p2
            double l1 = p1.distance(p4);//length from p1 to p4
            double l2 = p2.distance(p4);//length from p2 to p4
            double sub1 = l1 + l2 - 1;
            if (sub1 == 0 || Math.ceil(sub1) == 0 || Math.floor(sub1) == 0) {
                // 距离
                double distance = pOut.distance(p4);
                double widLen = sectionDto.getStore().getWidth();
                double dis = widLen / 2 - distance;
                if (dis >= 0) {
                    resultDto = sectionDto;
                    LogUtil.info(" get section : " + resultDto.getName());
                }
            }
//			if(sub1 == 0 || Math.ceil(sub1) == 0 || Math.floor(sub1) == 0 )
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
        if (height > mid && height <= max)
            height = 3;
        else if (height >= min && height <= mid)
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
            if (startPoint == null || startPoint.trim().length() == 0) {
                continue;
            }
            if (endPoint == null || endPoint.trim().length() == 0) {
                continue;
            }
            // 经纬高数组
            List<Double> startList = getListFromString(startPoint);
            List<Double> endList = getListFromString(endPoint);
            // 筛选高度，确定所在舱范围
            if (!startList.get(2).equals(height)) continue;

            // 获取所在舱的区域范围
            List<java.awt.geom.Point2D.Double> pts = new ArrayList<java.awt.geom.Point2D.Double>();
            pts.add(new java.awt.geom.Point2D.Double(startList.get(0) + 1.11E-5, startList.get(1) + 5.0E-5));
            pts.add(new java.awt.geom.Point2D.Double(startList.get(0) - 1.11E-5, startList.get(1) - 5.0E-5));
            pts.add(new java.awt.geom.Point2D.Double(endList.get(0) + 1.11E-5, endList.get(1) + 5.0E-5));
            pts.add(new java.awt.geom.Point2D.Double(endList.get(0) - 1.11E-5, endList.get(1) - 5.0E-5));

            if (IsPtInPoly(longitude, latitude, pts)) {
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
     *
     * @param pts 多边形的顶点
     * @return 点在多边形内返回true, 否则返回false
     */
    public static boolean IsPtInPoly(double lon, double lat, List<java.awt.geom.Point2D.Double> pts) {

        int N = pts.size();
        boolean boundOrVertex = true; //如果点位于多边形的顶点或边上，也算做点在多边形内，直接返回true
        int intersectCount = 0;//cross points count of x
        double precision = 2e-10; //浮点类型计算时候与0比较时候的容差
        java.awt.geom.Point2D.Double p1, p2;//neighbour bound vertices
        java.awt.geom.Point2D.Double p = new java.awt.geom.Point2D.Double(lon, lat);
        ; //当前点

        p1 = pts.get(0);//left vertex
        for (int i = 1; i <= N; ++i) {//check all rays
            if (p.equals(p1)) {
                return boundOrVertex;//p is an vertex
            }

            p2 = pts.get(i % N);//right vertex
            if (p.x < Math.min(p1.x, p2.x) || p.x > Math.max(p1.x, p2.x)) {//ray is outside of our interests
                p1 = p2;
                continue;//next ray left point
            }

            if (p.x > Math.min(p1.x, p2.x) && p.x < Math.max(p1.x, p2.x)) {//ray is crossing over by the algorithm (common part of)
                if (p.y <= Math.max(p1.y, p2.y)) {//x is before of ray
                    if (p1.x == p2.x && p.y >= Math.min(p1.y, p2.y)) {//overlies on a horizontal ray
                        return boundOrVertex;
                    }

                    if (p1.y == p2.y) {//ray is vertical
                        if (p1.y == p.y) {//overlies on a vertical ray
                            return boundOrVertex;
                        } else {//before ray
                            ++intersectCount;
                        }
                    } else {//cross point on the left side
                        double xinters = (p.x - p1.x) * (p2.y - p1.y) / (p2.x - p1.x) + p1.y;//cross point of y
                        if (Math.abs(p.y - xinters) < precision) {//overlies on a ray
                            return boundOrVertex;
                        }

                        if (p.y < xinters) {//before ray
                            ++intersectCount;
                        }
                    }
                }
            } else {//special case when ray is crossing through the vertex
                if (p.x == p2.x && p.y <= p2.y) {//p crossing over p2
                    java.awt.geom.Point2D.Double p3 = pts.get((i + 1) % N); //next vertex
                    if (p.x >= Math.min(p1.x, p3.x) && p.x <= Math.max(p1.x, p3.x)) {//p.x lies between p1.x & p3.x
                        ++intersectCount;
                    } else {
                        intersectCount += 2;
                    }
                }
            }
            p1 = p2;//next ray left point
        }

        if (intersectCount % 2 == 0) {//偶数在多边形外
            return false;
        } else { //奇数在多边形内
            return true;
        }

    }

    @Override
    public List<Integer> getSectionIdsByStoreIds(List<Integer> list) {
        List<Integer> li = sectionMapper.getSectionIdsByStoreIds(list);
        return li == null ? Collections.emptyList() : li;
    }

    @Override
    public List<Section> getSectionsByStoreIds(List<Integer> list) {
        List<Section> li = sectionMapper.getSectionsByStoreIds(list);
        return li == null ? Collections.emptyList() : li;
    }

    /**
     * 计算管廊中所有管仓的sp和ep
     *
     * @param tunnelId
     * @return
     */
    @Override
    public boolean calSectionsStartPointAndEndPointByTunnel(int tunnelId) {

        TunnelDto tunnel = tunnelMapper.getDtoById(tunnelId);
        LogUtil.info("------------ 管廊1 ------------");
        LogUtil.info(tunnel);
        int tunnel_dir = tunnel.getDirection().intValue();
        List<SectionDto> sections = getSectionsByTunnel(tunnelId);

        LogUtil.info(sections);

        for (SectionDto section : sections) {
            LogUtil.info("------------ section ------------");
            LogUtil.info(section);
            Area area = section.getArea();
            LogUtil.info("------------ area ------------");
            LogUtil.info(area);
            StoreDto store = section.getStore();
            LogUtil.info("------------ store ------------");
            LogUtil.info(store);

            // 获得区域的起点和终点
            Point3D startP = PointUtil.get3DPoint(area.getStartPoint());
            Point3D endP = PointUtil.get3DPoint(area.getEndPoint());
            LogUtil.info("------------ 区的起点和终点 ------------");
            LogUtil.info(startP);
            LogUtil.info(endP);

            if (startP == null || endP == null) continue;
            if (store.getK() == null || store.getL() == null) continue;

            double k = store.getK().doubleValue();
            double l = store.getL().doubleValue();
            double s1 = section.getS1();
            double s2 = section.getS2();

            Point3D outStartP = new Point3D();
            Point3D outEndP = new Point3D();
            test(startP, endP, k, l, s1, s2, tunnel_dir, outStartP, outEndP);
            LogUtil.info("------------ 经过计算后，每个section的起点和终点 ------------");
            LogUtil.info(outStartP);
            LogUtil.info(outEndP);

            section.setStartPoint(PointUtil.get3DPointString(outStartP.getLng(),outStartP.getLat(),outStartP.getHeight()));
            section.setEndPoint(PointUtil.get3DPointString(outEndP.getLng(),outEndP.getLat(),outEndP.getHeight()));

            sectionMapper.updateByPrimaryKeySelective(section);

        }

        return false;
    }

    private void test(Point3D startP, Point3D endP, double k, double l, double s1, double s2, int tunnel_dir, Point3D outStartP, Point3D outEndP) {

        // 计算高度
        outStartP.setHeight(startP.getHeight().doubleValue() + k);
        outEndP.setHeight(endP.getHeight().doubleValue() + k);

        // 先根据米勒投影法求
        Point2D sp = GPSUtil.MillierConvertion(startP.getLng().doubleValue(), startP.getLat().doubleValue());
        Point2D ep = GPSUtil.MillierConvertion(endP.getLng().doubleValue(), endP.getLat().doubleValue());
        Point2D out_sp = new Point2D(0, 0);
        Point2D out_ep = new Point2D(0, 0);
        //根据l求出对应的管仓的起点和终点二维
        if (sp.getY() == ep.getY()) {
            out_sp = new Point2D(sp.getX(), sp.getY() + l);
            out_ep = new Point2D(ep.getX(), ep.getY() + l);
        } else if (sp.getX() == ep.getX()) {
            out_sp = new Point2D(sp.getX() + l, sp.getY());
            out_ep = new Point2D(ep.getX() + l, ep.getY());
        } else {
            // 计算sp和ep距离
            double tmpDis = sp.distance(ep);
            double x1 = sp.getX() + (ep.getX() - sp.getX()) / tmpDis * s1;
            double y1 = sp.getY() + (ep.getY() - sp.getY()) / tmpDis * s1;
            double x2 = ep.getX() - (ep.getX() - sp.getX()) / tmpDis * s2;
            double y2 = ep.getY() - (ep.getY() - sp.getY()) / tmpDis * s2;

            double deltaX = (y2 - y1) / tmpDis * l;
            double deltaY = (x2 - x1) / tmpDis * l;

            int y_dir = tunnel_dir == 0 ? 1 : -1;
            out_sp = new Point2D(x1 + deltaX, y1 + deltaY * y_dir);
            out_ep = new Point2D(x2 + deltaX, y2 + deltaY * y_dir);
        }

        // 将米勒投影的值变为经纬度
        out_sp = GPSUtil.MillierConvertion2(out_sp.getX(), out_sp.getY());
        out_ep = GPSUtil.MillierConvertion2(out_ep.getX(), out_ep.getY());

        // 将结果赋值给原来的
        outStartP.setLng(out_sp.getX());
        outStartP.setLat(out_sp.getY());
        outEndP.setLng(out_ep.getX());
        outEndP.setLat(out_ep.getY());
    }
}
