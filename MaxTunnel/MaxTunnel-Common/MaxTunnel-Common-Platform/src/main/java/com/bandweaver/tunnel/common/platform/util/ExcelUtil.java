package com.bandweaver.tunnel.common.platform.util;

import com.bandweaver.tunnel.common.platform.log.LogUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.*;
import java.util.List;

/**
 * POI导出excel工具类
 */
public class ExcelUtil {

    /**
     * 生成excel文件
     *
     * @param exportPath       导出文件路径
     * @param templateFilePath excel模板路径
     * @param data             数据
     * @param searchDate       搜索日期
     */
    public static void exportExcel(String exportPath,
                                   String templateFilePath,
                                   List<List<String>> data,
                                   String searchDate) {

        FileInputStream fis = null;
        OutputStream out = null;

        try {
//            response.setHeader("Content-disposition", "attachment; filename=" + new String(exportFileName.getBytes("GBK"), "ISO-8859-1") + ""); // 设定输出文件头
//            response.setContentType("application/vnd.ms-excel"); // 定义输出类型

            //获取Excel模板
            fis = new FileInputStream(templateFilePath);
            Workbook workbook = new HSSFWorkbook(fis);
            Sheet sheet = workbook.getSheetAt(0);
            if (data != null && data.size() > 0) {
                generateExcelData(sheet, data, searchDate);
            }

//            out = response.getOutputStream();
//            workbook.write(out);
//            out.flush();
            out = new FileOutputStream(exportPath);
            workbook.write(out);

        } catch (Throwable e) {
            LogUtil.error(e);
        } finally {

            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    LogUtil.error(e);
                }
            }

            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    LogUtil.error(e);
                }
            }
        }
    }

    /**
     * 填充excel数据
     *
     * @param sheet
     * @param data
     * @param searchDate
     */
    private static void generateExcelData(Sheet sheet, List<List<String>> data, String searchDate) {

        //填充日期
        sheet.getRow(1).getCell(1).setCellValue(searchDate);

        //创建row，并从row的第一位开始填充cell
        int rowIndex = 3;// rowIndex = 3从第4行开始插入数据
        for (int i = 0; i < data.size(); i++) {
            List<String> oneRow = data.get(i);
            Row row = sheet.createRow(rowIndex + i);
            for (int j = 0; j < oneRow.size(); j++) {
                Cell cell = row.createCell(j);
                cell.setCellValue(oneRow.get(j));
            }
        }
    }


}
