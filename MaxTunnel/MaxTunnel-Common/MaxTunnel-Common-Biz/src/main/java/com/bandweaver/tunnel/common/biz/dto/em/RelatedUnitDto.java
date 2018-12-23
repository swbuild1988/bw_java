package com.bandweaver.tunnel.common.biz.dto.em;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.bandweaver.tunnel.common.biz.constant.em.UnitTypeEnum;
import com.bandweaver.tunnel.common.biz.dto.SectionDto;
import com.bandweaver.tunnel.common.biz.pojo.Section;

/**
 * ClassName: RelatedUnit
 * @Description: 相关单位实体类
 * @author shaosen
 * @date 2018年7月9日
 */
public class RelatedUnitDto implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
    //单位名称
    private String name;
    //单位联系人
    private String contact;
    //联系人电话
    private String tel;
    //单位类别：枚举
    private Integer unitType;
    private String unitTypeName;
    //单位地址
    private String address;
    //经度
    private String longitude;
    //维度
    private String latitude;
    //关联的sectionId
    private String sectionIds;
    private List<SectionDto> sectionList;
    
    private Date crtTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact == null ? null : contact.trim();
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel == null ? null : tel.trim();
    }

    public Integer getUnitType() {
        return unitType;
    }

    public void setUnitType(Integer unitType) {
        this.unitType = unitType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getSectionIds() {
        return sectionIds;
    }

    public void setSectionIds(String sectionIds) {
        this.sectionIds = sectionIds == null ? null : sectionIds.trim();
    }

    public List<SectionDto> getSectionList() {
		return sectionList;
	}

	public void setSectionList(List<SectionDto> sectionList) {
		this.sectionList = sectionList;
	}

	public Date getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(Date crtTime) {
        this.crtTime = crtTime;
    }

	/**
	 * @Description: 获取类别名称
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author shaosen
	 * @date 2018年7月9日
	 */
	public String getUnitTypeName() {
		return UnitTypeEnum.getEnum(this.unitType).getName();
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	
	
    
    
}