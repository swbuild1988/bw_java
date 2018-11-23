package com.bandweaver.tunnel.controller.mam;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.dto.mam.MeasObjDto;
import com.bandweaver.tunnel.common.biz.itf.mam.MeasValueAIService;
import com.bandweaver.tunnel.common.biz.itf.mam.measobj.MeasObjService;
import com.bandweaver.tunnel.common.biz.pojo.mam.MeasValueAI;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObj;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObjAI;
import com.bandweaver.tunnel.common.biz.vo.mam.MeasObjVo;
import com.bandweaver.tunnel.common.platform.constant.StatusCodeEnum;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.CommonUtil;
import com.bandweaver.tunnel.common.platform.util.ContextUtil;
import com.bandweaver.tunnel.common.platform.util.DataTypeUtil;
import com.bandweaver.tunnel.service.mam.measobj.MeasObjModuleCenter;
import com.github.pagehelper.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@ResponseBody
public class MeasValueAIController {
    @Autowired
    private MeasValueAIService measValueAIService;
    @Autowired
    private MeasObjModuleCenter measObjModuleCenter;
    @Autowired
    private MeasObjService measObjService;

    
    /**接收MaxView发送过来的数据 
     * @param list
     * @return   
     * @author shaosen
     * @Date 2018年10月18日
     */
    @RequestMapping(value = "measvalue-ai/batch", method = RequestMethod.POST)
    public JSONObject addMeasValueAIBatch(@RequestBody List<MeasValueAI> list) {
    	String ip = ContextUtil.getRemoteIp();
    	LogUtil.info("来自:" + ip + ",接收到MaxView发送的AI数据共：" + list.size() + "条" );
        for (MeasValueAI measValueAI : list) {
            measObjModuleCenter.updateMeasObjAIValue(measValueAI);
        }
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }
    
    

    @RequestMapping(value = "measvalue-ai", method = RequestMethod.POST)
    public JSONObject addMeasValueAI(@RequestBody MeasValueAI valueAI) {
        measObjModuleCenter.updateMeasObjAIValue(valueAI);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }

    @RequestMapping(value = "measvalue-ai/now", method = RequestMethod.GET)
    public JSONObject getNowMeasValueAIs() {
        List<MeasObjAI> l = measObjModuleCenter.getMeasObjAIs();
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, l);
    }


}
