package com.bandweaver.tunnel.common.biz.pojo.oam;

import java.io.Serializable;

/**
 * ClassName: CableSection
 * @Description: 管线，仓段关系表
 * @author shaosen
 * @date 2018年7月22日
 */
public class CableSection implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

    private String cableId;

    private Integer sectionId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCableId() {
        return cableId;
    }

    public void setCableId(String cableId) {
        this.cableId = cableId;
    }

    public Integer getSectionId() {
        return sectionId;
    }

    public void setSectionId(Integer sectionId) {
        this.sectionId = sectionId;
    }
}