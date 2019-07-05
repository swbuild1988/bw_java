package com.bandweaver.tunnel.service.common;

import com.bandweaver.tunnel.common.biz.dto.mam.Point3D;
import com.bandweaver.tunnel.common.biz.itf.common.TunnelLightService;
import com.bandweaver.tunnel.common.biz.itf.common.XMLService;
import com.bandweaver.tunnel.common.biz.pojo.Section;
import com.bandweaver.tunnel.common.biz.pojo.Store;
import com.bandweaver.tunnel.common.biz.pojo.common.TunnelLight;
import com.bandweaver.tunnel.common.biz.vo.SectionVo;
import com.bandweaver.tunnel.common.platform.util.GPSUtil;
import com.bandweaver.tunnel.common.platform.util.PointUtil;
import com.bandweaver.tunnel.dao.common.SectionMapper;
import com.bandweaver.tunnel.dao.common.StoreMapper;
import com.bandweaver.tunnel.dao.common.TunnelLightMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TunnelLightServiceImpl implements TunnelLightService {

    @Autowired
    private TunnelLightMapper tunnelLightMapper;

    @Autowired
    private SectionMapper sectionMapper;

    @Autowired
    private XMLService xmlService;

    @Autowired
    private StoreMapper storeMapper;

    @Override
    public List<TunnelLight> getTunnelLights() {
        return tunnelLightMapper.getTunnelLights();
    }

    @Override
    public void insertTunnelLight(TunnelLight tunnelLight) {
        tunnelLightMapper.insertSelective(tunnelLight);
    }

    @Override
    public void deleteTunnelLight(int id) {
        tunnelLightMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void createTunnelLights(Integer tunnelId, Integer areaId, Integer storeId) {

        SectionVo sectionVo = new SectionVo();
        sectionVo.setTunnelId(tunnelId);
        sectionVo.setAreaId(areaId);
        sectionVo.setStoreId(storeId);

        List<Section> sections = sectionMapper.getSectionListByStoreAndArea(sectionVo);
        for (Section section : sections) {
            createBySection(section.getId().intValue());
        }

    }

    /**
     * 创建某个section中的点光源
     *
     * @param sectionId
     */
    private void createBySection(int sectionId) {
        Section section = (Section) sectionMapper.getDtoById(sectionId);

        // 获取section的起始点和终点
        Point3D sp = PointUtil.get3DPoint(section.getStartPoint());
        Point3D ep = PointUtil.get3DPoint(section.getEndPoint());

        if (sp == null || ep == null) return;

        // 如果已经有了这个section的灯，都删了
        List<TunnelLight> delLights = tunnelLightMapper.getTunnelLightsBySection(sectionId);
        for (TunnelLight light : delLights) {
            tunnelLightMapper.deleteByPrimaryKey(light.getId().intValue());
        }

        // section的长度
        double sectionLength = GPSUtil.GetDistance(sp.getLon().doubleValue(), sp.getLat().doubleValue(),
                ep.getLon().doubleValue(), ep.getLat().doubleValue());

        // section的高度
        Store store = storeMapper.selectByPrimaryKey(section.getStoreId().intValue());
        double sectionHeight = store.getHeight().doubleValue();

        // 获取点光源的间隔距离
        double lightDistance = xmlService.getXMLAllInfo().getLightDistance();
        if (lightDistance == 0) lightDistance = 10;

        // 一个仓可安装光源数,用四舍五入一定程度上最好
        long lightNum = Math.round(sectionLength / lightDistance);
        if (lightNum == 0) return;

        double preDistance = (sectionLength - lightDistance * (lightNum - 1)) / 2;

        for (int i = 0; i < lightNum; i++) {

            // 计算每个点在section中的位置系数
            double k = (preDistance + i * lightDistance) / sectionLength;

            double tmp_lon = sp.getLon() + (ep.getLon() - sp.getLon()) * k;
            double tmp_lat = sp.getLat() + (ep.getLat() - sp.getLat()) * k;
            double tmp_height = sp.getHeight() + (ep.getHeight() - sp.getHeight()) * k + sectionHeight / 2;

            TunnelLight tunnelLight = new TunnelLight();

            tunnelLight.setTunnelId(section.getTunnelId());
            tunnelLight.setAreaId(section.getAreaId());
            tunnelLight.setStoreId(section.getStoreId());
            tunnelLight.setSectionId(section.getId());
            tunnelLight.setCrtTime(new Date());
            tunnelLight.setLat(tmp_lat);
            tunnelLight.setLon(tmp_lon);
            tunnelLight.setHeight(tmp_height);

            tunnelLightMapper.insertSelective(tunnelLight);

        }

    }
}
