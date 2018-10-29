package com.bandweaver.tunnel.controller.mam;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.dto.mam.Locator;
import com.bandweaver.tunnel.common.biz.itf.mam.locator.LocatorService;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObj;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObjSO;
import com.bandweaver.tunnel.common.platform.constant.StatusCodeEnum;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 人员定位控制器
 */
@Controller
@ResponseBody
public class LocatorController {
    @Autowired
    private LocatorService locatorService;

    /**
     * 获得所有定位信息（包括actived = false的）
     *
     * @return
     */
    @RequestMapping(value = "locators")
    public JSONObject getAllLocators() {
        List<MeasObjSO> measObjSOS = locatorService.getAllLocator();

        List<JSONObject> objects = new ArrayList<>();
        measObjSOS.forEach(measObjSO -> objects.add(getLocatorObj(measObjSO)));

        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, objects);
    }

    /**
     * 获得所有在线定位信息o
     *
     * @return
     */
    @RequestMapping(value = "actived-locators")
    public JSONObject getAllActivedLocators() {
        List<MeasObjSO> measObjSOS = locatorService.getAllActivedLocator();

        List<JSONObject> objects = new ArrayList<>();
        measObjSOS.forEach(measObjSO -> objects.add(getLocatorObj(measObjSO)));

        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, objects);
    }

    /**
     * 绑定某人和某个设备
     *
     * @param locatorId 设备ID
     * @param object    某人的信息，user
     * @return
     */
    @RequestMapping(value = "locators/{locatorId}/start", method = RequestMethod.POST)
    public JSONObject setLocatorUser(@PathVariable("locatorId") int locatorId, @RequestBody JSONObject object) {

        LogUtil.info("start locator：" + locatorId);
        LogUtil.info("User:" + object);

        Object user = object.get("user");

        locatorService.startLocator(locatorId, user);

        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }

    /**
     * 结束设备的使用
     *
     * @param locatorId
     * @return
     */
    @RequestMapping(value = "locators/{locatorId}/stop", method = RequestMethod.GET)
    public JSONObject stopLocatorUser(@PathVariable("locatorId") int locatorId) {

        locatorService.stopLocator(locatorId);

        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }

    /**
     * 将measObjSO转化为前段需要的对象
     *
     * @param measObjSO
     * @return
     */
    private JSONObject getLocatorObj(MeasObjSO measObjSO) {
        JSONObject object = new JSONObject();

        object.put("id", measObjSO.getId());
        object.put("name", measObjSO.getName());
        object.put("time", measObjSO.getRefreshTime());
        if (measObjSO.getCV() == null || measObjSO.getCV().equals("")) {
            object.put("owner", null);
            object.put("type", 0);
            object.put("longitude", 0);
            object.put("latitude", 0);
            object.put("height", 0);
        } else {
            Locator locator = (Locator) JSONObject.parseObject(measObjSO.getCV(), Locator.class);
            object.put("owner", locator.getOwner());
            object.put("type", 0);
            object.put("longitude", locator.getPosition() == null ? 0 : locator.getPosition().getLng());
            object.put("latitude", locator.getPosition() == null ? 0 : locator.getPosition().getLat());
            object.put("height", locator.getPosition() == null ? 0 : locator.getPosition().getHeight());
        }

        return object;
    }
}
