package com.bandweaver.tunnel.common.biz.constant.omm;

/**
 * 缺陷类型
 */
public enum DefectType {
    Tunnel("本体缺陷", 1),
    Equipment("设备缺陷", 2);

    DefectType() {
    }

    DefectType(String name, int value) {
        this.name = name;
        this.value = value;
    }

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

    public static DefectType getEnum(String name){
        for (DefectType dl:
                DefectType.values()) {
            if (dl.getName().equals(name)) return dl;
        }
        return null;
    }

    public static DefectType getEnum(int value){
        for (DefectType dl:
                DefectType.values()) {
            if (dl.getValue() == value) return dl;
        }
        return null;
    }
}
