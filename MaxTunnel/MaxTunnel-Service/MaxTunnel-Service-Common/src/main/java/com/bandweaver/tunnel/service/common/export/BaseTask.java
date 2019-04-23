package com.bandweaver.tunnel.service.common.export;

import com.bandweaver.tunnel.common.biz.constant.OfTypeEnum;
import com.bandweaver.tunnel.common.biz.constant.TimeEnum;
import com.bandweaver.tunnel.common.biz.itf.ExportService;
import com.bandweaver.tunnel.common.biz.itf.TunnelService;
import com.bandweaver.tunnel.common.biz.itf.mam.alarm.AlarmService;
import com.bandweaver.tunnel.common.biz.itf.mam.measobj.MeasObjService;
import com.bandweaver.tunnel.common.biz.itf.mam.report.MeasObjReportService;
import com.bandweaver.tunnel.common.biz.itf.oam.ConsumeService;
import com.bandweaver.tunnel.common.biz.itf.omm.EquipmentService;
import com.bandweaver.tunnel.common.biz.itf.omm.InspectionTaskService;
import com.bandweaver.tunnel.common.biz.pojo.Export;
import com.bandweaver.tunnel.common.biz.pojo.Item;
import com.bandweaver.tunnel.common.platform.constant.Constants;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.*;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public abstract class BaseTask {

    protected static ExportService exportService;
    protected static AlarmService alarmService;
    protected static EquipmentService equipmentService;
    protected static MeasObjReportService measObjReportService;
    protected static TunnelService tunnelService;
    protected static MeasObjService measObjService;
    protected static ConsumeService consumeService;
    protected static InspectionTaskService inspectionTaskService;


    static {
        exportService = SpringContextHolder.getBean("exportServiceImpl");
        alarmService = SpringContextHolder.getBean("alarmServiceImpl");
        equipmentService = SpringContextHolder.getBean("equipmentServiceImpl");
        measObjReportService = SpringContextHolder.getBean("measObjReportServiceImpl");
        tunnelService = SpringContextHolder.getBean("tunnelServiceImpl");
        measObjService = SpringContextHolder.getBean("measObjServiceImpl");
        consumeService = SpringContextHolder.getBean("consumeServiceImpl");
        inspectionTaskService = SpringContextHolder.getBean("inspectionTaskServiceImpl");
    }

    protected void checkLastExport(int currentWeekOrMonth, int type, int ofType) {}
    protected void export(int lastWeekOrMonth, int type, int ofType) {}

    protected boolean checkdbLog(int lastWeekOrMonth, int type, int ofType) {
        List<Export> list = exportService.getListByOfTypeAndTypeAndValue(ofType, type, lastWeekOrMonth);
        if (list == null || list.size() == 0) {  return false;  }
        return true;
    }


    public String getTemplateFilePath(String fileDirPath, int ofType) {
        String templateFilePath = fileDirPath + OfTypeEnum.getEnum(ofType).getName() + ".xls";
        return templateFilePath;
    }


    /**
     * 获取当前是第几周/月
     *
     * @param type
     * @return
     */
    public int getCurrentTime(int type) {
        int now = 0;
        if (type == TimeEnum.WEEK.getValue()) {
            now = DateUtil.getNowWeek();
        }
        if (type == TimeEnum.MONTH.getValue()) {
            now = DateUtil.getNowMonth();
        }
        return now;
    }

    /**
     * 获取周/月开始时间
     *
     * @param type
     * @param lastWeekOrMonth
     * @return
     */
    public Date getStartTime(int type, int lastWeekOrMonth) {
        if (type == TimeEnum.WEEK.getValue()) {
            return DateUtil.getStartDayOfWeekNo(DateUtil.getNowYear(), lastWeekOrMonth);
        }
        if (type == TimeEnum.MONTH.getValue()) {
            return DateUtil.getStartDayOfMonthNo(DateUtil.getNowYear(), lastWeekOrMonth);
        }
        return null;
    }

    /**
     * 获取周/月结束时间
     *
     * @param type
     * @param lastWeekOrMonth
     * @return
     */
    public Date getEndTime(int type, int lastWeekOrMonth) {
        if (type == TimeEnum.WEEK.getValue()) {
            return DateUtil.getEndDayOfWeekNo(DateUtil.getNowYear(), lastWeekOrMonth);
        }
        if (type == TimeEnum.MONTH.getValue()) {
            return DateUtil.getEndDayOfMonthNo(DateUtil.getNowYear(), lastWeekOrMonth);
        }
        return null;
    }


    /**
     * 获取标题的日期
     */
    public List<Item> getTitleYear(Date startTime, Date endTime, int row, int column) {

        String begin = DateUtil.getDate2StringFormat(startTime, "yyyy/MM/dd");
        String end = DateUtil.getDate2StringFormat(endTime, "yyyy/MM/dd");
        Item date1 = new Item(begin, row, column);
        Item date2 = new Item(end, row, column + 1);
        return Arrays.asList(date1, date2);
    }


    /**
     * 获取生成的excel路径
     */
    public String getExportPath(String fileName, int type, int lastWeekOrMonth) {
        //获取excel导出路径
        String exportPath = PropertiesUtil.getString(Constants.EXCEL_EXPORT_PATH);

        String str = fileName + "_" + DateUtil.getNowYear() + "_第" + lastWeekOrMonth;
        String newFileName = "";
        if (type == TimeEnum.WEEK.getValue()) {
            newFileName = str + "周.xls";
        }
        if (type == TimeEnum.MONTH.getValue()) {
            newFileName = str + "月.xls";
        }

        //生成导出路径
        if (type == TimeEnum.WEEK.getValue()) {
            exportPath = exportPath + File.separator + "周报";
        }
        if (type == TimeEnum.MONTH.getValue()) {
            exportPath = exportPath + File.separator + "月报";
        }
        if (!FileUtil.isExit(exportPath)) {
            FileUtil.createPath(exportPath);
        }
        exportPath = exportPath + File.separator + newFileName;
        return exportPath;
    }


    /**
     * 保存文件地址到数据库
     */
    public void save2DB(Integer ofType, String exportPath, Integer type, int lastWeekOrMonth) {
        //文件信息保存到数据库
        Export export = new Export();
        export.setOfType(ofType);
        export.setName(exportPath.substring(exportPath.lastIndexOf("\\") + 1, exportPath.length()));
        export.setPdfPath(exportPath.replace(".xls", ".pdf"));
        export.setExcelPath(exportPath);
        export.setType(type);
        export.setValue(lastWeekOrMonth);
        export.setCrtTime(new Date());

        exportService.insert(export);
        LogUtil.info(Thread.currentThread().getName() + ": 保存excel地址成功");
    }


    /**
     * 导出报表
     */
    public void doExport(List<Item> data, String templateFilePath, Integer type, OfTypeEnum ofType, int lastWeekOrMonth) {
        String exportPath = "";
        try {
            //获取文件导出路径
            exportPath = getExportPath(ofType.getName(), type, lastWeekOrMonth);

            //开始导出excel
            ExcelUtil.exportExcel(exportPath, templateFilePath, data);
            LogUtil.info(Thread.currentThread().getName() + ": excel导出完成");

            //文件信息保存到数据库
            save2DB(ofType.getValue(), exportPath, type, lastWeekOrMonth);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.error(e);
        }

        try {
            //将excel转换成pdf供前端预览
            PDFUtil.excel2Pdf(exportPath, exportPath.replace("xls", "pdf"));
            LogUtil.info(Thread.currentThread().getName() + ": 转化pdf完成");
        } catch (Exception e) {
            LogUtil.error(e.getCause());
        }

    }


}
