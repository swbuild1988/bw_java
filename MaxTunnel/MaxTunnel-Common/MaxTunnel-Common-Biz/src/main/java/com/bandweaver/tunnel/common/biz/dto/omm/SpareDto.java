package com.bandweaver.tunnel.common.biz.dto.omm;

import com.bandweaver.tunnel.common.biz.pojo.omm.Spare;

public class SpareDto extends Spare{
	private static final long serialVersionUID = 8103815110685756611L;
    private String typeName;
    private String modelName;
    private String venderName;
    
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public String getVenderName() {
		return venderName;
	}
	public void setVenderName(String venderName) {
		this.venderName = venderName;
	}
	@Override
	public String toString() {
		return "SpareDto [typeName=" + typeName + ", modelName=" 
				+ modelName + ", venderName=" + venderName + "]";
	}
    
}
