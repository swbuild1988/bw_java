package com.bandweaver.tunnel.common.biz.dto.omm;

import com.bandweaver.tunnel.common.biz.constant.omm.EquipmentStatusEnum;
import com.bandweaver.tunnel.common.biz.pojo.omm.Instrument;

public class InstrumentDto extends Instrument{
	
	private static final long serialVersionUID = 1L;
	
    private String typeName;
    private String modelName;
    private String venderName;
    private String useStatusName;
    
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
	public String getUseStatusName() {
		return EquipmentStatusEnum.getEnum(super.getUseStatus()).getName();
	}
	public void setUseStatusName(String useStatusName) {
		this.useStatusName = useStatusName;
	}
	@Override
	public String toString() {
		return "InstrumentDto [typeName=" + typeName + ", modelName=" 
				+ modelName + ", venderName=" + venderName
				+ ", useStatusName=" + useStatusName + "]";
	}
}
