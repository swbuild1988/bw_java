package com.bandweaver.tunnel.common.biz.dto.mam.h5;

import java.io.Serializable;
import java.util.List;

public class H5Source implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nConnectTypeComment;
	private String nConnectType;
	private String nRTSPTypeComment;
	private String nRTSPType;
	private String bEnablePreRecordComment;
	private boolean bEnablePreRecord;
	private String nPreRecordLengthComment;
	private int nPreRecordLength;
	private List<H5Src> src;
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
	public String getbEnablePreRecordComment() {
		return bEnablePreRecordComment;
	}
	public void setbEnablePreRecordComment(String bEnablePreRecordComment) {
		this.bEnablePreRecordComment = bEnablePreRecordComment;
	}
	public boolean getbEnablePreRecord() {
		return bEnablePreRecord;
	}
	public void setbEnablePreRecord(boolean bEnablePreRecord) {
		this.bEnablePreRecord = bEnablePreRecord;
	}
	public String getnPreRecordLengthComment() {
		return nPreRecordLengthComment;
	}
	public void setnPreRecordLengthComment(String nPreRecordLengthComment) {
		this.nPreRecordLengthComment = nPreRecordLengthComment;
	}
	public int getnPreRecordLength() {
		return nPreRecordLength;
	}
	public void setnPreRecordLength(int nPreRecordLength) {
		this.nPreRecordLength = nPreRecordLength;
	}
	public List<H5Src> getSrc() {
		return src;
	}
	public void setSrc(List<H5Src> src) {
		this.src = src;
	}
	@Override
	public String toString() {
		return "Source [nConnectTypeComment=" + nConnectTypeComment + ", nConnectType=" + nConnectType
				+ ", nRTSPTypeComment=" + nRTSPTypeComment + ", nRTSPType=" + nRTSPType + ", bEnablePreRecordComment="
				+ bEnablePreRecordComment + ", bEnablePreRecord=" + bEnablePreRecord + ", nPreRecordLengthComment="
				+ nPreRecordLengthComment + ", nPreRecordLength=" + nPreRecordLength + ", src=" + src + "]";
	}
	
	
	
	
}
