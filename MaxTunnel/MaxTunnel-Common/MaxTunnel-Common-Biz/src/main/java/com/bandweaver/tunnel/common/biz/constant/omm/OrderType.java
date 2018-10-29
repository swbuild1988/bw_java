package com.bandweaver.tunnel.common.biz.constant.omm;

/**
 * 缺陷类型
 */
public enum OrderType {
    Defect("缺陷维修", 1);

    OrderType() {
    }

    OrderType(String name, int value) {
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

    public static OrderType getEnum(String name){
        for (OrderType dl:
                OrderType.values()) {
            if (dl.getName().equals(name)) return dl;
        }
        return null;
    }

    public static OrderType getEnum(int value){
        for (OrderType dl:
                OrderType.values()) {
            if (dl.getValue() == value) return dl;
        }
        return null;
    }
}
