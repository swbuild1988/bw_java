package com.bandweaver.tunnel.common.biz.dto.mam.h5;

import java.io.Serializable;

public class H5Src implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String strNameComment;
	private String strName;
	private String strTokenComment;
	private String strToken;
	private String nTypeComment;
	private String nType;
	private String strUrlComment;
	private String strUrl;
	private String strUserComment;
	private String strUser;
	private String strPasswdComment;
	private String strPasswd;
	private String bPasswdEncryptComment;
	private boolean bPasswdEncrypt;
	private String bEnableAudioComment;
	private boolean bEnableAudio;
	private String nConnectTypeComment;
	private String nConnectType;
	private String nRTSPTypeComment;
	private String nRTSPType;
	private String strSrcIpAddressComment;
	private String strSrcIpAddress;
	private String strSrcPortComment;
	private String strSrcPort;
	private String nChannelNumberComment;
	private int nChannelNumber;
	private String bOnvifProfileAutoComment;
	private boolean bOnvifProfileAuto;
	private String strOnvifAddrComment;
	private String strOnvifAddr;
	private String strOnvifProfileMainComment;
	private String strOnvifProfileMain;
	private String strOnvifProfileSubComment;
	private String strOnvifProfileSub;
	
	
	
	public H5Src() {
		super();
		
		//初始化时给这些字段赋值
		this.strNameComment = "name for this stream";
		this.strTokenComment = "token for this stream, must unique, if same, only first will be available";
		this.nTypeComment = "source type H5_FILE/H5_STREAM/H5_ONVIF";
		this.strUrlComment = "url(RTSP/RTMP...) or file path";
		this.strUserComment = "username";
		this.strPasswdComment = "password";
		this.bPasswdEncryptComment = "Password Encrypted";
		this.bPasswdEncrypt = false;
		this.bEnableAudioComment = "Enable Audio";
		this.bEnableAudio = false;
		this.nConnectTypeComment =  "H5_ONDEMAND/H5_ALWAYS/H5_AUTO";
		this.nConnectType = "H5_AUTO";
		this.nRTSPTypeComment =  "RTSP Connect protocol H5_RTSP_TCP/H5_RTSP_UDP/H5_RTSP_HTTP/H5_RTSP_HTTPS/H5_RTSP_AUTO";
		this.nRTSPType = "H5_RTSP_AUTO";
		this.strSrcIpAddressComment =  "Ip Address for the device";
		this.strSrcPortComment =  "Port for the device";
		this.strSrcPort = "80";
		this.nChannelNumberComment ="Channel number (1-512)";
		this.nChannelNumber = 1;
		this.bOnvifProfileAutoComment = "ONVIF Auto select the video profile";
		this.bOnvifProfileAuto = true;
		this.strOnvifAddrComment = "ONVIF address (/onvif/device_service)";
		this.strOnvifAddr = "/onvif/device_service";
		this.strOnvifProfileMainComment = "ONVIF Main stream profile name";
		this.strOnvifProfileMain = "Profile_1";
		this.strOnvifProfileSubComment =  "ONVIF Sub stream profile name";
		this.strOnvifProfileSub = "Profile_2";
	}



	public String getStrNameComment() {
		return strNameComment;
	}



	public void setStrNameComment(String strNameComment) {
		this.strNameComment = strNameComment;
	}



	public String getStrName() {
		return strName;
	}



	public void setStrName(String strName) {
		this.strName = strName;
	}



	public String getStrTokenComment() {
		return strTokenComment;
	}



	public void setStrTokenComment(String strTokenComment) {
		this.strTokenComment = strTokenComment;
	}



	public String getStrToken() {
		return strToken;
	}



	public void setStrToken(String strToken) {
		this.strToken = strToken;
	}



	public String getnTypeComment() {
		return nTypeComment;
	}



	public void setnTypeComment(String nTypeComment) {
		this.nTypeComment = nTypeComment;
	}



	public String getnType() {
		return nType;
	}



	public void setnType(String nType) {
		this.nType = nType;
	}



	public String getStrUrlComment() {
		return strUrlComment;
	}



	public void setStrUrlComment(String strUrlComment) {
		this.strUrlComment = strUrlComment;
	}



	public String getStrUrl() {
		return strUrl;
	}



	public void setStrUrl(String strUrl) {
		this.strUrl = strUrl;
	}



	public String getStrUserComment() {
		return strUserComment;
	}



	public void setStrUserComment(String strUserComment) {
		this.strUserComment = strUserComment;
	}



	public String getStrUser() {
		return strUser;
	}



	public void setStrUser(String strUser) {
		this.strUser = strUser;
	}



	public String getStrPasswdComment() {
		return strPasswdComment;
	}



	public void setStrPasswdComment(String strPasswdComment) {
		this.strPasswdComment = strPasswdComment;
	}



	public String getStrPasswd() {
		return strPasswd;
	}



	public void setStrPasswd(String strPasswd) {
		this.strPasswd = strPasswd;
	}



	public String getbPasswdEncryptComment() {
		return bPasswdEncryptComment;
	}



	public void setbPasswdEncryptComment(String bPasswdEncryptComment) {
		this.bPasswdEncryptComment = bPasswdEncryptComment;
	}



	public boolean getbPasswdEncrypt() {
		return bPasswdEncrypt;
	}



	public void setbPasswdEncrypt(boolean bPasswdEncrypt) {
		this.bPasswdEncrypt = bPasswdEncrypt;
	}



	public String getbEnableAudioComment() {
		return bEnableAudioComment;
	}



	public void setbEnableAudioComment(String bEnableAudioComment) {
		this.bEnableAudioComment = bEnableAudioComment;
	}



	public boolean getbEnableAudio() {
		return bEnableAudio;
	}



	public void setbEnableAudio(boolean bEnableAudio) {
		this.bEnableAudio = bEnableAudio;
	}



	public String getnConnectTypeComment() {
		return nConnectTypeComment;
	}



	public void setnConnectTypeComment(String nConnectTypeComment) {
		this.nConnectTypeComment = nConnectTypeComment;
	}



	public String getnConnectType() {
		return nConnectType;
	}



	public void setnConnectType(String nConnectType) {
		this.nConnectType = nConnectType;
	}



	public String getnRTSPTypeComment() {
		return nRTSPTypeComment;
	}



	public void setnRTSPTypeComment(String nRTSPTypeComment) {
		this.nRTSPTypeComment = nRTSPTypeComment;
	}



	public String getnRTSPType() {
		return nRTSPType;
	}



	public void setnRTSPType(String nRTSPType) {
		this.nRTSPType = nRTSPType;
	}



	public String getStrSrcIpAddressComment() {
		return strSrcIpAddressComment;
	}



	public void setStrSrcIpAddressComment(String strSrcIpAddressComment) {
		this.strSrcIpAddressComment = strSrcIpAddressComment;
	}



	public String getStrSrcIpAddress() {
		return strSrcIpAddress;
	}



	public void setStrSrcIpAddress(String strSrcIpAddress) {
		this.strSrcIpAddress = strSrcIpAddress;
	}



	public String getStrSrcPortComment() {
		return strSrcPortComment;
	}



	public void setStrSrcPortComment(String strSrcPortComment) {
		this.strSrcPortComment = strSrcPortComment;
	}



	public String getStrSrcPort() {
		return strSrcPort;
	}



	public void setStrSrcPort(String strSrcPort) {
		this.strSrcPort = strSrcPort;
	}



	public String getnChannelNumberComment() {
		return nChannelNumberComment;
	}



	public void setnChannelNumberComment(String nChannelNumberComment) {
		this.nChannelNumberComment = nChannelNumberComment;
	}



	public int getnChannelNumber() {
		return nChannelNumber;
	}



	public void setnChannelNumber(int nChannelNumber) {
		this.nChannelNumber = nChannelNumber;
	}



	public String getbOnvifProfileAutoComment() {
		return bOnvifProfileAutoComment;
	}



	public void setbOnvifProfileAutoComment(String bOnvifProfileAutoComment) {
		this.bOnvifProfileAutoComment = bOnvifProfileAutoComment;
	}



	public boolean getbOnvifProfileAuto() {
		return bOnvifProfileAuto;
	}



	public void setbOnvifProfileAuto(boolean bOnvifProfileAuto) {
		this.bOnvifProfileAuto = bOnvifProfileAuto;
	}



	public String getStrOnvifAddrComment() {
		return strOnvifAddrComment;
	}



	public void setStrOnvifAddrComment(String strOnvifAddrComment) {
		this.strOnvifAddrComment = strOnvifAddrComment;
	}



	public String getStrOnvifAddr() {
		return strOnvifAddr;
	}



	public void setStrOnvifAddr(String strOnvifAddr) {
		this.strOnvifAddr = strOnvifAddr;
	}



	public String getStrOnvifProfileMainComment() {
		return strOnvifProfileMainComment;
	}



	public void setStrOnvifProfileMainComment(String strOnvifProfileMainComment) {
		this.strOnvifProfileMainComment = strOnvifProfileMainComment;
	}



	public String getStrOnvifProfileMain() {
		return strOnvifProfileMain;
	}



	public void setStrOnvifProfileMain(String strOnvifProfileMain) {
		this.strOnvifProfileMain = strOnvifProfileMain;
	}



	public String getStrOnvifProfileSubComment() {
		return strOnvifProfileSubComment;
	}



	public void setStrOnvifProfileSubComment(String strOnvifProfileSubComment) {
		this.strOnvifProfileSubComment = strOnvifProfileSubComment;
	}



	public String getStrOnvifProfileSub() {
		return strOnvifProfileSub;
	}



	public void setStrOnvifProfileSub(String strOnvifProfileSub) {
		this.strOnvifProfileSub = strOnvifProfileSub;
	}



	@Override
	public String toString() {
		return "H5Src [strNameComment=" + strNameComment + ", strName=" + strName + ", strTokenComment="
				+ strTokenComment + ", strToken=" + strToken + ", nTypeComment=" + nTypeComment + ", nType=" + nType
				+ ", strUrlComment=" + strUrlComment + ", strUrl=" + strUrl + ", strUserComment=" + strUserComment
				+ ", strUser=" + strUser + ", strPasswdComment=" + strPasswdComment + ", strPasswd=" + strPasswd
				+ ", bPasswdEncryptComment=" + bPasswdEncryptComment + ", bPasswdEncrypt=" + bPasswdEncrypt
				+ ", bEnableAudioComment=" + bEnableAudioComment + ", bEnableAudio=" + bEnableAudio
				+ ", nConnectTypeComment=" + nConnectTypeComment + ", nConnectType=" + nConnectType
				+ ", nRTSPTypeComment=" + nRTSPTypeComment + ", nRTSPType=" + nRTSPType + ", strSrcIpAddressComment="
				+ strSrcIpAddressComment + ", strSrcIpAddress=" + strSrcIpAddress + ", strSrcPortComment="
				+ strSrcPortComment + ", strSrcPort=" + strSrcPort + ", nChannelNumberComment=" + nChannelNumberComment
				+ ", nChannelNumber=" + nChannelNumber + ", bOnvifProfileAutoComment=" + bOnvifProfileAutoComment
				+ ", bOnvifProfileAuto=" + bOnvifProfileAuto + ", strOnvifAddrComment=" + strOnvifAddrComment
				+ ", strOnvifAddr=" + strOnvifAddr + ", strOnvifProfileMainComment=" + strOnvifProfileMainComment
				+ ", strOnvifProfileMain=" + strOnvifProfileMain + ", strOnvifProfileSubComment="
				+ strOnvifProfileSubComment + ", strOnvifProfileSub=" + strOnvifProfileSub + "]";
	}


	
	
}
