package com.bandweaver.tunnel.service.mam.measobj;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.bandweaver.tunnel.common.biz.constant.mam.ObjectType;
import com.bandweaver.tunnel.common.biz.dto.mam.report.MeasObjReportDto;
import com.bandweaver.tunnel.common.biz.itf.mam.locator.LocatorService;
import com.bandweaver.tunnel.common.biz.itf.mam.measobj.MeasObjSOService;
import com.bandweaver.tunnel.common.biz.itf.mam.report.MeasObjReportService;
import com.bandweaver.tunnel.common.biz.pojo.mam.*;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.*;
import com.bandweaver.tunnel.common.biz.pojo.mam.report.MeasObjReport;
import com.bandweaver.tunnel.dao.mam.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bandweaver.tunnel.common.biz.constant.TimeEnum;
import com.bandweaver.tunnel.common.biz.constant.mam.DataType;
import com.bandweaver.tunnel.common.biz.itf.ModuleCenterInterface;
import com.bandweaver.tunnel.common.biz.pojo.mam.video.Video;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.DateUtil;
import com.bandweaver.tunnel.common.platform.util.MathUtil;
import com.bandweaver.tunnel.common.platform.util.SpringContextHolder;
import com.bandweaver.tunnel.service.mam.video.VideoModuleCenter;

import sun.util.logging.resources.logging;

@Service
public class MeasObjModuleCenter implements ModuleCenterInterface {
    @Autowired
    private MeasObjMapper measObjMapper;
    @Autowired
    private MeasObjAIMapper measObjAIMapper;
    @Autowired
    private MeasObjDIMapper measObjDIMapper;
    @Autowired
    private MeasObjSIMapper measObjSIMapper;
    @Autowired
    private MeasObjSOMapper measObjSOMapper;
    @Autowired
    private MeasObjDistributeMapper measObjDistributeMapper;
    @Autowired
    private VideoModuleCenter videoModuleCenter;
    @Autowired
    private LocatorService locatorService;
    @Autowired
    private MeasObjReportService measObjReportService;
    
    /**
     * value table
     */
    @Autowired
    private MeasValueAIMapper measValueAIMapper;
    @Autowired
    private MeasValueDIMapper measValueDIMapper;
    @Autowired
    private MeasValueSIMapper measValueSIMapper;
    @Autowired
    private MeasValueSOMapper measValueSOMapper;
    @Autowired
    private MeasValueDistributeMapper measValueDistributeMapper;
    @Autowired
    private MeasValueDASSpectrumMapper measValueDASSpectrumMapper;
    @Autowired
    private MeasValueTabdictMapper measValueTabdictMapper;
    

    private HashMap<Integer, MeasObj> measObjHashMap;
    private HashMap<Integer, MeasObjAI> measObjAIHashMap;
    private HashMap<Integer, MeasObjDI> measObjDIHashMap;
    private HashMap<Integer, MeasObjSI> measObjSIHashMap;
    private HashMap<Integer, MeasObjSO> measObjSOHashMap;
    private HashMap<Integer, MeasObjDistribute> measObjDistributeHashMap;

    public List<MeasObj> getMeasObjs() {
        return new ArrayList<MeasObj>(measObjHashMap.values());
    }

    public List<MeasObjAI> getMeasObjAIs() {
        return new ArrayList<MeasObjAI>(measObjAIHashMap.values());
    }

    public List<MeasObjDI> getMeasObjDIs() {
        return new ArrayList<MeasObjDI>(measObjDIHashMap.values());
    }

    public List<MeasObjSI> getMeasObjSIs() {
        return new ArrayList<MeasObjSI>(measObjSIHashMap.values());
    }

    public List<MeasObjSO> getMeasObjSOs() {
        return new ArrayList<MeasObjSO>(measObjSOHashMap.values());
    }

    public List<MeasObjDistribute> getMeasObjDistribute() {
        return new ArrayList<MeasObjDistribute>(measObjDistributeHashMap.values());
    }

    public MeasObj getMeasObj(int id) {
        return measObjHashMap.get(id);
    }
    
    public MeasObjAI getMeasObjAI(int id) {
    	return measObjAIHashMap.get(id);
    }
    
