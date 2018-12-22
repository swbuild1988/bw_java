package com.bandweaver.tunnel.common.platform.exception;

import com.bandweaver.tunnel.common.platform.constant.StatusCodeEnum;

public class BandWeaverException extends RuntimeException {

	private String code;

	public BandWeaverException(String msg) {
		super(msg);
		this.code = "-1";
	}
	
	public BandWeaverException(StatusCodeEnum statusCodeEnum) {
		super(statusCodeEnum.getMsg());
		this.code = statusCodeEnum.getCode();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
