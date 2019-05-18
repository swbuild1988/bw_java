package com.bandweaver.tunnel.common.biz.vo.oam;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.bandweaver.tunnel.common.biz.pojo.oam.Cable;
import com.bandweaver.tunnel.common.biz.pojo.oam.CableContract;
import com.bandweaver.tunnel.common.biz.vo.BaseVo;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ContractVo extends BaseVo implements Serializable {

    private static final long serialVersionUID = 2875630261505081411L;

    /**
     * 合同信息
     */
    // 合同编号
    private String id;
    // 合同名称
    private String name;
    // 付款方式
    private Integer payType;
    // 合同状态
    private Integer contractStatus;
    // 合同开始日期
    private Date contractStartTime;
    // 合同结束日期
    private Date contractEndTime;
    // 企业信息
    private Integer companyId;

    /**
     * 录入人信息
     */
    private String operateUsername;

    /**
     * 附件地址
     */
    private String path;

    /**
     * section信息
     */
    private List<Integer> areaIds;
    private Integer storeId;


    /**
     * 管线信息
     */
    // 管线id
    private String cableId;
    // 管线名称
    private String cableName;
    // 管线长度
    private Double cableLength;
    // 管线位置
    private String cableLocation;
    // 管线状态
    private Integer cableStatus;

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
}
