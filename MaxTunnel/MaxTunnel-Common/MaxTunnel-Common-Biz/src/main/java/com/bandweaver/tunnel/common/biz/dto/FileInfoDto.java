package com.bandweaver.tunnel.common.biz.dto;

import java.io.Serializable;
import java.util.Date;

import com.bandweaver.tunnel.common.biz.constant.DocTypeEnum;
import com.bandweaver.tunnel.common.biz.constant.FileTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**文件信息
 * @author shaosen
 * @date 2018年8月27日
 */
public class FileInfoDto implements Serializable{
    /**
	 * 
	 */
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
    private String path;//隐藏路径，防止黑客攻击

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
        this.name = name == null ? null : name.trim();
    }

    public Integer getFileType() {
        return fileType;
    }

    public void setFileType(Integer fileType) {
        this.fileType = fileType;
    }

    public Integer getDocType() {
        return docType;
    }

    public void setDocType(Integer docType) {
        this.docType = docType;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    public Date getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(Date crtTime) {
        this.crtTime = crtTime;
    }

	public String getFileTypeName() {
		return FileTypeEnum.getEnum(this.fileType).getName();
	}

	public void setFileTypeName(String fileTypeName) {
		this.fileTypeName = fileTypeName;
	}

	public String getDocTypeName() {
		return DocTypeEnum.getEnum(this.docType).getName();
	}

	public void setDocTypeName(String docTypeName) {
		this.docTypeName = docTypeName;
	}
    
    
}