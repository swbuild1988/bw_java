package com.bandweaver.tunnel.service.common;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.dto.TunnelDto;
import com.bandweaver.tunnel.common.biz.dto.mam.Point3D;
import com.bandweaver.tunnel.common.biz.pojo.Tunnel;
import com.bandweaver.tunnel.common.platform.util.DataTypeUtil;
import com.bandweaver.tunnel.common.platform.util.PointUtil;
import com.bandweaver.tunnel.dao.common.TunnelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bandweaver.tunnel.common.biz.dto.AreaDto;
import com.bandweaver.tunnel.common.biz.itf.AreaService;
import com.bandweaver.tunnel.common.biz.pojo.Area;
import com.bandweaver.tunnel.common.biz.pojo.Section;
import com.bandweaver.tunnel.common.biz.pojo.Store;
import com.bandweaver.tunnel.common.biz.vo.AreaVo;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.dao.common.AreaMapper;
import com.bandweaver.tunnel.dao.common.SectionMapper;
import com.bandweaver.tunnel.dao.common.StoreMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class AreaServiceImpl implements AreaService {

    @Autowired
    private AreaMapper areaMapper;
    @Autowired
    private StoreMapper storeMapper;
    @Autowired
    private SectionMapper sectionMapper;
    @Autowired
    private TunnelMapper tunnelMapper;

    @Override
    @Transactional
    public void addArea(Area area) {
        LogUtil.debug("insert before:" + area);
        area.setCrtTime(new Date());
        areaMapper.insertSelective(area);
        LogUtil.debug("insert after:" + area);

        String area_name = area.getName();
        List<Store> store_list = storeMapper.getStoresByTunnelId(area.getTunnelId());
        if (store_list == null) return;
        for (Store store : store_list) {
            String section_name = area_name + "-" + store.getName();

            Section section = new Section();
            section.setName(section_name);
            section.setTunnelId(store.getTunnelId());
            section.setStoreId(store.getId());
            section.setAreaId(area.getId());
            section.setCrtTime(new Date());
            LogUtil.debug("create section:" + section);
            sectionMapper.insertSelective(section);
        }


    }

    @Override
    public void addAreaBatch(List<Area> list) {
        areaMapper.addAreaBatch(list);
    }

    @Override
    public List<AreaDto> getList() {
        return areaMapper.getList();
    }

    @Override
    public List<AreaDto> getAreasByCondition(AreaVo areaVo) {
        return areaMapper.getAreasByCondition(areaVo);
    }

    @Override
    public AreaDto getAreasById(Integer id) {
        return areaMapper.getAreasById(id);
    }

    @Override
    public List<AreaDto> getAreasByTunnelId(Integer id) {
        AreaVo vo = new AreaVo();
        vo.setTunnelId(id);

        List<AreaDto> areas = areaMapper.getAreasByCondition(vo);
//        Collections.sort(areas, new Comparator<Area>() {
//            public int compare(Area arg0, Area arg1) {
//                if (arg0.getSn() == null || arg0.getSn().length() == 0
//                        || arg1.getSn() == null || arg1.getSn().length() == 0) return 0;
//                return DataTypeUtil.toInt(arg0.getSn()) - DataTypeUtil.toInt(arg1.getSn());
//            }
//        });
        return areas;
    }

    @Override
    public PageInfo<AreaDto> dataGrid(AreaVo vo) {
        PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
        List<AreaDto> list = getAreasByCondition(vo);
        return new PageInfo<>(list);
    }

    @Override
    public void update(Area area) {
        areaMapper.updateByPrimaryKeySelective(area);
    }

    @Override
    public void delete(Integer id) {
        areaMapper.deleteByPrimaryKey(id);
    }

    @Override
    @Transactional
    public void deleteBatch(List<Integer> list) {
        areaMapper.deleteBatch(list);

        //同时删除对应的section
        List<Integer> sectionIds = sectionMapper.getSectionIdsByAreaIds(list);
        sectionMapper.deleteBatch(sectionIds);
    }

    @Override
    public Area getByName(String name) {
        return areaMapper.getByName(name);
    }

    @Override
    public Area getByTunnelAndSN(Integer tunnelId, String sn) {
        return areaMapper.getAreaByTunnelAndSN(tunnelId, sn);
    }

    @Override
    public int getTotalCount() {
        return areaMapper.getTotalCount();
    }

    /**
     * 计算管廊所有区的起点和终点位置信息
     *
     * @param tunnelId 管廊ID
     * @return
     */
    @Override
    public boolean calAllAreasStartPointAndEndPointByTunnel(int tunnelId) {
        TunnelDto tunnel = tunnelMapper.getDtoById(tunnelId);
        Point3D tunnel_start_point = PointUtil.get3DPoint(tunnel.getStartPoint());
        Point3D tunnel_end_point = PointUtil.get3DPoint(tunnel.getEndPoint());

        // 如果管廊2端没有填，则直接退出
        if (tunnel_start_point == null || tunnel_end_point == null) return false;

        // 获得所有的区域，并根据sn排序
        List<AreaDto> areas = getAreasByTunnelId(tunnelId);

        // 获取所有区的长度总和（管廊长度）
        double total_len = 0;
        for (Area area : areas) {
            total_len += area.getLength().doubleValue();
        }

        // 设置所有区域的sp和ep
        double pre_len = 0;
        for (int i = 0; i < areas.size(); i++) {

            // 计算区的起始
            double tmp_lon = getMiddleX(tunnel_start_point.getLng(), tunnel_end_point.getLng(), total_len, pre_len);
            double tmp_lat = getMiddleX(tunnel_start_point.getLat(), tunnel_end_point.getLat(), total_len, pre_len);
            double tmp_hei = getMiddleX(tunnel_start_point.getHeight(), tunnel_end_point.getHeight(), total_len, pre_len);
            Area area = (Area) areas.get(i);
            area.setStartPoint(PointUtil.get3DPointString(tmp_lon, tmp_lat, tmp_hei));

            // 计算区的结束位置
            pre_len += area.getLength().doubleValue();
            tmp_lon = getMiddleX(tunnel_start_point.getLng(), tunnel_end_point.getLng(), total_len, pre_len);
            tmp_lat = getMiddleX(tunnel_start_point.getLat(), tunnel_end_point.getLat(), total_len, pre_len);
            tmp_hei = getMiddleX(tunnel_start_point.getHeight(), tunnel_end_point.getHeight(), total_len, pre_len);
            area.setEndPoint(PointUtil.get3DPointString(tmp_lon, tmp_lat, tmp_hei));

            areaMapper.updateByPrimaryKeySelective(area);
        }

        return true;
    }

    /**
     * 获取x1，x2中的某一点位置
     *
     * @param x1
     * @param x2
     * @param total 总长度
     * @param index 离x1的距离
     * @return
     */
    private double getMiddleX(double x1, double x2, double total, double index) {
        return (double) Math.round((x1 + (x2 - x1) / total * index) * 100000000) / 100000000;
    }
}