    public MeasObjDI getMeasObjDI(int id) {
    	return measObjDIHashMap.get(id);
    }
    
    public MeasObjSI getMeasObjSI(int id) {
    	return measObjSIHashMap.get(id);
    }

    public MeasObjSO getMeasObjSO(int id) {
        return measObjSOHashMap.get(id);
    }



    public List<MeasObj> getMeasObjsByIds(List<Integer> ids) {
        List<MeasObj> results = new ArrayList<>();
        for (Integer id : ids) {
            results.add(measObjHashMap.get(id));
        }
        return results;
    }
    public List<MeasObjAI> getMeasObjAIListByIds(List<Integer> ids) {
    	List<MeasObjAI> results = new ArrayList<>();
    	for (Integer id : ids) {
    		results.add(measObjAIHashMap.get(id));
    	}
    	return results;
    }
    public List<MeasObjDI> getMeasObjDIListByIds(List<Integer> ids) {
    	List<MeasObjDI> results = new ArrayList<>();
    	for (Integer id : ids) {
    		results.add(measObjDIHashMap.get(id));
    	}
    	return results;
    }
    
    public Object getMeasObjSIListByIds(List<Integer> ids) {
		List<MeasObjSI> results = new ArrayList<>();
    	for (Integer id : ids) {
    		results.add(measObjSIHashMap.get(id));
    	}
    	return results;
	}

    public Object getMeasObjSOListByIds(List<Integer> ids) {
        List<MeasObjSO> results = new ArrayList<>();
        for (Integer id : ids) {
            results.add(measObjSOHashMap.get(id));
        }
        return results;
    }

//    @Transactional
    public void insertMeasObj(MeasObj measObj) {
        if (measObjHashMap.containsKey(measObj.getId())) return;
     
        // 塞进数据库
        measObjMapper.insertSelective(measObj);
        insertObj2OwnDB(measObj);
       
        // 数据库成功后塞进缓存
        measObjHashMap.put(measObj.getId(), measObj);
        insertObj2OwnHashMap(measObj);
    }

    
    public void addMeasObjHashMap(MeasObj measObj){
        measObjHashMap.put(measObj.getId(), measObj);
    }

    public void updateMeasObj(MeasObj measObj) {
        //LogUtil.info("update measobj:" + measObj);
        //LogUtil.info("hashmap measobj:" + measObjHashMap);

        if (!measObjHashMap.containsKey(measObj.getId())) return;

        measObjHashMap.put(measObj.getId(), measObj);
        measObjMapper.updateByPrimaryKeySelective(measObj);
    }

    
    /** 更新ai缓存
     * @param measValueAI   
     * @author shaosen
     * @Date 2018年10月18日
     */
    public void updateMeasObjAIValue(MeasValueAI measValueAI) {
        if (!measObjAIHashMap.containsKey(measValueAI.getObjectId())) return;
        
        //更新缓存
        MeasObjAI ai = measObjAIHashMap.get(measValueAI.getObjectId());
    
        if (ai.getRefreshTime() != null && ai.getRefreshTime().getTime() >= measValueAI.getTime().getTime()) return;
        ai.setCV(measValueAI.getCV());
        ai.setRefreshTime(measValueAI.getTime());
    }

    
    
    /** 更新di缓存
     * @param measValueDI   
     * @author shaosen
     * @Date 2018年10月18日
     */
    public void updateMeasObjDIValue(MeasValueDI measValueDI) {
    	 if (!measObjDIHashMap.containsKey(measValueDI.getObjectId())) return;
         
         //更新缓存
         MeasObjDI di = measObjDIHashMap.get(measValueDI.getObjectId());
         if (di.getRefreshTime() != null && di.getRefreshTime().getTime() >= measValueDI.getTime().getTime()) return;
         di.setCV(measValueDI.getCV());
         di.setRefreshTime(measValueDI.getTime());
    }

    /**更新si缓存
     * @param measValueSI   
     * @author shaosen
     * @Date 2018年10月18日
     */
    public void updateMeasObjSIValue(MeasValueSI measValueSI) {
    	if (!measObjSIHashMap.containsKey(measValueSI.getObjectId())) return;
         
         //更新缓存
         MeasObjSI si = measObjSIHashMap.get(measValueSI.getObjectId());
         if (si.getRefreshTime() != null && si.getRefreshTime().getTime() >= measValueSI.getTime().getTime()) return;
         si.setCV(measValueSI.getCV());
         si.setRefreshTime(measValueSI.getTime());
    }
    
