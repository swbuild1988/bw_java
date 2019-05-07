package com.bandweaver.tunnel.common.biz.constant;

public enum BankEnum {

	/** 工商 */
	ICBC("中国工商银行", 1),
	/** 农业 */
	ABC("中国农业银行", 2),
	/** 建行 */
	CCB("中国建设银行", 3);
	
	private String name;
	private Integer value;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	BankEnum(String name, Integer value) {
		this.name = name;
		this.value = value;
	}
	
	
	public static BankEnum getEnum(Integer value) {
		for(BankEnum outsideEnum : BankEnum.values()) {
			if(value == outsideEnum.getValue())
				return outsideEnum;
		}
		return null;
	}
}
