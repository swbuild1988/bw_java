package com.bandweaver.tunnel.common.biz.constant;



/**管廊状态枚举
 * @author sen.shao
 *
 */
public enum TunnelStatus {

	RUNNING("运行中",1),
	BUILDING("建设中",2),
	PLANNING("规划中",3);
	
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

	TunnelStatus(String name, int value) {
		this.name = name;
		this.value = value;
	}
	
	public static TunnelStatus getEnum(int value) {
		for(TunnelStatus e : TunnelStatus.values()) {
			if(value == e.getValue())
				return e;
		}
		return null;
	}
	
	

}
