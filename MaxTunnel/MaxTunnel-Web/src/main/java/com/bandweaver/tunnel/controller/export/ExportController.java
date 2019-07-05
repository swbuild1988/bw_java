package com.bandweaver.tunnel.controller.export;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.constant.TimeEnum;
import com.bandweaver.tunnel.common.biz.itf.ExportService;
import com.bandweaver.tunnel.common.biz.pojo.Export;
import com.bandweaver.tunnel.common.platform.constant.Constants;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.CommonUtil;
import com.bandweaver.tunnel.service.common.export.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 报表导出管理
 */
@Controller
@ResponseBody
public class ExportController {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private ExportService exportService;


    /**
     * pdf文件预览
     * @param id
     * @param response
     */
    @RequestMapping(value = "reports/preview/{id}",method = RequestMethod.GET)
    public void preview(@PathVariable("id")Integer id, HttpServletResponse response){
        FileInputStream fis = null;
        try {
            Export export = exportService.get(id);
            fis = new FileInputStream(new File(export.getPdfPath()));
            byte[] data = new byte[fis.available()];
            fis.read(data);
            response.getOutputStream().write(data);

        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.error(e);
        } finally {
            close(fis);
        }
    }

    /**
     * excel文件下载
     * @param id
     * @param response
     * @return
     */
    @RequiresPermissions("report:download")
    @RequestMapping(value = "reports/download/{id}",method = RequestMethod.GET)
    public void download(@PathVariable("id")Integer id, HttpServletResponse response){
        FileInputStream fis = null;
        try {
            Export export = exportService.get(id);
            String excelPath = export.getExcelPath();
            fis = new FileInputStream(new File(excelPath));
            byte[] data = new byte[fis.available()];
            fis.read(data);
            //获取输出文件名
            String downloadFileName = excelPath.substring(excelPath.lastIndexOf("\\"), excelPath.length());
            response.setHeader("Content-disposition", "attachment; filename=" + new String(downloadFileName.getBytes("GBK"), "ISO-8859-1") + ""); // 设定输出文件头
            response.setContentType("application/vnd.ms-excel"); // 定义输出类型
            response.getOutputStream().write(data);

        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.error(e);
        } finally {
            close(fis);
        }
    }

    private void close(FileInputStream fis) {
        if (fis != null){
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
                LogUtil.error(e);
            }
        }
    }


    /**
     * 查询报表记录
     * @param reqJson
     * @return
     */
    @RequestMapping(value = "reports/list",method = RequestMethod.POST)
    public JSONObject getList(@RequestBody JSONObject reqJson){
        CommonUtil.hasAllRequired(reqJson,"ofType,type");
        Integer ofType = reqJson.getInteger("ofType");
        Integer type = reqJson.getInteger("type");
        List<Export> list = exportService.getListByOfTypeAndType(ofType,type);
        if (list == null || list.isEmpty()) {
            return CommonUtil.success();
        }

        // 排序
        list = list.stream().sorted(Comparator.comparing(Export::getName)).collect(Collectors.toList());
        return CommonUtil.success(list);
    }


    /**
     * 手动生成报表
     * @return
     */
    @RequestMapping(value = "reports/mannual",method = RequestMethod.GET)
    public JSONObject test(){
        String fileDirPath = request.getServletContext().getRealPath("files/excel")+ File.separator;

        new Thread(new AlarmTask(fileDirPath, TimeEnum.MONTH.getValue()),"告警线程").start();
        new Thread(new InspectionTask(fileDirPath, TimeEnum.MONTH.getValue()),"巡检线程").start();
        new Thread(new MeasObjTask(fileDirPath, TimeEnum.MONTH.getValue()),"监测线程").start();
        new Thread(new EnergyTask(fileDirPath, TimeEnum.MONTH.getValue()),"能耗线程").start();
        new Thread(new EquipmentTask(fileDirPath, TimeEnum.MONTH.getValue()),"设备线程").start();

        new Thread(new AlarmTask(fileDirPath, TimeEnum.WEEK.getValue()),"告警线程").start();
        new Thread(new InspectionTask(fileDirPath, TimeEnum.WEEK.getValue()),"巡检线程").start();
        new Thread(new MeasObjTask(fileDirPath, TimeEnum.WEEK.getValue()),"监测线程").start();
        new Thread(new EnergyTask(fileDirPath, TimeEnum.WEEK.getValue()),"能耗线程").start();
        new Thread(new EquipmentTask(fileDirPath, TimeEnum.WEEK.getValue()),"设备线程").start();

        return CommonUtil.success();
    }




}
