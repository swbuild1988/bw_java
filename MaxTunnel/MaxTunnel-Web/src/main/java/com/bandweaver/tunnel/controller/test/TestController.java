package com.bandweaver.tunnel.controller.test;


import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.dto.TunnelDto;
import com.bandweaver.tunnel.common.biz.dto.TunnelSimpleDto;
import com.bandweaver.tunnel.common.biz.dto.mam.MeasObjDto;
import com.bandweaver.tunnel.common.biz.dto.oam.ConsumeDto;
import com.bandweaver.tunnel.common.biz.itf.*;
import com.bandweaver.tunnel.common.biz.itf.mam.alarm.AlarmService;
import com.bandweaver.tunnel.common.biz.itf.mam.measobj.MeasObjService;
import com.bandweaver.tunnel.common.biz.itf.oam.ConsumeDataService;
import com.bandweaver.tunnel.common.biz.itf.oam.ConsumeService;
import com.bandweaver.tunnel.common.biz.pojo.mam.MeasValueAI;
import com.bandweaver.tunnel.common.biz.pojo.mam.alarm.Alarm;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObj;
import com.bandweaver.tunnel.common.biz.pojo.oam.Consume;
import com.bandweaver.tunnel.common.biz.pojo.oam.ConsumeData;
import com.bandweaver.tunnel.common.biz.vo.mam.MeasObjVo;
import com.bandweaver.tunnel.common.platform.constant.StatusCodeEnum;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.CommonUtil;
import com.bandweaver.tunnel.common.platform.util.DateUtil;
import com.bandweaver.tunnel.common.platform.util.MathUtil;
import com.bandweaver.tunnel.dao.mam.MeasValueAIMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import com.bandweaver.tunnel.service.mam.measobj.MeasObjModuleCenter;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@ResponseBody
public class TestController {
    @Autowired
    private ConsumeService consumeService;
    @Autowired
    private MeasObjModuleCenter measObjModuleCenter;
    @Autowired
    private MeasValueAIMapper measValueAIMapper;
    @Autowired
    private ConsumeDataService consumeDataService;
    @Autowired
    private TunnelService tunnelService;
    @Autowired
    private MeasObjService measObjService;
    @Autowired
    private AlarmService alarmService;

    /**
     * 测试添加每个管廊的moid和总能耗
     *
     * @return
     */
    @RequestMapping(value = "test/add-consume", method = RequestMethod.GET)
    public JSONObject addTestConsumes() {
        List<TunnelSimpleDto> tunnels = tunnelService.getList();
        // 循环添加每个管廊的moid（只用于能耗）和能耗
        for (TunnelSimpleDto dto : tunnels) {
            String tmp_id = dto.getId() + "0008";
            MeasObj measObj = new MeasObj();
            measObj.setId(Integer.parseInt(tmp_id));
            measObj.setTunnelId(dto.getId());
            measObj.setAreaId(0);
            measObj.setStoreId(0);
            measObj.setActived(true);
            measObj.setObjtypeId(31);
            measObj.setDatatypeId(1);
            measObj.setName("总电表");
            measObjModuleCenter.insertMeasObj(measObj);

            Consume consume = new Consume();
            consume.setTunnelId(dto.getId());
            consume.setAreaId(0);
            consume.setStoreId(0);
            consume.setObjectType(0);
            consume.setEnergyType(0);
            consume.setObjectId(measObj.getId());
            consumeService.insert(consume);
        }
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }

