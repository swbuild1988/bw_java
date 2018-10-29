package com.bandweaver.tunnel.common.biz.constant.em;



/**
 * 通风预案节点枚举
 * @author ya.liu
 * @date 2018年9月23日
 */
public enum WindPlanNodeEnum {

	alarm("开启声光报警",1),
	camera("调用摄像头",2),
	confirm("值班人员确认",3),
	fan("打开风机",4),
	shutter("打开百叶",5),
	inform_relatedunit("通知相关单位",6);
	
	
	private String name;
    private int value;
	
    
    WindPlanNodeEnum() {
    }

    WindPlanNodeEnum(String name, int value) {
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
    
	public static WindPlanNodeEnum getEnum(int value) {
		for (WindPlanNodeEnum statusEnum : WindPlanNodeEnum.values()) {
			if(value == statusEnum.getValue()) 
				return statusEnum;
		}
		return null;
	}
	public static WindPlanNodeEnum getEnum(String name) {
		for (WindPlanNodeEnum statusEnum : WindPlanNodeEnum.values()) {
			if(name.equals(statusEnum.getName())) 
				return statusEnum;
		}
		return null;
	}
    
}
