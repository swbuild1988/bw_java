package com.bandweaver.tunnel.controller.mam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.constant.mam.AlarmLevelEnum;
import com.bandweaver.tunnel.common.biz.constant.mam.DataType;
import com.bandweaver.tunnel.common.biz.dto.mam.MeasObjDto;
import com.bandweaver.tunnel.common.biz.dto.mam.alarm.AlarmDto;
import com.bandweaver.tunnel.common.biz.itf.mam.MeasValueAIService;
import com.bandweaver.tunnel.common.biz.itf.mam.MeasValueDIService;
import com.bandweaver.tunnel.common.biz.itf.mam.MeasValueSIService;
import com.bandweaver.tunnel.common.biz.itf.mam.alarm.AlarmService;
import com.bandweaver.tunnel.common.biz.itf.mam.measobj.MeasObjAIService;
import com.bandweaver.tunnel.common.biz.itf.mam.measobj.MeasObjDIService;
import com.bandweaver.tunnel.common.biz.itf.mam.measobj.MeasObjDistributeService;
import com.bandweaver.tunnel.common.biz.itf.mam.measobj.MeasObjSIService;
import com.bandweaver.tunnel.common.biz.itf.mam.measobj.MeasObjService;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObj;
import com.bandweaver.tunnel.common.biz.vo.mam.MeasObjVo;
import com.bandweaver.tunnel.common.biz.vo.mam.alarm.AlarmVo;
import com.bandweaver.tunnel.common.platform.constant.StatusCodeEnum;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.CommonUtil;
import com.bandweaver.tunnel.service.mam.measobj.MeasObjModuleCenter;
import com.github.pagehelper.PageInfo;

/**数据分析接口
 * @author shaosen
 * @date 2018年11月19日
 */