    /**更新分布式缓存 
     * @param measValueDistribute   
     * @author shaosen
     * @Date 2018年10月22日
     */
    public void updateMeasObjDistributeValue(MeasValueDistribute measValueDistribute) {
        if (!measObjDistributeHashMap.containsKey(measValueDistribute.getObjectId())) return;
        MeasObjDistribute tmp = measObjDistributeHashMap.get(measValueDistribute.getObjectId());
        if (tmp.getRefreshTime() != null && tmp.getRefreshTime().getTime() >= measValueDistribute.getTime().getTime())
            return;

        tmp.setDcv(measValueDistribute.getDcv());
        tmp.setRefreshTime(measValueDistribute.getTime());
        tmp.setSpacePrecision(measValueDistribute.getSpacePrecision());
    }
    

    /**更新so缓存  
     * @param measValueSO   
     * @author shaosen
     * @Date 2018年10月22日
     */
    public void updateMeasObjSOValue(MeasValueSO measValueSO) {
        if (!measObjSOHashMap.containsKey(measValueSO.getObjectId())) return;

        MeasObjSO tmp = measObjSOHashMap.get(measValueSO.getObjectId());

        if(!tmp.isActived()) {return;}
        if (tmp.getRefreshTime() != null && tmp.getRefreshTime().getTime() >= measValueSO.getTime().getTime()) return;

        // 如果是定位类型，转换之
        if (tmp.getObjtypeId() == ObjectType.POSITION.getValue()) {
            locatorService.update(tmp, measValueSO);
        } else {
            tmp.setRefreshTime(measValueSO.getTime());
            tmp.setCV(measValueSO.getCV());
        }
    }

    
    public void insertMeasObjSOValue(MeasValueSO measValueSO) {
        if (!measObjSOHashMap.containsKey(measValueSO.getObjectId())) return;

        MeasObjSO tmp = measObjSOHashMap.get(measValueSO.getObjectId());
        if (tmp.getRefreshTime() != null && tmp.getRefreshTime().getTime() >= measValueSO.getTime().getTime()) return;

        tmp.setRefreshTime(measValueSO.getTime());
        tmp.setCV(measValueSO.getCV());
        measObjSOMapper.update(tmp);
        measValueSOMapper.addMeasValueSO(measValueSO);
        //LogUtil.info("after insert MeasObjSO : " + tmp);

    }

   

    /**
     * 初始化数据
     */
    private void initData() {

        measObjHashMap = new HashMap<>();
        measObjAIHashMap = new HashMap<>();
        measObjDIHashMap = new HashMap<>();
        measObjSIHashMap = new HashMap<>();
        measObjSOHashMap = new HashMap<>();
        measObjDistributeHashMap = new HashMap<>();

        List<MeasObj> measObjs = measObjMapper.getAllMeasObjList();
        for (MeasObj measObj : measObjs) {
            measObjHashMap.put(measObj.getId(), measObj);
        }
        List<MeasObjAI> measObjAIS = measObjAIMapper.getAllMeasObjAIs();
        for (MeasObjAI tmp : measObjAIS) {
            measObjAIHashMap.put(tmp.getId(), tmp);
        }
        List<MeasObjDI> measObjDIS = measObjDIMapper.getAllMeasObjDIs();
        for (MeasObjDI tmp : measObjDIS) {
        	measObjDIHashMap.put(tmp.getId(), tmp);
        }
        List<MeasObjSI> measObjSIs = measObjSIMapper.getAllMeasObjSIs();
        for (MeasObjSI tmp : measObjSIs) {
        	measObjSIHashMap.put(tmp.getId(), tmp);
        }

        List<MeasObjSO> measObjSOs = measObjSOMapper.getAllMeasObjSOs();
        //LogUtil.info("measObjSOs:" + measObjSOs);
        for (MeasObjSO tmp : measObjSOs) {
            measObjSOHashMap.put(tmp.getId(), tmp);
        }
        //LogUtil.info("measObjSOHashMap:" + measObjSOHashMap);

        List<MeasObjDistribute> measObjDistributes = measObjDistributeMapper.getAllMeasObjDistributes();
        for (MeasObjDistribute tmp : measObjDistributes) {
            measObjDistributeHashMap.put(tmp.getId(), tmp);
        }
    }

