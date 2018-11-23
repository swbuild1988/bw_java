package com.bandweaver.tunnel.controller.quartz;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.bandweaver.tunnel.common.biz.constant.TimeEnum;
import com.bandweaver.tunnel.common.biz.constant.mam.ObjectType;
import com.bandweaver.tunnel.common.biz.dto.TunnelDto;
import com.bandweaver.tunnel.common.biz.dto.TunnelSimpleDto;
import com.bandweaver.tunnel.common.biz.dto.mam.report.MeasObjReportDto;
import com.bandweaver.tunnel.common.biz.dto.oam.ConsumeDto;
import com.bandweaver.tunnel.common.biz.itf.SectionService;
import com.bandweaver.tunnel.common.biz.itf.TunnelService;
import com.bandweaver.tunnel.common.biz.itf.mam.locator.LocatorService;
import com.bandweaver.tunnel.common.biz.itf.mam.maxview.MaxviewConfigService;
import com.bandweaver.tunnel.common.biz.itf.mam.report.MeasObjReportService;
import com.bandweaver.tunnel.common.biz.itf.oam.ConsumeDataService;
import com.bandweaver.tunnel.common.biz.itf.oam.ConsumeService;
import com.bandweaver.tunnel.common.biz.itf.oam.EnergyService;
import com.bandweaver.tunnel.common.biz.pojo.mam.MeasValueAI;
import com.bandweaver.tunnel.common.biz.pojo.mam.MeasValueDI;
import com.bandweaver.tunnel.common.biz.pojo.mam.MeasValueDistribute;
import com.bandweaver.tunnel.common.biz.pojo.mam.MeasValueSI;
import com.bandweaver.tunnel.common.biz.pojo.mam.MeasValueSO;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObjAI;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObjDI;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObjDistribute;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObjSI;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObjSO;
import com.bandweaver.tunnel.common.biz.pojo.mam.report.MeasObjReport;
import com.bandweaver.tunnel.common.biz.pojo.oam.Consume;
import com.bandweaver.tunnel.common.biz.pojo.oam.ConsumeData;
import com.bandweaver.tunnel.common.biz.pojo.oam.Energy;
import com.bandweaver.tunnel.common.biz.vo.mam.report.MeasObjReportVo;
import com.bandweaver.tunnel.common.biz.vo.oam.ConsumeDataVo;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.DateUtil;
import com.bandweaver.tunnel.common.platform.util.MathUtil;
import com.bandweaver.tunnel.dao.mam.MeasObjAIMapper;
import com.bandweaver.tunnel.dao.mam.MeasObjDIMapper;
import com.bandweaver.tunnel.dao.mam.MeasObjDistributeMapper;
import com.bandweaver.tunnel.dao.mam.MeasObjMapper;
import com.bandweaver.tunnel.dao.mam.MeasObjReportMapper;
import com.bandweaver.tunnel.dao.mam.MeasObjSIMapper;
import com.bandweaver.tunnel.dao.mam.MeasObjSOMapper;
import com.bandweaver.tunnel.dao.mam.MeasValueAIMapper;
import com.bandweaver.tunnel.dao.mam.MeasValueDASSpectrumMapper;
import com.bandweaver.tunnel.dao.mam.MeasValueDIMapper;
import com.bandweaver.tunnel.dao.mam.MeasValueDistributeMapper;
import com.bandweaver.tunnel.dao.mam.MeasValueSIMapper;
import com.bandweaver.tunnel.dao.mam.MeasValueSOMapper;
import com.bandweaver.tunnel.dao.mam.MeasValueTabdictMapper;
import com.bandweaver.tunnel.service.mam.measobj.MeasObjModuleCenter;
import com.bandweaver.tunnel.service.mam.video.VideoModuleCenter;

/**定时任务入口
 * @author shaosen
 * @date 2018年11月8日
 */
