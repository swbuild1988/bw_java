package com.bandweaver.tunnel.common.biz.pojo.omm;

import java.io.Serializable;
import java.util.Date;

/**
 * 巡检任务多图上传
 * @author ya.liu
 * @Date 2019年6月17日
 */
public class MultiFile implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private Integer relationId;
	
	private String imgUrl;
	// 预留字段，用于其他模块的多图上传
	private Integer type;
	
	private Date crtTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRelationId() {
		return relationId;
	}

	public void setRelationId(Integer relationId) {
		this.relationId = relationId;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getCrtTime() {
		return crtTime;
	}

	public void setCrtTime(Date crtTime) {
		this.crtTime = crtTime;
	}

	@Override
	public String toString() {
		return "MultiFile [id=" + id + ", relationId=" + relationId + ", imgUrl="
				+ imgUrl + ", type=" + type + ", crtTime=" + crtTime + "]";
	}
	
}
