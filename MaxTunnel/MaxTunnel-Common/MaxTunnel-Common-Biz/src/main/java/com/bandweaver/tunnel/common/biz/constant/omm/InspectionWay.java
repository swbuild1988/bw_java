package com.bandweaver.tunnel.common.biz.constant.omm;

/**
 * 巡检方式
 * @author ya.liu
 * @Date 2019年5月6日
 */
public enum InspectionWay {
    TOUR("巡视", 1),
    CHECK("检查", 2),
    TEST("测试", 3);

    InspectionWay() {
    }

    InspectionWay(String name, Integer value) {
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

    public static InspectionWay getEnum(String name){
        for (InspectionWay dl: InspectionWay.values()) {
            if (dl.getName().equals(name)) return dl;
        }
        return null;
    }

    public static InspectionWay getEnum(Integer value){
        for (InspectionWay dl:
                InspectionWay.values()) {
            if (dl.getValue().intValue() == value) return dl;
        }
        return null;
    }
}
