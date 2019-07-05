package com.bandweaver.tunnel.service.common.export;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.constant.OfTypeEnum;
import com.bandweaver.tunnel.common.biz.constant.mam.AlarmLevelEnum;
import com.bandweaver.tunnel.common.biz.dto.mam.alarm.AlarmDto;
import com.bandweaver.tunnel.common.biz.pojo.Export;
import com.bandweaver.tunnel.common.biz.pojo.Item;
import com.bandweaver.tunnel.common.biz.pojo.Tunnel;
import com.bandweaver.tunnel.common.platform.log.LogUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 能耗报表
 */
public class EnergyTask extends BaseTask implements Runnable {

    private String fileDirPath;

    private int type;

    public EnergyTask(String fileDirPath, int type) {
        this.fileDirPath = fileDirPath;
        this.type = type;
    }


    @Override
    public void run() {
        //获取当前周
        int currentWeekOrMonth = getCurrentTime(type);
        checkLastExport(currentWeekOrMonth, type, OfTypeEnum.ENERGY_REPORT.getValue());
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
        List<JSONObject> list = consumeService.getConsumeExcel(startTime, endTime);

        List<Item> data = new ArrayList<>();
        //set title date
        data.addAll(getTitleYear(startTime, endTime, 0, 10));//把标题日期加入data数组中

        ConcurrentHashMap<String, Integer> tunnelMap = new ConcurrentHashMap<>();
        tunnelMap.put("古城大街", 2);//key为管廊名称，value为所在的行数
        tunnelMap.put("实验路", 3);
        tunnelMap.put("经三路", 4);
        tunnelMap.put("经二路", 5);
        tunnelMap.put("纬三路", 6);

        ConcurrentHashMap<String, Integer> timeMap = new ConcurrentHashMap<>();
        timeMap.put("now", 10);//key为日期，value为所在列
        timeMap.put("before", 11);//key为日期，value为所在列

        if (list != null && list.size() > 0) {
            for (String key : tunnelMap.keySet()) {
                Item item = null;
                for (String key2 : timeMap.keySet()) {
                    item = getValue(timeMap.get(key2), tunnelMap.get(key), key, key2, list);
                    data.add(item);
                }
                ;
            }
            ;
        }

        LogUtil.info(Thread.currentThread().getName() + "data.size: " + data.size());
        doExport(data, getTemplateFilePath(fileDirPath,ofType), type, OfTypeEnum.getEnum(ofType), lastWeekOrMonth);
    }

    private Item getValue(int column, int row, String tunnel, String type, List<JSONObject> list) {
        List<JSONObject> tmp = list.stream().filter(x -> x.getString("tunnel").equals(tunnel)).collect(Collectors.toList());
        if (tmp.size() > 0) {
            Double value = tmp.get(0).getDouble(type);
            Item item = new Item(value, row, column);
            return item;
        }

        return new Item(0, 100, 100);

    }


}
