package com.bandweaver.tunnel.common.biz.pojo.oam;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * ClassName: CableContract
 *
 * @author shaosen
 * @Description: 管线合同实体类
 * @date 2018年7月22日
 */
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class CableContract implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String id;

    private String name;

    private Integer companyId;

    private Integer payType;

    private Integer contractStatus;

    private Date contractStartTime;

    private Date contractEndTime;

    private Date crtTime;

    private String operateUsername;

    private String path;

}