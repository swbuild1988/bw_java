package com.bandweaver.tunnel.common.biz.pojo.xml;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.bandweaver.tunnel.common.biz.constant.mam.ObjectType;
import com.bandweaver.tunnel.common.biz.pojo.em.RelatedUnit;

@XmlRootElement(name = "Config")
public class Config implements Serializable {

    private static final long serialVersionUID = 1L;
    // 批量导入监测对象所需管廊参数
    private TunnelParam tunnelParam;
    // 批量导入监测对象所需设备参数
    private List<EquipmentTypeFile> typeFiles;
    // 大屏地图各点位置
    private List<RelatedUnit> units;
    // 要展示的对象类型
    private List<Integer> objectTypeIds;
    // 需转换的复杂类型
    private List<ComplexObjectConvert> complexObjectConverts;
    /**
     * 对象类型参数
     */
    private List<ObjTypeParam> objTypeParams;
    // AI数据获取小数点位数
    private Integer decimal;
    /**
     * 管廊内灯光的距离
     */
    private double lightDistance;
    // 告警时间
    private List<CommonEnum> alarmTimes;
    // 入侵延时关闭时间,单位秒
    private double closeTime;

    public boolean init() {
        // 判断初始化有没有修改
        boolean f = false;

        if (this.tunnelParam == null) {
            this.tunnelParam = new TunnelParam(0, 0, 0, 0, 0, 0, 0, 0);
            f = true;
        }

        if (this.units == null || this.units.size() < 1) {
            List<RelatedUnit> units = new ArrayList<>();
            units.add(new RelatedUnit(1, "112", "37"));
            this.units = units;
            f = true;
        }
        if (this.typeFiles == null || this.typeFiles.size() < 1) {
            List<EquipmentTypeFile> typeFiles = new ArrayList<>();
            typeFiles.add(new EquipmentTypeFile("01", "设备", 0));
            this.typeFiles = typeFiles;
            f = true;
        }
        if (this.objectTypeIds == null || this.objectTypeIds.size() < 1) {
            List<Integer> objectTypeIds = new ArrayList<>();
            objectTypeIds.add(0);
            this.objectTypeIds = objectTypeIds;
            f = true;
        }

        if (this.complexObjectConverts == null || this.complexObjectConverts.size() == 0) {
            this.complexObjectConverts = new ArrayList<>();

            ComplexObjectConvert tmp = new ComplexObjectConvert();
            tmp.setObjectType(56);
            tmp.setObjectName("电子井盖");
            tmp.setOriginalId(0);

            List<ConvertType> convertTypes = new ArrayList<>();
            // 开光量输入的 关
            ConvertType tmp2 = new ConvertType();
            tmp2.setCode("close");
            tmp2.setConvertId(1);
            tmp2.setDescribe("关");
            tmp2.setType(0);
            tmp2.setValue(0);
            convertTypes.add(tmp2);
            // 开光量输出的 关
            ConvertType tmp3 = new ConvertType();
            tmp3.setCode("close");
            tmp3.setConvertId(5);
            tmp3.setDescribe("关");
            tmp3.setType(1);
            tmp3.setValue(0);
            convertTypes.add(tmp3);

            tmp.setConvertTypes(convertTypes);

            this.complexObjectConverts.add(tmp);
            f = true;
        }
        return f;
    }

    public TunnelParam getTunnelParam() {
        return tunnelParam;
    }

    public void setTunnelParam(TunnelParam tunnelParam) {
        this.tunnelParam = tunnelParam;
    }

    public List<EquipmentTypeFile> getTypeFiles() {
        return typeFiles;
    }

    public void setTypeFiles(List<EquipmentTypeFile> typeFiles) {
        this.typeFiles = typeFiles;
    }

    public List<RelatedUnit> getUnits() {
        return units;
    }

    public void setUnits(List<RelatedUnit> units) {
        this.units = units;
    }

    public List<Integer> getObjectTypeIds() {
        return objectTypeIds;
    }

    public void setObjectTypeIds(List<Integer> objectTypeIds) {
        this.objectTypeIds = objectTypeIds;
    }

    public double getLightDistance() {
        return lightDistance;
    }

    public void setLightDistance(double lightDistance) {
        this.lightDistance = lightDistance;
    }

    public List<CommonEnum> getAlarmTimes() {
        return alarmTimes;
    }

    public void setAlarmTimes(List<CommonEnum> alarmTimes) {
        this.alarmTimes = alarmTimes;
    }

    public double getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(double closeTime) {
        this.closeTime = closeTime;
    }

    public List<ComplexObjectConvert> getComplexObjectConverts() {
        return complexObjectConverts;
    }

    public void setComplexObjectConverts(List<ComplexObjectConvert> complexObjectConverts) {
        this.complexObjectConverts = complexObjectConverts;
    }

    public List<ObjTypeParam> getObjTypeParams() {
        return objTypeParams;
    }

    public void setObjTypeParams(List<ObjTypeParam> objTypeParams) {
        this.objTypeParams = objTypeParams;
    }

    public Integer getDecimal() {
        return decimal;
    }

    public void setDecimal(Integer decimal) {
        this.decimal = decimal;
    }

    public ObjTypeParam getObjTypeParam(int objectTypeId) {
        for (ObjTypeParam objTypeParam : objTypeParams) {
            if (objectTypeId == objTypeParam.getTypeId()) {
                return objTypeParam;
            }
        }
        return null;
    }

    public ObjTypeParam getObjTypeParam(ObjectType objectType) {
        if (objectType == null || objectType == ObjectType.NONE) return null;

        for (ObjTypeParam objTypeParam : objTypeParams) {
            if (objectType.getValue() == objTypeParam.getTypeId()) {
                return objTypeParam;
            }
        }
        return null;
    }
}
