package com.bandweaver.tunnel.common.biz.pojo.oam;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * ClassName: CableType
 * @Description: 管线类型类
 */
public class CableType implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int id;

    private String typeName;

    private String inStoreType;

    private Date crtTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getInStoreType() {
        return inStoreType;
    }

    public List<Integer> getInStoreTypeIds() {
        List<String> ls = Arrays.asList(inStoreType.split(","));
        List<Integer> res = new ArrayList();
        for (String s : ls) {
            Integer i = Integer.parseInt(s);
            res.add(i);
        }
        return res;
    }

    public void setInStoreType(String inStoreType) {
        this.inStoreType = inStoreType;
    }

    public Date getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(Date crtTime) {
        this.crtTime = crtTime;
    }

    @Override
    public String toString() {
        return "CableType{" +
                "id='" + id + '\'' +
                ", typeName='" + typeName + '\'' +
                ", inStoreType='" + inStoreType + '\'' +
                ", crtTime=" + crtTime +
                '}';
    }
}