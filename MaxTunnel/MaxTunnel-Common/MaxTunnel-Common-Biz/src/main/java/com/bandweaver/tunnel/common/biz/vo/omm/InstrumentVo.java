package com.bandweaver.tunnel.common.biz.vo.omm;


import com.bandweaver.tunnel.common.biz.vo.BaseVo;

public class InstrumentVo extends BaseVo{
	
	private static final long serialVersionUID = 1L;
    private String name;
    private Integer modelId;
    private Integer typeId;
    private Integer venderId;
    private Integer useStatus;
    private Boolean status;
    private Boolean isSpare;//是否在备品库取出
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getModelId() {
		return modelId;
	}
	public void setModelId(Integer modelId) {
		this.modelId = modelId;
	}
	public Integer getTypeId() {
		return typeId;
	}
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	public Integer getVenderId() {
		return venderId;
	}
	public void setVenderId(Integer venderId) {
		this.venderId = venderId;
	}
	public Integer getUseStatus() {
		return useStatus;
	}
	public void setUseStatus(Integer useStatus) {
		this.useStatus = useStatus;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public Boolean getIsSpare() {
		return isSpare;
	}
	public void setIsSpare(Boolean isSpare) {
		this.isSpare = isSpare;
	}
	@Override
	public String toString() {
		return "InstrumentVo [name=" + name + ", modelId=" + modelId 
				+ ", typeId=" + typeId + ", venderId=" + venderId
				+ ", useStatus=" + useStatus + ", status=" + status 
				+ ", isSpare=" + isSpare + "]";
	}
}
