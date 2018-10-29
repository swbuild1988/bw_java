package com.bandweaver.tunnel.common.biz.constant.mam;

public enum VideoVendorVersion {
    None("无", 0),
    VDS("VDS", 1),
    HDS("HDS", 2);

    VideoVendorVersion() {
    }

    VideoVendorVersion(String name, int value) {
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

    public static VideoVendorVersion getEnum(String name){
        for (VideoVendorVersion dl: VideoVendorVersion.values()) {
            if (dl.getName().equals(name)) return dl;
        }
        return null;
    }

    public static VideoVendorVersion getEnum(int value){
        for (VideoVendorVersion dl: VideoVendorVersion.values()) {
            if (dl.getValue() == value) return dl;
        }
        return None;
    }
}
