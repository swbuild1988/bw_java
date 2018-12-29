package com.bandweaver.tunnel.common.biz.dto.mam.video;

import com.bandweaver.tunnel.common.biz.constant.mam.VideoVendor;
import com.bandweaver.tunnel.common.biz.constant.mam.VideoVendorVersion;
import com.bandweaver.tunnel.common.biz.pojo.mam.video.VideoServer;


public class VideoServerDto extends VideoServer {
	
	private String vendorName;
	
	private String vendorVersionName;

	public String getVendorName() {
		VideoVendor vendor = VideoVendor.getEnum(super.getVendor());
		return vendor.getName();
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getVendorVersionName() {
		return VideoVendorVersion.getEnum(super.getVendorVersion()).getName();
	}

	public void setVendorVersionName(String vendorVersionName) {
		this.vendorVersionName = vendorVersionName;
	}
	
	
}
