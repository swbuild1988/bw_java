package com.bandweaver.tunnel.common.biz.pojo.xml;

public class ConvertType {
    // 转换后的id
    private int convertId;
    // 转换的类型（0：代表获得，DI；1:代表输出，DO；2：代表模拟量输入）
    private int type;
    // 被转换的描述（开，关，故障，复位等等）
    private String describe;
    // 英文（open，close等）
    private String code;
    // 对应点位的值
    private int value;

    public int getConvertId() {
        return convertId;
    }

    public void setConvertId(int convertId) {
        this.convertId = convertId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ConvertType{" +
                "convertId=" + convertId +
                ", type=" + type +
                ", describe='" + describe + '\'' +
                ", code='" + code + '\'' +
                ", value=" + value +
                '}';
    }
}