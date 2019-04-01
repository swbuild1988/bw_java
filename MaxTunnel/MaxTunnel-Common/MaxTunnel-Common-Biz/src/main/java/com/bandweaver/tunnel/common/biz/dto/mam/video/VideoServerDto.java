package com.bandweaver.tunnel.common.biz.dto.mam.video;

import com.bandweaver.tunnel.common.biz.constant.mam.VideoVendor;
import com.bandweaver.tunnel.common.biz.constant.mam.VideoVendorVersion;
import com.bandweaver.tunnel.common.biz.pojo.mam.video.VideoServer;


public class VideoServerDto extends VideoServer {
	
	private static final long serialVersionUID = 1L;
	
	private String vendorName;
	
	private String vendorVersionName;

	public String getVendorName() {
		if(super.getVendor() == null) return null;
		VideoVendor vendor = VideoVendor.getEnum(super.getVendor());
		return vendor == null ? null : vendor.getName();
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getVendorVersionName() {
		if(super.getVendorVersion() == null) return null;
		VideoVendorVersion e = VideoVendorVersion.getEnum(super.getVendorVersion());
		return e == null ? null : e.getName();
	}

	public void setVendorVersionName(String vendorVersionName) {
		this.vendorVersionName = vendorVersionName;
	}
	
	
}
