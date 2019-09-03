package com.bandweaver.tunnel.controller.mam;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.itf.mam.MeasValueDASSpectrumService;
import com.bandweaver.tunnel.common.biz.pojo.mam.MeasValueDASSpectrum;
import com.bandweaver.tunnel.common.platform.constant.StatusCodeEnum;
import com.bandweaver.tunnel.common.platform.util.CommonUtil;
import com.bandweaver.tunnel.common.platform.util.DataTypeUtil;
import com.bandweaver.tunnel.common.platform.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@ResponseBody
public class MeasValueDASSpectrumController {
    @Autowired
    private MeasValueDASSpectrumService measValueDASSpectrumService;

    /**
     * @Description: 添加
     * @param @param valueDASSpectrum
     * @param @return   
     * @return JSONObject  
     * @throws
     * @author shaosen
     * @date 2018年5月28日
     */
    @RequestMapping(value = "measvalue-dasspectrum", method = RequestMethod.POST)
    public JSONObject addMeasValueDAS(@RequestBody MeasValueDASSpectrum valueDASSpectrum){
		measValueDASSpectrumService.addMeasValueDAS(valueDASSpectrum);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }

    /**
     * @Description: 批量添加
     * @param @param list
     * @param @return   
     * @return JSONObject  
     * @throws
     * @author shaosen
     * @date 2018年5月30日
     */
    @RequestMapping(value = "measvalue-dasspectrum/list", method = RequestMethod.POST)
    public JSONObject addMeasValueDASBatch(@RequestBody List<MeasValueDASSpectrum> list){
		measValueDASSpectrumService.addMeasValueDASBatch(list);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,list.size());
    }

    
}
