package com.bandweaver.tunnel.common.biz.constant;

import java.util.Objects;

/**
 * 操作资源领域
 */
public enum ShiroResourceType {
    /**
     *
     */
    TUNNEL(1, "tunnel", "管廊"),
    STORE(2, "store", "管舱"),
    STORETYPE(2, "storetype", "管舱类型"),
    AREA(3, "area", "区域"),
    SECTION(4, "section", "区段"),

    STAFF(5, "staff", "员工"),
    ROLE(6, "role", "角色"),
    PERMISSION(7, "permission", "权限"),

    COMPANY(8, "company", "公司"),
    DEPARTMENT(9, "department", "部门"),
    POSITION(10, "position", "职位"),
    FILE(11, "file", "文件"),
    RELATEDUNIT(12, "relatedunit", "相关单位"),
    REPORT(13, "report", "报表"),

    ALARM(14, "alarm", "告警"),
    MEASOBJ(15, "measobj", "监测对象"),
    SUBSTATIONS(16, "substations", "变电站"),
    SUBSYS(17, "subsys", "二级子系统"),
    VIDEO(18, "video", "视频"),
    CONTRACT(19, "contract", "合同"),
    CUSTOMER(20, "customer", "客户"),
    EQUIPMENT(21, "equipment", "设备"),
    INSPECTION(22, "inspection", "巡检"),
    SPARE(23, "spare", "备品"),
    SCHEDULEJOB(24, "schedulejob", "定时任务");


    private int key;
    private String value;
    private String name;


    ShiroResourceType(int key, String value, String name) {
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

    public static ShiroResourceType getEnum(int key) {
        ShiroResourceType[] values = ShiroResourceType.values();
        for (ShiroResourceType e : values) {
            if (e.getKey() == key) {
                return e;
            }
        }
        return null;
    }


    public static ShiroResourceType getEnum(String value) {
        ShiroResourceType[] values = ShiroResourceType.values();
        for (ShiroResourceType e : values) {
            if (Objects.equals(value, e.getValue())) {
                return e;
            }
        }
        return null;
    }


    @Override
    public String toString() {
        return "ShiroResourceType{" +
                "key=" + key +
                ", value='" + value + '\'' +
                ", name='" + name + '\'' +
                '}';
    }


}