    private void insertObj2OwnDB(MeasObj measObj) {
        DataType dataType = DataType.getEnum(measObj.getDatatypeId().intValue());
//        LogUtil.info(dataType);
        switch (dataType) {
            case AI:
                MeasObjAI measObjAI = MeasObjAI.fromMeasObj(measObj);
                measObjAIMapper.insertSelective(measObjAI);
                break;
                
            case DI:
            	MeasObjDI measObjDI = MeasObjDI.fromMeasObj(measObj);
            	measObjDIMapper.insertSelective(measObjDI);
            	break;
            	
            case SI:
            	MeasObjSI measObjSI = MeasObjSI.fromMeasObj(measObj);
            	measObjSIMapper.insertSelective(measObjSI);
            	break;

            case SO:
                MeasObjSO measObjSO = MeasObjSO.fromMeasObj(measObj);
                measObjSOMapper.insertSelective(measObjSO);
                break;

            case DISTRIBUTE:
                MeasObjDistribute measObjDistribute = MeasObjDistribute.fromMeasObj(measObj);
                measObjDistributeMapper.insertSelective(measObjDistribute);
                break;

            case VIDEO:
                Video video = Video.fromMeasObj(measObj);
                video.setServerId(1);
                video.setVideoSceneId(1);
                video.setChannelNo(1);
                videoModuleCenter.insertVideo2DB(video);
                break;

            default:
                break;
        }
    }

    private void insertObj2OwnHashMap(MeasObj measObj){
        DataType dataType = DataType.getEnum(measObj.getDatatypeId().intValue());
        switch (dataType) {
            case AI:
                MeasObjAI measObjAI = MeasObjAI.fromMeasObj(measObj);
                measObjAIHashMap.put(measObjAI.getId(), measObjAI);
                break;

            case DI:
            	MeasObjDI measObjDI = MeasObjDI.fromMeasObj(measObj);
            	measObjDIHashMap.put(measObjDI.getId(), measObjDI);
            	break;
            	
            case SI:
            	MeasObjSI measObjSI = MeasObjSI.fromMeasObj(measObj);
            	measObjSIHashMap.put(measObjSI.getId(), measObjSI);
            	break;

            case SO:
                MeasObjSO measObjSO = MeasObjSO.fromMeasObj(measObj);
                measObjSOHashMap.put(measObjSO.getId(), measObjSO);
                break;

            case DISTRIBUTE:
                MeasObjDistribute measObjDistribute = MeasObjDistribute.fromMeasObj(measObj);
                measObjDistributeHashMap.put(measObjDistribute.getId(), measObjDistribute);
                break;

            case VIDEO:
                Video video = Video.fromMeasObj(measObj);
                video.setServerId(1);
                video.setVideoSceneId(1);
                video.setChannelNo(1);
                videoModuleCenter.insertVideo2HashMap(video);
                break;

            default:
                break;
        }
    }

    @Override
    public void start() {
        //LogUtil.info("MeasObj ModuleCenter start !!");
        initData();
    }

