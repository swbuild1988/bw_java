package com.bandweaver.tunnel.common.biz.constant;

import java.util.Objects;

/**
 * 操作权限枚举
 */
public enum ShiroOperateType {

    /**
     * 添加
     */
    ADD(1, "add", "添加"),
    /**
     * 删除
     */
    DELETE(2, "delete", "删除"),
    /**
     * 更新
     */
    UPDATE(3, "update", "更新"),
    /**
     * 列表
     */
    LIST(4, "list", "列表"),

    /**
     * 导出
     */
    EXPORT(5, "export", "导出"),
    /**
     * 重置密码
     */
    RESET(6, "reset", "重置密码"),
    /**
     * 文件上传
     */
    UPLOAD(7, "upload", "文件上传"),
    /**
     * 文件下载
     */
    DOWNLOAD(8, "download", "文件下载");

    private int key;
    private String value;
    private String name;


    ShiroOperateType(int key, String value, String name) {
        this.key = key;
        this.value = value;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }


    public static ShiroOperateType getEnum(int key) {
        ShiroOperateType[] values = ShiroOperateType.values();
        for (ShiroOperateType e : values) {
            if (e.getKey() == key) {
                return e;
            }
        }
        return null;
    }

    public static ShiroOperateType getEnum(String value) {
        ShiroOperateType[] values = ShiroOperateType.values();
        for (ShiroOperateType e : values) {
            if (Objects.equals(value,e.getValue())) {
                return e;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "ShiroOperateType{" +
                "key=" + key +
                ", value='" + value + '\'' +
                ", name='" + name + '\'' +
                '}';
    }


}