@Controller
@ResponseBody
public class DataAnalyseController {

	
	@Autowired
    private MeasObjModuleCenter measObjModuleCenter;
	@Autowired
	private MeasObjService measObjService;
    @Autowired
    private MeasValueAIService measValueAIService;
    @Autowired
    private MeasValueDIService measValueDIService;
    @Autowired
    private MeasValueSIService measValueSIService;
    @Autowired
    private MeasObjAIService measObjAIService;
    @Autowired
    private MeasObjDIService measObjDIService;
    @Autowired
    private MeasObjSIService measObjSIService;
    @Autowired
    private MeasObjDistributeService measObjDistributeService;
    @Autowired
	private AlarmService alarmService;
   
	
    
    
    /**根据条件查询监测对象显示其最新数据值并分页
     * @param id 可选，监测对象id
     * @param tunnelId 可选
     * @param storeId 可选
     * @param areaId 可选
     * @param objtypeIds 可选,数组形式
     * @param datatypeId *必选
     * @param pageNum *必选
     * @param pageSize *必选
     * @return {"msg":"请求成功","code":"200","data":{"total":2,"list":[{"id":8018,"refreshTime":1543248000000,"obj":{"id":8018,"tunnelId":1,"storeId":1001,"areaId":1012,"sectionId":1011,"name":"tets","datatypeId":1,"datatypeName":"模拟量输入","objtypeId":3,"objtypeName":"氧气","actived":false,"description":null,"longitude":null,"latitude":null,"height":null,"deviation":null,"cv":0.0,"section":{"id":1011,"name":"3区-电力","tunnelId":1,"totalCableNumber":10,"camera":null,"startPoint":null,"endPoint":null,"crtTime":1543211311000,"store":{"id":1001,"name":"电力","sn":"02","tunnel":{"id":1,"name":"古城大街"},"storeType":{"id":1002,"name":"电力","sn":"02","crtTime":1543211260000},"camera":null,"crtTime":1543211288000},"area":{"id":1012,"name":"3区","sn":"03","location":null,"tunnelId":1,"camera":null,"crtTime":1543211305000}}},"cv":12.0},{"id":8019,"refreshTime":1543248000000,"obj":{"id":8019,"tunnelId":1,"storeId":1003,"areaId":1013,"sectionId":1048,"name":"tets2","datatypeId":1,"datatypeName":"模拟量输入","objtypeId":5,"objtypeName":"甲烷","actived":false,"description":null,"longitude":null,"latitude":null,"height":null,"deviation":null,"cv":0.0,"section":{"id":1048,"name":"4区-综合","tunnelId":1,"totalCableNumber":10,"camera":null,"startPoint":null,"endPoint":null,"crtTime":1543211311000,"store":{"id":1003,"name":"综合","sn":"03","tunnel":{"id":1,"name":"古城大街"},"storeType":{"id":1003,"name":"综合","sn":"03","crtTime":1543211269000},"camera":null,"crtTime":1543211288000},"area":{"id":1013,"name":"4区","sn":"04","location":null,"tunnelId":1,"camera":null,"crtTime":1543211305000}}},"cv":11.0}],"pageNum":1,"pageSize":10,"size":2,"startRow":1,"endRow":2,"pages":1,"prePage":0,"nextPage":0,"isFirstPage":true,"isLastPage":true,"hasPreviousPage":false,"hasNextPage":false,"navigatePages":8,"navigatepageNums":[1],"navigateFirstPage":1,"navigateLastPage":1,"lastPage":1,"firstPage":1}}
     * @author shaosen
     * @Date 2018年11月19日
     */
    @RequestMapping(value = "data-analyse/measvalue/datagrid" ,method = RequestMethod.POST)
    public JSONObject dataGrid(@RequestBody MeasObjVo vo) {
    	
    	if(vo.getObjtypeIds().size()==0) {
    		vo.setObjtypeIds(null);
    	}
    	
    	PageInfo<?> pageInfo = null;
    	DataType dataType = DataType.getEnum(vo.getDatatypeId());
    	switch (dataType) {
		case AI:
			pageInfo= measObjAIService.getByCondition(vo);
			break;
		case DI:
			pageInfo= measObjDIService.getByCondition(vo);
			break;
		case SI:
			pageInfo= measObjSIService.getByCondition(vo);
			break;
		case DISTRIBUTE:
			pageInfo= measObjDistributeService.getByCondition(vo);
			break;
		default:
			break;
		}
    
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, pageInfo);
    }
    
    
    
    
    /**查询指定的监测对象历史数据 
     * @param ids 监测对象id数组
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return   
     * @author shaosen
     * @Date 2018年11月27日
     */
    @RequestMapping(value = "data-analyse/measvalue/history/diagram" ,method = RequestMethod.POST)
    public JSONObject getMoHisDataDiagram(@RequestBody MeasObjVo vo) {
    	List<Integer> ids = vo.getIds();
    	if(ids == null || ids.isEmpty()) {
    		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, Collections.emptyList());
    	}
    	
    	DataType dataType = null;
    	for (Integer id : ids) {//如果缓存中找不到这个对象则继续寻找下一个
			MeasObj measObj = measObjModuleCenter.getMeasObj(id);
			if(measObj != null) {
				dataType = DataType.getEnum( measObj.getDatatypeId());
				break;
			}
		}
    	
    	JSONObject result = new JSONObject();
    	switch (dataType) {
		case AI:
			result = measValueAIService.getEchartsData(vo.getIds(), vo.getStartTime(), vo.getEndTime());
			break;
		case DI:
			result = measValueDIService.getEchartsData(vo.getIds(), vo.getStartTime(), vo.getEndTime());
			break;
		case SI:
			//TODO
			break;
		case DISTRIBUTE:
			//TODO
			break;

		default:
			break;
		}
    	
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, result);
    }
    
    
    
    
    /**条件查询监测对象并统计告警总数排名
     * @param ids 监测对象id数组
     * @param startTime 可选
     * @param endTime 可选
     * @return   {"msg":"请求成功","code":"200","data":[{"严重":28,"一般":21,"提示":31,"致命":29,"key":"监测对象1022"},{"严重":24,"一般":25,"提示":26,"致命":28,"key":"监测对象1021"},{"严重":26,"一般":24,"提示":25,"致命":25,"key":"监测对象1020"}]}
     * @author shaosen
     * @Date 2018年11月20日
     */
    @RequestMapping(value="data-analyse/alarms/topN",method=RequestMethod.POST)
    public JSONObject getAlarmCount(@RequestBody MeasObjVo vo) {
    	List<JSONObject> result = getResult(vo.getStartTime(), vo.getEndTime(), vo.getIds());
    	
    	//根据告警总数由多到少排列
    	List<JSONObject> lt = selectSort(result,"总数");
    	
    	//前端用的是组件，所以去掉总数这个字段，由前端自己计算，但顺序依然不能变
    	List<JSONObject> li = new ArrayList<>();
    	for (JSONObject jsonObject : lt) {
    		JSONObject js = new JSONObject();
    		js.put("key",jsonObject.getString("key"));
    		js.put(AlarmLevelEnum.PROMPT.getName(), jsonObject.getIntValue(AlarmLevelEnum.PROMPT.getName()));
    		js.put(AlarmLevelEnum.NORMAL.getName(), jsonObject.getIntValue(AlarmLevelEnum.NORMAL.getName()));
    		js.put(AlarmLevelEnum.SERIOUS.getName(), jsonObject.getIntValue(AlarmLevelEnum.SERIOUS.getName()));
    		js.put(AlarmLevelEnum.DANGEROUS.getName(), jsonObject.getIntValue(AlarmLevelEnum.DANGEROUS.getName()));
    		li.add(js);
		}
    	
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,li);
    }

    
    public List<JSONObject> getResult(Date stime, Date etime, List<Integer> ids) {
		List<JSONObject> result = new ArrayList<>();
    	for (Integer objectId : ids) {
    		AlarmVo vo = new AlarmVo();
    		vo.setObjectId(objectId);
    		vo.setStartTime(stime);
    		vo.setEndTime(etime);
    		List<AlarmDto> almList = alarmService.getByCondition(vo);
    		
    		int prompt = 0;
    		int normal = 0;
    		int serious = 0;
    		int dangerous = 0;
    		for (AlarmDto alarmDto : almList) {
				AlarmLevelEnum levelEnum = AlarmLevelEnum.getEnum(alarmDto.getAlarmLevel());
				switch (levelEnum) {
				case PROMPT:
					prompt++;
					break;
				case NORMAL:
					normal++;
					break;
				case SERIOUS:
					serious++;
					break;
				case DANGEROUS:
					dangerous++;
					break;
				default:
					break;
				}
			}
    		
    		
    		MeasObj measObj = measObjModuleCenter.getMeasObj(objectId);
    		JSONObject jj = new JSONObject();
    		jj.put("key",measObj == null ? objectId : measObj.getName());
    		jj.put(AlarmLevelEnum.PROMPT.getName(), prompt);
    		jj.put(AlarmLevelEnum.NORMAL.getName(), normal);
    		jj.put(AlarmLevelEnum.SERIOUS.getName(), serious);
    		jj.put(AlarmLevelEnum.DANGEROUS.getName(), dangerous);
    		jj.put("总数", prompt + normal + serious + dangerous );
    		result.add(jj);
		}
		return result;
	}


	/**选择排序由大到小排列 
	 * @param result 要排序的集合
	 * @param sort 排序字段
	 */
	private List<JSONObject> selectSort(List<JSONObject> result,String sort) {
		for (int i=0;i<result.size()-1 ;i++ ){   
            int min=i;   
            for (int j=i+1;j<result.size() ;j++ ){   
            	int minval = result.get(min).getIntValue(sort);
            	int jval = result.get(j).getIntValue(sort);
              if (minval<jval){   
                   min=j;   
                 }   
            }   
          if (min!=i){  
          JSONObject json = result.get(i);
          result.set(i, result.get(min));
          result.set(min, json);
          }  
        }  
         return result;  
	}
	
	
	
	
//	//统计object_id在alarm表中共有多少
//	int totalCount = alarmService.getCountByObjectIds(objectIdList,vo.getStartTime(),vo.getEndTime());
//	LogUtil.info("totalCount:" + totalCount);
//	
//	//开始分页
//	PageBean<AlarmDto> pageBean = new PageBean<>(vo.getPageNum(), vo.getPageSize(), totalCount);
//	LogUtil.info("start:" + pageBean.getStart());
//	LogUtil.info("end:" + pageBean.getEnd());
//	
//	List<AlarmDto> almList = alarmService.startPage(pageBean.getStart(),pageBean.getEnd(),objectIdList,vo.getStartTime(),vo.getEndTime());
//	pageBean.setList(almList);
//	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, pageBean);
  
    
}
