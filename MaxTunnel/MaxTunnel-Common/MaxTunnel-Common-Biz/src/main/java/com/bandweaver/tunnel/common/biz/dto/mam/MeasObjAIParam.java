package com.bandweaver.tunnel.common.biz.dto.mam;

import com.bandweaver.tunnel.common.biz.constant.mam.ObjectType;

public class MeasObjAIParam {

	private Integer objId;
	private Integer sectionId;
	private Double CV;
	private String objTypeName;

	public MeasObjAIParam(double CV) {
		this.CV = CV;
	}

	public Integer getObjId() {
		return objId;
	}

	public void setObjId(Integer objId) {
		this.objId = objId;
	}

	public MeasObjAIParam() {
		super();
	}

	public Integer getSectionId() {
		return sectionId;
	}

	public void setSectionId(Integer sectionId) {
		this.sectionId = sectionId;
	}

	public Double getCV() {
		return CV;
	}

	public void setCV(Double cV) {
		CV = cV;
	}

	
	public String getObjTypeName() {
		return objTypeName;
	}

	public void setObjTypeName(String objTypeName) {
		this.objTypeName = objTypeName;
	}

	@Override
	public String toString() {
		return "MeasObjAIParam [objId=" + objId + ", sectionId=" + sectionId + ", CV=" + CV + ", objTypeName="
				+ objTypeName + "]";
	}

}
