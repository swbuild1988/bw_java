package com.bandweaver.tunnel.common.biz.constant;


/**
 * 资料类型枚举
 *
 * @author shaosen
 * @date 2018年8月27日
 */
public enum DocTypeEnum {

    /**
     * 项目验收资料
     */
    PROJECT_FILE("项目验收资料", 1),
    /**
     * 与厂家直接来往函件合同
     */
    MANUFACTOR_FILE("与厂家直接来往函件合同", 2),
    /**
     * 与管理部门间来往函件
     */
    MANAGER_DEPARTMENT_FILE("与管理部门间来往函件", 3),
    /**
     * 管廊运维制度文件
     */
    TUNNEL_MAINTENANCE_FILE("管廊运维制度文件", 4),
    /**
     * 管廊相关法律法规
     */
    TUNNEL_REGULATION_FILE("管廊相关法律法规", 5),
    /**
     * 入廊单位来往函件,合同,图纸,行政文件
     */
    COMPANY_INFO_FILE("入廊单位来往函件,合同,图纸,行政文件", 6);

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

    DocTypeEnum(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public static DocTypeEnum getEnum(int value) {
        for (DocTypeEnum e : DocTypeEnum.values()) {
            if (value == e.getValue()) {
                return e;
            }
        }
        return null;
    }

}
