package com.bandweaver.tunnel.common.biz.pojo.xml;

import java.io.Serializable;
import java.util.List;

/**
 * 复杂类型转换
 */
public class ComplexObjectConvert implements Serializable {

    private static final long serialVersionUID = 1L;

    // 对象类型
    private int objectType;
    // 对象名称
    private String objectName;
    // 原始Id
    private int originalId;
    // 可被转换的量
    private List<ConvertType> convertTypes;
    // 故障对应的ids
    private List<Integer> faultIds;

    public int getObjectType() {
        return objectType;
    }

    public void setObjectType(int objectType) {
        this.objectType = objectType;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public int getOriginalId() {
        return originalId;
    }

    public void setOriginalId(int originalId) {
        this.originalId = originalId;
    }

    public List<ConvertType> getConvertTypes() {
        return convertTypes;
    }

    public void setConvertTypes(List<ConvertType> convertTypes) {
        this.convertTypes = convertTypes;
    }

    public List<Integer> getFaultIds() {
        return faultIds;
    }

    public void setFaultIds(List<Integer> faultIds) {
        this.faultIds = faultIds;
    }

    /**
     *
     * @param code
     * @param typeId
     * @return
     */
    public ConvertType getConvertType(String code, int typeId) {
        for (ConvertType type : this.convertTypes) {
            if (type.getCode().equals(code) && type.getType() == typeId) return type;
        }
        return null;
    }

    @Override
    public String toString() {
        return "ComplexObjectConvert{" +
                "objectType=" + objectType +
                ", objectName='" + objectName + '\'' +
                ", originalId=" + originalId +
                ", convertTypes=" + convertTypes +
                ", faultIds=" + faultIds +
                '}';
    }
}