    @Override
    public void stop() {

    }

    
    
//**********************************************************************************************
//**********************************************************************************************
//**********************定时任务保存数据********************************************************
//**********************************************************************************************
//**********************************************************************************************
    
    
    public void saveDataScheduleBatch() {
    	saveAISchedule();
    	saveDISchedule();
    	saveSISchedule();
    	saveDistributeSchedule();
    	saveSOSchedule();
    }
    
    
    /**AI--定时任务保存缓存中的数据到对象表和valu表 
     * @author shaosen
     * @Date 2018年9月10日
     */
    public void saveAISchedule() {
    	 //从缓存中获取数据，然后定时向对象表中更新数据，并同时保存到value表中
        List<MeasObjAI> measObjAIs = getMeasObjAIs();
        for (MeasObjAI measObjAI : measObjAIs) {
        	
        	if(measObjAI.getRefreshTime() == null) {continue;}
        	
        	//step1:更新对象表
        	MeasObjAI fromDb = measObjAIMapper.getMeasObjAI(measObjAI.getId());
        	if(fromDb.getRefreshTime() != null && fromDb.getRefreshTime().getTime()/1000 >= measObjAI.getRefreshTime().getTime()/1000) {
        		//LogUtil.info("AI监测对象[" + measObjAI.getId() + "]缓存数据暂无更新 ");
        		continue;
        	}
        	fromDb.setRefreshTime(measObjAI.getRefreshTime());
        	fromDb.setCV(measObjAI.getCV());
        	measObjAIMapper.update(fromDb);
        	
        	//step2:保存数据到value表中
        	MeasValueAI measValueAI = new MeasValueAI();
        	measValueAI.setObjectId(measObjAI.getId());
        	measValueAI.setTime(measObjAI.getRefreshTime());
        	measValueAI.setCV(measObjAI.getCV());
        	measValueAIMapper.addMeasValueAI(measValueAI);
    	}
        LogUtil.info("定时保存缓存中AI数据成功");
    }
    
    
    /**DI 
     */
    public void saveDISchedule() {
   	 //从缓存中获取数据，然后定时向对象表中更新数据，并同时保存到value表中
       List<MeasObjDI> measObjDIs =getMeasObjDIs();
       for (MeasObjDI measObjDI : measObjDIs) {
    	    if(measObjDI.getRefreshTime() == null) {continue;}
	       	//step1:更新对象表
	       	MeasObjDI fromDb = measObjDIMapper.getMeasObjDI(measObjDI.getId());
	       	if(fromDb.getRefreshTime() != null && fromDb.getRefreshTime().getTime()/1000 >= measObjDI.getRefreshTime().getTime()/1000) {
	       		//LogUtil.info("DI监测对象[" + measObjDI.getId() + "]缓存数据暂无更新 ");
	       		continue;
	       	}
	       	fromDb.setRefreshTime(measObjDI.getRefreshTime());
	       	fromDb.setCV(measObjDI.getCV());
	       	measObjDIMapper.update(fromDb);
	       	
	       	//step2:保存数据到value表中
	       	MeasValueDI measValueDI = new MeasValueDI();
	       	measValueDI.setObjectId(measObjDI.getId());
	       	measValueDI.setTime(measObjDI.getRefreshTime());
	       	measValueDI.setCV(measObjDI.getCV());
	       	measValueDIMapper.addMeasValueDI(measValueDI);
   	}
       LogUtil.info("定时保存缓存中DI数据成功");
   }
    
