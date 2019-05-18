package com.bandweaver.tunnel.common.biz.constant.omm;

/**
 * 巡检对象
 * @author ya.liu
 * @Date 2019年5月6日
 */
public enum InspectionObject {
    MONITORALRAMSYSTEM("监控报警系统", 1),
    FIREALARMSYSTEM("消防报警系统", 2),
    POWERSYSTEM("配电系统", 3),
    OTHERSYSTEM("通排照系统", 4),
    CIVILSTRUCTURE("土建结构", 5),
    FIXEDPOINTINSPECTION("管线关键点日常直观巡检", 6);

    InspectionObject() {
    }

    InspectionObject(String name, Integer value) {
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

    public static InspectionObject getEnum(String name){
        for (InspectionObject dl: InspectionObject.values()) {
            if (dl.getName().equals(name)) return dl;
        }
        return null;
    }

    public static InspectionObject getEnum(Integer value){
        for (InspectionObject dl:
                InspectionObject.values()) {
            if (dl.getValue().intValue() == value) return dl;
        }
        return null;
    }
}
