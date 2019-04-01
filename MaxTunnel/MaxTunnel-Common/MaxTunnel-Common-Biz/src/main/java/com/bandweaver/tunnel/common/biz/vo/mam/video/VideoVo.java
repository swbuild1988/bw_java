package com.bandweaver.tunnel.common.biz.vo.mam.video;

import com.bandweaver.tunnel.common.biz.vo.BaseVo;

public class VideoVo extends BaseVo {

	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer tunnelId;
    private Integer storeId;
    private Integer areaId;
    private Integer sectionId;
    private String name;
    private Integer datatypeId;
    private Integer objtypeId;
    private Boolean actived;
    private String description;
    /** 经度 */
    private String longitude;
    /** 维度 */
    private String latitude;
    /** 高度 */
    private String height;
    /** 偏差 */
    private Double deviation;
    
    private Integer serverId;
    private Integer vendor;
    private String ip;
    private Integer port;
    private String username;
    private String password;
    private Integer channelNo;
    private Integer videoSceneId;

	public Integer getChannelNo() {
		return channelNo;
	}

	public void setChannelNo(Integer channelNo) {
		this.channelNo = channelNo;
	}

	public Integer getVideoSceneId() {
		return videoSceneId;
	}

	public void setVideoSceneId(Integer videoSceneId) {
		this.videoSceneId = videoSceneId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Integer getServerId() {
		return serverId;
	}

	public void setServerId(Integer serverId) {
		this.serverId = serverId;
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

	public Integer getTunnelId() {
		return tunnelId;
	}

	public void setTunnelId(Integer tunnelId) {
		this.tunnelId = tunnelId;
	}

	public Integer getStoreId() {
		return storeId;
	}

	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public Integer getSectionId() {
		return sectionId;
	}

	public void setSectionId(Integer sectionId) {
		this.sectionId = sectionId;
	}

	public Integer getDatatypeId() {
		return datatypeId;
	}

	public void setDatatypeId(Integer datatypeId) {
		this.datatypeId = datatypeId;
	}

	public Integer getObjtypeId() {
		return objtypeId;
	}

	public void setObjtypeId(Integer objtypeId) {
		this.objtypeId = objtypeId;
	}

	public Boolean getActived() {
		return actived;
	}

	public void setActived(Boolean actived) {
		this.actived = actived;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public Double getDeviation() {
		return deviation;
	}

	public void setDeviation(Double deviation) {
		this.deviation = deviation;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "VideoVo [id=" + id + ", tunnelId=" + tunnelId + ", storeId=" + storeId + ", areaId=" + areaId
				+ ", sectionId=" + sectionId + ", name=" + name + ", datatypeId=" + datatypeId + ", objtypeId="
				+ objtypeId + ", actived=" + actived + ", description=" + description + ", longitude=" + longitude
				+ ", latitude=" + latitude + ", height=" + height + ", deviation=" + deviation + ", serverId="
				+ serverId + ", vendor=" + vendor + ", ip=" + ip + ", port=" + port + ", username=" + username
				+ ", password=" + password + "]";
	}

}
