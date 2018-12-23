package com.bandweaver.tunnel.common.biz.constant.omm;

public enum DefectStatus {
    None("未处理", 1),
    Repair("维修中", 2),
    Finish("维修完成", 3);

    DefectStatus() {
    }

    DefectStatus(String name, int value) {
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

    public static DefectStatus getEnum(String name){
        for (DefectStatus dl:
                DefectStatus.values()) {
            if (dl.getName().equals(name)) return dl;
        }
        return null;
    }

    public static DefectStatus getEnum(int value){
        for (DefectStatus dl:
                DefectStatus.values()) {
            if (dl.getValue() == value) return dl;
        }
        return null;
    }
}
