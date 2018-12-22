package com.bandweaver.tunnel.common.biz.constant;



/**资料类型枚举
 * @author shaosen
 * @date 2018年8月27日
 */
public enum DocTypeEnum {
	
	/**管廊本体资料*/
	TUNNEL("管廊本体资料",1),
	/**其他资料*/
	OTHER("其他资料",2);
	
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

	DocTypeEnum(String name, int value) {
		this.name = name;
		this.value = value;
	}
	
	public static DocTypeEnum getEnum(int value) {
		for(DocTypeEnum e : DocTypeEnum.values()) {
			if(value == e.getValue())
				return e;
		}
		return null;
	}

}
