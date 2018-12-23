package com.bandweaver.tunnel.common.biz.constant;



/**MaxView回执码枚举
 * @author shaosen
 * @date 2018年11月15日
 */
public enum ErrorCodeEnum {
	
	NonExistUser("用户不存在",11000),
	PasswordWrong ("密码错误",11001),
	InactivatedUser ("用户未激活",11002),
	LockedUser ("账号被锁",11005),
    NoPermission("没有权限",11009);
	
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

	ErrorCodeEnum(String name, int value) {
		this.name = name;
		this.value = value;
	}
	
	public static ErrorCodeEnum getEnum(int value) {
		for(ErrorCodeEnum e : ErrorCodeEnum.values()) {
			if(value == e.getValue())
				return e;
		}
		return null;
	}

}
