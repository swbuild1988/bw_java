package com.bandweaver.tunnel.controller.omm;


import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.bandweaver.tunnel.common.biz.dto.omm.DefectDto;
import com.bandweaver.tunnel.common.biz.dto.omm.MaintenanceOrderDto;
import com.bandweaver.tunnel.common.biz.itf.omm.*;
import com.bandweaver.tunnel.common.biz.pojo.Item;
import com.bandweaver.tunnel.common.biz.pojo.omm.*;
import com.bandweaver.tunnel.common.biz.vo.omm.DefectVo;
import com.bandweaver.tunnel.common.biz.vo.omm.MaintenanceOrderVo;
import com.bandweaver.tunnel.common.platform.constant.Constants;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.*;
import org.apache.poi.hssf.usermodel.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.constant.omm.EquipmentStatusEnum;
import com.bandweaver.tunnel.common.biz.dto.StoreDto;
import com.bandweaver.tunnel.common.biz.dto.TunnelSimpleDto;
import com.bandweaver.tunnel.common.biz.dto.omm.EquipmentDto;
import com.bandweaver.tunnel.common.biz.itf.SectionService;
import com.bandweaver.tunnel.common.biz.itf.StoreService;
import com.bandweaver.tunnel.common.biz.itf.TunnelService;
import com.bandweaver.tunnel.common.biz.pojo.ListPageUtil;
import com.bandweaver.tunnel.common.biz.pojo.Section;
import com.bandweaver.tunnel.common.biz.vo.omm.EquipmentVo;
import com.bandweaver.tunnel.common.platform.constant.StatusCodeEnum;
import com.bandweaver.tunnel.common.platform.log.DescEnum;
import com.bandweaver.tunnel.common.platform.log.WriteLog;

/**
 * 设备管理
 *
 * @author shaosen
 * @date 2018年5月30日
 */
@Controller
@ResponseBody
public class EquipmentController {
    @Autowired
    private EquipmentService equipmentService;
    @Autowired
    private TunnelService tunnelService;
    @Autowired
    private EquipmentVenderService equipmentVenderService;
    @Autowired
    private EquipmentModelService equipmentModelService;
    @Autowired
    private EquipmentTypeService equipmentTypeService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private SectionService sectionService;
    @Autowired
    private DefectService defectService;
    @Autowired
    private MaintenanceOrderService maintenanceOrderService;


    /**
     * 添加设备
     *
     * @param name         设备名称
     * @param type         设备类型
     * @param runTime      投运时间
     * @param status       设备状态（枚举）
     * @param tunnelId     管廊id
     * @param venderId     供应商id
     * @param modelId      设备型号id
     * @param objId        关联对象id
     * @param sectionId    管舱id
     * @param qaTerm       质保期限
     * @param assetNo      资产编码
     * @param ratedVoltage 额定电压
     * @param range        量程
     * @param factory      厂家
     * @param brand        品牌
     * @return {"msg":"请求成功","code":"200","data":{}}
     * @throws
     * @author shaosen
     * @date 2018年6月13日
     */
    @RequiresPermissions("equipment:add")
    @WriteLog(DescEnum.OMM_ADD_EQUIPMENT)
    @RequestMapping(value = "equipments", method = RequestMethod.POST)
    public JSONObject addEquipment(@RequestBody Equipment equipment) {
        equipmentService.addEquipment(equipment);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }

    /**
     * 批量添加设备
     *
     * @param 参数参考添加
     * @return
     * @author ya.liu
     * @Date 2019年4月22日
     */
    @RequiresPermissions("equipment:add")
    @WriteLog(DescEnum.OMM_ADD_EQUIPMENT)
    @RequestMapping(value = "equipments/batch", method = RequestMethod.POST)
    public JSONObject addBatch(@RequestBody List<Equipment> list) {
        for (Equipment equipment : list) {
            equipmentService.addEquipment(equipment);
        }
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }

