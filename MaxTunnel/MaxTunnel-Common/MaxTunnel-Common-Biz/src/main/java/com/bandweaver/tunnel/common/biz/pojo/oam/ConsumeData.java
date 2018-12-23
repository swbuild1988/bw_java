package com.bandweaver.tunnel.common.biz.pojo.oam;

import java.io.Serializable;
import java.util.Date;

public class ConsumeData implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 能耗id
	 */
	private Integer id;
	/**
	 * 直接值/读取值
	 */
	private Double direct;
	/**
	 * 间接值/计算值
	 */
	private Double indirect;
	/**
	 * 时间
	 */
	private Date time;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Double getDirect() {
		return direct;
	}
	public void setDirect(Double direct) {
		this.direct = direct;
	}
	public Double getIndirect() {
		return indirect;
	}
	public void setIndirect(Double indirect) {
		this.indirect = indirect;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	@Override
	public String toString() {
		return "ConsumeData [id=" + id + ", direct=" + direct
				+ ", indirect=" + indirect + ", time=" + time + "]";
	}
	
	
}
