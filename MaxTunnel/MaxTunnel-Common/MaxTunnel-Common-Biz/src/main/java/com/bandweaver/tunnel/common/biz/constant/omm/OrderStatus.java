package com.bandweaver.tunnel.common.biz.constant.omm;

public enum OrderStatus {
    None("未分配", 1),
    Repair("维修中", 2),
    Finish("已完结 ", 3);

    OrderStatus() {
    }

    OrderStatus(String name, int value) {
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

    public static OrderStatus getEnum(String name){
        for (OrderStatus dl:
                OrderStatus.values()) {
            if (dl.getName().equals(name)) return dl;
        }
        return null;
    }

    public static OrderStatus getEnum(int value){
        for (OrderStatus dl:
                OrderStatus.values()) {
            if (dl.getValue() == value) return dl;
        }
        return null;
    }
}
