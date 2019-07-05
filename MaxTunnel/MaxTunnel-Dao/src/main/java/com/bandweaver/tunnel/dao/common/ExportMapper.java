package com.bandweaver.tunnel.dao.common;

import com.bandweaver.tunnel.common.biz.pojo.Export;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExportMapper {
    void insert(Export export);

    List<Export> getList();

    Export get(Integer id);

    Export getByExcelPath(String excelPath);

    List<Export> getListByOfTypeAndType(@Param("ofType") Integer ofType, @Param("type") Integer type);

    List<Export> getListByOfTypeAndTypeAndValue(@Param("ofType") int ofType, @Param("type") int type, @Param("value") int value);
}
