package com.bandweaver.tunnel.common.biz.dto.mam.h5;

import java.io.Serializable;

import javax.xml.transform.Source;

public class H5Obj implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Object http;
	private Object rtsp;
	private Object rtmp;
	private Object flv;
	private Object hls;
	private Object webrtc;
	private Object system;
	private Object cloud;
	private Object cluster;
	private Object user;
	private H5Source source;
	public Object getHttp() {
		return http;
	}
	public void setHttp(Object http) {
		this.http = http;
	}
	public Object getRtsp() {
		return rtsp;
	}
	public void setRtsp(Object rtsp) {
		this.rtsp = rtsp;
	}
	public Object getRtmp() {
		return rtmp;
	}
	public void setRtmp(Object rtmp) {
		this.rtmp = rtmp;
	}
	public Object getFlv() {
		return flv;
	}
	public void setFlv(Object flv) {
		this.flv = flv;
	}
	public Object getHls() {
		return hls;
	}
	public void setHls(Object hls) {
		this.hls = hls;
	}
	public Object getWebrtc() {
		return webrtc;
	}
	public void setWebrtc(Object webrtc) {
		this.webrtc = webrtc;
	}
	public Object getSystem() {
		return system;
	}
	public void setSystem(Object system) {
		this.system = system;
	}
	public Object getCloud() {
		return cloud;
	}
	public void setCloud(Object cloud) {
		this.cloud = cloud;
	}
	public Object getCluster() {
		return cluster;
	}
	public void setCluster(Object cluster) {
		this.cluster = cluster;
	}
	public Object getUser() {
		return user;
	}
	public void setUser(Object user) {
		this.user = user;
	}
	public H5Source getSource() {
		return source;
	}
	public void setSource(H5Source source) {
		this.source = source;
	}
	
	
	
	
	

}
