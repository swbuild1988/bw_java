package com.bandweaver.tunnel.common.platform.log;


/**
 * ClassName: DescEnum
 * @Description: 操作日志描述枚举
 * @author shaosen
 * @date 2018年5月25日
 */
public enum DescEnum {


	/**登录账号*/
	LOGIN("00101","登录账号"),
	/**退出账号*/
	LOGOUT("00102","退出账号"),
	
	
	QUERY_USER("00201","查询用户"),
	ADD_USER("00202","添加用户"),
	DELETE_USER("00203","删除用户"),
	UPDATE_USER("00204","更新用户"),
	
	/**运维管理*/
	/**设备管理*/
	OMM_ADD_EQUIPMENT("00301","添加设备"),
	OMM_UPDATE_EQUIPMENT("00302","更新设备"),
	OMM_DELETE_EQUIPMENT("00303","删除设备"),
	
	/**运营管理*/
	/**客户管理*/
	OAM_ADD_CUSTOMER("00404","添加客户"),
	OAM_DELETE_CUSTOMER("00405","删除客户"),
	OAM_UPDATE_CUSTOMER("00406","修改客户"),
	
	
	/**文件管理*/
	FILE_UPLOAD("00501","文件上传"),
	FILE_DOWNLOAD("00502","文件下载"),
	FILE_DELETE("00503","文件删除");
	
	private String id;

	private String name;

	DescEnum(String id, String name) {
		this.id = id;
		this.name = name;
	}

	DescEnum() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



}
