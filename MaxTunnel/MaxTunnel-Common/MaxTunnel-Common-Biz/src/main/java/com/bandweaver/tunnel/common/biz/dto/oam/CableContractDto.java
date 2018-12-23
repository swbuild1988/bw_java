package com.bandweaver.tunnel.common.biz.dto.oam;

import java.io.Serializable;
import java.util.Date;

import com.bandweaver.tunnel.common.biz.constant.oam.ContractStatusEnum;
import com.bandweaver.tunnel.common.biz.constant.oam.PayTypeEnum;
import com.bandweaver.tunnel.common.biz.pojo.oam.Customer;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * ClassName: CableContract
 * @Description: 管线合同实体类
 * @author shaosen
 * @date 2018年7月22日
 */
public class CableContractDto implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

    private String name;
    
    @JsonIgnore
    private Integer customerId;
    private CustomerDto customer;

    /**付款方式*/
    private Integer payType;
    private String payTypeName;
    
    //合同状态
    private Integer contractStatus;
    private String contractStatusName;
    
    private Date contractStartTime;
    private Date contractEndTime;
    private Date crtTime;

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
        this.name = name == null ? null : name.trim();
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

	public CustomerDto getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerDto customer) {
		this.customer = customer;
	}

	public String getPayTypeName() {
		return PayTypeEnum.getEnum(this.payType).getName();
	}

	public void setPayTypeName(String payTypeName) {
		this.payTypeName = payTypeName;
	}

	public Integer getContractStatus() {
		return contractStatus;
	}

	public void setContractStatus(Integer contractStatus) {
		this.contractStatus = contractStatus;
	}

	public String getContractStatusName() {
		return ContractStatusEnum.getEnum(this.contractStatus).getName();
	}

	public void setContractStatusName(String contractStatusName) {
		this.contractStatusName = contractStatusName;
	}

	public Date getContractStartTime() {
		return contractStartTime;
	}

	public void setContractStartTime(Date contractStartTime) {
		this.contractStartTime = contractStartTime;
	}

	public Date getContractEndTime() {
		return contractEndTime;
	}

	public void setContractEndTime(Date contractEndTime) {
		this.contractEndTime = contractEndTime;
	}

	public Date getCrtTime() {
		return crtTime;
	}

	public void setCrtTime(Date crtTime) {
		this.crtTime = crtTime;
	}
    
    
}