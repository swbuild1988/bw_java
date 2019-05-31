package com.bandweaver.tunnel.common.biz.pojo.mam.video;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObj;

public class Video extends MeasObj {
	
	private static final long serialVersionUID = 1L;

    public static Video fromMeasObj(MeasObj obj){
        Video tmp = new Video();
        String str = JSONObject.toJSONString(obj);
        tmp = (Video)JSONObject.parseObject(str, Video.class);
        return tmp;
    }

    private Integer serverId;
    private Integer videoSceneId;
    private Integer videoExtendSceneId;
    private int channelNo;
    
    private Integer vendor;
    private String ip;
    private Integer port;
    private String username;
    private String password;

    public Integer getServerId() {
        return serverId;
    }

    public void setServerId(Integer serverId) {
        this.serverId = serverId;
    }

    public Integer getVideoSceneId() {
        return videoSceneId;
    }

    public void setVideoSceneId(Integer videoSceneId) {
        this.videoSceneId = videoSceneId;
    }

    public Integer getVideoExtendSceneId() {
        return videoExtendSceneId;
    }

    public void setVideoExtendSceneId(Integer videoExtendSceneId) {
        this.videoExtendSceneId = videoExtendSceneId;
    }

    public int getChannelNo() {
        return channelNo;
    }

    public void setChannelNo(int channelNo) {
        this.channelNo = channelNo;
    }

	public Integer getVendor() {
		return vendor;
	}

	public void setVendor(Integer vendor) {
		this.vendor = vendor;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

    @Override
    public String toString() {
        return "Video{" +
                "serverId=" + serverId +
                ", videoSceneId=" + videoSceneId +
                ", videoExtendSceneId=" + videoExtendSceneId +
                ", channelNo=" + channelNo +
                ", vendor=" + vendor +
                ", ip='" + ip + '\'' +
                ", port=" + port +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                "} " + super.toString();
    }


}
