package com.bandweaver.tunnel.common.platform.util;

import com.bandweaver.tunnel.common.biz.pojo.Item;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;

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

            HSSFSheet sheet = workbook.getSheetAt(0);
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
     * 创建一个新的excel
     *
     * @param path
     * @param data
     * @throws Exception
     */
    public static void createExcel(String path, List<Item> data) throws Exception {

        //获得Excel文件输出流
        FileOutputStream out = new FileOutputStream(new File(path));

        try {
            //创建excel工作簿对象
            HSSFWorkbook wb = new HSSFWorkbook();
            //创建excel页
            HSSFSheet sheet = wb.createSheet("POI导出测试");

            HSSFFormulaEvaluator eval = new HSSFFormulaEvaluator(wb);
            if (data != null && data.size() > 0) {
                generateExcelData(eval, sheet, data);
            }
            wb.write(out);

        } finally {

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
    public static void generateExcelData(HSSFFormulaEvaluator eval, HSSFSheet sheet, List<Item> data) {

        fillExcelData(sheet, data);

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

    /**
     * 填充表格
     *
     * @param sheet
     * @param data
     */
    public static void fillExcelData(HSSFSheet sheet, List<Item> data) {
        for (Item item : data) {
            Row row = sheet.getRow(item.getRow());
            if (row == null) {
                row = sheet.createRow(item.getRow());
            }
            Cell cell = row.getCell(item.getColumn());
            if (cell == null) {
                cell = row.createCell(item.getColumn());
            }
            if (item.getStringValue() != null && item.getStringValue() != "")
                cell.setCellValue(item.getStringValue());
            else
                cell.setCellValue(item.getValue());
        }
    }


    /**
     * 合并单元格
     *
     * @param sheet
     * @param top    首行
     * @param bottom 最后一行
     * @param left   首列
     * @param right  最后一列
     */
    public static void mergedRegion(HSSFSheet sheet, int top, int bottom, int left, int right) {
        sheet.addMergedRegion(new CellRangeAddress(top, bottom, left, right));
    }

    /**
     * 设置表格边框变黑
     * @param sheet
     * @param style
     */
    public static void setBorder(HSSFWorkbook workbook, HSSFSheet sheet, int top, int bottom, int left, int right) {
        CellRangeAddress rangeAddress = new CellRangeAddress(top, bottom, left, right);
        RegionUtil.setBorderBottom(2, rangeAddress, sheet, workbook);
        RegionUtil.setBorderLeft(2, rangeAddress, sheet, workbook);
        RegionUtil.setBorderRight(1, rangeAddress, sheet, workbook);
        RegionUtil.setBorderTop(1, rangeAddress, sheet, workbook);
    }

}
