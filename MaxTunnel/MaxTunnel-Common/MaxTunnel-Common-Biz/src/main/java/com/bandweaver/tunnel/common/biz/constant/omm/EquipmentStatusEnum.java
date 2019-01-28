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

	/**运行*/
	NORMAL("运行" , 1),
	/**故障*/
	BROKEN("故障" , 2),
	/** 停运 */
	STOP("停运" , 3),
	/**其他*/
	OTHER("其他" , 4);
	
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
