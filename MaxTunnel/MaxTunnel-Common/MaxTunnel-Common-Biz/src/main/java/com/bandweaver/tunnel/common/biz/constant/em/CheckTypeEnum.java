package com.bandweaver.tunnel.common.biz.constant.em;

import java.util.Objects;

/**
 * @author SHAOSEN
 */

public enum CheckTypeEnum {
    /**
     *检查风机是否打开
     */
    CHECK_FAN("检查风机是否打开", 1),
    /**
     *检查百叶是否打开
     */
    CHECK_LOUVER("检查百叶是否打开", 2),
    /**
     * 持续观察数值至正常
     */
    CHECK_VALUE("持续观察数值至正常", 3);


    CheckTypeEnum(String name, int value) {
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

    public static CheckTypeEnum getEnum(String name) {
        for (CheckTypeEnum anEnum : CheckTypeEnum.values()) {
            if (Objects.equals(name, anEnum.getName())) {
                return anEnum;
            }
        }
        return null;
    }

    public static CheckTypeEnum getEnum(int value) {
        for (CheckTypeEnum anEnum : CheckTypeEnum.values()) {
            if (anEnum.getValue() == value) {
                return anEnum;
            }
        }
        return null;
    }

}
