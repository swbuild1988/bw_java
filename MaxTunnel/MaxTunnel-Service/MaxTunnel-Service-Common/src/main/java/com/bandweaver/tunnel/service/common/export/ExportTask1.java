package com.bandweaver.tunnel.service.common.export;

import com.bandweaver.tunnel.common.platform.constant.Constants;
import com.bandweaver.tunnel.common.platform.util.DateUtil;
import com.bandweaver.tunnel.common.platform.util.ExcelUtil;
import com.bandweaver.tunnel.common.platform.util.FileUtil;
import com.bandweaver.tunnel.common.platform.util.PropertiesUtil;

import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 报表导出线程1
 */
public class ExportTask1 implements Runnable {

    private String templateFilePath;

    public ExportTask1(String templateFilePath){
        this.templateFilePath = templateFilePath;
    }

    @Override
    public void run() {

        System.out.println(Thread.currentThread().getName() + ": ExportTask1");
        List<String> list1 = Arrays.asList("1", "2", "3");
        List<String> list2 = Arrays.asList("2", "2", "3");
        List<String> list3 = Arrays.asList("3", "2", "3");
        List<String> list4 = Arrays.asList("4", "2", "3");

        //获取excel数据
        List<List<String>> data = Arrays.asList(list1, list2, list3, list4);

        //获取导出文件时间
        String searchDate = DateUtil.getDate2YYYYMMdd(new Date());

        //获取文件导出路径
        String exportPath = PropertiesUtil.getString(Constants.EXCEL_EXPORT_PATH);
        if (!FileUtil.isExit(exportPath)) {FileUtil.createPath(exportPath);}
        exportPath = exportPath + File.separator + Constants.TEMPLATE_01.substring(0,Constants.TEMPLATE_01.lastIndexOf(".")) + searchDate + ".xls";

        ExcelUtil.exportExcel(exportPath, templateFilePath, data, searchDate);
    }


}
