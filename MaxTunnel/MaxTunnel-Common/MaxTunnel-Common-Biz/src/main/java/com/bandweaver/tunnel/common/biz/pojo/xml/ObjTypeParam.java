package com.bandweaver.tunnel.common.biz.pojo.xml;

import java.io.Serializable;

/**
 * 监测类型参数
 */
public class ObjTypeParam implements Serializable {

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
     * 显示类型
     * 0： 正常显示
     * 1： 正常范围内随机
     * 2： 显示showValue
     */
    private int showType;
    /**
     * 当shwoType为2时，显示的值
     */
    private double showValue;
    /**
     * 测量范围最小值
     */
    private double measMin;
    /**
     * 测量范围最大值
     */
    private double measMax;
    /**
     * 正常最小值
     */
    private double normalMin;
    /**
     * 正常最大值
     */
    private double normalMax;
    /**
     * 警告最小值
     */
    private double warmingMin;
    /**
     * 警告最大值
     */
    private double warmingMax;
    /**
     * 错误最大值
     */
    private double errorMin;
    /**
     * 错误最小值
     */
    private double errorMax;

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

    public int getShowType() {
        return showType;
    }

    public void setShowType(int showType) {
        this.showType = showType;
    }

    public double getShowValue() {
        return showValue;
    }

    public void setShowValue(double showValue) {
        this.showValue = showValue;
    }

    public double getMeasMin() {
        return measMin;
    }

    public void setMeasMin(double measMin) {
        this.measMin = measMin;
    }

    public double getMeasMax() {
        return measMax;
    }

    public void setMeasMax(double measMax) {
        this.measMax = measMax;
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

    public double getWarmingMin() {
        return warmingMin;
    }

    public void setWarmingMin(double warmingMin) {
        this.warmingMin = warmingMin;
    }

    public double getWarmingMax() {
        return warmingMax;
    }

    public void setWarmingMax(double warmingMax) {
        this.warmingMax = warmingMax;
    }

    public double getErrorMin() {
        return errorMin;
    }

    public void setErrorMin(double errorMin) {
        this.errorMin = errorMin;
    }

    public double getErrorMax() {
        return errorMax;
    }

    public void setErrorMax(double errorMax) {
        this.errorMax = errorMax;
    }
}
