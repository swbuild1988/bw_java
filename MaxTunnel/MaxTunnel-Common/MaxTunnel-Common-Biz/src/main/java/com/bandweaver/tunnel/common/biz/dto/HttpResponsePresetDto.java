package com.bandweaver.tunnel.common.biz.dto;

/**预置位
 * @author shaosen
 * @date 2018年10月30日
 */
public class HttpResponsePresetDto {

	private String strName;
	
	private String strToken;

	public String getStrName() {
		return strName;
	}

	public void setStrName(String strName) {
		this.strName = strName;
	}

	public String getStrToken() {
		return strToken;
	}

	public void setStrToken(String strToken) {
		this.strToken = strToken;
	}

	@Override
	public String toString() {
		return "PresetDto [strName=" + strName + ", strToken=" + strToken + "]";
	}
	
	
	
}
