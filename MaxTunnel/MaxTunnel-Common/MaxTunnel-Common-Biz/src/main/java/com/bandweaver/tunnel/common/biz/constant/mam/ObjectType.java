package com.bandweaver.tunnel.common.biz.constant.mam;

import java.util.ArrayList;
import java.util.List;

public enum ObjectType {

    NONE("无", 0, 0, 0),
    TEMPERATURE("温度", 1, 1, 1),
    HUMIDITY("湿度", 2, 1, 1),
    OXYGEN("氧气", 3, 1, 1),
    H2S("硫化氢", 4, 1, 1),
    CH4("甲烷", 5, 1, 1),
    CO("一氧化碳", 6, 1, 1),
    VIDEO("视频", 7, 3, 7),
    DISTRIBUTET("分布式温度", 8, 1, 1),
    ROBOT("机器人", 9, 3, 97),
    FAN("风机", 10, 3, 3),
    LIGHT("灯", 11, 3, 3),
    POSITION("定位设备", 20, 3, 97),
    LIQUID("液位仪", 21, 1, 1),


    ELECTRICITY_METER1("风机类电表", 31, 1, 1),
    ELECTRICITY_METER2("灯类电表", 32, 1, 1),
    ELECTRICITY_METER3("水泵类电表", 33, 1, 1),
    ELECTRICITY_METER4("其他类电表", 34, 1, 1),

    ALTEROR("声光报警", 41, 3, 3),

    SETTLE("沉降", 51, 2, 97),
    EXPANSION_JOINT("伸缩缝", 52, 2, 97),
    STRESS("应力", 53, 2, 97),
    EXTERNAL_WATER_PRESSURE("外水压力", 54, 2, 97),

    ENTRANCE_GUARD("门禁", 55, 3, 3),
    ELECTRONIC_COVERS("电子井盖", 56, 3, 3),
    INFRARED("红外", 57, 3, 3),
    BLINDS("百叶", 58, 3, 3),
    PUMP("水泵", 59, 3, 3);

    private String name;
    private int value;
    private int monitorType;
    private int dataType;


    ObjectType() {
    }

    ObjectType(String name, int value, int monitorType, int dataType) {
        this.name = name;
        this.value = value;
        this.monitorType = monitorType;
        this.dataType = dataType;
    }

    /**
     * 返回电表监测类型数组
     *
     * @return
     * @author shaosen
     * @Date 2018年8月16日
     */
    public static ObjectType[] getArr() {
        return new ObjectType[]{
                ELECTRICITY_METER1,
                ELECTRICITY_METER2,
                ELECTRICITY_METER3,
                ELECTRICITY_METER4
        };
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }


    public int getMonitorType() {
        return monitorType;
    }

    public void setMonitorType(int monitorType) {
        this.monitorType = monitorType;
    }

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public static ObjectType getEnum(String name) {
        for (ObjectType dl : ObjectType.values()) {
            if (dl.getName().equals(name)) return dl;
        }
        return null;
    }

    public static ObjectType getEnum(int value) {
        for (ObjectType dl : ObjectType.values()) {
            if (dl.getValue() == value) return dl;
        }
        return NONE;
    }

    public static List<ObjectType> getEnumByMonitorType(int monitorType) {
        List<ObjectType> list = new ArrayList<>();
        for (ObjectType dl : ObjectType.values()) {
            if (dl.getMonitorType() == monitorType)
                list.add(dl);
        }
        return list;
    }

    public static List<ObjectType> getEnumByDataType(int dataType) {
        List<ObjectType> list = new ArrayList<>();
        for (ObjectType dl : ObjectType.values()) {
            if (dl.getDataType() == dataType)
                list.add(dl);
        }
        return list;
    }

}
