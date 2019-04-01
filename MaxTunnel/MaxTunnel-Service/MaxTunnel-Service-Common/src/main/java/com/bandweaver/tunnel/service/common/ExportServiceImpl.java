package com.bandweaver.tunnel.service.common;

import com.bandweaver.tunnel.common.biz.itf.ExportService;
import com.bandweaver.tunnel.common.biz.pojo.Export;
import com.bandweaver.tunnel.dao.common.ExportMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ExportServiceImpl implements ExportService {

    @Autowired
    private ExportMapper exportMapper;

    @Override
    public void insert(Export export) {
        exportMapper.insert(export);
    }

    @Override
    public List<Export> getList() {
        return exportMapper.getList();
    }

    @Override
    public Export get(Integer id) {
        return exportMapper.get(id);
    }

    @Override
    public Export getByExcelPath(String excelPath) {
        return exportMapper.getByExcelPath(excelPath);
    }

    @Override
    public List<Export> getListByOfTypeAndType(Integer ofType, Integer type) {
        List<Export> list = exportMapper.getListByOfTypeAndType(ofType,type);
        return list == null ? Collections.emptyList():list;
    }

    @Override
    public List<Export> getListByOfTypeAndTypeAndValue(int ofType, int type, int value) {
        List<Export> list = exportMapper.getListByOfTypeAndTypeAndValue(ofType,type,value);
        return list == null ? Collections.emptyList():list;
    }
}
