package com.bandweaver.tunnel.controller.oam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.constant.mam.ObjectType;
import com.bandweaver.tunnel.common.biz.constant.oam.EnergyType;
import com.bandweaver.tunnel.common.biz.dto.AreaDto;
import com.bandweaver.tunnel.common.biz.dto.StoreDto;
import com.bandweaver.tunnel.common.biz.dto.TunnelSimpleDto;
import com.bandweaver.tunnel.common.biz.dto.oam.ConsumeDto;
import com.bandweaver.tunnel.common.biz.itf.AreaService;
import com.bandweaver.tunnel.common.biz.itf.StoreService;
import com.bandweaver.tunnel.common.biz.itf.TunnelService;
import com.bandweaver.tunnel.common.biz.itf.oam.ConsumeService;
import com.bandweaver.tunnel.common.biz.pojo.oam.Consume;
import com.bandweaver.tunnel.common.biz.vo.oam.ConsumeDataVo;
import com.bandweaver.tunnel.common.platform.constant.StatusCodeEnum;
import com.bandweaver.tunnel.common.platform.util.CommonUtil;
import com.bandweaver.tunnel.controller.common.BaseController;

/**
 * 能耗管理
 * @author ya.liu
 * @Date 2018年11月13日
 */
@Controller
@ResponseBody
public class ConsumeController extends BaseController<Consume> {

	@Autowired
	private ConsumeService consumeService;
	@Autowired
	private TunnelService tunnelService;
	@Autowired
	private StoreService storeService;
	@Autowired
	private AreaService areaService;
    
	/**
	 * 添加
	 * @param tunnelId
	 * @param storeId
	 * @param areaId
	 * @param objectType 对象类型
	 * @param objectId  moid
	 * @param energyType 能耗类型枚举
	 * @param compute 计算式
	 * @return
	 * @author ya.liu
	 * @Date 2018年11月14日
	 */
	@Override
    @RequestMapping(value="consumes",method=RequestMethod.POST)
    public JSONObject add(@RequestBody Consume consume) {
    	
    	consumeService.insert(consume);
    	
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }

    /**
     * 添加测试
     * @return
     * @author ya.liu
     * @Date 2018年11月14日
     */
    @RequestMapping(value="consumes/add",method=RequestMethod.GET)
    public JSONObject addBatch() {
    	List<Consume> list = new ArrayList<>();
    	List<TunnelSimpleDto> tunnelList = tunnelService.getList();
    	for(TunnelSimpleDto tunnelDto : tunnelList) {
    		List<StoreDto> storeList = storeService.getStoresByTunnelId(tunnelDto.getId());
    		StoreDto store = new StoreDto();
    		store.setId(0);
    		storeList.add(store);
    		for(StoreDto storeDto : storeList) {
    			List<AreaDto> areaList = areaService.getAreasByTunnelId(tunnelDto.getId());
    			AreaDto area = new AreaDto();
    			area.setId(0);
    			areaList.add(area);
    			for(AreaDto areaDto : areaList) {
    				for(EnergyType energyType : EnergyType.values()) {
    					List<ObjectType> objList = new ArrayList<>();
    					if(EnergyType.COMMON.equals(energyType))
    						objList = Arrays.asList(ObjectType.getCommon());
    					else if(EnergyType.EMERGENCY.equals(energyType))
    						objList = Arrays.asList(ObjectType.getEmergency());
    					List<ObjectType> objlist = new ArrayList<>(objList);
    					objlist.add(ObjectType.NONE);
    					for(ObjectType objectType : objlist) {
    						
    						Consume consume = new Consume();
    						consume.setTunnelId(tunnelDto.getId());
    						consume.setStoreId(storeDto.getId());
    						consume.setAreaId(areaDto.getId());
    						consume.setObjectType(objectType.getValue());
    						consume.setEnergyType(energyType.getValue());
    						
    						list.add(consume);
    					}
    				}
    			}
    		}
    	}
    	
    	consumeService.addBatch(list);
    	
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }
   
