package com.bandweaver.tunnel.common.biz.constant;

/**流程节点状态枚举
 * @author shaosen
 * @date 2018年11月14日
 */
public enum NodeStatusEnum {
	
	FINISHED("已完成",1),
	PROCESSING("进行中",2),
	WAITING("待完成",3);
	
	private String name;
	private int value;

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

	NodeStatusEnum(String name, int value) {
		this.name = name;
		this.value = value;
	}
	
	public static NodeStatusEnum getEnum(int value) {
		for(NodeStatusEnum e : NodeStatusEnum.values()) {
			if(value == e.getValue())
				return e;
		}
		return null;
	}

}
