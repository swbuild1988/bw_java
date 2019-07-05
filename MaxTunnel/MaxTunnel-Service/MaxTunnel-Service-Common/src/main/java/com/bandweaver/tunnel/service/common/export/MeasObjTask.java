package com.bandweaver.tunnel.service.common.export;

import com.bandweaver.tunnel.common.biz.constant.OfTypeEnum;
import com.bandweaver.tunnel.common.biz.constant.TimeEnum;
import com.bandweaver.tunnel.common.biz.constant.mam.AlarmLevelEnum;
import com.bandweaver.tunnel.common.biz.constant.mam.ObjectType;
import com.bandweaver.tunnel.common.biz.dto.TunnelSimpleDto;
import com.bandweaver.tunnel.common.biz.dto.mam.alarm.AlarmDto;
import com.bandweaver.tunnel.common.biz.pojo.Export;
import com.bandweaver.tunnel.common.biz.pojo.Item;
import com.bandweaver.tunnel.common.biz.pojo.Tunnel;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObj;
import com.bandweaver.tunnel.common.biz.pojo.mam.report.MeasObjReport;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.DateUtil;
import org.onvif.ver10.schema.Object;

import java.security.Guard;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 监测对象报表
 */
public class MeasObjTask extends BaseTask implements Runnable {

    private String fileDirPath;

    private int type;

    public MeasObjTask(String fileDirPath, int type) {
        this.fileDirPath = fileDirPath;
        this.type = type;
    }


    @Override
    public void run() {
        //获取当前周
        int currentWeekOrMonth = getCurrentTime(type);
        checkLastExport(currentWeekOrMonth, type, OfTypeEnum.MEASOBJ_REPORT.getValue());

    }


    public void checkLastExport(int currentWeekOrMonth, int type, int ofType) {
        int lastWeekOrMonth = currentWeekOrMonth - 1;
        if (lastWeekOrMonth < 1) {
            return;
        }

        boolean exported = checkdbLog(lastWeekOrMonth, type, ofType);
        if (exported) {
            return;
        } else {
            //导出报表并继续检查上一周有没有导出过报表
            export(lastWeekOrMonth, type, ofType);
            checkLastExport(lastWeekOrMonth, type, ofType);
        }
    }


    public void export(int lastWeekOrMonth, int type, int ofType) {
        Date startTime = getStartTime(type, lastWeekOrMonth);
        Date endTime = getEndTime(type, lastWeekOrMonth);
        List<MeasObjReport> list = measObjReportService.getListByTimeAndTimeType(startTime, endTime, type);
        List<AlarmDto> alarmList = alarmService.getListByStartTimeAndEndTime(startTime, endTime);

        //获取告警属于哪种监测对象类型
        alarmList.forEach(a -> {
            MeasObj measObj = measObjService.get(a.getObjectId());
            if (null != measObj) {
                Integer objtypeId = measObj.getObjtypeId();
                a.setFlag(objtypeId);
            }
        });
        LogUtil.info(Thread.currentThread().getName() + "给flag赋值完成");


        List<Item> data = new ArrayList<>();
        data.addAll(getTitleYear(startTime, endTime, 0, 11));//把标题日期加入data数组中


        ConcurrentHashMap<String, Integer> tunnelMap = new ConcurrentHashMap<>();
        tunnelMap.put("古城大街", 11);//key为管廊名称，value为所在的列数
        tunnelMap.put("实验路", 12);
        tunnelMap.put("经三路", 13);
        tunnelMap.put("经二路", 14);
        tunnelMap.put("纬三路", 15);

        ConcurrentHashMap<Integer, Integer> objTypeMap = new ConcurrentHashMap<>();
        objTypeMap.put(ObjectType.TEMPERATURE.getValue(), 2);//key为监测对象类型，value为所在的行数
        objTypeMap.put(ObjectType.HUMIDITY.getValue(), 4);
        objTypeMap.put(ObjectType.OXYGEN.getValue(), 6);
        objTypeMap.put(ObjectType.H2S.getValue(), 8);
        objTypeMap.put(ObjectType.CH4.getValue(), 10);
        objTypeMap.put(ObjectType.LIQUID.getValue(), 12);


        if (list != null && list.size() > 0) {
            for (String key : tunnelMap.keySet()) {
                Tunnel t = tunnelService.getByName(key);
                if (null == t)
                    continue;

                Item objItem = null, alarmItem = null;
                for (Integer key2 : objTypeMap.keySet()) {
                    if (ObjectType.getEnum(key2) == ObjectType.OXYGEN)
                        objItem = getMin(objTypeMap.get(key2), tunnelMap.get(key), t.getId().intValue(), key2.intValue(), list);
                    else
                        objItem = getMax(objTypeMap.get(key2), tunnelMap.get(key), t.getId().intValue(), key2.intValue(), list);

                    if (alarmList != null && alarmList.size() > 0) {
                        //获取告警统计
                        alarmItem = getAlarmCount(objTypeMap.get(key2) + 1, tunnelMap.get(key), t.getId().intValue(), key2.intValue(), alarmList);
                        data.add(alarmItem);
                    }
                    data.add(objItem);

                }
                ;
            }
            ;
        }

        LogUtil.info(Thread.currentThread().getName() + "data.size: " + data.size());
        doExport(data, getTemplateFilePath(fileDirPath, ofType), type, OfTypeEnum.getEnum(ofType), lastWeekOrMonth);
    }


    private Item getMax(int row, int column, int tunnelId, int objectType, List<MeasObjReport> list) {
        List<MeasObjReport> collect = list.stream().filter(x -> x.getObjectType().intValue() == objectType && x.getTunnelId().intValue() == tunnelId).collect(Collectors.toList());
        double maxValue = collect.stream().max(Comparator.comparing(MeasObjReport::getMaxValue)).get().getMaxValue();
        Item item = new Item(maxValue, row, column);
        return item;
    }

    private Item getMin(int row, int column, int tunnelId, int objectType, List<MeasObjReport> list) {
        List<MeasObjReport> collect = list.stream().filter(x -> x.getObjectType().intValue() == objectType && x.getTunnelId().intValue() == tunnelId).collect(Collectors.toList());
        double minValue = collect.stream().max(Comparator.comparing(MeasObjReport::getMinValue)).get().getMaxValue();
        return new Item(minValue, row, column);
    }


    private Item getAlarmCount(int row, int column, int tunnelId, int objectType, List<AlarmDto> list) {
        long count = list.stream().filter(x -> x.getTunnelId().intValue() == tunnelId && x.getFlag().intValue() == objectType).count();
        return new Item(count, row + 1, column);
    }

}
