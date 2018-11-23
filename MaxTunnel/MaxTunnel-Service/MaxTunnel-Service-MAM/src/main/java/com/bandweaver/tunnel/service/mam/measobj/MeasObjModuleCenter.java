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
import com.bandweaver.tunnel.common.biz.pojo.Section;
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
import com.bandweaver.tunnel.common.biz.itf.SectionService;
import com.bandweaver.tunnel.common.biz.itf.TunnelService;
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
	@Autowired
	private SectionService sectionService;

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

	public void insertMeasObj(MeasObj measObj) {
		
		ObjectType objectType = ObjectType.getEnum(measObj.getObjtypeId());
		measObj.setDatatypeId(objectType.getDataType());
		
		if (measObjHashMap.containsKey(measObj.getId()))
			return;

		// 根据storeid & areaid查找
		Section section = sectionService.getSectionByStoreAndArea(measObj.getStoreId(), measObj.getAreaId());
		measObj.setSectionId(section.getId());

		// 塞进数据库
		measObjMapper.insertSelective(measObj);
		insertObj2OwnDB(measObj);

		// 数据库成功后塞进缓存
		measObjHashMap.put(measObj.getId(), measObj);
		insertObj2OwnHashMap(measObj);
	}

	public void addMeasObjHashMap(MeasObj measObj) {
		measObjHashMap.put(measObj.getId(), measObj);
	}

	public void updateMeasObj(MeasObj measObj) {
		if (!measObjHashMap.containsKey(measObj.getId())) return;
		
		//先更新数据库
		Section section = sectionService.getSectionByStoreAndArea(measObj.getStoreId(), measObj.getAreaId());
		if(section == null) return;
		measObj.setSectionId(section.getId());
		measObjMapper.updateByPrimaryKeySelective(measObj);
		
		//再更新缓存
		measObjHashMap.put(measObj.getId(), measObj);
	}

	/**
	 * 更新ai缓存
	 * 
	 * @param measValueAI
	 * @author shaosen
	 * @Date 2018年10月18日
	 */
	public void updateMeasObjAIValue(MeasValueAI measValueAI) {
		if (!measObjAIHashMap.containsKey(measValueAI.getObjectId()))
			return;

		// 更新缓存
		MeasObjAI ai = measObjAIHashMap.get(measValueAI.getObjectId());

		if (ai.getRefreshTime() != null && ai.getRefreshTime().getTime() >= measValueAI.getTime().getTime())
			return;
		ai.setCV(measValueAI.getCV());
		ai.setRefreshTime(measValueAI.getTime());
	}

	/**
	 * 更新di缓存
	 * 
	 * @param measValueDI
	 * @author shaosen
	 * @Date 2018年10月18日
	 */
	public void updateMeasObjDIValue(MeasValueDI measValueDI) {
		if (!measObjDIHashMap.containsKey(measValueDI.getObjectId()))
			return;

		// 更新缓存
		MeasObjDI di = measObjDIHashMap.get(measValueDI.getObjectId());
		if (di.getRefreshTime() != null && di.getRefreshTime().getTime() >= measValueDI.getTime().getTime())
			return;
		di.setCV(measValueDI.getCV());
		di.setRefreshTime(measValueDI.getTime());
	}

	/**
	 * 更新si缓存
	 * 
	 * @param measValueSI
	 * @author shaosen
	 * @Date 2018年10月18日
	 */
	public void updateMeasObjSIValue(MeasValueSI measValueSI) {
		if (!measObjSIHashMap.containsKey(measValueSI.getObjectId()))
			return;

		// 更新缓存
		MeasObjSI si = measObjSIHashMap.get(measValueSI.getObjectId());
		if (si.getRefreshTime() != null && si.getRefreshTime().getTime() >= measValueSI.getTime().getTime())
			return;
		si.setCV(measValueSI.getCV());
		si.setRefreshTime(measValueSI.getTime());
	}

	/**
	 * 更新分布式缓存
	 * 
	 * @param measValueDistribute
	 * @author shaosen
	 * @Date 2018年10月22日
	 */
	public void updateMeasObjDistributeValue(MeasValueDistribute measValueDistribute) {
		if (!measObjDistributeHashMap.containsKey(measValueDistribute.getObjectId()))
			return;
		MeasObjDistribute tmp = measObjDistributeHashMap.get(measValueDistribute.getObjectId());
		if (tmp.getRefreshTime() != null && tmp.getRefreshTime().getTime() >= measValueDistribute.getTime().getTime())
			return;

		tmp.setDcv(measValueDistribute.getDcv());
		tmp.setRefreshTime(measValueDistribute.getTime());
		tmp.setSpacePrecision(measValueDistribute.getSpacePrecision());
	}

	/**
	 * 更新so缓存
	 * 
	 * @param measValueSO
	 * @author shaosen
	 * @Date 2018年10月22日
	 */
	public void updateMeasObjSOValue(MeasValueSO measValueSO) {
		if (!measObjSOHashMap.containsKey(measValueSO.getObjectId()))
			return;

		MeasObjSO tmp = measObjSOHashMap.get(measValueSO.getObjectId());

		if (!tmp.isActived()) {
			return;
		}
		if (tmp.getRefreshTime() != null && tmp.getRefreshTime().getTime() >= measValueSO.getTime().getTime())
			return;

		// 如果是定位类型，转换之
		if (tmp.getObjtypeId() == ObjectType.POSITION.getValue()) {
			locatorService.update(tmp, measValueSO);
		} else {
			tmp.setRefreshTime(measValueSO.getTime());
			tmp.setCV(measValueSO.getCV());
		}
	}

	public void insertMeasObjSOValue(MeasValueSO measValueSO) {
		if (!measObjSOHashMap.containsKey(measValueSO.getObjectId()))
			return;

		MeasObjSO tmp = measObjSOHashMap.get(measValueSO.getObjectId());
		if (tmp.getRefreshTime() != null && tmp.getRefreshTime().getTime() >= measValueSO.getTime().getTime())
			return;

		tmp.setRefreshTime(measValueSO.getTime());
		tmp.setCV(measValueSO.getCV());
		measObjSOMapper.update(tmp);
		measValueSOMapper.addMeasValueSO(measValueSO);
		// LogUtil.info("after insert MeasObjSO : " + tmp);

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
		// LogUtil.info("measObjSOs:" + measObjSOs);
		for (MeasObjSO tmp : measObjSOs) {
			measObjSOHashMap.put(tmp.getId(), tmp);
		}
		// LogUtil.info("measObjSOHashMap:" + measObjSOHashMap);

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

	private void insertObj2OwnHashMap(MeasObj measObj) {
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
		long beginTime = System.currentTimeMillis();
		initData();
		long endTime = System.currentTimeMillis();
		LogUtil.info(	"*********************************\n"
						+ "描述：加载监测对象数据到缓存\n"
						+ "耗时：" + (endTime - beginTime) + "ms\n"
						+ "*********************************" 	);
	}

	@Override
	public void stop() {

	}

	public void deleteObj(Integer id) {

		// delete Cache
		MeasObj measObj = measObjHashMap.get(id);
		if(measObj == null)
			return;
		measObjHashMap.remove(id);
		deleteObjFromOwnHashMap(measObj);

		// delete DB
		measObjMapper.deleteByPrimaryKey(id);
		deleteObjFromOwnDB(measObj);

	}

	private void deleteObjFromOwnDB(MeasObj measObj) {
		DataType dataType = DataType.getEnum(measObj.getDatatypeId().intValue());
		switch (dataType) {
		case AI:
			measObjAIMapper.deleteByPrimaryKey(measObj.getId());
			break;

		case DI:
			measObjDIMapper.deleteByPrimaryKey(measObj.getId());
			break;

		case SI:
			measObjSIMapper.deleteByPrimaryKey(measObj.getId());
			break;

		case SO:
			measObjSOMapper.deleteByPrimaryKey(measObj.getId());
			break;

		case DISTRIBUTE:
			measObjDistributeMapper.deleteByPrimaryKey(measObj.getId());
			break;

		case VIDEO:
			// TODO
			break;

		default:
			break;
		}

	}

	private void deleteObjFromOwnHashMap(MeasObj measObj) {

		DataType dataType = DataType.getEnum(measObj.getDatatypeId().intValue());
		switch (dataType) {
		case AI:
			measObjAIHashMap.remove(measObj.getId());
			break;

		case DI:
			measObjDIHashMap.remove(measObj.getId());
			break;

		case SI:
			measObjSIHashMap.remove(measObj.getId());
			break;

		case SO:
			measObjSOHashMap.remove(measObj.getId());
			break;

		case DISTRIBUTE:
			measObjDistributeHashMap.remove(measObj.getId());
			break;

		case VIDEO:
			// TODO
			break;

		default:
			break;
		}

	}

}
