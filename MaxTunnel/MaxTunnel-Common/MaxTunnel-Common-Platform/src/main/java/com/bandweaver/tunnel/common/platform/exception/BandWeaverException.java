package com.bandweaver.tunnel.common.platform.exception;

import com.bandweaver.tunnel.common.platform.constant.StatusCodeEnum;

/**
 * ClassName: BandWeaverException
 * @Description: 自定义异常没有继承Exception
 *	 而是继承RuntimeException是有原因的
 * 	RuntimeException是继承Exception
 * 	但是Spring只对RuntimeException进行事务回滚，如果抛出的是Exception是不会回滚的
 * @author shaosen
 * @date 2018年5月16日
 */
public class BandWeaverException extends RuntimeException{

	private String code;

    
    public BandWeaverException(StatusCodeEnum statusCodeEnum) {
        super(statusCodeEnum.getMsg());
        this.code = statusCodeEnum.getCode();
    }
    
    public BandWeaverException(StatusCodeEnum statusCodeEnum,Throwable cause) {
    	super(statusCodeEnum.getMsg(),cause);
    	this.code = statusCodeEnum.getCode();
    }

    
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
	
	
}
