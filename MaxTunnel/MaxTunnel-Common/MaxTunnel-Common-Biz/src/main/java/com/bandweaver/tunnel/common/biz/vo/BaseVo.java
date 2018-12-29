package com.bandweaver.tunnel.common.biz.vo;


import java.io.Serializable;
import java.util.Date;


/**
 * ClassName: BaseVo
 * @Description: 通用vo 
 * @author shaosen
 * @date 2018年6月11日
 */
public class BaseVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**开始时间*/
	private Date startTime;

	/**结束时间*/
	private Date endTime;
	
	/**当前页码*/
	private Integer pageNum ;
	
	/**每页条数*/
	private Integer pageSize ;

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
}
