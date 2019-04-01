package com.bandweaver.tunnel.common.platform.util;

import com.bandweaver.tunnel.common.biz.pojo.Item;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
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
     */
    public static void exportExcel(String exportPath,
                                   String templateFilePath,
                                   List<Item> data) throws Exception {

        FileInputStream fis = null;
        OutputStream out = null;

        try {
            //获取Excel模板
            fis = new FileInputStream(templateFilePath);
            HSSFWorkbook workbook = new HSSFWorkbook(fis);

            //poi中提供了org.apache.poi.ss.usermodel.FormulaEvaluator这个接口，实现对公式的更新。
            // 其中HSSFFormulaEvaluator, XSSFFormulaEvaluator 实现了这个接口。
            HSSFFormulaEvaluator eval = new HSSFFormulaEvaluator(workbook);

            Sheet sheet = workbook.getSheetAt(0);
            if (data != null && data.size() > 0) {
                generateExcelData(eval, sheet, data);
            }

            out = new FileOutputStream(exportPath);
            workbook.write(out);

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
     */
    private static void generateExcelData(HSSFFormulaEvaluator eval, Sheet sheet, List<Item> data) {

        for (Item item : data) {
            Row row = sheet.getRow(item.getRow());
            Cell cell = row.createCell(item.getColumn());
            if (item.getStringValue() != null && item.getStringValue() != "")
                cell.setCellValue(item.getStringValue());
            else
                cell.setCellValue(item.getValue());
        }

        //遍历一行单元格中的所有格，判断如果其类型为Cell.CELL_TYPE_FORMULA，则证明此单元格为公式
        // 可以使用evaluator.evaluateFormulaCell(cell);将其更新
        Cell c = null;
        for (int x = 0; x < sheet.getLastRowNum(); x++) {
            Row r = sheet.getRow(x);
            for (int i = r.getFirstCellNum(); i < r.getLastCellNum(); i++) {
                c = r.getCell(i);
                if (c != null) {
                    if (c.getCellType() == Cell.CELL_TYPE_FORMULA)
                        eval.evaluateFormulaCell(c);
                }

            }
        }

        sheet.setForceFormulaRecalculation(true);


//        //填充日期
//        sheet.getRow(1).getCell(1).setCellValue(searchDate);
//
//        //创建row，并从row的第一位开始填充cell
//        int rowIndex = 3;// rowIndex = 3从第4行开始插入数据
//        for (int i = 0; i < data.size(); i++) {
//            List<String> oneRow = data.get(i);
//            Row row = sheet.createRow(rowIndex + i);
//            for (int j = 0; j < oneRow.size(); j++) {
//                Cell cell = row.createCell(j);
//                cell.setCellValue(oneRow.get(j));
//            }
//        }
    }


}
