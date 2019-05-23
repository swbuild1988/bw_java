package com.bandweaver.tunnel.common.biz.constant.em;

/**
 * 应急行为枚举
 *
 * @author shaosen
 * @date 2018年10月9日
 */
public enum ActionEnum {

    /**
     * 自动联动控制
     */
    SWITCH("自动联动控制", 1),
    /**
     * 不需要结果
     */
    NONE("不需要结果", 2),
    /**
     * 需要结果
     */
    CHECK("需要结果", 3),
    /**
     * 手动联动控制
     */
    MANNUL_SWITCH("手动联动控制", 4);

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

    ActionEnum(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public static ActionEnum getEnum(int value) {
        for (ActionEnum actionEnum : ActionEnum.values()) {
            if (value == actionEnum.getValue()) {
                return actionEnum;
            }
        }
        return null;
    }

}
