package com.bandweaver.tunnel.common.biz.pojo.oam;

import java.io.Serializable;
import java.util.Date;

/**
 * ClassName: Cable
 * @Description: 管线实体类
 * @author shaosen
 * @date 2018年7月22日
 */
public class Cable implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

    private String cableName;

    private Integer cableLength;

    private Integer cableStatus;

    private String cableLocation;

    private String contractId;

    private Date crtTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCableName() {
        return cableName;
    }

    public void setCableName(String cableName) {
        this.cableName = cableName == null ? null : cableName.trim();
    }

    public Integer getCableLength() {
        return cableLength;
    }

    public void setCableLength(Integer cableLength) {
        this.cableLength = cableLength;
    }

    public Integer getCableStatus() {
        return cableStatus;
    }

    public void setCableStatus(Integer cableStatus) {
        this.cableStatus = cableStatus;
    }

    public String getCableLocation() {
        return cableLocation;
    }

    public void setCableLocation(String cableLocation) {
        this.cableLocation = cableLocation == null ? null : cableLocation.trim();
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public Date getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(Date crtTime) {
        this.crtTime = crtTime;
    }
}