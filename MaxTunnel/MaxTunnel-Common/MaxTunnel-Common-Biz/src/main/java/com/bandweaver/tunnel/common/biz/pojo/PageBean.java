package com.bandweaver.tunnel.common.biz.pojo;

import java.util.List;

/**
 * 分页实体类
 * 
 * @author shaosen
 * @date 2018年8月9日
 */
public class PageBean<T> {

	// 当前页
	private int pageNum;
	// 当前页条数
	private int pageSize;
	// 共多少页
	private int totalPage;
	// 共多少条数据
	private int totalCount;
	// 起始位置
	private int start;
	// 结束位置
	private int end;
	// 存放数据的list
	private List<T> list;

	public PageBean(int pageNum, int pageSize, int totalCount) {
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		this.totalCount = totalCount;
		this.totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
	}

	public int getPageNum() {
		return pageNum < 0 ? 1 : pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public void setStart(int start) {
		this.start = start;
	}

	// 获取起始位置
	public int getStart() {
//		if (pageNum < 1) {
//			pageNum = 1;
//		}
		start = pageSize * (pageNum - 1);

		return start;
	}
	

	public int getEnd() {
		return pageNum * pageSize + 1;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	@Override
	public String toString() {
		return "PageBean [pageNum=" + pageNum + ", pageSize=" + pageSize + ", totalPage=" + totalPage + ", totalCount="
				+ totalCount + ", start=" + start + ", end=" + end + ", list=" + list + "]";
	}

}