    /**
     * 添加测试，含有objectId
     * @return
     * @author ya.liu
     * @Date 2018年11月14日
     */
    @RequestMapping(value="consumes/tunnels",method=RequestMethod.GET)
    public JSONObject addTunnel() {
    	List<Consume> list = new ArrayList<>();
    	int count = 1000;
    	List<TunnelSimpleDto> tunnelList = tunnelService.getList();
    	for(TunnelSimpleDto tunnelDto : tunnelList) {
    		if(tunnelDto.getId() != 2) continue;
    		List<StoreDto> storeList = storeService.getStoresByTunnelId(tunnelDto.getId());
    		for(StoreDto storeDto : storeList) {
    			List<AreaDto> areaList = areaService.getAreasByTunnelId(tunnelDto.getId());
    			for(AreaDto areaDto : areaList) {
    				for(EnergyType energyType : EnergyType.values()) {
    					if(!EnergyType.EMERGENCY.equals(energyType))
    						continue;
    					List<ObjectType> objList = Arrays.asList(ObjectType.getEmergency());
    					for(ObjectType objectType : objList) {
    						
    						Consume consume = new Consume();
    						consume.setTunnelId(tunnelDto.getId());
    						consume.setStoreId(storeDto.getId());
    						consume.setAreaId(areaDto.getId());
    						consume.setObjectType(objectType.getValue());
    						consume.setEnergyType(energyType.getValue());
    						consume.setObjectId(++count);
    						list.add(consume);
    					}
    				}
    			}
    		}
    	}
    	
    	consumeService.addBatchWithObjectId(list);
    	
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }
    
    /**
	 * 更新
	 * @param id
	 * @param tunnelId
	 * @param storeId
	 * @param areaId
	 * @param objectType 对象类型
	 * @param objectId  moid
	 * @param energyType 能耗类型枚举
	 * @param compute 计算式
	 * @return
	 * @author ya.liu
	 * @Date 2018年11月14日
	 */
    @Override
    @RequestMapping(value="consumes",method=RequestMethod.PUT)
    public JSONObject update(@RequestBody Consume consume) {
    	
    	consumeService.update(consume);
    	
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }
    
    /**
     * 通过id获取能耗
     * @param id
     * @return
     * @author ya.liu
     * @Date 2018年11月14日
     */
    @Override
    @RequestMapping(value = "consumes/{id}", method = RequestMethod.GET)
    public JSONObject getById(@PathVariable("id") Integer id) {
    	ConsumeDto consumeDto = consumeService.getConsumeDtoById(id);
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,consumeDto);
    }
    
    /**
     * 删除
     * @param id
     * @return
     * @author ya.liu
     * @Date 2018年11月14日
     */
    @Override
    @RequestMapping(value = "consumes/{id}", method = RequestMethod.DELETE)
    public JSONObject delete(@PathVariable("id") Integer id) {
    	consumeService.delete(id);
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }
    
    /**
     * 批量删除
     * @param ids 用逗号隔开
     * @return
     * @author ya.liu
     * @Date 2018年11月15日
     */
    @Override
    @RequestMapping(value = "consumes/batch/{ids}", method = RequestMethod.DELETE)
    public JSONObject deleteBatch(@PathVariable("ids") String ids) {
    	List<Integer> list = convertStringToList(ids);
    	consumeService.deleteBatch(list);
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }
    
    /**
     * 参数获取能耗列表
     * @param id
	 * @param tunnelId
	 * @param storeId
	 * @param areaId
	 * @param objectType 对象类型
	 * @param objectId  moid
	 * @param energyType 能耗类型枚举
	 * @param compute 计算式
     * @return
     * @author ya.liu
     * @Date 2018年11月14日
     */
    @RequestMapping(value = "consumes/condition", method = RequestMethod.POST)
    public JSONObject getConsumesByCondition(@RequestBody ConsumeDataVo vo) {
    	List<ConsumeDto> list = consumeService.getConsumesByCondition(vo);
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,list);
    }
}
			