public class TaskEntrance {

	
	@Autowired
	private MeasObjModuleCenter measObjModuleCenter;
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
    @Autowired
    private TunnelService tunnelService;
    @Autowired
    private EnergyService energyService;
    
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
    @Autowired
    private MeasObjReportMapper measObjReportMapper;
    @Autowired
    private MaxviewConfigService maxviewConfigService;
    @Autowired
    private ConsumeService consumeService;
    @Autowired
    private ConsumeDataService consumeDataService;
    
    
    
    /**登录maxview终端之后，持续发送心跳 
     * @author shaosen
     * @Date 2018年11月16日
     */
    public void heartBeat() {
    	Set<Integer> set = new HashSet<>();
    	List<TunnelDto> list = tunnelService.getDtoList();
    	for (TunnelDto tunnelDto : list) {
			set.add(tunnelDto.getMaxviewConfigId());
		}
    	
    	for (Integer configId : set) {
    		maxviewConfigService.heartBeat(configId);
		}
    	
    }
	
	
	
	/** 定时任务保存缓存中的数据到对象表和valu表 
	 * @author shaosen
	 * @Date 2018年11月8日
	 */
	public void saveDataScheduleBatch() {
    	saveAISchedule();
    	saveDISchedule();
    	saveSISchedule();
    	saveDistributeSchedule();
    	saveSOSchedule();
    }
    
//------------------------------适配器发送数据之后删除该段代码-----------------------
	private static double [] count = new double[48];
    static {
    	for(int i=0;i<48;i++) {
    		count[i] = 0.0;
    	}
    }
//-------------------------------------------------------------------------
	public void saveAISchedule() {
//------------------------------适配器发送数据之后删除该段代码-----------------------
		// TODO
    	List<MeasValueAI> list = new ArrayList<>();
    	List<ConsumeDto> consumes = consumeService.getConsumesByCondition(new ConsumeDataVo());
    	for(int i=0;i<48;i++) {
    		double d = MathUtil.div((double)((int)(Math.random()*100)), 100.0);
    		count[i] = MathUtil.add(count[i], d);
    	}
    	for(ConsumeDto dto : consumes) {
    		if(dto.getObjectId() == null) continue;
    		MeasValueAI ai = new MeasValueAI();
    		ai.setObjectId(dto.getObjectId());
    		ai.setTime(new Date());
    		ai.setCV(count[dto.getObjectId() - 1001]);
    		list.add(ai);
    	}
    	for (MeasValueAI measValueAI : list) {
            measObjModuleCenter.updateMeasObjAIValue(measValueAI);
        }
    	LogUtil.info("缓存成功");
//---------------------------------------------------------------------------
    	 //从缓存中获取数据，然后定时向对象表中更新数据，并同时保存到value表中
        List<MeasObjAI> measObjAIs = measObjModuleCenter.getMeasObjAIs();
        for (MeasObjAI measObjAI : measObjAIs) {
        	
        	if(measObjAI.getRefreshTime() == null) {continue;}
        	
        	//step1:更新对象表
        	MeasObjAI fromDb = measObjAIMapper.getMeasObjAI(measObjAI.getId());
        	if(fromDb.getRefreshTime() != null && fromDb.getRefreshTime().getTime()/1000 >= measObjAI.getRefreshTime().getTime()/1000) {
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
    
    
    public void saveDISchedule() {
   	 //从缓存中获取数据，然后定时向对象表中更新数据，并同时保存到value表中
       List<MeasObjDI> measObjDIs =measObjModuleCenter.getMeasObjDIs();
       for (MeasObjDI measObjDI : measObjDIs) {
    	    if(measObjDI.getRefreshTime() == null) {continue;}
	       	//step1:更新对象表
	       	MeasObjDI fromDb = measObjDIMapper.getMeasObjDI(measObjDI.getId());
	       	if(fromDb.getRefreshTime() != null && fromDb.getRefreshTime().getTime()/1000 >= measObjDI.getRefreshTime().getTime()/1000) {
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
    
    public void saveSISchedule() {
   	 //从缓存中获取数据，然后定时向对象表中更新数据，并同时保存到value表中
       List<MeasObjSI> measObjSIs = measObjModuleCenter.getMeasObjSIs();
       for (MeasObjSI measObjSI : measObjSIs) {
    	if(measObjSI.getRefreshTime() == null) {continue;}
       	//step1:更新对象表
       	MeasObjSI fromDb = measObjSIMapper.getMeasObjSI(measObjSI.getId());
       	if(fromDb.getRefreshTime() != null && fromDb.getRefreshTime().getTime()/1000 >= measObjSI.getRefreshTime().getTime()/1000) {
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
    
    
    public void saveDistributeSchedule() {
   	   //从缓存中获取数据，然后定时向对象表中更新数据，并同时保存到value表中
       List<MeasObjDistribute> measObjDistributes = measObjModuleCenter.getMeasObjDistribute();
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
    
    
    public void saveSOSchedule() {
   	 //从缓存中获取数据，然后定时向对象表中更新数据，并同时保存到value表中
       List<MeasObjSO> measObjSOs = measObjModuleCenter.getMeasObjSOs();
       for (MeasObjSO measObjSO : measObjSOs) {
    	   if(measObjSO.getRefreshTime() == null) {continue;}
       	//step1:更新对象表
       	MeasObjSO fromDb = measObjSOMapper.getMeasObjSO(measObjSO.getId());
       	if(fromDb.getRefreshTime() != null && fromDb.getRefreshTime().getTime()/1000 >= measObjSO.getRefreshTime().getTime()/1000) {
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
    
    
    
    /**统计每时的最大最小平均值 
     * @author shaosen
     * @Date 2018年9月12日
     */
    public void reportMeasObjHour() {
    	
    	Date beforHour = DateUtil.getBeforHour(new Date(), 1);
		Date startTime = DateUtil.getHourBeginTime(beforHour);
		Date endTime = DateUtil.getHourEndTime(beforHour);
		
    	List<MeasObjAI> measObjAIs = measObjModuleCenter.getMeasObjAIs();
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
    	List<MeasObjAI> measObjAIs = measObjModuleCenter.getMeasObjAIs();
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
		report.setName(measObjAI.getName());
		report.setTunnelId(measObjAI.getTunnelId());
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
    
    
    
    
	/**定时任务统计每天的能耗并保存到ConsumeData表中 
	 * @author ya.liu
	 * @Date 2018年11月22日
	 */
	public void saveToConsumeData() {
		
		// 获取昨天开始时间
		Date startTime = DateUtil.getDayStartTime(DateUtil.getFrontDay(new Date(),1));
    	// 获取今天的开始时间
		Date endTime = DateUtil.getDayBegin();
		
		// 获取consume表所有电表(包括不存在的电表)
    	List<ConsumeDto> consumes = consumeService.getConsumes();
    	// for循环插入数据
    	for(ConsumeDto dto : consumes) {
    		ConsumeData data = new ConsumeData();
    		data.setId(dto.getId());
    		data.setTime(startTime);
    		Double sum = 0.0;
    		// 判断是否存在电表
    		if(dto.getObjectId() != null) {
    			List<MeasValueAI> ais = measValueAIMapper.getListByObjectIdAndTime(dto.getObjectId(), startTime, endTime);
    			//统计aiValue表中某一电表一天内的能耗
    			if(ais.size() > 0)
    				sum = MathUtil.sub(ais.get(0).getCV(), ais.get(ais.size() - 1).getCV());
    			data.setDirect(sum);
    		}
    		else if(dto.getCompute() != null) {
    			String [] s = dto.getCompute().split(",");
    			//统计没有电表时一天内的合计能耗
    			for(String ss : s) {
    				List<MeasValueAI> ais = measValueAIMapper.getListByObjectIdAndTime(Integer.valueOf(ss), startTime, endTime);
    				//统计aiValue表中某一电表一天内的能耗
    				if(ais.size() > 0) {
    					Double sum1 = MathUtil.sub(ais.get(0).getCV(), ais.get(ais.size() - 1).getCV());
    					sum = MathUtil.add(sum, sum1);
    				}
    			}
    			data.setIndirect(sum);
    		}
    		//添加
    		consumeDataService.insert(data);
    	}
	}
    
    
}
