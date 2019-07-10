package com.bandweaver.tunnel.common.biz.pojo.xml;

import java.util.List;

/**
 * 复杂类型转换
 */
public class ComplexObjectConvert {
    private static final long serialVersionUID = 1L;

    // 对象类型
    private int objectType;
    // 对象名称
    private String objectName;
    // 原始Id
    private int originalId;
//    // 原始值起点
//    private int startPositionOfOriginalValue;
//    // 原始值长度
//    private int lenghtOfOriginalValue;
    // 可被转换的量
    private List<ConvertType> convertTypes;

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

//    public int getStartPositionOfOriginalValue() {
//        return startPositionOfOriginalValue;
//    }
//
//    public void setStartPositionOfOriginalValue(int startPositionOfOriginalValue) {
//        this.startPositionOfOriginalValue = startPositionOfOriginalValue;
//    }
//
//    public int getLenghtOfOriginalValue() {
//        return lenghtOfOriginalValue;
//    }
//
//    public void setLenghtOfOriginalValue(int lenghtOfOriginalValue) {
//        this.lenghtOfOriginalValue = lenghtOfOriginalValue;
//    }

    public List<ConvertType> getConvertTypes() {
        return convertTypes;
    }

    public void setConvertTypes(List<ConvertType> convertTypes) {
        this.convertTypes = convertTypes;
    }

    public ConvertType getConvertType(String code){
        for (ConvertType type : this.convertTypes             ) {
            if (type.getCode().equals(code)) return type;
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
                '}';
    }
}

