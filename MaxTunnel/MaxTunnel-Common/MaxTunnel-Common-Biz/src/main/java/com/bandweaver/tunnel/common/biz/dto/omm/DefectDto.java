package com.bandweaver.tunnel.common.biz.dto.omm;

import com.bandweaver.tunnel.common.biz.constant.omm.DefectLevel;
import com.bandweaver.tunnel.common.biz.constant.omm.DefectStatus;
import com.bandweaver.tunnel.common.biz.constant.omm.DefectType;
import com.bandweaver.tunnel.common.biz.dto.AreaDto;
import com.bandweaver.tunnel.common.biz.dto.StoreDto;
import com.bandweaver.tunnel.common.biz.pojo.omm.Defect;

import java.io.Serializable;
import java.util.Date;

public class DefectDto extends Defect implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -8464728669964512307L;
    private String tunnelName;
    private String typeName;
    private String objName;
    private String levelName;
    private String statusName;
    
    private AreaDto area;
    private StoreDto store;
    
    //本体缺陷所在位置的经纬度
    private String longitude;
    private String latitude;
    
    //预留字段，判断缺陷的设备类型
    private Integer equipmentType;

    public String getTunnelName() {
        return tunnelName;
    }

    public void setTunnelName(String tunnelName) {
        this.tunnelName = tunnelName;
    }

    public String getTypeName() {
    	DefectType type = DefectType.getEnum(Integer.valueOf(typeName));
        return type == null ? typeName : type.getName();
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getObjName() {
        return objName;
    }

    public void setObjName(String objName) {
        this.objName = objName;
    }

    public String getLevelName() {
    	DefectLevel level = DefectLevel.getEnum(Integer.valueOf(levelName));
        return level == null ? levelName : level.getName();
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getStatusName() {
    	DefectStatus status = DefectStatus.getEnum(Integer.valueOf(statusName));
        return status == null ? statusName : status.getName();
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public AreaDto getArea() {
		return area;
	}

	public void setArea(AreaDto area) {
		this.area = area;
	}

	public StoreDto getStore() {
		return store;
	}

	public void setStore(StoreDto store) {
		this.store = store;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public Integer getEquipmentType() {
		return equipmentType;
	}

	public void setEquipmentType(Integer equipmentType) {
		this.equipmentType = equipmentType;
	}

	@Override
    public String toString() {
        return "DefectDto{" +
                "tunnelName='" + tunnelName + '\'' +
                ", typeName='" + typeName + '\'' +
                ", objName='" + objName + '\'' +
                ", levelName='" + levelName + '\'' +
                ", statusName='" + statusName + '\'' +
                ", area='" + area + '\'' +
                ", store='" + store + '\'' +
                "} " + super.toString();
    }
}
