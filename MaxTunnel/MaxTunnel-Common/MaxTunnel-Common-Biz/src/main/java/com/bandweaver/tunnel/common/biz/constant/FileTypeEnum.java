package com.bandweaver.tunnel.common.biz.constant;



/**文件类型枚举
 * @author shaosen
 * @date 2018年8月27日
 */
public enum FileTypeEnum {
	
	DOCUMENT("文档",1,"path.file.document"),
	VIDEO("视频",2,"path.file.video"),
	PIC("图片",3,"path.file.pic");
	
	private String name;
	private int value;
	private String type;

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

	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
	private FileTypeEnum(String name, int value, String type) {
		this.name = name;
		this.value = value;
		this.type = type;
	}

	public static FileTypeEnum getEnum(int value) {
		for(FileTypeEnum e : FileTypeEnum.values()) {
			if(value == e.getValue())
				return e;
		}
		return null;
	}

}
