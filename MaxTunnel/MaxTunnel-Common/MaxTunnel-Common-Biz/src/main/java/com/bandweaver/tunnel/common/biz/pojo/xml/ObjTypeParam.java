package com.bandweaver.tunnel.common.biz.pojo.xml;

/**
 * 监测类型参数
 */
public class ObjTypeParam {

    private static final long serialVersionUID = 1L;

    /**
     * 类型值
     */
    private int typeId;
    /**
     * 类型名称
     */
    private String typeName;
    /**
     * 最小值
     */
    private double min;
    /**
     * 最大值
     */
    private double max;
    /**
     * 测量范围最小值
     */
    private double normalMin;
    /**
     * 测量范围最大值
     */
    private double normalMax;

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getNormalMin() {
        return normalMin;
    }

    public void setNormalMin(double normalMin) {
        this.normalMin = normalMin;
    }

    public double getNormalMax() {
        return normalMax;
    }

    public void setNormalMax(double normalMax) {
        this.normalMax = normalMax;
    }
}
