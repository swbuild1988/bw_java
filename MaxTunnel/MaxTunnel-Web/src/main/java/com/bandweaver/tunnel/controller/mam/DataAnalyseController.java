package com.bandweaver.tunnel.controller.mam;

import java.util.ArrayList;
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
import com.bandweaver.tunnel.common.biz.dto.mam.CountResult;
import com.bandweaver.tunnel.common.biz.dto.mam.MeasObjDto;
import com.bandweaver.tunnel.common.biz.dto.mam.alarm.AlarmDto;
import com.bandweaver.tunnel.common.biz.itf.mam.MeasValueAIService;
import com.bandweaver.tunnel.common.biz.itf.mam.alarm.AlarmService;
import com.bandweaver.tunnel.common.biz.itf.mam.measobj.MeasObjService;
import com.bandweaver.tunnel.common.biz.pojo.PageBean;
import com.bandweaver.tunnel.common.biz.pojo.mam.MeasValueAI;
import com.bandweaver.tunnel.common.biz.pojo.mam.alarm.Alarm;
import com.bandweaver.tunnel.common.biz.vo.mam.MeasObjVo;
import com.bandweaver.tunnel.common.biz.vo.mam.alarm.AlarmVo;
import com.bandweaver.tunnel.common.platform.constant.StatusCodeEnum;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.CommonUtil;
import com.bandweaver.tunnel.common.platform.util.DateUtil;
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
    private MeasValueAIService measValueAIService;
    @Autowired
    private MeasObjModuleCenter measObjModuleCenter;
    @Autowired
    private MeasObjService measObjService;
    @Autowired
	private AlarmService alarmService;
	
    
    
    /**查找各个监测对象的历史值并分页 
     * @param vo
     * @return {"msg":"请求成功","code":"200","data":{"total":1,"list":[{"id":8009,"tunnelId":3,"storeId":3,"areaId":1,"sectionId":42,"name":"风机类电表","datatypeId":1,"datatypeName":"模拟量输入","objtypeId":31,"objtypeName":"风机类电表","actived":true,"description":null,"longitude":"0.4386016987524316","latitude":"0.48531866384309663","height":"0.6030770379809333","deviation":0.28828624319183,"cv":0.0,"valueAIList":[{"time":1537516800000,"objectId":8009,"cv":177.0},{"time":1537459200000,"objectId":8009,"cv":1.0},{"time":1537430400000,"objectId":8009,"cv":66.0},{"time":1537372800000,"objectId":8009,"cv":1.0},{"time":1536652800000,"objectId":8009,"cv":117.0},{"time":1536595200000,"objectId":8009,"cv":1.0},{"time":1533974400000,"objectId":8009,"cv":97.0},{"time":1533916800000,"objectId":8009,"cv":1.0},{"time":1528704000000,"objectId":8009,"cv":77.0},{"time":1528646400000,"objectId":8009,"cv":1.0},{"time":1515657600000,"objectId":8009,"cv":57.0},{"time":1515600000000,"objectId":8009,"cv":1.0}],"section":{"id":42,"name":"20区-污水仓","tunnelId":null,"totalCableNumber":5,"camera":null,"startPoint":null,"endPoint":null,"crtTime":1535611491000,"store":{"id":4,"name":"污水仓","tunnel":{"id":1,"name":"古城大街"},"storeType":{"id":4,"name":"污水仓","crtTime":1535611490000},"camera":null,"crtTime":1535611490000},"area":{"id":20,"name":"20区","location":"1","tunnelId":1,"camera":null,"crtTime":1535611490000}}}],"pageNum":1,"pageSize":5,"size":1,"startRow":1,"endRow":1,"pages":1,"prePage":0,"nextPage":0,"isFirstPage":true,"isLastPage":true,"hasPreviousPage":false,"hasNextPage":false,"navigatePages":8,"navigatepageNums":[1],"navigateFirstPage":1,"navigateLastPage":1,"lastPage":1,"firstPage":1}}
     * @author shaosen
     * @Date 2018年11月19日
     */
    @RequestMapping(value = "data-analyse/measvalue-ai/datagrid" ,method = RequestMethod.POST)
    public JSONObject dataGrid(@RequestBody MeasObjVo vo) {
    	PageInfo<MeasObjDto> pageInfo = measObjService.dataGrid(vo);
         
        List<MeasObjDto> objDtoList = pageInfo.getList();
        for (MeasObjDto measObjDto : objDtoList) {
        	List<MeasValueAI> aiList = measValueAIService.getListByObjectAndTime(measObjDto.getId(), vo.getStartTime(), vo.getEndTime());
        	measObjDto.setValueAIList(aiList);
		}
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, pageInfo);
    }
    
    
    
    /** 查询告警总数top10 
     * @param startTime 必须
     * @param endTime 必须
     * @return  {"msg":"请求成功","code":"200","data":[{"严重":0,"一般":0,"提示":2,"致命":0,"总数":2,"key":"监测对象1"},{"严重":0,"一般":0,"提示":1,"致命":0,"总数":1,"key":"监测对象2"}]}
     * @author shaosen
     * @Date 2018年11月19日
     */
    @RequestMapping(value="data-analyse/alarms/{startTime}/{endTime}/topN",method=RequestMethod.GET)
    public JSONObject getAlarmTop10(@PathVariable("startTime")long startTime,@PathVariable("endTime")long endTime) {
    	Date stime = DateUtil.setMillisTimestamp2Date(startTime);
    	Date etime = DateUtil.setMillisTimestamp2Date(endTime);
    	List<CountResult> list = alarmService.getObjCountByTimeOrderByDesc(stime,etime);
    	
    	List<Integer> ids = new ArrayList<>();
    	int topSize = 10;
    	if(list.size() < topSize) {
    		for (int i = 0; i < list.size(); i++) {
    			ids.add(list.get(i).getObjectId());
    		}
    	}else {
    		for (int i = 0; i < topSize; i++) {
    			ids.add(list.get(i).getObjectId());
			}
    	}
    
    	List<JSONObject> result = getResult(stime, etime, ids);
    	
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,result);
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
    		
    		JSONObject jj = new JSONObject();
    		jj.put("key", almList.get(0).getObjectName());
    		jj.put(AlarmLevelEnum.PROMPT.getName(), prompt);
    		jj.put(AlarmLevelEnum.NORMAL.getName(), normal);
    		jj.put(AlarmLevelEnum.SERIOUS.getName(), serious);
    		jj.put(AlarmLevelEnum.DANGEROUS.getName(), dangerous);
    		jj.put("总数", prompt + normal + serious + dangerous );
    		result.add(jj);
		}
		return result;
	}
    
    
    /**条件查询监测对象告警排名 
     * @param vo
     * @return   
     * @author shaosen
     * @Date 2018年11月20日
     */
    @RequestMapping(value="data-analyse/alarms/condition",method=RequestMethod.POST)
    public JSONObject test(@RequestBody MeasObjVo vo) {
    	
    	List<Integer> objectIdList = new ArrayList<>();
    	//获取object_id集合
    	List<MeasObjDto> list = measObjService.getMeasObjByCondition(vo);
    	for (MeasObjDto measObjDto : list) {
    		objectIdList.add(measObjDto.getId());
		}
    	LogUtil.info("objectIdList:" + objectIdList);
    	
    	List<JSONObject> result = getResult(vo.getStartTime(), vo.getEndTime(), objectIdList);
    	
    	//根据告警总数由多到少排列
    	List<JSONObject> selectSort = selectSort(result);
    	
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,selectSort);
    	
    	
//    	//统计object_id在alarm表中共有多少
//    	int totalCount = alarmService.getCountByObjectIds(objectIdList,vo.getStartTime(),vo.getEndTime());
//    	LogUtil.info("totalCount:" + totalCount);
//    	
//    	//开始分页
//    	PageBean<AlarmDto> pageBean = new PageBean<>(vo.getPageNum(), vo.getPageSize(), totalCount);
//    	LogUtil.info("start:" + pageBean.getStart());
//    	LogUtil.info("end:" + pageBean.getEnd());
//    	
//    	List<AlarmDto> almList = alarmService.startPage(pageBean.getStart(),pageBean.getEnd(),objectIdList,vo.getStartTime(),vo.getEndTime());
//    	pageBean.setList(almList);
//    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, pageBean);
    }



    /**选择排序由大到小排列
     */
	private List<JSONObject> selectSort(List<JSONObject> result) {
		for (int i=0;i<result.size()-1 ;i++ ){   
            int min=i;   
            for (int j=i+1;j<result.size() ;j++ ){   
            	int minval = result.get(min).getIntValue("总数");
            	int jval = result.get(j).getIntValue("总数");
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
  
    
}
