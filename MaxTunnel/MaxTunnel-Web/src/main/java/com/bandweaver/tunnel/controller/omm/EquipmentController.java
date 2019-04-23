package com.bandweaver.tunnel.controller.omm;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

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
import com.bandweaver.tunnel.common.biz.dto.TunnelSimpleDto;
import com.bandweaver.tunnel.common.biz.dto.omm.EquipmentDto;
import com.bandweaver.tunnel.common.biz.itf.TunnelService;
import com.bandweaver.tunnel.common.biz.itf.omm.EquipmentModelService;
import com.bandweaver.tunnel.common.biz.itf.omm.EquipmentService;
import com.bandweaver.tunnel.common.biz.itf.omm.EquipmentTypeService;
import com.bandweaver.tunnel.common.biz.itf.omm.EquipmentVenderService;
import com.bandweaver.tunnel.common.biz.pojo.Tunnel;
import com.bandweaver.tunnel.common.biz.pojo.omm.Equipment;
import com.bandweaver.tunnel.common.biz.pojo.omm.EquipmentModel;
import com.bandweaver.tunnel.common.biz.pojo.omm.EquipmentType;
import com.bandweaver.tunnel.common.biz.pojo.omm.EquipmentVender;
import com.bandweaver.tunnel.common.biz.vo.omm.EquipmentVo;
import com.bandweaver.tunnel.common.platform.constant.StatusCodeEnum;
import com.bandweaver.tunnel.common.platform.log.DescEnum;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.log.WriteLog;
import com.bandweaver.tunnel.common.platform.util.CommonUtil;
import com.bandweaver.tunnel.common.platform.util.ContextUtil;
import com.bandweaver.tunnel.common.platform.util.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 设备管理
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
    
    
    /**
     *  添加设备
     * @param name 设备名称
     * @param type 设备类型
     * @param runTime 投运时间
     * @param status 设备状态（枚举）
     * @param tunnelId 管廊id
     * @param venderId 供应商id
     * @param modelId 设备型号id
     * @param objId 关联对象id
     * @return {"msg":"请求成功","code":"200","data":{}}  
     * @throws
     * @author shaosen
     * @date 2018年6月13日
     */
    @WriteLog(DescEnum.OMM_ADD_EQUIPMENT)
    @RequestMapping(value="equipments",method=RequestMethod.POST)
    public JSONObject addEquipment(@RequestBody Equipment equipment) {
    	equipmentService.addEquipment(equipment);
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }
    
    
    /**
     *  分页查询
     * @param name 设备名称（支持模糊查询）
     * @param type 设备类型
     * @param status 设备状态（枚举）
     * @param tunnelId 管廊id
     * @param venderId 供应商id
     * @param modelId 设备型号id
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param pageNum 必须
     * @param pageSize 必须
     * @return {"msg":"请求成功","code":"200","data":{"total":11,"list":[{"id":1,"name":"安全防范设备3","type":1,"typeName":"安全防范","crtTime":1525406400000,"status":1,"statusName":"运行中","tunnel":{"id":1,"name":"凤岭北路"},"model":{"id":1,"name":"model-1","crtTime":1531901454000},"vender":{"id":1,"name":"张三","crtTime":1531901454000}},{"id":2,"assetNo":"sn001","name":"安全防范设备1","type":1,"typeName":"安全防范","crtTime":1525406400000,"serviceLife":1000,"status":1,"statusName":"运行中","imgUrl":"\\upload\\equipment\\安全防范\\model-2\\testimg.jpg","tunnel":{"id":1,"name":"凤岭北路"},"model":{"id":2,"name":"model-2","crtTime":1531901454000},"vender":{"id":1,"name":"张三","crtTime":1531901454000}}],"pageNum":1,"pageSize":2,"size":2,"startRow":1,"endRow":2,"pages":6,"prePage":0,"nextPage":2,"isFirstPage":true,"isLastPage":false,"hasPreviousPage":false,"hasNextPage":true,"navigatePages":8,"navigatepageNums":[1,2,3,4,5,6],"navigateFirstPage":1,"navigateLastPage":6,"firstPage":1,"lastPage":6}}  
     * @throws
     * @author shaosen
     * @date 2018年5月29日
     */
    @RequestMapping(value="equipments/datagrid",method=RequestMethod.POST)
    public JSONObject dataGrid(@RequestBody EquipmentVo vo) {
    	PageInfo<EquipmentDto> pageInfo = equipmentService.dataGrid(vo);
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, pageInfo);
    }
    
    /**
     * 获取所有设备列表
     * @param    
     * @return JSONObject  
     * @throws
     * @author shaosen
     * @date 2018年6月6日
     */
    @RequestMapping(value="equipments",method=RequestMethod.GET)
    public JSONObject getAllEquipmentList() {
    	List<EquipmentDto> list = equipmentService.getAllEquipmentList();
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, list);
    }
    
    /**
     	* 通过id获取设备信息
     * @param  id 设备id
     * @return {"msg":"请求成功","code":"200","data":{"id":1,"name":"安全防范设备3","type":1,"typeName":"安全防范","crtTime":1525406400000,"serviceLife":1000,"status":1,"statusName":"运行中","imgUrl":"\\upload\\equipment\\安全防范\\model-1\\testimg.jpg","tunnel":{"id":1,"name":"凤岭北路"},"model":{"id":1,"name":"model-1","crtTime":1531901454000},"vender":{"id":1,"name":"张三","crtTime":1531901454000}}}  
     * @throws
     * @author shaosen
     * @date 2018年6月6日
     */
    @RequestMapping(value="equipments/{id}",method=RequestMethod.GET)
    public JSONObject getEquipmentById(@PathVariable("id")Integer id) {
    	EquipmentDto dto = equipmentService.getEquipmentById(id);
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, dto);
    }
    
    /**
          * 更新设备
     * @param  equipment 参考添加参数列表
     * @return {"msg":"请求成功","code":"200","data":{}}  
     * @throws
     * @author shaosen
     * @date 2018年5月29日
     */
    @WriteLog(DescEnum.OMM_UPDATE_EQUIPMENT)
    @RequestMapping(value = "equipments", method=RequestMethod.PUT)
    public JSONObject updateEquipment(@RequestBody Equipment equipment) {
    	equipmentService.updateEquipmentById(equipment);
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }
    
    /**
     *  根据id删除设备
     * @param  id 设备id
     * @return {"msg":"请求成功","code":"200","data":{}}  
     * @throws
     * @author shaosen
     * @date 2018年6月20日
     */
    @WriteLog(DescEnum.OMM_DELETE_EQUIPMENT)
    @RequestMapping(value="equipments/{id}",method=RequestMethod.DELETE)
    public JSONObject deleteById(@PathVariable Integer id) {
    	equipmentService.deleteById(id);
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }
    
    
    /**
     *  图片上传
     * @param  id 设备id
     * @throws IllegalStateException
     * @throws IOException   
     * @return {"msg":"请求成功","code":"200","data":{}}   
     * @throws
     * @author shaosen
     * @date 2018年5月31日
     */
    /*如果只是上传一个文件，则只需要MultipartFile类型接收文件即可，而且无需显式指定@RequestParam注解   
	  如果想上传多个文件，那么这里就要用MultipartFile[]类型来接收文件，并且还要指定@RequestParam注解 
	  并且上传多个文件时，前台表单中的所有<input type="file"/>的name都应该是file，否则参数里的file无法获取到所有上传的文件  */
    @RequestMapping(value="equipments/{id}/file",method= {RequestMethod.POST,RequestMethod.GET})
    public JSONObject uploadImg(@PathVariable("id") Integer id, @RequestParam("file")MultipartFile file) throws IllegalStateException, IOException {
    	
    	EquipmentDto equipment = equipmentService.getEquipmentById(id);
    	String realPath = ContextUtil.getRequest().getServletContext().getRealPath("/upload/equipment");
    	String originalFilename = file.getOriginalFilename();
    	realPath = realPath + "\\" + equipment.getTypeName() + "\\" + equipment.getModel().getName();
    	
    	File f = new File(realPath);
    	//如果文件夹不存在就创建
    	if( !f.exists() ) {
    		f.mkdirs();
    	}
    	file.transferTo(new File(realPath,originalFilename));
    	System.out.println("图片路径：" + realPath + "\\" + originalFilename);
    	
    	//更新设备型号对应的图片地址
    	Equipment e = new Equipment();
    	e.setModelId(equipment.getModelId());
    	e.setImgUrl("\\" + "upload\\equipment" + "\\" + equipment.getTypeName() + "\\" + equipment.getModel().getName() + "\\" + originalFilename);
    	equipmentService.updateEquipmentByModelId(e);
    	
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }
    
    
    /**
     * 获取设备类型图片
     * @param id 设备id
     * @return jpg 图片  
     * @throws IOException 
     * @throws
     * @author shaosen
     * @Date 2018年7月26日
     */
    @RequestMapping(value="equipments/{id}/img",method=RequestMethod.GET)
    public JSONObject getImg(@PathVariable Integer id,HttpServletResponse response) throws IOException {
    	
    	//根据设备类型和型号查找图片位置
    	EquipmentDto dto = equipmentService.getEquipmentById(id);
    	if(dto == null) {
    		throw new RuntimeException("没有获取到id=【"+id+"】的设备信息");
    	}
    	String imgUrl = dto.getImgUrl();
    	String realPath = ContextUtil.getRequest().getServletContext().getRealPath(imgUrl);
//    	LogUtil.info(realPath);
    	FileInputStream is = new FileInputStream(realPath);
    	
    	byte[] buffer = new byte[1024];
    	int len = 0;
    	ByteArrayOutputStream bos = new ByteArrayOutputStream();
    	while((len = is.read(buffer))!=-1) {
    		bos.write(buffer, 0, len);
    	}
    	bos.flush();
    	
    	ServletOutputStream outputStream = response.getOutputStream();
    	outputStream.write(bos.toByteArray());
    	
    	is.close();
    	bos.close();
    	outputStream.close();
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }
    
    /******************************************************************/
    /*设备供应商模块
    /******************************************************************/
    
    /**
	 * 添加设备供应商
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
	     *  获取所有供应商列表
	* @param    
	* @return {"msg":"请求成功","code":"200","data":[{"id":1,"name":"张三","crtTime":1531901454000},{"id":2,"name":"李四","crtTime":1531901454000},{"id":3,"name":"王五","crtTime":1531901454000}]}  
	* @throws
	* @author shaosen
	* @date 2018年6月6日
	*/
	@RequestMapping(value="equipment-venders",method=RequestMethod.GET)
	public JSONObject getAllEquipmentVenderList() {
		List<EquipmentVender> list = equipmentVenderService.getAllEquipmentVenderList();
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, list);
	}
	
	/**
	   * 通过id获取设备供应商
	* @param  id 供应商id
	* @return   {"msg":"请求成功","code":"200","data":{"id":1,"name":"张三","crtTime":1531901454000}}
	* @throws
	* @author shaosen
	* @date 2018年6月6日
	*/
	@RequestMapping(value="equipment-venders/{id}",method=RequestMethod.GET)
	public JSONObject getEquipmentVenderById(@PathVariable("id")Integer id) {
		EquipmentVender vender = equipmentVenderService.getEquipmentVenderById(id);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, vender);
	}
	
	/**
	 * 删除设备供应商
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
	     *  获取所有设备类型列表
	* @param    
	* @return {"msg":"请求成功","code":"200","data":[{"id":1,"name":"安全防范"}]}  
	* @throws
	* @author ya.liu
	* @date 2018年11月27日
	*/
	@RequestMapping(value="equipment-types",method=RequestMethod.GET)
	public JSONObject getAllEquipmentTypeList() {
		List<EquipmentType> list = equipmentTypeService.getAllEquipmentTypeList();
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, list);
	}
	
	/**
	   * 通过id获取设备类型
	* @param  id 供应商id
	* @return   {"msg":"请求成功","code":"200","data":{"id":1,"name":"安全防范"}}
	* @author ya.liu
	* @date 2018年11月27日
	*/
	@RequestMapping(value="equipment-types/{id}",method=RequestMethod.GET)
	public JSONObject getEquipmentTypeById(@PathVariable("id")Integer id) {
		EquipmentType type = equipmentTypeService.selectByPrimaryKey(id);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, type);
	}
	
	/**
	 * 删除设备类型
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
     * @param    
     * @return {"msg":"请求成功","code":"200","data":[{"id":1,"name":"model-1","crtTime":1531901454000},{"id":2,"name":"model-2","crtTime":1531901454000},{"id":3,"name":"model-3","crtTime":1531901454000}]}  
     * @throws
     * @author shaosen
     * @date 2018年6月6日
     */
    @RequestMapping(value="equipment-models",method=RequestMethod.GET)
    public JSONObject getAllEquipmentModelList() {
    	List<EquipmentModel> list = equipmentModelService.getAllEquipmentModelList();
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, list);
    }
    
    /**
	     * 通过id获取设备型号
	* @param  id  设备型号id
	* @return {"msg":"请求成功","code":"200","data":{"id":1,"name":"model-1","crtTime":1531901454000}}  
	* @throws
	* @author shaosen
	* @date 2018年6月6日
	*/
	@RequestMapping(value="equipment-models/{id}",method=RequestMethod.GET)
	public JSONObject getEquipmentModelById(@PathVariable("id")Integer id) {
		EquipmentModel model = equipmentModelService.getEquipmentModelById(id);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, model);
	}
    
    /**
     * 删除设备型号
     * @param id
     * @return
     * @author ya.liu
     * @Date 2018年11月27日
     */
    @RequestMapping(value="equipment-models/{id}",method=RequestMethod.DELETE)
    public JSONObject delete(@PathVariable("id") Integer id) {
    	equipmentModelService.deleteByPrimaryKey(id);
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }
    
    /**
     * 获取某一类型下的设备型号列表
     * @param typeId
     * @return
     * @author ya.liu
     * @Date 2018年11月29日
     */
    @RequestMapping(value="types/{typeId}/equipment-models",method=RequestMethod.GET)
	public JSONObject getEquipmentModelByTypeId(@PathVariable("typeId")Integer typeId) {
    	List<EquipmentModel> list = equipmentModelService.getEquipmentModelByTypeId(typeId);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, list);
	}
	
    /******************************************************************/
    /*以下是管廊设备信息总览
    /******************************************************************/
    
    /**
     * 获取各种类型设备的数据统计
     * @param    
     * @return {"msg":"请求成功","code":"200","data":[{"val":3,"key":"安全防范"},{"val":2,"key":"视频监控"},{"val":2,"key":"环境监测"},{"val":1,"key":"电缆本体"},{"val":1,"key":"火灾报警"},{"val":2,"key":"隧道通信"}]}  
     * @throws
     * @author shaosen
     * @date 2018年5月30日
     */
    @RequestMapping(value = "equipments/type/count" ,method = RequestMethod.GET)
    public JSONObject getCountOfType(){
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
     * @param    
     * @return  {"msg":"请求成功","code":"200","data":[{"val":[{"val":3,"key":"总数"},{"val":2,"key":"运行中"},{"val":1,"key":"故障"},{"val":0,"key":"备品"}],"key":"安全防范"},{"val":[{"val":2,"key":"总数"},{"val":2,"key":"运行中"},{"val":0,"key":"故障"},{"val":0,"key":"备品"}],"key":"视频监控"},{"val":[{"val":2,"key":"总数"},{"val":2,"key":"运行中"},{"val":0,"key":"故障"},{"val":0,"key":"备品"}],"key":"环境监测"},{"val":[{"val":1,"key":"总数"},{"val":1,"key":"运行中"},{"val":0,"key":"故障"},{"val":0,"key":"备品"}],"key":"电缆本体"},{"val":[{"val":1,"key":"总数"},{"val":1,"key":"运行中"},{"val":0,"key":"故障"},{"val":0,"key":"备品"}],"key":"火灾报警"},{"val":[{"val":2,"key":"总数"},{"val":2,"key":"运行中"},{"val":0,"key":"故障"},{"val":0,"key":"备品"}],"key":"隧道通信"}]} 
     * @throws
     * @author shaosen
     * @date 2018年5月30日
     */
    @RequestMapping(value = "equipments/type/status/count" ,method = RequestMethod.GET)
    public JSONObject getCountOfTypeAndStatus() {
        List<JSONObject> result = new ArrayList<>();
        List<EquipmentType> equipmentTypes = equipmentTypeService.getAllEquipmentTypeList();
        for (EquipmentType equipmentType : equipmentTypes) {
            JSONObject o = new JSONObject();
            o.put("key", equipmentType.getName());

            List<JSONObject> typeVals = new ArrayList<>();
            JSONObject o2 = new JSONObject();
            o2.put("key", "总数");
            o2.put("val", equipmentService.getCountByCondition(null, null, equipmentType.getId()));
            typeVals.add(o2);
            for (EquipmentStatusEnum statusEnum : EquipmentStatusEnum.values()) {
                o2 = new JSONObject();
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
     * @return
     * @author liuya
     * @Date 2018年9月1日
     */
    @RequestMapping(value = "tunnels/equipments/status",method=RequestMethod.GET)
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
     * @return	{"msg":"请求成功","code":"200","data":[{"key":"古城大街","val":10},{"key":"金科路","val":12}]}
     * @author liuya
     * @Date 2018年9月1日
     */
    @RequestMapping(value = "tunnels/equipments/count",method=RequestMethod.GET)
    public JSONObject getTunnelEquipmentCount() {
    	List<TunnelSimpleDto> tunnelList = tunnelService.getList();
		List<Map<String,Object>> list = new ArrayList<>();
		for (TunnelSimpleDto tunnel : tunnelList) {
			Map<String,Object> map = new HashMap<>();
			map.put("key", tunnelService.getNameById(tunnel.getId()));
			map.put("val", equipmentService.getCountByCondition(tunnel.getId(), null, null));
			list.add(map);
		}
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, list);
    }
    
    /**
     * 不同设备类型下，每条管廊的设备数
     * @return	{"msg":"请求成功","code":"200","data":{"安全防范":[{"key":"古城大街","val":3},{"key":"金科路","val":1}],"火灾报警":[{"key":"古城大街","val":3},{"key":"金科路","val":1}]}
     * @author liuya
     * @Date 2018年9月3日
     */
    @RequestMapping(value="tunnels/equipments/types", method=RequestMethod.GET)
    public JSONObject getCountByTunnelAndEquipment() {
    	JSONObject json = new JSONObject();
    	List<EquipmentType> equipmentTypes = equipmentTypeService.getAllEquipmentTypeList();
    	List<TunnelSimpleDto> tunnelList = tunnelService.getList();
		for(EquipmentType type : equipmentTypes) {
    		List<JSONObject> list = new ArrayList<>();
    		for(TunnelSimpleDto tunnel : tunnelList) {
    			JSONObject map = new JSONObject();
    			map.put("key",tunnel.getName());
    			map.put("val",equipmentService.getCountByCondition(tunnel.getId(), null, type.getId()));
    			list.add(map);
    		}
    		json.put(type.getName(), list);
    	}
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, json);
    }
    
    /**
     * 不同管廊下，每个设备类型的设备数
     * @return
     * @author ya.liu
     * @Date 2019年1月17日
     */
    @RequestMapping(value="types/equipments/tunnels", method=RequestMethod.GET)
    public JSONObject getCountByTunnelAndType() {
    	List<EquipmentType> equipmentTypes = equipmentTypeService.getAllEquipmentTypeList();
    	List<TunnelSimpleDto> tunnelList = tunnelService.getList();
    	
    	JSONObject obj = new JSONObject();
    	List<String> types = equipmentTypes.stream().map(a -> a.getName()).collect(Collectors.toList());
    	List<String> tunnels = tunnelList.stream().map(a -> a.getName()).collect(Collectors.toList());
    	List<JSONObject> list = new ArrayList<>();
    	for(EquipmentType type : equipmentTypes) {
    		JSONObject json = new JSONObject();
    		List<Integer> typeList = new ArrayList<>();
    		for(TunnelSimpleDto tunnel : tunnelList) {
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
     * @param objId
     * @return
     * @author liuya
     * @Date 2018年9月7日
     */
    @RequestMapping(value = "measobjs/{objId}/equipment", method=RequestMethod.GET)
    public JSONObject getEquipmentListByObj(@PathVariable("objId") Integer objId) {
    	EquipmentDto equipment = equipmentService.getEquipmentListByObj(objId);
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, equipment);
    }
    
    /**
     * 取消mo与设备关联，并修改设备的状态
     * @param status 设备状态
     * @param id 设备id
     * @return
     * @author liuya
     * @Date 2018年9月7日
     */
    @RequestMapping(value = "equipments/status", method=RequestMethod.PUT)
    public JSONObject updateEquipmentOfObj(@RequestBody Equipment equipment) {
    	equipmentService.updateEquipmentOfObj(equipment);
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }
}
