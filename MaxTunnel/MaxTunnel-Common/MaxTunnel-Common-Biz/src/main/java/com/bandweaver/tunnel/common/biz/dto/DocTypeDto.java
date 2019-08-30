package com.bandweaver.tunnel.common.biz.dto;

import java.util.List;

import com.bandweaver.tunnel.common.biz.pojo.DocType;

public class DocTypeDto extends DocType {

	private static final long serialVersionUID = 1L;
	
	private List<DocType> children;

	public List<DocType> getChildren() {
		return children;
	}

	public void setChildren(List<DocType> children) {
		this.children = children;
	}

	@Override
	public String toString() {
		return "DocTypeDto [" + super.toString() + "children=" + children + "]";
	}
	
}