    /**SI 
     */
    public void saveSISchedule() {
   	 //从缓存中获取数据，然后定时向对象表中更新数据，并同时保存到value表中
       List<MeasObjSI> measObjSIs = getMeasObjSIs();
       for (MeasObjSI measObjSI : measObjSIs) {
    	if(measObjSI.getRefreshTime() == null) {continue;}
       	//step1:更新对象表
       	MeasObjSI fromDb = measObjSIMapper.getMeasObjSI(measObjSI.getId());
       	if(fromDb.getRefreshTime() != null && fromDb.getRefreshTime().getTime()/1000 >= measObjSI.getRefreshTime().getTime()/1000) {
       		//LogUtil.info("SI监测对象[" + measObjSI.getId() + "]缓存数据暂无更新 ");
       		continue;
       	}
       	fromDb.setRefreshTime(measObjSI.getRefreshTime());
       	fromDb.setCV(measObjSI.getCV());
       	measObjSIMapper.update(fromDb);
       	
    	//step2:保存数据到value表中
       	MeasValueSI measValueSI = new MeasValueSI();
       	measValueSI.setObjectId(measObjSI.getId());
       	measValueSI.setTime(measObjSI.getRefreshTime());
       	measValueSI.setCV(measObjSI.getCV());
       	measValueSIMapper.addMeasValueSI(measValueSI);
   	}
       LogUtil.info("定时保存缓存中SI数据成功");
   }
    
    
    /**分布式数据定时保存
     */
    public void saveDistributeSchedule() {
   	   //从缓存中获取数据，然后定时向对象表中更新数据，并同时保存到value表中
       List<MeasObjDistribute> measObjDistributes = getMeasObjDistribute();
       for (MeasObjDistribute distribute : measObjDistributes) {
    	if(distribute.getRefreshTime() == null) {continue;}
       	//step1:更新对象表
        MeasObjDistribute fromDb = measObjDistributeMapper.getMeasObjDistribute(distribute.getId());
       	if(fromDb.getRefreshTime() != null && fromDb.getRefreshTime().getTime()/1000 >= distribute.getRefreshTime().getTime()/1000) {
       		continue;
       	}
       	fromDb.setRefreshTime(distribute.getRefreshTime());
       	fromDb.setDcv(distribute.getDcv());
       	fromDb.setSpacePrecision(distribute.getSpacePrecision());
       	measObjDistributeMapper.update(fromDb);
       	
    	//step2:保存数据到value表中
       	MeasValueDistribute measValueDistribute = new MeasValueDistribute();
       	measValueDistribute.setObjectId(distribute.getId());
       	measValueDistribute.setTime(distribute.getRefreshTime());
       	measValueDistribute.setDcv(distribute.getDcv());
       	measValueDistribute.setSpacePrecision(distribute.getSpacePrecision());
       	measValueDistributeMapper.addMeasValueDis(measValueDistribute);
   	}
       LogUtil.info("定时保存缓存中分布式数据成功");
   }
    
    
    /**SO 
     */
    public void saveSOSchedule() {
   	 //从缓存中获取数据，然后定时向对象表中更新数据，并同时保存到value表中
       List<MeasObjSO> measObjSOs = getMeasObjSOs();
       for (MeasObjSO measObjSO : measObjSOs) {
    	   if(measObjSO.getRefreshTime() == null) {continue;}
       	//step1:更新对象表
       	MeasObjSO fromDb = measObjSOMapper.getMeasObjSO(measObjSO.getId());
       	if(fromDb.getRefreshTime() != null && fromDb.getRefreshTime().getTime()/1000 >= measObjSO.getRefreshTime().getTime()/1000) {
//       		LogUtil.info("SO监测对象[" + measObjSO.getId() + "]缓存数据暂无更新 ");
       		continue;
       	}
       	fromDb.setRefreshTime(measObjSO.getRefreshTime());
       	fromDb.setCV(measObjSO.getCV());
       	measObjSOMapper.update(fromDb);
       	
    	//step2:保存数据到value表中
       	MeasValueSO measValueSO = new MeasValueSO();
       	measValueSO.setObjectId(measObjSO.getId());
       	measValueSO.setTime(measObjSO.getRefreshTime());
       	measValueSO.setCV(measObjSO.getCV());
       	measValueSOMapper.addMeasValueSO(measValueSO);
   	}
       LogUtil.info("定时保存缓存中SO数据成功");
   }
	
    
    
    
//******************************************************************************************
//******************************************************************************************
//*************************************定时任务保存统计数据*********************************
//******************************************************************************************
//******************************************************************************************
    
    /**统计每时的最大最小平均值 
     * @author shaosen
     * @Date 2018年9月12日
     */
    public void reportMeasObjHour() {
    	
    	Date beforHour = DateUtil.getBeforHour(new Date(), 1);
		Date startTime = DateUtil.getHourBeginTime(beforHour);
		Date endTime = DateUtil.getHourEndTime(beforHour);
		
    	List<MeasObjAI> measObjAIs = getMeasObjAIs();
    	for (MeasObjAI measObjAI : measObjAIs) {
    		double maxValue = 0.00;
    		double minValue = 0.00;
    		double avgValue = 0.00;
    		List<MeasValueAI> ai_value_list = measValueAIMapper.getListByObjectIdAndTime(measObjAI.getId(), startTime, endTime);
    		if(ai_value_list != null && ai_value_list.size() > 0 ) {
    			maxValue = getMaxValue(ai_value_list);
    			minValue = getMinValue(ai_value_list);
    			avgValue = getAvgValue(ai_value_list);
    		}
    		
    		//防止重复插入
    		MeasObjReport measObjReport = measObjReportService.getByObjectIdAndStartTime(measObjAI.getId(),startTime);
    		if(measObjReport != null) 
    			continue;
    		else
    			saveReport(measObjAI, TimeEnum.HOUR.getValue(),startTime, endTime, maxValue, minValue, avgValue);
		}
    	//LogUtil.info("统计每时的最大最小平均值 END");
    }
    
    
    /**统计每天的最大最小平均值 
     * @author shaosen
     * @Date 2018年9月12日
     */
    public void reportMeasObjDay() {
    	Date startTime = DateUtil.getDayStartTime(DateUtil.getFrontDay(new Date(),1));
    	Date endTime = DateUtil.getDayEndTime(DateUtil.getFrontDay(new Date(),1));
    	reportMeasObjArgs(startTime, endTime, TimeEnum.HOUR.getValue(), TimeEnum.DAY.getValue());
    	//LogUtil.info("统计每天的最大最小平均值END");
    }
    
