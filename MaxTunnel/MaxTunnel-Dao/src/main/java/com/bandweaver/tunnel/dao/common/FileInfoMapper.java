package com.bandweaver.tunnel.dao.common;

import java.util.List;

import com.bandweaver.tunnel.common.biz.dto.FileInfoDto;
import com.bandweaver.tunnel.common.biz.pojo.FileInfo;
import com.bandweaver.tunnel.common.biz.vo.FileInfoVo;

public interface FileInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FileInfo record);

    int insertSelective(FileInfo record);

    FileInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FileInfo record);

    int updateByPrimaryKey(FileInfo record);

	List<FileInfoDto> getDtoListByCondition(FileInfoVo vo);
}