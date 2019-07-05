package com.bandweaver.tunnel.common.biz.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**文件信息
 * @author shaosen
 * @date 2018年8月27日
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class FileInfoVo extends BaseVo{

	private static final long serialVersionUID = 1L;

	private Integer id;

    private String name;

    private Integer fileType;

    private Integer docType;

    private Integer docTypeSon;

    private String path;

    private Date crtTime;

}