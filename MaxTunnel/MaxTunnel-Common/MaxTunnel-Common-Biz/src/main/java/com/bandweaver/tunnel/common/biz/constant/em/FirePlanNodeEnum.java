package com.bandweaver.tunnel.common.biz.constant.em;



/**
 * 消防预案节点枚举
 * @author shaosen
 * @date 2018年7月30日
 */
public enum FirePlanNodeEnum {

	alarm("开启声光报警",1),
	camera("调用摄像头",2),
	confirm("值班人员确认",3),
	fan("打开风机",4),
	wind_valve("打开风阀",5),
	shutter("打开百叶",6),
	dry_powder("启动干粉灭火",7),
	inform_relatedunit("通知相关单位",8);
	
	
	private String name;
    private int value;
	
    
    FirePlanNodeEnum() {
    }

    FirePlanNodeEnum(String name, int value) {
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
    
	public static FirePlanNodeEnum getEnum(int value) {
		for (FirePlanNodeEnum statusEnum : FirePlanNodeEnum.values()) {
			if(value == statusEnum.getValue()) 
				return statusEnum;
		}
		return null;
	}
	public static FirePlanNodeEnum getEnum(String name) {
		for (FirePlanNodeEnum statusEnum : FirePlanNodeEnum.values()) {
			if(name.equals(statusEnum.getName())) 
				return statusEnum;
		}
		return null;
	}
    
}
