package com.bandweaver.tunnel.common.biz.itf;

import com.bandweaver.tunnel.common.biz.pojo.Export;

import java.util.List;

public interface ExportService {
    void insert(Export export);

    List<Export> getList();

    Export get(Integer id);

    Export getByExcelPath(String excelPath);

    List<Export> getListByOfTypeAndType(Integer ofType, Integer type);

    List<Export> getListByOfTypeAndTypeAndValue(int ofType, int type, int lastWeekOrMonth);
}
