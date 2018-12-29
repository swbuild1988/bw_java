package com.bandweaver.tunnel.controller.common;


import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.dto.AreaDto;
import com.bandweaver.tunnel.common.biz.dto.StoreDto;
import com.bandweaver.tunnel.common.biz.itf.*;
import com.bandweaver.tunnel.common.biz.pojo.*;
import com.bandweaver.tunnel.common.platform.constant.StatusCodeEnum;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@ResponseBody
public class InitController {
    @Autowired
    private TunnelService tunnelService;
    @Autowired
    private StoreTypeService storeTypeService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private AreaService areaService;
    @Autowired
    private SectionService sectionService;

    @Transactional
    @RequestMapping(value = "tunnels/init", method = RequestMethod.GET)
    public JSONObject initTunnel() {

        // 管仓类型
        StoreType storeType = new StoreType();
        storeType.setCrtTime(new Date());
        storeType.setName("燃气仓");
        storeTypeService.insert(storeType);
        storeType.setName("综合仓");
        storeTypeService.insert(storeType);
        storeType.setName("电力仓");
        storeTypeService.insert(storeType);
        storeType.setName("污水仓");
        storeTypeService.insert(storeType);

        // 添加管廊
        for (int i = 0; i < 5; i++) {
            String tunnelName = "";
            Double tunnelLength = 0.0;
            Integer tunnelAreaNum = 0;


            Tunnel tunnel = new Tunnel();
            tunnel.setCrtTime(new Date());
            switch (i) {
                case 0:
                    tunnelName = "古城大街";
                    tunnelLength = 5000.0;
                    tunnelAreaNum = 22;
                    break;

                case 1:
                    tunnelName = "实验路";
                    tunnelLength = 1000.0;
                    tunnelAreaNum = 6;
                    break;

                case 2:
                    tunnelName = "经三路";
                    tunnelLength = 1000.0;
                    tunnelAreaNum = 6;
                    break;

                case 3:
                    tunnelName = "经二路";
                    tunnelLength = 1000.0;
                    tunnelAreaNum = 6;
                    break;

                case 4:
                    tunnelName = "纬三路";
                    tunnelLength = 5000.0;
                    tunnelAreaNum = 22;
                    break;
            }
            tunnel.setName(tunnelName);
            tunnel.setLength(tunnelLength);
            tunnel.setConstructId(5);
            tunnel.setResponsibilityId(1);
            tunnel.setOperationId(1);

            // 将管廊添加
            tunnelService.add(tunnel);

            // 给每个管廊添加管仓
            List<StoreType> storeTypes = storeTypeService.getList();
            for (StoreType storeType1 : storeTypes) {
                Store store = new Store();
                store.setCrtTime(new Date());
                store.setName(storeType1.getName());
                store.setStoreTypeId(storeType1.getId());
                store.setTunnelId(tunnel.getId());
                store.setCamera("");
                LogUtil.info("store:" + store);
                storeService.add(store);
            }

            // 给管廊添加区域
            for (int j = 0; j < tunnelAreaNum; j++) {
                Area area = new Area();
                area.setCamera("");
                area.setCrtTime(new Date());
                area.setLocation("1");
                area.setName((j + 1) + "区");
                area.setTunnelId(tunnel.getId());
                LogUtil.info("area:" + area);
                areaService.addArea(area);
            }

            // 给管廊添加section
            List<StoreDto> storeDtos = storeService.getStoresByTunnelId(tunnel.getId());
            List<AreaDto> areaDtos = areaService.getAreasByTunnelId(tunnel.getId());
            for (StoreDto storeDto : storeDtos) {
                for (AreaDto areaDto : areaDtos) {
                    Section section = new Section();
                    section.setAreaId(areaDto.getId());
                    section.setStoreId(storeDto.getId());
                    section.setCrtTime(new Date());
                    section.setCamera("");
                    section.setName(areaDto.getName() + "-" + storeDto.getName());
                    section.setStartPoint("");
                    section.setEndPoint("");
                    section.setTotalCableNumber(storeDto.getStoreTypeId() == 3 ? 20 : 5);
                    sectionService.add(section);
                }
            }
        }


        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }

}
