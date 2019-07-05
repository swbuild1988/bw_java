package com.bandweaver.tunnel.common.biz.constant.omm;

/**
 * 巡检路径
 * @author ya.liu
 * @Date 2019年5月6日
 */
public enum InspectionPath {
    CABIN("按舱室", 1),
    RANGE("按区间", 2),
    EQUIPMENT("中层设备间", 3),
    MONITOR("监控中心机房", 4);

    InspectionPath() {
    }

    InspectionPath(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    private String name;
    private Integer value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public static InspectionPath getEnum(String name){
        for (InspectionPath dl: InspectionPath.values()) {
            if (dl.getName().equals(name)) return dl;
        }
        return null;
    }

    public static InspectionPath getEnum(Integer value){
        for (InspectionPath dl:
                InspectionPath.values()) {
            if (dl.getValue().intValue() == value) return dl;
        }
        return null;
    }
}
