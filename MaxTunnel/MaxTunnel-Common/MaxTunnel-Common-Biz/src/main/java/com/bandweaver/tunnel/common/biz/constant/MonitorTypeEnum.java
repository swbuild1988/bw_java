package com.bandweaver.tunnel.common.biz.constant;

/**
 * 管廊本地监测内容枚举
 *
 * @author shaosen
 * @date 2018年8月16日
 */
public enum MonitorTypeEnum {
    /**
     * 环境监测
     */
    ENVIRONMENTAL("环境监测", 1),
    /**
     * 结构
     */
    STRUCTURE("结构监测", 2),
    /**
     * 安防
     */
    SECURITY("安防监测", 3),
    /**
     * 机电
     */
    ELECTROMECHANICAL("机电监测", 4),
    /**
     * 消防
     */
    FIRE_CONTROL("消防监测", 5),
    /**
     * 其他
     */
    OTHERS("其他监测", 6);
    // 机电 ： 风机，水泵，百叶
    // 消防 ： 烟感(ai)，温感(ai)，手报（di）

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

    MonitorTypeEnum(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public static MonitorTypeEnum getEnum(int value) {
        for (MonitorTypeEnum actionEnum : MonitorTypeEnum.values()) {
            if (value == actionEnum.getValue()) {
                return actionEnum;
            }
        }
        return null;
    }

}
