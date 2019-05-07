package com.bandweaver.tunnel.common.biz.vo.oam;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.bandweaver.tunnel.common.biz.pojo.oam.Cable;
import com.bandweaver.tunnel.common.biz.pojo.oam.CableContract;
import com.bandweaver.tunnel.common.biz.vo.BaseVo;

public class ContractVo extends BaseVo implements Serializable {

	private static final long serialVersionUID = 2875630261505081411L;

	// 合同信息
	private String id;// 合同编号

	private String name;// 合同名称

	private Integer payType; // 付款方式
	
	private Integer contractStatus;//合同状态

	private Date contractStartTime; // 合同开始日期

	private Date contractEndTime; // 合同结束日期

	private Integer companyId;// 企业信息

	/**
	 * 录入人信息
	 */
	private String operateUsername;

	/**
	 * 附件地址
	 */
	private String path;

	// section信息
	private List<Integer> areaIds;
	private Integer storeId;
	

	// 管线信息
	private String cableId;//管线id
	
	private String cableName;// 管线名称

	private Double cableLength;// 管线长度

	private String cableLocation;// 管线位置
	
	private Integer cableStatus;//管线状态

	public CableContract toCableContract() {
		CableContract cableContract = new CableContract();
		cableContract.setId(this.id);
		cableContract.setName(this.name);
		cableContract.setPayType(this.payType);
		cableContract.setCompanyId(this.companyId);
		cableContract.setContractStatus(this.contractStatus);
		cableContract.setContractStartTime(this.contractStartTime);
		cableContract.setContractEndTime(this.contractEndTime);
		cableContract.setCrtTime(new Date());
		cableContract.setOperateUsername(this.operateUsername);
		cableContract.setPath(this.path);
		return cableContract;
	}

	public Cable toCable() {
		Cable cable = new Cable();
		cable.setId(this.cableId);
		cable.setCableName(this.cableName);
		cable.setCableLength(this.cableLength);
		cable.setCableLocation(this.cableLocation);
		cable.setCableStatus(this.cableStatus);
		cable.setCrtTime(new Date());
		return cable;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



	public List<Integer> getAreaIds() {
		return areaIds;
	}

	public void setAreaIds(List<Integer> areaIds) {
		this.areaIds = areaIds;
	}

	public Integer getStoreId() {
		return storeId;
	}

	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}

	public String getCableId() {
		return cableId;
	}

	public void setCableId(String cableId) {
		this.cableId = cableId;
	}

	public String getCableName() {
		return cableName;
	}

	public void setCableName(String cableName) {
		this.cableName = cableName;
	}

	public Double getCableLength() {
		return cableLength;
	}

	public void setCableLength(Double cableLength) {
		this.cableLength = cableLength;
	}

	public String getCableLocation() {
		return cableLocation;
	}

	public void setCableLocation(String cableLocation) {
		this.cableLocation = cableLocation;
	}

	public Integer getCableStatus() {
		return cableStatus;
	}

	public void setCableStatus(Integer cableStatus) {
		this.cableStatus = cableStatus;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public Integer getContractStatus() {
		return contractStatus;
	}

	public void setContractStatus(Integer contractStatus) {
		this.contractStatus = contractStatus;
	}

	public Date getContractStartTime() {
		return contractStartTime;
	}

	public void setContractStartTime(Date contratStartTime) {
		this.contractStartTime = contratStartTime;
	}

	public Date getContractEndTime() {
		return contractEndTime;
	}

	public void setContractEndTime(Date contractEndTime) {
		this.contractEndTime = contractEndTime;
	}


	public String getOperateUsername() {
		return operateUsername;
	}

	public void setOperateUsername(String operateUsername) {
		this.operateUsername = operateUsername;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
