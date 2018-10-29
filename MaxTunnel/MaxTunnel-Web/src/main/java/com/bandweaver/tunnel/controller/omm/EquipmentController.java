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
import com.bandweaver.tunnel.common.biz.constant.omm.EquipmentTypeEnum;
import com.bandweaver.tunnel.common.biz.dto.TunnelSimpleDto;
import com.bandweaver.tunnel.common.biz.dto.omm.EquipmentDto;
import com.bandweaver.tunnel.common.biz.itf.TunnelService;
import com.bandweaver.tunnel.common.biz.itf.omm.EquipmentModelService;
import com.bandweaver.tunnel.common.biz.itf.omm.EquipmentService;
import com.bandweaver.tunnel.common.biz.itf.omm.EquipmentVenderService;
import com.bandweaver.tunnel.common.biz.pojo.Tunnel;
import com.bandweaver.tunnel.common.biz.pojo.omm.Equipment;
import com.bandweaver.tunnel.common.biz.pojo.omm.EquipmentModel;
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
    
    
    /**
     *  添加设备
     * @param name 设备名称
     * @param type 设备类型（枚举）
     * @param serviceLife 声明周期（数值）
     * @param status 设备状态（枚举）
     * @param assetNo 资产编号（字符串）
     * @param tunnelId 管廊id
     * @param venderId 供应商id
     * @param modelId 设备型号id
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
     * 添加测试数据
     * @return JSONObject  
     * @throws
     * @author shaosen
     * @date 2018年6月11日
     */
    @RequestMapping(value="equipments/batch",method=RequestMethod.GET)
    public JSONObject addTestData() {
    	
    	List<Equipment> list = new ArrayList<>();
    		for (int i = 1; i <= 200; i++) {
    			Equipment pojo = new Equipment();
    			pojo.setName("设备-"+i);
    			pojo.setType((int)(Math.random()*6)+1);
    			pojo.setCrtTime(DateUtil.getFrontDay(new Date(), i));
    			pojo.setServiceLife(1000);
    			pojo.setStatus((int)(Math.random()*2)+1);
    			pojo.setAssetNo("sn-"+i);
    			pojo.setTunnelId((int)(Math.random()*5)+1);
    			pojo.setVenderId((int)(Math.random()*3)+1);
    			pojo.setModelId((int)(Math.random()*3)+1);
    			pojo.setImgUrl("");
    			list.add(pojo);
    		
    		}
    		equipmentService.addEquipmentBatch(list);
    	
    		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }
    
    
    

    /**
     *  分页查询
     * @param name 设备名称（支持模糊查询）
     * @param type 设备类型（枚举）
     * @param status 设备状态（枚举）
     * @param assetNo 资产编号（字符串）
     * @param tunnelId 管廊id
     * @param venderId 供应商id
     * @param modelId 设备型号id
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param pageNum 必须
     * @param pageSize 必须
     * @return {"msg":"请求成功","code":"200","data":{"total":11,"list":[{"id":1,"assetNo":"sn003","name":"安全防范设备3","type":1,"typeName":"安全防范","crtTime":1525406400000,"serviceLife":1000,"status":1,"statusName":"运行中","imgUrl":"\\upload\\equipment\\安全防范\\model-1\\testimg.jpg","tunnel":{"id":1,"name":"凤岭北路"},"model":{"id":1,"name":"model-1","crtTime":1531901454000},"vender":{"id":1,"name":"张三","crtTime":1531901454000}},{"id":2,"assetNo":"sn001","name":"安全防范设备1","type":1,"typeName":"安全防范","crtTime":1525406400000,"serviceLife":1000,"status":1,"statusName":"运行中","imgUrl":"\\upload\\equipment\\安全防范\\model-2\\testimg.jpg","tunnel":{"id":1,"name":"凤岭北路"},"model":{"id":2,"name":"model-2","crtTime":1531901454000},"vender":{"id":1,"name":"张三","crtTime":1531901454000}}],"pageNum":1,"pageSize":2,"size":2,"startRow":1,"endRow":2,"pages":6,"prePage":0,"nextPage":2,"isFirstPage":true,"isLastPage":false,"hasPreviousPage":false,"hasNextPage":true,"navigatePages":8,"navigatepageNums":[1,2,3,4,5,6],"navigateFirstPage":1,"navigateLastPage":6,"firstPage":1,"lastPage":6}}  
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
     * @return {"msg":"请求成功","code":"200","data":{"id":1,"assetNo":"sn003","name":"安全防范设备3","type":1,"typeName":"安全防范","crtTime":1525406400000,"serviceLife":1000,"status":1,"statusName":"运行中","imgUrl":"\\upload\\equipment\\安全防范\\model-1\\testimg.jpg","tunnel":{"id":1,"name":"凤岭北路"},"model":{"id":1,"name":"model-1","crtTime":1531901454000},"vender":{"id":1,"name":"张三","crtTime":1531901454000}}}  
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
     * @param @return   
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
    
    
    
    /**
           *  获取所有供应商列表
     * @param    
     * @return {"msg":"请求成功","code":"200","data":[{"id":1,"name":"张三","crtTime":1531901454000},{"id":2,"name":"李四","crtTime":1531901454000},{"id":3,"name":"王五","crtTime":1531901454000}]}  
     * @throws
     * @author shaosen
     * @date 2018年6月6日
     */
    @RequestMapping(value="venders",method=RequestMethod.GET)
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
    @RequestMapping(value="venders/{id}",method=RequestMethod.GET)
    public JSONObject getEquipmentVenderById(@PathVariable("id")Integer id) {
    	EquipmentVender vender = equipmentVenderService.getEquipmentVenderById(id);
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, vender);
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

        for (EquipmentTypeEnum equipmentTypeEnum : EquipmentTypeEnum.values()) {
            JSONObject o = new JSONObject();
            o.put("key", equipmentTypeEnum.getName());
            o.put("val", equipmentService.getCountByCondition(null, null, equipmentTypeEnum.getValue()));
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
        for (EquipmentTypeEnum typeEnum : EquipmentTypeEnum.values()) {
            JSONObject o = new JSONObject();
            o.put("key", typeEnum.getName());

            List<JSONObject> typeVals = new ArrayList<>();
            JSONObject o2 = new JSONObject();
            o2.put("key", "总数");
            o2.put("val", equipmentService.getCountByCondition(null, null, typeEnum.getValue()));
            typeVals.add(o2);
            for (EquipmentStatusEnum statusEnum : EquipmentStatusEnum.values()) {
                o2 = new JSONObject();
                o2.put("key", statusEnum.getName());
                o2.put("val", equipmentService.getCountByCondition(null, statusEnum.getValue(), typeEnum.getValue()));
                typeVals.add(o2);
            }

            o.put("val", typeVals);
            result.add(o);
        }
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, result);
    }
    
    /**
     * 获取设备状态统计 ： 设备总数，故障设备数，备品数，设备运行总时长
     * @param    
     * @return {"msg":"请求成功","code":"200","data":[{"val":11,"key":"设备总数"},{"val":0,"key":"故障设备数"},{"key":"备品数"},{"val":21934,"key":"设备运行总时长"}]}  
     * @throws
     * @author shaosen
     * @date 2018年6月13日
     */
    @RequestMapping(value="equipments/status/count",method=RequestMethod.GET)
    public JSONObject getEquipmentCountByStatus() {
    	List<JSONObject> list  = new ArrayList<>();
    	JSONObject jsonOne = new JSONObject();
    	jsonOne.put("key", "设备总数");
    	jsonOne.put("val", equipmentService.getCountByCondition(null, null, null));
    	list.add(jsonOne);
    	
    	JSONObject jsonTwo = new JSONObject();
    	jsonTwo.put("key", "故障设备数");
    	jsonTwo.put("val", equipmentService.getCountByCondition(null,EquipmentStatusEnum.BROKEN.getValue(),null));
    	list.add(jsonTwo);
    	
    	JSONObject jsonThree = new JSONObject();
    	jsonThree.put("key", "备品数");
    	jsonThree.put("val", equipmentService.getCountByCondition(null,EquipmentStatusEnum.BACKUP.getValue(),null));
    	list.add(jsonThree);
    	
    	//设备运行总时长
    	List<EquipmentDto> dtoList = equipmentService.getAllEquipmentList();
    	long hours = 0;
    	for (EquipmentDto equipmentDto : dtoList) {
    		hours += DateUtil.getDiffHours(equipmentDto.getCrtTime(), new Date());
		}
    	
    	JSONObject jsonFour = new JSONObject();
    	jsonFour.put("key", "设备运行总时长");
    	jsonFour.put("val", hours);
    	list.add(jsonFour);
    	
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, list);
    }
    
   
    /**
          * 每条管廊设备总数，故障数，备品数统计
     * @param    
     * @return {"msg":"请求成功","code":"200","data":["管廊[ test ],设备总数[ 0 ]台,损坏[ 0 ]台,备品[ 0 ]台","管廊[ test ],设备总数[ 0 ]台,损坏[ 0 ]台,备品[ 0 ]台","管廊[ 凤岭北路 ],设备总数[ 11 ]台,损坏[ 1 ]台,备品[ 0 ]台","管廊[ 长虹路 ],设备总数[ 0 ]台,损坏[ 0 ]台,备品[ 0 ]台","管廊[ 高坡岭路 ],设备总数[ 0 ]台,损坏[ 0 ]台,备品[ 0 ]台","管廊[ 凤凰岭路 ],设备总数[ 0 ]台,损坏[ 0 ]台,备品[ 0 ]台","管廊[ 佛子岭路 ],设备总数[ 0 ]台,损坏[ 0 ]台,备品[ 0 ]台"]}  
     * @throws
     * @author shaosen
     * @date 2018年6月13日
     */
    @RequestMapping(value="tunnels/equipments/status/count",method=RequestMethod.GET)
    public JSONObject getTunnelEquipmentInfo() {
    	
    		List<TunnelSimpleDto> tunnelList = tunnelService.getList();
    		List<String> strList = new ArrayList<>();
    		for (TunnelSimpleDto tunnel : tunnelList) {
    			StringBuffer buffer = new StringBuffer();
        		buffer.append("管廊[ " + tunnelService.getNameById(tunnel.getId()) + " ],");
        		buffer.append("设备总数[ " + equipmentService.getCountByCondition(tunnel.getId(), null, null) + " ]台,");
        		buffer.append("损坏[ " + equipmentService.getCountByCondition(tunnel.getId(), EquipmentStatusEnum.BROKEN.getValue(), null) + " ]台,");
        		buffer.append("备品[ " + equipmentService.getCountByCondition(tunnel.getId(), EquipmentStatusEnum.BACKUP.getValue(), null) + " ]台");
        		strList.add(buffer.toString());
			}
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, strList);
    }
    
    /**
     * 每条管廊的故障设备数
     * @return	{"msg":"请求成功","code":"200","data":[{"key":"古城大街","val":1},{"key":"金科路","val":0}]}
     * @author liuya
     * @Date 2018年9月1日
     */
    @RequestMapping(value = "tunnels/equipments/broken/count",method=RequestMethod.GET)
    public JSONObject getTunnelEquipmentBrokenCount() {
    	List<TunnelSimpleDto> tunnelList = tunnelService.getList();
		List<Map<String,Object>> list = new ArrayList<>();
		for (TunnelSimpleDto tunnel : tunnelList) {
			Map<String,Object> map = new HashMap<>();
			map.put("key", tunnelService.getNameById(tunnel.getId()));
			map.put("val", equipmentService.getCountByCondition(tunnel.getId(), EquipmentStatusEnum.BROKEN.getValue(), null));
			list.add(map);
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
    @RequestMapping(value="equipments/tunnels/count", method=RequestMethod.GET)
    public JSONObject getCountByTunnelAndEquipment() {
    	List<TunnelSimpleDto> tunnelList = tunnelService.getList();
    	JSONObject json = new JSONObject();
    	for(TunnelSimpleDto tunnel : tunnelList) {
    		List<Map<String,Object>> list = new ArrayList<>();
    		for(EquipmentTypeEnum type : EquipmentTypeEnum.values()) {
    			Map<String,Object> map = new HashMap<>();
    			map.put("key",type.getName());
    			map.put("val",equipmentService.getCountByCondition(tunnel.getId(), null, type.getValue()));
    			list.add(map);
    		}
    		json.put(tunnelService.getNameById(tunnel.getId()), list);
    	}
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, json);
    }
    
    /**
     * 通过moid获取设备信息
     * @param objId
     * @return
     * @author liuya
     * @Date 2018年9月7日
     */
    @RequestMapping(value = "measobjs/{objId}/equipment", method=RequestMethod.GET)
    public JSONObject getEquipmentListByObj(@PathVariable("objId")Integer objId) {
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
