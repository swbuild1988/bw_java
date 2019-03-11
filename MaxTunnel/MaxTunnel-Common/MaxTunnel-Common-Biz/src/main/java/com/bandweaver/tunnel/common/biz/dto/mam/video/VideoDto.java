package com.bandweaver.tunnel.common.biz.dto.mam.video;

import com.bandweaver.tunnel.common.biz.constant.mam.VideoVendor;
import com.bandweaver.tunnel.common.biz.constant.mam.VideoVendorVersion;
import com.bandweaver.tunnel.common.biz.pojo.mam.video.Video;
import com.bandweaver.tunnel.common.biz.pojo.mam.video.VideoPreset;
import com.bandweaver.tunnel.common.biz.pojo.mam.video.VideoScene;
import com.bandweaver.tunnel.common.biz.pojo.mam.video.VideoServer;

import java.util.List;

public class VideoDto extends Video {

    private VideoServerDto videoServerDto;
    private boolean ptzOperationsSupported;
    private List<VideoPreset> videoPresets;
    
    private String tunnelName;
    private String storeName;
    private String areaName;
    private String sceneName;
    
    private String vendorName;
    
    public String getSceneName() {
		return sceneName;
	}

	public void setSceneName(String sceneName) {
		this.sceneName = sceneName;
	}

    public String getVendorName() {
    	String result = this.getVendor() == null ? null : VideoVendor.getEnum(this.getVendor()).getName();
		return result;
	}

	public String getTunnelName() {
		return tunnelName;
	}

	public void setTunnelName(String tunnelName) {
		this.tunnelName = tunnelName;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public VideoServerDto getVideoServerDto() {
        return videoServerDto;
    }

    public void setVideoServerDto(VideoServerDto videoServerDto) {
        this.videoServerDto = videoServerDto;
    }

    public boolean isPtzOperationsSupported() {
        return ptzOperationsSupported;
    }

    public void setPtzOperationsSupported(boolean ptzOperationsSupported) {
        this.ptzOperationsSupported = ptzOperationsSupported;
    }

    public List<VideoPreset> getVideoPresets() {
        return videoPresets;
    }

    public void setVideoPresets(List<VideoPreset> videoPresets) {
        this.videoPresets = videoPresets;
    }

    public String getUrl() {
        String url = "";
        VideoVendor vendor = VideoVendor.getEnum(this.videoServerDto.getVendor());

        switch (vendor) {
            case DaKang:
                url = "rtsp://" + this.videoServerDto.getUsername() + ":" + this.videoServerDto.getPassword() +
                        "@" + this.videoServerDto.getIp() + ":" + this.videoServerDto.getPort() +
                        "/Streaming/Channels/" + super.getChannelNo();
                break;

            case HoneyWell_HISD:
                url = "rtsp://" + this.videoServerDto.getUsername() + ":" + this.videoServerDto.getPassword() +
                        "@" + this.videoServerDto.getIp() + ":" + this.videoServerDto.getPort() +
                        "/h264/ch" + super.getChannelNo() + "/main/av_stream";
                break;

            case HoneyWell_HICC:
                url = "rtsp://" + this.videoServerDto.getUsername() + ":" + this.videoServerDto.getPassword() +
                        "@" + this.videoServerDto.getIp() + ":" + this.videoServerDto.getPort() +
                        "/media?stream=0";
                break;

            case H5STREAM:
                url = this.videoServerDto.getIp() + ":" + this.videoServerDto.getPort();
                break;

            default:
                break;
        }
        return url;
    }
    
    public String getStrUrl() {
    	String url = "";
    	VideoVendor vendor = VideoVendor.getEnum(this.videoServerDto.getVendor());
    	
    	switch (vendor) {
    	case DaKang:
    		url = "rtsp://" + this.videoServerDto.getIp() + ":" + this.videoServerDto.getPort() +
    		"/Streaming/Channels/" + super.getChannelNo();
    		break;
    		
    	case HoneyWell_HISD:
    		url = "rtsp://" + this.videoServerDto.getIp() + ":" + this.videoServerDto.getPort() +
    		"/h264/ch" + super.getChannelNo() + "/main/av_stream";
    		break;
    		
    	case HoneyWell_HICC:
    		url = "rtsp://" + this.videoServerDto.getIp() + ":" + this.videoServerDto.getPort() +
    		"/media?stream=0";
    		break;
	
    	default:
    		break;
    	}
    	return url;
    }

	@Override
	public String toString() {
		return "VideoDto [videoServerDto=" + videoServerDto
				+ ", ptzOperationsSupported=" + ptzOperationsSupported
				+ ", videoPresets=" + videoPresets + ", tunnelName="
				+ tunnelName + ", storeName=" + storeName
				+ ", areaName=" + areaName + super.toString() +  "]";
	}

    
}
