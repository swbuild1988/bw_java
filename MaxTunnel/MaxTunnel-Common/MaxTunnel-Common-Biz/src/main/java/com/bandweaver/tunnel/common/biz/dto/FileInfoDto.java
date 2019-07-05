package com.bandweaver.tunnel.common.biz.dto;

import java.io.Serializable;
import java.util.Date;

import com.bandweaver.tunnel.common.biz.constant.DocTypeEnum;
import com.bandweaver.tunnel.common.biz.constant.DocTypeSonEnum;
import com.bandweaver.tunnel.common.biz.constant.FileTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 文件信息
 *
 * @author shaosen
 * @date 2018年8月27日
 */
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class FileInfoDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    @JsonIgnore
    private Integer fileType;
    private String fileTypeName;

    @JsonIgnore
    private Integer docType;
    private String docTypeName;

    @JsonIgnore
    private Integer docTypeSon;
    private String docTypeSonName;

    @JsonIgnore
    private String path;

    private Date crtTime;


    public String getFileTypeName() {
        FileTypeEnum e = FileTypeEnum.getEnum(this.fileType);
        return e == null ? "" : e.getName();
    }

    public void setFileTypeName(String fileTypeName) {
        this.fileTypeName = fileTypeName;
    }

    public String getDocTypeName() {
        DocTypeEnum e = DocTypeEnum.getEnum(this.docType);
        return e == null ? "" : e.getName();
    }

    public void setDocTypeName(String docTypeName) {
        this.docTypeName = docTypeName;
    }

    public String getDocTypeSonName() {
        DocTypeSonEnum e = DocTypeSonEnum.getEnum(this.docTypeSon);
        return e == null ? "" : e.getName();
    }

    public void setDocTypeSonName(String docTypeSonName) {
        this.docTypeSonName = docTypeSonName;
    }
}