    /**
     * 分页查询
     *
     * @param name         设备名称（支持模糊查询）
     * @param type         设备类型
     * @param status       设备状态（枚举）
     * @param tunnelId     管廊id
     * @param venderId     供应商id
     * @param modelId      设备型号id
     * @param sectionId    管舱id
     * @param qaTerm       质保期限
     * @param assetNo      资产编码
     * @param ratedVoltage 额定电压
     * @param range        量程
     * @param factory      厂家
     * @param brand        品牌
     * @param startTime    开始时间
     * @param endTime      结束时间
     * @param pageNum      必须
     * @param pageSize     必须
     * @return {"msg":"请求成功","code":"200","data":{"total":11,"list":[{"id":1,"name":"安全防范设备3","type":1,"typeName":"安全防范","crtTime":1525406400000,"status":1,"statusName":"运行中","tunnel":{"id":1,"name":"凤岭北路"},"model":{"id":1,"name":"model-1","crtTime":1531901454000},"vender":{"id":1,"name":"张三","crtTime":1531901454000}},{"id":2,"assetNo":"sn001","name":"安全防范设备1","type":1,"typeName":"安全防范","crtTime":1525406400000,"serviceLife":1000,"status":1,"statusName":"运行中","imgUrl":"\\upload\\equipment\\安全防范\\model-2\\testimg.jpg","tunnel":{"id":1,"name":"凤岭北路"},"model":{"id":2,"name":"model-2","crtTime":1531901454000},"vender":{"id":1,"name":"张三","crtTime":1531901454000}}],"pageNum":1,"pageSize":2,"size":2,"startRow":1,"endRow":2,"pages":6,"prePage":0,"nextPage":2,"isFirstPage":true,"isLastPage":false,"hasPreviousPage":false,"hasNextPage":true,"navigatePages":8,"navigatepageNums":[1,2,3,4,5,6],"navigateFirstPage":1,"navigateLastPage":6,"firstPage":1,"lastPage":6}}
     * @throws
     * @author shaosen
     * @date 2018年5月29日
     */
    @RequiresPermissions("equipment:list")
    @RequestMapping(value = "equipments/datagrid", method = RequestMethod.POST)
    public JSONObject dataGrid(@RequestBody EquipmentVo vo) {
        List<EquipmentDto> list = equipmentService.getEquipmentListByCondition(vo);
        if (vo.getStoreId() != null)
            list = list.stream().filter(a -> vo.getStoreId().equals(a.getSection() == null ? null : a.getSection().getStoreId())).collect(Collectors.toList());

        ListPageUtil<EquipmentDto> page = new ListPageUtil<>(list, vo.getPageNum(), vo.getPageSize());
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, page);
    }

    /**
     * 不分页查询
     *
     * @param name         设备名称（支持模糊查询）
     * @param type         设备类型
     * @param status       设备状态（枚举）
     * @param sectionId    舱段id
     * @param venderId     供应商id
     * @param modelId      设备型号id
     * @param qaTerm       质保期限
     * @param assetNo      资产编码
     * @param ratedVoltage 额定电压
     * @param range        量程
     * @param factory      厂家
     * @param brand        品牌
     * @param startTime    开始时间
     * @param endTime      结束时间
     * @return {"msg":"请求成功","code":"200","data":{"total":11,"list":[{"id":1,"name":"安全防范设备3","type":1,"typeName":"安全防范","crtTime":1525406400000,"status":1,"statusName":"运行中","tunnel":{"id":1,"name":"凤岭北路"},"model":{"id":1,"name":"model-1","crtTime":1531901454000},"vender":{"id":1,"name":"张三","crtTime":1531901454000}},{"id":2,"assetNo":"sn001","name":"安全防范设备1","type":1,"typeName":"安全防范","crtTime":1525406400000,"serviceLife":1000,"status":1,"statusName":"运行中","imgUrl":"\\upload\\equipment\\安全防范\\model-2\\testimg.jpg","tunnel":{"id":1,"name":"凤岭北路"},"model":{"id":2,"name":"model-2","crtTime":1531901454000},"vender":{"id":1,"name":"张三","crtTime":1531901454000}}],"pageNum":1,"pageSize":2,"size":2,"startRow":1,"endRow":2,"pages":6,"prePage":0,"nextPage":2,"isFirstPage":true,"isLastPage":false,"hasPreviousPage":false,"hasNextPage":true,"navigatePages":8,"navigatepageNums":[1,2,3,4,5,6],"navigateFirstPage":1,"navigateLastPage":6,"firstPage":1,"lastPage":6}}
     * @throws
     * @author ya.liu
     * @date 2019年4月8日
     */
    @RequestMapping(value = "equipments/condition", method = RequestMethod.POST)
    public JSONObject getEquipmentsByCondition(@RequestBody(required = false) EquipmentVo vo) {
        List<EquipmentDto> list = equipmentService.getEquipmentListByCondition(vo);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, list);
    }

    /**
     * 获取所有设备列表
     *
     * @param
     * @return JSONObject
     * @throws
     * @author shaosen
     * @date 2018年6月6日
     */
    @RequestMapping(value = "equipments", method = RequestMethod.GET)
    public JSONObject getAllEquipmentList() {
        List<EquipmentDto> list = equipmentService.getAllEquipmentList();
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, list);
    }

    /**
     * 通过id获取设备信息
     *
     * @param id 设备id
     * @return {"msg":"请求成功","code":"200","data":{"id":1,"name":"安全防范设备3","type":1,"typeName":"安全防范","crtTime":1525406400000,"serviceLife":1000,"status":1,"statusName":"运行中","imgUrl":"\\upload\\equipment\\安全防范\\model-1\\testimg.jpg","tunnel":{"id":1,"name":"凤岭北路"},"model":{"id":1,"name":"model-1","crtTime":1531901454000},"vender":{"id":1,"name":"张三","crtTime":1531901454000}}}
     * @throws
     * @author shaosen
     * @date 2018年6月6日
     */
    @RequestMapping(value = "equipments/{id}", method = RequestMethod.GET)
    public JSONObject getEquipmentById(@PathVariable("id") Integer id) {
        EquipmentDto dto = equipmentService.getEquipmentById(id);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, dto);
    }

    /**
     * 更新设备
     *
     * @param equipment 参考添加参数列表
     * @return {"msg":"请求成功","code":"200","data":{}}
     * @throws
     * @author shaosen
     * @date 2018年5月29日
     */
    @RequiresPermissions("equipment:update")
    @WriteLog(DescEnum.OMM_UPDATE_EQUIPMENT)
    @RequestMapping(value = "equipments", method = RequestMethod.PUT)
    public JSONObject updateEquipment(@RequestBody Equipment equipment) {
        equipmentService.updateEquipmentById(equipment);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }

    /**
     * 通过监测对象id修改设备状态信息
     *
     * @param objId  对象id
     * @param status 设备状态
     * @return
     * @author ya.liu
     * @Date 2019年7月22日
     */
    @RequiresPermissions("equipment:update")
    @WriteLog(DescEnum.OMM_UPDATE_EQUIPMENT)
    @RequestMapping(value = "objects/equipments", method = RequestMethod.PUT)
    public JSONObject updateEquipmentByObjId(@RequestBody Equipment e) {
        Equipment equipment = equipmentService.getEquipmentByObj(e.getObjId());
        // 判断该设备是否处于正常状态，如果不是则跳过
        if (equipment.getStatus().equals(EquipmentStatusEnum.NORMAL.getValue())) {
            equipment.setStatus(e.getStatus());
            equipmentService.updateEquipmentById(equipment);
        }
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }

    /**
     * 根据id删除设备
     *
     * @param id 设备id
     * @return {"msg":"请求成功","code":"200","data":{}}
     * @throws
     * @author shaosen
     * @date 2018年6月20日
     */
    @RequiresPermissions("equipment:delete")
    @WriteLog(DescEnum.OMM_DELETE_EQUIPMENT)
    @RequestMapping(value = "equipments/{id}", method = RequestMethod.DELETE)
    public JSONObject deleteById(@PathVariable Integer id) {
        equipmentService.deleteById(id);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }


    /**
     * 图片上传
     *
     * @param id 设备id
     * @return {"msg":"请求成功","code":"200","data":{}}
     * @throws IllegalStateException
     * @throws IOException
     * @throws
     * @author shaosen
     * @date 2018年5月31日
     */
    /*如果只是上传一个文件，则只需要MultipartFile类型接收文件即可，而且无需显式指定@RequestParam注解   
	  如果想上传多个文件，那么这里就要用MultipartFile[]类型来接收文件，并且还要指定@RequestParam注解 
	  并且上传多个文件时，前台表单中的所有<input type="file"/>的name都应该是file，否则参数里的file无法获取到所有上传的文件  */
    @RequestMapping(value = "equipments/{id}/file", method = {RequestMethod.POST, RequestMethod.GET})
    public JSONObject uploadImg(@PathVariable("id") Integer id, @RequestParam("file") MultipartFile file) throws IllegalStateException, IOException {

        EquipmentDto equipment = equipmentService.getEquipmentById(id);
        String realPath = ContextUtil.getRequest().getServletContext().getRealPath("/upload/equipment");
        String originalFilename = file.getOriginalFilename();
        realPath = realPath + "\\" + equipment.getTypeName() + "\\" + equipment.getModel().getName();

        File f = new File(realPath);
        //如果文件夹不存在就创建
        if (!f.exists()) {
            f.mkdirs();
        }
        file.transferTo(new File(realPath, originalFilename));
        System.out.println("图片路径：" + realPath + "\\" + originalFilename);

        //更新设备型号对应的图片地址
        Equipment e = new Equipment();
        e.setModelId(equipment.getModelId());
        e.setImgUrl("\\" + "upload\\equipment" + "\\" + equipment.getTypeName() + "\\" + equipment.getModel().getName() + "\\" + originalFilename);
        //equipmentService.updateEquipmentByModelId(e);

        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }


    /**
     * 获取设备类型图片
     *
     * @param id 设备id
     * @return jpg 图片
     * @throws IOException
     * @throws
     * @author shaosen
     * @Date 2018年7月26日
     */
    @RequestMapping(value = "equipments/{id}/img", method = RequestMethod.GET)
    public JSONObject getImg(@PathVariable Integer id, HttpServletResponse response) throws IOException {
        FileInputStream is = null;
        ByteArrayOutputStream bos = null;
        ServletOutputStream outputStream = null;
        try {
            //根据设备类型和型号查找图片位置
            EquipmentDto dto = equipmentService.getEquipmentById(id);
            if (dto == null) {
                throw new RuntimeException("没有获取到id=【" + id + "】的设备信息");
            }
            String imgUrl = dto.getImgUrl();
            String realPath = ContextUtil.getRequest().getServletContext().getRealPath(imgUrl);
            is = new FileInputStream(realPath);

            byte[] buffer = new byte[1024];
            int len = 0;
            bos = new ByteArrayOutputStream();
            while ((len = is.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }
            bos.flush();

            outputStream = response.getOutputStream();
            outputStream.write(bos.toByteArray());
        } catch (RuntimeException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                is.close();
            }
            if (bos != null) {
                bos.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }

        }
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }

    /******************************************************************/
    /*设备供应商模块
    /******************************************************************/

    /**
     * 添加设备供应商
     *
     * @param name
     * @param crtTime
     * @return
     * @author ya.liu
     * @Date 2018年11月27日
     */
    @RequestMapping(value = "equipment-venders", method = RequestMethod.POST)
    public JSONObject insert(@RequestBody EquipmentVender e) {
        equipmentVenderService.insert(e);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }

    /**
     * 获取所有供应商列表
     *
     * @param
     * @return {"msg":"请求成功","code":"200","data":[{"id":1,"name":"张三","crtTime":1531901454000},{"id":2,"name":"李四","crtTime":1531901454000},{"id":3,"name":"王五","crtTime":1531901454000}]}
     * @throws
     * @author shaosen
     * @date 2018年6月6日
     */
    @RequestMapping(value = "equipment-venders", method = RequestMethod.GET)
    public JSONObject getAllEquipmentVenderList() {
        List<EquipmentVender> list = equipmentVenderService.getAllEquipmentVenderList();
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, list);
    }

    /**
     * 通过id获取设备供应商
     *
     * @param id 供应商id
     * @return {"msg":"请求成功","code":"200","data":{"id":1,"name":"张三","crtTime":1531901454000}}
     * @throws
     * @author shaosen
     * @date 2018年6月6日
     */
    @RequestMapping(value = "equipment-venders/{id}", method = RequestMethod.GET)
    public JSONObject getEquipmentVenderById(@PathVariable("id") Integer id) {
        EquipmentVender vender = equipmentVenderService.getEquipmentVenderById(id);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, vender);
    }

    /**
     * 删除设备供应商
     *
     * @param id
     * @return
     * @author ya.liu
     * @Date 2018年11月27日
     */
    @RequestMapping(value = "equipment-venders/{id}", method = RequestMethod.DELETE)
    public JSONObject deleteVenders(@PathVariable("id") Integer id) {
        equipmentVenderService.deleteByPrimaryKey(id);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }

    /******************************************************************/
    /*设备类型模块
    /******************************************************************/

    /**
     * 添加设备类型
     *
     * @param name
     * @return
     * @author ya.liu
     * @Date 2018年11月27日
     */
    @RequestMapping(value = "equipment-types", method = RequestMethod.POST)
    public JSONObject insert(@RequestBody EquipmentType e) {
        equipmentTypeService.insert(e);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }

    /**
     * 获取所有设备类型列表
     *
     * @param
     * @return {"msg":"请求成功","code":"200","data":[{"id":1,"name":"安全防范"}]}
     * @throws
     * @author ya.liu
     * @date 2018年11月27日
     */
    @RequestMapping(value = "equipment-types", method = RequestMethod.GET)
    public JSONObject getAllEquipmentTypeList() {
        List<EquipmentType> list = equipmentTypeService.getAllEquipmentTypeList();
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, list);
    }

    /**
     * 通过id获取设备类型
     *
     * @param id 供应商id
     * @return {"msg":"请求成功","code":"200","data":{"id":1,"name":"安全防范"}}
     * @author ya.liu
     * @date 2018年11月27日
     */
    @RequestMapping(value = "equipment-types/{id}", method = RequestMethod.GET)
    public JSONObject getEquipmentTypeById(@PathVariable("id") Integer id) {
        EquipmentType type = equipmentTypeService.selectByPrimaryKey(id);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, type);
    }

    /**
     * 删除设备类型
     *
     * @param id
     * @return
     * @author ya.liu
     * @Date 2018年11月27日
     */
    @RequestMapping(value = "equipment-types/{id}", method = RequestMethod.DELETE)
    public JSONObject deleteTypes(@PathVariable("id") Integer id) {
        equipmentTypeService.deleteByPrimaryKey(id);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }

    /******************************************************************/
    /*设备型号模块
    /******************************************************************/

    /**
     * 添加设备型号
     *
     * @param typeId
     * @param name
     * @param crtTime
     * @return
     * @author ya.liu
     * @Date 2018年11月27日
     */
    @RequestMapping(value = "equipment-models", method = RequestMethod.POST)
    public JSONObject insert(@RequestBody EquipmentModel e) {
        equipmentModelService.insert(e);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }

    /**
     * 获取所有设备型号列表
     *
     * @param
     * @return {"msg":"请求成功","code":"200","data":[{"id":1,"name":"model-1","crtTime":1531901454000},{"id":2,"name":"model-2","crtTime":1531901454000},{"id":3,"name":"model-3","crtTime":1531901454000}]}
     * @throws
     * @author shaosen
     * @date 2018年6月6日
     */
    @RequestMapping(value = "equipment-models", method = RequestMethod.GET)
    public JSONObject getAllEquipmentModelList() {
        List<EquipmentModel> list = equipmentModelService.getAllEquipmentModelList();
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, list);
    }

    /**
     * 通过id获取设备型号
     *
     * @param id 设备型号id
     * @return {"msg":"请求成功","code":"200","data":{"id":1,"name":"model-1","crtTime":1531901454000}}
     * @throws
     * @author shaosen
     * @date 2018年6月6日
     */
    @RequestMapping(value = "equipment-models/{id}", method = RequestMethod.GET)
    public JSONObject getEquipmentModelById(@PathVariable("id") Integer id) {
        EquipmentModel model = equipmentModelService.getEquipmentModelById(id);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, model);
    }

    /**
     * 删除设备型号
     *
     * @param id
     * @return
     * @author ya.liu
     * @Date 2018年11月27日
     */
    @RequestMapping(value = "equipment-models/{id}", method = RequestMethod.DELETE)
    public JSONObject delete(@PathVariable("id") Integer id) {
        equipmentModelService.deleteByPrimaryKey(id);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }

    /**
     * 获取某一类型下的设备型号列表
     *
     * @param typeId
     * @return
     * @author ya.liu
     * @Date 2018年11月29日
     */
    @RequestMapping(value = "types/{typeId}/equipment-models", method = RequestMethod.GET)
    public JSONObject getEquipmentModelByTypeId(@PathVariable("typeId") Integer typeId) {
        List<EquipmentModel> list = equipmentModelService.getEquipmentModelByTypeId(typeId);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, list);
    }

    /******************************************************************/
    /*以下是管廊设备信息总览
    /******************************************************************/

    /**
     * 获取各种类型设备的数据统计
     *
     * @param
     * @return {"msg":"请求成功","code":"200","data":[{"val":3,"key":"安全防范"},{"val":2,"key":"视频监控"},{"val":2,"key":"环境监测"},{"val":1,"key":"电缆本体"},{"val":1,"key":"火灾报警"},{"val":2,"key":"隧道通信"}]}
     * @throws
     * @author shaosen
     * @date 2018年5月30日
     */
    @RequestMapping(value = "equipments/type/count", method = RequestMethod.GET)
    public JSONObject getCountOfType() {
        List<JSONObject> result = new ArrayList<>();
        List<EquipmentType> equipmentTypes = equipmentTypeService.getAllEquipmentTypeList();
        for (EquipmentType equipmentType : equipmentTypes) {
            JSONObject o = new JSONObject();
            o.put("key", equipmentType.getName());
            o.put("val", equipmentService.getCountByCondition(null, null, equipmentType.getId()));
            result.add(o);
        }
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, result);
    }


    /**
     * 获取每个类型设备下，各个状态设备的个数
     *
     * @param
     * @return {"msg":"请求成功","code":"200","data":[{"val":[{"val":3,"key":"总数"},{"val":2,"key":"运行中"},{"val":1,"key":"故障"},{"val":0,"key":"备品"}],"key":"安全防范"},{"val":[{"val":2,"key":"总数"},{"val":2,"key":"运行中"},{"val":0,"key":"故障"},{"val":0,"key":"备品"}],"key":"视频监控"},{"val":[{"val":2,"key":"总数"},{"val":2,"key":"运行中"},{"val":0,"key":"故障"},{"val":0,"key":"备品"}],"key":"环境监测"},{"val":[{"val":1,"key":"总数"},{"val":1,"key":"运行中"},{"val":0,"key":"故障"},{"val":0,"key":"备品"}],"key":"电缆本体"},{"val":[{"val":1,"key":"总数"},{"val":1,"key":"运行中"},{"val":0,"key":"故障"},{"val":0,"key":"备品"}],"key":"火灾报警"},{"val":[{"val":2,"key":"总数"},{"val":2,"key":"运行中"},{"val":0,"key":"故障"},{"val":0,"key":"备品"}],"key":"隧道通信"}]}
     * @throws
     * @author shaosen
     * @date 2018年5月30日
     */
    @RequestMapping(value = "equipments/type/status/count", method = RequestMethod.GET)
    public JSONObject getCountOfTypeAndStatus() {
        List<JSONObject> result = new ArrayList<>();
        List<EquipmentType> equipmentTypes = equipmentTypeService.getAllEquipmentTypeList();
        for (EquipmentType equipmentType : equipmentTypes) {
            JSONObject o = new JSONObject();
            o.put("key", equipmentType.getName());

            List<JSONObject> typeVals = new ArrayList<>();
//            JSONObject o2 = new JSONObject();
//            o2.put("key", "总数");
//            o2.put("val", equipmentService.getCountByCondition(null, null, equipmentType.getId()));
//            typeVals.add(o2);
            for (EquipmentStatusEnum statusEnum : EquipmentStatusEnum.values()) {
                JSONObject o2 = new JSONObject();
                o2.put("key", statusEnum.getName());
                o2.put("val", equipmentService.getCountByCondition(null, statusEnum.getValue(), equipmentType.getId()));
                typeVals.add(o2);
            }

            o.put("val", typeVals);
            result.add(o);
        }
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, result);
    }

    /**
     * 每条管廊的故障/正常设备数
     *
     * @return
     * @author liuya
     * @Date 2018年9月1日
     */
    @RequestMapping(value = "tunnels/equipments/status", method = RequestMethod.GET)
    public JSONObject getTunnelEquipmentBrokenCount() {
        List<TunnelSimpleDto> tunnelList = tunnelService.getList();
        List<JSONObject> list = new ArrayList<>();
        for (TunnelSimpleDto tunnel : tunnelList) {
            JSONObject obj = new JSONObject();
            obj.put("key", tunnel.getName());
            List<JSONObject> statusList = new ArrayList<>();
            JSONObject brokenObj = new JSONObject();
            brokenObj.put("key", "故障");
            brokenObj.put("val", equipmentService.getCountByCondition(tunnel.getId(), EquipmentStatusEnum.BROKEN.getValue(), null));
            statusList.add(brokenObj);
            JSONObject normalObj = new JSONObject();
            normalObj.put("key", "正常");
            normalObj.put("val", equipmentService.getCountByCondition(tunnel.getId(), EquipmentStatusEnum.NORMAL.getValue(), null));
            statusList.add(normalObj);
            obj.put("val", statusList);
            list.add(obj);
        }
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, list);
    }

    /**
     * 每条管廊设备总数
     *
     * @return {"msg":"请求成功","code":"200","data":[{"key":"古城大街","val":10},{"key":"金科路","val":12}]}
     * @author liuya
     * @Date 2018年9月1日
     */
    @RequestMapping(value = "tunnels/equipments/count", method = RequestMethod.GET)
    public JSONObject getTunnelEquipmentCount() {
        List<TunnelSimpleDto> tunnelList = tunnelService.getList();
        List<JSONObject> list = new ArrayList<>();
        for (TunnelSimpleDto tunnel : tunnelList) {
            JSONObject map = new JSONObject();
            map.put("key", tunnelService.getNameById(tunnel.getId()));
            map.put("val", equipmentService.getCountByCondition(tunnel.getId(), null, null));
            list.add(map);
        }
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, list);
    }

    /**
     * 不同设备类型下，每条管廊的设备数
     *
     * @return {"msg":"请求成功","code":"200","data":{"安全防范":[{"key":"古城大街","val":3},{"key":"金科路","val":1}],"火灾报警":[{"key":"古城大街","val":3},{"key":"金科路","val":1}]}
     * @author liuya
     * @Date 2018年9月3日
     */
    @RequestMapping(value = "tunnels/equipments/types", method = RequestMethod.GET)
    public JSONObject getCountByTunnelAndEquipment() {
        JSONObject json = new JSONObject();
        List<EquipmentType> equipmentTypes = equipmentTypeService.getAllEquipmentTypeList();
        List<TunnelSimpleDto> tunnelList = tunnelService.getList();
        for (EquipmentType type : equipmentTypes) {
            List<JSONObject> list = new ArrayList<>();
            for (TunnelSimpleDto tunnel : tunnelList) {
                JSONObject map = new JSONObject();
                map.put("key", tunnel.getName());
                map.put("val", equipmentService.getCountByCondition(tunnel.getId(), null, type.getId()));
                list.add(map);
            }
            json.put(type.getName(), list);
        }
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, json);
    }

    /**
     * 不同管廊下，每个设备类型的设备数
     *
     * @return
     * @author ya.liu
     * @Date 2019年1月17日
     */
    @RequestMapping(value = "types/equipments/tunnels", method = RequestMethod.GET)
    public JSONObject getCountByTunnelAndType() {
        List<EquipmentType> equipmentTypes = equipmentTypeService.getAllEquipmentTypeList();
        List<TunnelSimpleDto> tunnelList = tunnelService.getList();

        JSONObject obj = new JSONObject();
        List<String> types = equipmentTypes.stream().map(a -> a.getName()).collect(Collectors.toList());
        List<String> tunnels = tunnelList.stream().map(a -> a.getName()).collect(Collectors.toList());
        List<JSONObject> list = new ArrayList<>();
        for (EquipmentType type : equipmentTypes) {
            JSONObject json = new JSONObject();
            List<Integer> typeList = new ArrayList<>();
            for (TunnelSimpleDto tunnel : tunnelList) {
                int count = equipmentService.getCountByCondition(tunnel.getId(), null, type.getId());
                typeList.add(count);
            }
            json.put("data", typeList);
            list.add(json);
        }

        obj.put("types", types);
        obj.put("tunnels", tunnels);
        obj.put("list", list);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, obj);
    }

    /**
     * 通过moid获取设备信息
     *
     * @param objId
     * @return
     * @author liuya
     * @Date 2018年9月7日
     */
    @RequestMapping(value = "measobjs/{objId}/equipment", method = RequestMethod.GET)
    public JSONObject getEquipmentByObj(@PathVariable("objId") Integer objId) {
        EquipmentDto equipment = equipmentService.getEquipmentByObj(objId);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, equipment);
    }

    /**
     * 取消mo与设备关联，并修改设备的状态
     *
     * @param status 设备状态
     * @param id     设备id
     * @return
     * @author liuya
     * @Date 2018年9月7日
     */
    @RequestMapping(value = "equipments/status", method = RequestMethod.PUT)
    public JSONObject updateEquipmentOfObj(@RequestBody Equipment equipment) {
        equipmentService.updateEquipmentOfObj(equipment);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }

    /**
     * 获取某条管廊各舱的设备数目
     *
     * @param tunnelId
     * @return
     * @author ya.liu
     * @Date 2019年5月19日
     */
    @RequestMapping(value = "tunnels/{tunnelId}/stores/equipments/count", method = RequestMethod.GET)
    public JSONObject getEquipmentCountByStoreId(@PathVariable("tunnelId") Integer tunnelId) {
        List<StoreDto> stores = storeService.getStoresByTunnelId(tunnelId);
        List<JSONObject> list = new ArrayList<>();
        for (StoreDto dto : stores) {
            JSONObject map = new JSONObject();
            map.put("id", dto.getId());
            map.put("name", dto.getName());
            Integer sum = 0;
            List<Integer> storeIds = new ArrayList<>();
            storeIds.add(dto.getId());
            List<Section> sections = sectionService.getSectionsByStoreIds(storeIds);
            for (Section section : sections) {
                int count = equipmentService.getCountBySectionId(section.getId());
                sum += count;
            }
            map.put("val", sum);
            list.add(map);
        }
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, list);
    }


    /**
     * 获取设备的维修报告
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "equipments/{id}/report")
    public JSONObject createEquipmentReport(@PathVariable("id") int id) {
        EquipmentDto equipmentDto = equipmentService.getEquipmentById(id);

        List<Item> data = new ArrayList<>();

        int rowIndex = 0;
        // 标题
        data.add(new Item("设备维修报告", rowIndex, 0));

        rowIndex++;
        // 设备类型
        data.add(new Item("设备类型", rowIndex, 0));
        data.add(new Item(equipmentDto.getName(), rowIndex, 1));
        // 检测对象编号
        data.add(new Item("检测对象", rowIndex, 2));
        data.add(new Item(equipmentDto.getObjId().toString(), rowIndex, 3));
        // 资产编码
        data.add(new Item("资产编码", rowIndex, 4));
        data.add(new Item(equipmentDto.getAssetNo(), rowIndex, 5));

        rowIndex++;
        // 所属管廊、区域、管仓
        data.add(new Item("所属管廊", rowIndex, 0));
        data.add(new Item(equipmentDto.getTunnel().getName(), rowIndex, 1));
        data.add(new Item("所属区域", rowIndex, 2));
        data.add(new Item(equipmentDto.getSection().getArea().getName(), rowIndex, 3));
        data.add(new Item("所属舱室", rowIndex, 4));
        data.add(new Item(equipmentDto.getSection().getStore().getName(), rowIndex, 5));

        rowIndex++;
        // 安装时间、使用时间、厂家
        data.add(new Item("安装时间", rowIndex, 0));
        data.add(new Item(DateUtil.getDate2String(equipmentDto.getRunTime()), rowIndex, 1));
        data.add(new Item("已使用时间", rowIndex, 2));
        long hour = DateUtil.getDiffHours(equipmentDto.getRunTime(), new Date());
        data.add(new Item((hour / 24) + "天" + (hour % 24) + "小时", rowIndex, 3));
        data.add(new Item("厂家", rowIndex, 4));
        data.add(new Item(equipmentDto.getFactory(), rowIndex, 5));

        rowIndex++;
        // 维修次数
        data.add(new Item("维修次数", rowIndex, 0));
        DefectVo vo = new DefectVo();
        vo.setObjectId(equipmentDto.getObjId());
        List<DefectDto> defects = defectService.getDefectsByCondition(vo);
        int repairNum = defects.size();
        data.add(new Item(repairNum, rowIndex, 1));

        rowIndex++;
        // 维修的系列抬头
        List<String> colNames = Arrays.asList("序号", "维修人", "开始时间", "结束时间", "维修结果", "描述");
        for (int i = 0; i < colNames.size(); i++) {
            data.add(new Item(colNames.get(i), rowIndex, i));
        }

        // 将所有维修记录加入
        for (int i = 0; i < repairNum; i++) {
            DefectDto defect = defects.get(i);
            MaintenanceOrderDto maintenanceOrderDto = maintenanceOrderService.getOrderByDefectId(defect.getId());

            rowIndex++;
            data.add(new Item(i + 1, rowIndex, 0));
            data.add(new Item(maintenanceOrderDto.getWorkerName(), rowIndex, 1));
            data.add(new Item(DateUtil.getDate2String(maintenanceOrderDto.getStartTime()), rowIndex, 2));
            data.add(new Item(DateUtil.getDate2String(maintenanceOrderDto.getEndTime()), rowIndex, 3));
            data.add(new Item(maintenanceOrderDto.getProcessStatus(), rowIndex, 4));
            data.add(new Item(maintenanceOrderDto.getDescribe(), rowIndex, 5));
        }

        rowIndex += 2;
        // 加入审核人、时间
        data.add(new Item("审核人", rowIndex, 4));
        rowIndex++;
        data.add(new Item("时间", rowIndex, 4));

        try {
            String xlsPath = getEquipmentFileName(equipmentDto.getAssetNo()) + ".xls";
            createExcel(xlsPath, data, rowIndex + 1, 6);
            String pdfPath = getEquipmentFileName(equipmentDto.getAssetNo()) + ".pdf";
            PDFUtil.excel2Pdf(xlsPath, pdfPath);
            LogUtil.info("设备维修报告转化pdf完成");
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.error(e);
            return CommonUtil.returnStatusJson(StatusCodeEnum.E_400, e);
        }

        return CommonUtil.success();
    }


    /**
     * pdf文件预览
     *
     * @param id
     * @param response
     */
    @RequestMapping(value = "equipments/{id}/report-view", method = RequestMethod.GET)
    public void preview(@PathVariable("id") Integer id, HttpServletResponse response) {
        FileInputStream fis = null;
        try {
            Equipment equipment = equipmentService.getEquipmentById(id);
            String pdfPath = getEquipmentFileName(equipment.getAssetNo()) + ".pdf";
            fis = new FileInputStream(new File(pdfPath));
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
     *
     * @param id
     * @param response
     * @return
     */
    @RequiresPermissions("report:download")
    @RequestMapping(value = "equipments/{id}/report-download", method = RequestMethod.GET)
    public void download(@PathVariable("id") Integer id, HttpServletResponse response) {
        FileInputStream fis = null;
        try {
            Equipment equipment = equipmentService.getEquipmentById(id);
            String excelPath = getEquipmentFileName(equipment.getAssetNo()) + ".xls";
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

    private void createExcel(String path, List<Item> data, int rowNum, int colNum) throws Exception {
        //获得Excel文件输出流
        FileOutputStream out = new FileOutputStream(new File(path));

        try {
            //创建excel工作簿对象
            HSSFWorkbook wb = new HSSFWorkbook();
            //创建excel页
            HSSFSheet sheet = wb.createSheet("POI导出测试");

            // 设置单元格格式
            HSSFCellStyle cellStyle = wb.createCellStyle();

            //将所有的单元格创建并加边框
            cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
            cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
            cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            // 自动换行
            cellStyle.setWrapText(true);
            for (int i = 0; i < rowNum; i++) {
                HSSFRow row = sheet.createRow(i);
                for (int j = 0; j < colNum; j++) {
                    HSSFCell cell = row.createCell(j);
                    cell.setCellStyle(cellStyle);
                }
            }

            // 将数据存到excel中
            ExcelUtil.fillExcelData(sheet, data);
            // 设置列宽
            int widths[] = {2500, 3000, 3000, 3000, 2500, 7000};
            for (int i = 0; i < widths.length; i++) {
                sheet.setColumnWidth(i, widths[i]);
            }

            // 合并首行
            ExcelUtil.mergedRegion(sheet, 0, 0, 0, 5);

            // 设置任务标题居中、加粗
            cellStyle = wb.createCellStyle();
            cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
            cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
            cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            HSSFFont font = wb.createFont();
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            font.setFontHeightInPoints((short) 14);
            cellStyle.setFont(font);
            HSSFCell cell = sheet.getRow(0).getCell(0);
            cell.setCellStyle(cellStyle);


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

    // 生成文件名
    private String getEquipmentFileName(String assetNo) {
        String exportPath = PropertiesUtil.getString(Constants.EQUIPMENT_PATH);
        if (!FileUtil.isExit(exportPath)) {
            FileUtil.createPath(exportPath);
        }
        String path = exportPath + File.separator + "设备" + assetNo + "维修记录";
        return path;
    }

    private void close(FileInputStream fis) {
        if (fis != null) {
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
                LogUtil.error(e);
            }
        }
    }

}
