package com.bandweaver.tunnel.common.biz.constant;

/**资料类型枚举下一级菜单
 * @author sen.shao
 */

public enum DocTypeSonEnum {

    /**
     *移交清单
     */
    HANDOVER_LIST("移交清单", 1, 1),
    /**
     * 竣工报告
     */
    COMPLETION_REPORT("竣工报告", 2, 1),
    /**
     * 设备,主板检验报告,合格证,说明书
     */
    CERTIFICATE_INSTRUCTIONS_ETC("设备,主板检验报告,合格证,说明书", 3, 1),
    /**
     * 设备,主板检验报告,合格证,说明书
     */
    COMPLETION_PNG_ETC("竣工图,变更设计图纸,洽商记录", 4, 1);


    private String name;

    private int value;

    private int parentValue;

    DocTypeSonEnum(String name, int value, int parentValue) {
        this.name = name;
        this.value = value;
        this.parentValue = parentValue;
    }

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

    public int getParentValue() {
        return parentValue;
    }

    public void setParentValue(int parentValue) {
        this.parentValue = parentValue;
    }

    public static DocTypeSonEnum getEnum(int value) {
        for (DocTypeSonEnum e : DocTypeSonEnum.values()) {
            if (value == e.getValue()) {
                return e;
            }
        }
        return null;
    }
}
