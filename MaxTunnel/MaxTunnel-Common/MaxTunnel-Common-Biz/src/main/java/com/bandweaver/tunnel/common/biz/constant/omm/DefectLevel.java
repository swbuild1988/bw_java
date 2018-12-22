package com.bandweaver.tunnel.common.biz.constant.omm;

/**
 * 缺陷的问题级别
 */
public enum DefectLevel {
    Normal("正常", 0),
    Hidden("隐患", 1),
    Hard("严重", 2),
    Dangerous("危险",10);

    DefectLevel() {
    }

    DefectLevel(String name, int value) {
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

    public static DefectLevel getEnum(String name){
        for (DefectLevel dl:
             DefectLevel.values()) {
            if (dl.getName().equals(name)) return dl;
        }
        return null;
    }

    public static DefectLevel getEnum(int value){
        for (DefectLevel dl:
                DefectLevel.values()) {
            if (dl.getValue() == value) return dl;
        }
        return null;
    }
}
