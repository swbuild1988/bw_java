package com.bandweaver.tunnel.controller.em;


import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.itf.em.TemperatureAndHumidityAlarmSopService;
import com.bandweaver.tunnel.common.platform.util.CommonUtil;
import com.bandweaver.tunnel.common.platform.util.PropertiesUtil;
import org.activiti.engine.ProcessEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletResponse;


/**
 * @author sen.shao 消防预案执行流程管理
 */
@Controller
@ResponseBody
public class TemperatureAndHumidityAlarmSopController {

    @Autowired
    private TemperatureAndHumidityAlarmSopService temperatureAndHumidityAlarmSopService;
    @Autowired
    private ProcessEngine processEngine;

    /**
     * 火灾预案开始
     *
     * @return
     */
    @RequestMapping(value = "sop/fire/start", method = RequestMethod.GET)
    public JSONObject start() {
        temperatureAndHumidityAlarmSopService.start();
        return CommonUtil.success();
    }

    /**获取png图片
     * @param response
     * @return
     */
    @RequestMapping(value = "sop/png", method = RequestMethod.GET)
    public JSONObject getPng(HttpServletResponse response) {
        String processInstance = "2501";
        temperatureAndHumidityAlarmSopService.getPng(processInstance, response);
        return CommonUtil.success();
    }

}
