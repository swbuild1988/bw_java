package com.bandweaver.tunnel.common.biz.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Date;

public class Export implements Serializable{

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;
    private Integer ofType;
    @JsonIgnore
    private String pdfPath;
    @JsonIgnore
    private String excelPath;
    private Integer type;
    private Integer value;
    private Date crtTime;

    public Integer getId() {
        return id;
    }

    public Integer getOfType() {
        return ofType;
    }

    public void setOfType(Integer ofType) {
        this.ofType = ofType;
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

    public String getPdfPath() {
        return pdfPath;
    }

    public void setPdfPath(String pdfPath) {
        this.pdfPath = pdfPath;
    }

    public String getExcelPath() {
        return excelPath;
    }

    public void setExcelPath(String excelPath) {
        this.excelPath = excelPath;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Date getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(Date crtTime) {
        this.crtTime = crtTime;
    }

    @Override
    public String toString() {
        return "Export{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ofType=" + ofType +
                ", pdfPath='" + pdfPath + '\'' +
                ", excelPath='" + excelPath + '\'' +
                ", type=" + type +
                ", value=" + value +
                ", crtTime=" + crtTime +
                '}';
    }
}
