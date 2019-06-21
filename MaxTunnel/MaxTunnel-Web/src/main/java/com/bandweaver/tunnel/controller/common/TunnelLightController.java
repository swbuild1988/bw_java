package com.bandweaver.tunnel.controller.common;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.itf.common.TunnelLightService;
import com.bandweaver.tunnel.common.biz.pojo.common.TunnelLight;
import com.bandweaver.tunnel.common.platform.constant.StatusCodeEnum;
import com.bandweaver.tunnel.common.platform.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 管廊光源的控制类
 */
@Controller
@ResponseBody
public class TunnelLightController {

    @Autowired
    private TunnelLightService tunnelLightService;

    @RequestMapping(value = "tunnel_lights", method = RequestMethod.GET)
    public JSONObject getAllLights() {

        List<TunnelLight> tunnelLights = tunnelLightService.getTunnelLights();
        for (TunnelLight light : tunnelLights) {
            light.setHeight(-1.35);
        }
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, tunnelLights);
    }
}
