package com.bandweaver.tunnel.service.common.export;

import com.bandweaver.tunnel.common.biz.constant.OfTypeEnum;
import com.bandweaver.tunnel.common.biz.constant.mam.AlarmLevelEnum;
import com.bandweaver.tunnel.common.biz.dto.mam.alarm.AlarmDto;
import com.bandweaver.tunnel.common.biz.pojo.Export;
import com.bandweaver.tunnel.common.biz.pojo.Item;
import com.bandweaver.tunnel.common.biz.pojo.Tunnel;
import com.bandweaver.tunnel.common.biz.vo.mam.alarm.AlarmVo;
import com.bandweaver.tunnel.common.platform.log.LogUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 告警报表
 */
public class AlarmTask extends BaseTask implements Runnable {

    private String fileDirPath;

    private int type;

    public AlarmTask(String fileDirPath, int type) {
        this.fileDirPath = fileDirPath;
        this.type = type;
    }


    @Override
    public void run() {
        //获取当前周
        int currentWeekOrMonth = getCurrentTime(type);
        checkLastExport(currentWeekOrMonth, type, OfTypeEnum.ALARM_REPORT.getValue());
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
        //get data
        List<AlarmDto> list = alarmService.getListByStartTimeAndEndTime(startTime, endTime);

        List<Item> data = new ArrayList<>();
        //set title date
        data.addAll(getTitleYear(startTime, endTime, 0, 10));//把标题日期加入data数组中

        ConcurrentHashMap<String, Integer> tunnelMap = new ConcurrentHashMap<>();
        tunnelMap.put("古城大街", 3);//key为管廊名称，value为所在的行数
        tunnelMap.put("实验路", 4);
        tunnelMap.put("经三路", 5);
        tunnelMap.put("经二路", 6);
        tunnelMap.put("纬三路", 7);
        tunnelMap.put("监控中心", 8);

        ConcurrentHashMap<Integer, Integer> levelMap = new ConcurrentHashMap<>();
        levelMap.put(AlarmLevelEnum.PROMPT.getValue(), 10);//key为告警级别，value为所在列
        levelMap.put(AlarmLevelEnum.NORMAL.getValue(), 11);
        levelMap.put(AlarmLevelEnum.SERIOUS.getValue(), 12);
        levelMap.put(AlarmLevelEnum.DANGEROUS.getValue(), 13);


        if (list != null && list.size() > 0) {
            for (String key : tunnelMap.keySet()) {
                Tunnel t = tunnelService.getByName(key);
                if (null == t)
                    continue;

                Item item = null;
                for (Integer key2 : levelMap.keySet()) {
                    item = getValue(levelMap.get(key2), tunnelMap.get(key), t.getId().intValue(), key2.intValue(), list);
                    data.add(item);
                }
            }
        }

        LogUtil.info(Thread.currentThread().getName() + ": data.size = " + data.size());
        doExport(data, getTemplateFilePath(fileDirPath, ofType), type, OfTypeEnum.getEnum(ofType), lastWeekOrMonth);

    }


    private Item getValue(int column, int row, int tunnelId, int level, List<AlarmDto> list) {
        long count = list.stream().filter(x -> x.getTunnel().getId().intValue() == tunnelId && x.getAlarmLevel().intValue() == level).count();
        return new Item(count, row, column);
    }


}