    /**统计每周的最大最小平均值  
     * @author shaosen
     * @Date 2018年9月12日
     */
    public void reportMeasObjWeek() {
    	Date startTime = DateUtil.getBeginDayOfWeek(DateUtil.getFrontDay(new Date(),1));
    	Date endTime = DateUtil.getEndDayOfWeek(DateUtil.getFrontDay(new Date(),1));
    	reportMeasObjArgs(startTime, endTime, TimeEnum.DAY.getValue(), TimeEnum.WEEK.getValue());
    	//LogUtil.info("统计每周的最大最小平均值 END");
    }
    
    /**统计每月的最大最小平均值   
     * @author shaosen
     * @Date 2018年9月12日
     */
    public void reportMeasObjMonth() {
    	Date startTime = DateUtil.getBeginDayOfMonth(DateUtil.getFrontDay(new Date(),1));
    	Date endTime = DateUtil.getEndDayOfMonth(DateUtil.getFrontDay(new Date(),1));
    	reportMeasObjArgs(startTime, endTime, TimeEnum.WEEK.getValue(), TimeEnum.MONTH.getValue());
    	//LogUtil.info("统计每月的最大最小平均值END");
    }
    
    /**统计每年的最大最小平均值
     * @author shaosen
     * @Date 2018年9月12日
     */
    public void reportMeasObjYear() {
    	Date startTime = DateUtil.getBeginDayOfYear(DateUtil.getFrontDay(new Date(),1));
    	Date endTime = DateUtil.getEndDayOfYear(DateUtil.getFrontDay(new Date(),1));
    	reportMeasObjArgs(startTime, endTime, TimeEnum.MONTH.getValue(), TimeEnum.YEAR.getValue());
    	//LogUtil.info("统计每年的最大最小平均值END");
    }

    
    /**获取最大最小平均值并插入数据库 
     * @param startTime 
     * @param endTime
     * @param queryTimeType
     * @param insertTimeType   
     * @author shaosen
     * @Date 2018年9月15日
     */
    private void reportMeasObjArgs(Date startTime,Date endTime,Integer queryTimeType,Integer insertTimeType) {
    	List<MeasObjAI> measObjAIs = getMeasObjAIs();
    	for (MeasObjAI measObjAI : measObjAIs) {
    		double maxValue = 0.00;
    		double minValue = 0.00;
    		double avgValue = 0.00;
    		List<MeasObjReportDto> list = measObjReportService.getListByObjectIdAndTimeAndTimeType(measObjAI.getId(),startTime,endTime,queryTimeType);
    		if(list != null && list.size() > 0 ) {
    			maxValue = getMaxValue(list);
    			minValue = getMinValue(list);
    			avgValue = getAvgValue(list);
    		}
    		//防止重复插入
    		MeasObjReport measObjReport = measObjReportService.getByObjectIdAndStartTime(measObjAI.getId(),startTime);
    		if(measObjReport != null) 
    			continue;
    		else
    			saveReport(measObjAI, insertTimeType,startTime, endTime, maxValue, minValue, avgValue);
		}
    }
    
    
	/**保存数据库 
	 * @param measObjAI
	 * @param timeType
	 * @param startTime
	 * @param endTime
	 * @param maxValue
	 * @param minValue
	 * @param avgValue   
	 * @author shaosen
	 * @Date 2018年9月15日
	 */
	public void saveReport(MeasObjAI measObjAI, Integer timeType, Date startTime, Date endTime, double maxValue, double minValue,
			double avgValue) {
		MeasObjReport report = new MeasObjReport();
		report.setObjectId(measObjAI.getId());
		report.setObjectType(measObjAI.getObjtypeId());
		report.setTimeType(timeType);
		report.setsTime(startTime);
		report.seteTime(endTime);
		report.setMaxValue(maxValue);
		report.setMinValue(minValue);
		report.setAvgValue(avgValue);
		measObjReportService.add(report);
	}

