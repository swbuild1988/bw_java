package com.bandweaver.tunnel.common.biz.constant.omm;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: EquipmentStatusEnum
 * @Description: 设备状态枚举
 * @author shaosen
 * @date 2018年5月30日
 */
public enum EquipmentStatusEnum {

	/**正常*/
	NORMAL("正常" , 1),
	/**损坏*/
	BROKEN("损坏" , 2);
	/** 报废 */
	//SCRAP("报废" , 3);
	
	private String name;
    private int value;
	
    
    EquipmentStatusEnum() {
    }

    EquipmentStatusEnum(String name, int value) {
        this.name = name;
        this.value = value;
    }

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
    
	public static EquipmentStatusEnum getEnum(int value) {
        for(EquipmentStatusEnum e : EquipmentStatusEnum.values()) {
        	if(e.getValue() == value) return e;
        }
        return null;
    }

	public static EquipmentStatusEnum getEnum(String name) {
        for(EquipmentStatusEnum e : EquipmentStatusEnum.values()) {
        	if(e.getName().equals(name)) return e;
        }
        return null;
    }
}
