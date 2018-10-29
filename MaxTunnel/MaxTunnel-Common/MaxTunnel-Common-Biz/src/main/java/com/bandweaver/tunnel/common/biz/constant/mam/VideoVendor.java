package com.bandweaver.tunnel.common.biz.constant.mam;

import java.util.ArrayList;
import java.util.List;

public enum VideoVendor {
    None("无", 0),
    DaKang("大康", 1),
    WeiShi("威视", 2),
    HoneyWell_HISD("霍尼韦尔-球形", 3),
    HoneyWell_HICC("霍尼韦尔-枪形", 4),
    H5STREAM("H5Stream", 5);

    VideoVendor() {
    }

    VideoVendor(String name, int value) {
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


    public static VideoVendor getEnum(String name){
        for (VideoVendor dl: VideoVendor.values()) {
            if (dl.getName().equals(name)) return dl;
        }
        return null;
    }

    public static VideoVendor getEnum(int value){
        for (VideoVendor dl: VideoVendor.values()) {
            if (dl.getValue() == value) return dl;
        }
        return None;
    }
}