	/**求平均值 
	 * @param list
	 * @return   
	 * @author shaosen
	 * @Date 2018年9月15日
	 */
	private double getAvgValue(List<?> list) {
		double totalValue = 0.00;
		Object object = list.get(0);
		if(object instanceof MeasValueAI) {
			for (Object obj : list) {
				MeasValueAI ai = (MeasValueAI)obj;
				totalValue = MathUtil.add(ai.getCV(), totalValue);
			}
		}else if(object instanceof MeasObjReportDto) {
			for (Object obj : list) {
				MeasObjReportDto report = (MeasObjReportDto)obj;
				totalValue = MathUtil.add(report.getAvgValue(), totalValue);
			}
		}
		return MathUtil.div(totalValue, (double)list.size(), 2);
	}

	/**求最小值 
	 * @param list
	 * @return   
	 * @author shaosen
	 * @Date 2018年9月15日
	 */
	private double getMinValue(List<?> list) {
		Object object = list.get(0);
		if(object instanceof MeasValueAI) {
			MeasValueAI measValueAI = (MeasValueAI)object;
			double minValue = measValueAI.getCV();
			for (Object obj : list) {
				MeasValueAI ai = (MeasValueAI)obj;
				minValue = MathUtil.sub(ai.getCV(), minValue) < 0 ? ai.getCV() : minValue ;
			}
			return minValue;
		}else if(object instanceof MeasObjReportDto) {
			MeasObjReportDto measObjReportDto = (MeasObjReportDto)object;
			double minValue = measObjReportDto.getMinValue();
			for (Object obj : list) {
				MeasObjReportDto report = (MeasObjReportDto)obj;
				minValue = MathUtil.sub(report.getMinValue(), minValue) < 0 ? report.getMinValue() : minValue ;
			}
			return minValue;
		}
		return 0;
	}

	/**求最大值 
	 * @param list
	 * @return   
	 * @author shaosen
	 * @Date 2018年9月15日
	 */
	private double getMaxValue(List<?> list) {
		Object object = list.get(0);
		if(object instanceof MeasValueAI) {
			MeasValueAI measValueAI = (MeasValueAI)object;
			double maxValue = measValueAI.getCV();
			for (Object obj : list) {
				MeasValueAI ai = (MeasValueAI)obj;
				maxValue = MathUtil.sub(ai.getCV(), maxValue) > 0 ? ai.getCV() : maxValue ;
			}
			return maxValue;
		}else if(object instanceof MeasObjReportDto) {
			MeasObjReportDto measObjReportDto = (MeasObjReportDto)object;
			double maxValue = measObjReportDto.getMaxValue();
			for (Object obj : list) {
				MeasObjReportDto report = (MeasObjReportDto)obj;
				maxValue = MathUtil.sub(report.getMaxValue(), maxValue) > 0 ? report.getMaxValue() : maxValue ;
			}
			return maxValue;
		}
		return 0;
	}
    
	/** 定时删除监测对象表数据
	 * @author ya.liu
	 * @Date 2018年10月18日
	 */
    public void deleteDataSchedule() {
    	// 获取一年后的时间
    	Date time = new Date();
    	time.setYear(time.getYear() - 1);
    	// 删除AI缓存
    	measValueAIMapper.deleteBatch(time);
    	//删除DI缓存
    	measValueDIMapper.deleteBatch(time);
    	//删除SI缓存
    	measValueSIMapper.deleteBatch(time);
    	//删除SO缓存
    	measValueSOMapper.deleteBatch(time);
    	//删除DIstritute缓存
    	measValueDistributeMapper.deleteBatch(time);
    	//删除DASSpectrum缓存
    	measValueDASSpectrumMapper.deleteBatch(time);
    	//删除Tabdict缓存
    	measValueTabdictMapper.deleteBatch(time);
    	//删除定时保存的数据
    	measObjReportService.deleteBatch(time);
    }
    
}	