    /**
     * 每八小时查看一次电表，并将数值保存到measValueAI中
     *
     * @return
     */
    @RequestMapping(value = "test/add-value", method = RequestMethod.GET)
    public JSONObject addTestValue() {
    	// 设置存储数据的开始和结束时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date sd = null;
        Date ed = null;
        try {
            sd = sdf.parse("2018-6-1");
            ed = sdf.parse("2019-6-1");
        } catch (Exception e) {

        }
        
        // 获取每个管廊的用于能耗的mo
        MeasObjVo vo = new MeasObjVo();
        vo.setObjtypeId(31);
        vo.setAreaId(0);
        vo.setStoreId(0);
        vo.setSectionId(0);
        List<MeasObjDto> objList = measObjService.getMeasObjByCondition(vo);
        // 初始化电表值
        List<Double> cvList = new ArrayList<>();
        // 获取管廊信息
        List<TunnelDto> tunnelList = new ArrayList<>();
        for (int i = 0; i < objList.size(); i++) {
            cvList.add(0.0);
            TunnelDto dto = tunnelService.getDtoById(objList.get(i).getTunnelId());
            tunnelList.add(dto);
        }

        while (sd.before(ed)) {
            //LogUtil.info("before add time : " + sd);
            // 遍历每个管廊的mo，保存数据
            for (int i = 0; i < objList.size(); i++) {
                // 获取[8,12]随机数
                double random = Math.random() * 4 + 8;
                double sum = random * tunnelList.get(i).getLength() / 100 + cvList.get(i);
                // 保留两位小数
                BigDecimal bg = new BigDecimal(sum);
                sum = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                // 保存电表读数
                cvList.set(i, sum);
                LogUtil.info("cvList.get(" + i + ") : " + cvList.get(i));

                MeasValueAI measValueAI = new MeasValueAI();
                measValueAI.setTime(sd);
                measValueAI.setObjectId(objList.get(i).getId());
                measValueAI.setCv(sum);
                measValueAIMapper.addMeasValueAI(measValueAI);
            }

            sd = new Date(sd.getTime() + 8 * 60 * 60 * 1000);
            //LogUtil.info("after add time : " + sd);
        }


        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }


    /**
     * 查看每天的能耗，并保存到consumeData表中
     *
     * @return
     */
    @RequestMapping(value = "test/add-consumedata", method = RequestMethod.GET)
    public JSONObject addTestConsumeData() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date sd = null;
        Date ed = null;
        try {
            sd = sdf.parse("2018-6-2");
            ed = sdf.parse("2019-6-2");
        } catch (Exception e) {

        }
        while (sd.before(ed)) {
            getDatas(sd);
            sd = new Date(sd.getTime() + 24 * 60 * 60 * 1000);
        }
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }

    private void getDatas(Date time) {
        // 获取昨天开始时间
        Date startTime = DateUtil.getDayStartTime(DateUtil.getFrontDay(time, 1));
        // 获取今天的开始时间
        Date endTime = DateUtil.getDayStartTime(time);

        // 获取consume表所有电表(包括不存在的电表)
        List<ConsumeDto> consumes = consumeService.getConsumes();
        // for循环插入数据
        for (ConsumeDto dto : consumes) {
            ConsumeData data = new ConsumeData();
            data.setId(dto.getId());
            data.setTime(startTime);
            Double sum = 0.0;
            // 判断是否存在电表
            if (dto.getObjectId() != null) {
                List<MeasValueAI> ais = measValueAIMapper.getListByObjectIdAndTime(dto.getObjectId(), startTime, endTime);
                //统计aiValue表中某一电表一天内的能耗
                if (ais.size() > 0)
                    sum = MathUtil.sub(ais.get(0).getCv(), ais.get(ais.size() - 1).getCv());
                data.setDirect(sum);
            } else if (dto.getCompute() != null) {
                String[] s = dto.getCompute().split(",");
                //统计没有电表时一天内的合计能耗
                for (String ss : s) {
                    List<MeasValueAI> ais = measValueAIMapper.getListByObjectIdAndTime(Integer.valueOf(ss), startTime, endTime);
                    //统计aiValue表中某一电表一天内的能耗
                    if (ais.size() > 0) {
                        Double sum1 = MathUtil.sub(ais.get(0).getCv(), ais.get(ais.size() - 1).getCv());
                        sum = MathUtil.add(sum, sum1);
                    }
                }
                data.setIndirect(sum);
            }
            //添加
            consumeDataService.insert(data);
        }
    }

    @RequestMapping(value = "test/add_alarm", method = RequestMethod.GET)
    public JSONObject sendMQMessage() {
        Alarm alarm = new Alarm();
        alarm.setId((int) ((new Date()).getTime() % 1000000));
        alarm.setAlarmDate(new Date());
        alarm.setAlarmLevel(1);
        alarm.setAlarmName("温度测试告警");
        alarm.setObjectId(222032401);
        alarm.setObjectName("温度检测仪");
        alarm.setTunnelId(1);
        alarm.setAlarmSource("");
        alarm.setCleaned(false);
        alarm.setIsDistribute(false);
        alarm.setCleanedDate(new Date());
        alarm.setDescription("");
        alarm.setLatitude("");
        alarm.setLongitude("");
        alarmService.add(alarm);

        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }

}
