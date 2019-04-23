package com.bandweaver.tunnel.common.biz.constant;


public enum OfTypeEnum {
    INSPECTION_REPORT("巡检报表", 1),
    ENERGY_REPORT("能耗报表", 2),
    EQUIPMENT_REPORT("设备报表", 3),
    ALARM_REPORT("告警报表", 4),
    MEASOBJ_REPORT("监测报表", 5);

    private String name;
    private int value;

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

    OfTypeEnum(String name, int value) {
        this.name = name;
        this.value = value;
    }


    public static OfTypeEnum getEnum(int value) {
        for (OfTypeEnum actionEnum : OfTypeEnum.values()) {
            if (value == actionEnum.getValue())
                return actionEnum;
        }
        return null;
    }

}
