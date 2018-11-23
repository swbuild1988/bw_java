package com.bandweaver.tunnel.service.mam.maxview;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.activiti.engine.impl.cmd.GetNextIdBlockCmd;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.constant.ErrorCodeEnum;
import com.bandweaver.tunnel.common.biz.constant.mam.DataType;
import com.bandweaver.tunnel.common.biz.dto.TunnelDto;
import com.bandweaver.tunnel.common.biz.itf.TunnelService;
import com.bandweaver.tunnel.common.biz.itf.mam.mapping.MeasObjMapService;
import com.bandweaver.tunnel.common.biz.itf.mam.maxview.MaxviewConfigService;
import com.bandweaver.tunnel.common.biz.pojo.mam.MaxviewConfig;
import com.bandweaver.tunnel.common.biz.pojo.mam.mapping.MeasObjMap;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObj;
import com.bandweaver.tunnel.common.biz.vo.mam.MaxviewConfigVo;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.ContextUtil;
import com.bandweaver.tunnel.common.platform.util.DateUtil;
import com.bandweaver.tunnel.common.platform.util.HttpUtil;
import com.bandweaver.tunnel.common.platform.util.Sha256;
import com.bandweaver.tunnel.dao.mam.MaxviewConfigMapper;
import com.bandweaver.tunnel.service.mam.measobj.MeasObjModuleCenter;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
@Service
public class MaxviewConfigServiceImpl implements MaxviewConfigService {

	@Autowired
	private MaxviewConfigMapper maxviewConfigMapper;
	@Autowired
	private MeasObjModuleCenter measObjModuleCenter;
	@Autowired
	private MaxviewConfigModuleCenter maxviewConfigModuleCenter;
	@Autowired
	private TunnelService tunnelService;
	@Autowired
	private MeasObjMapService measObjMapService;
	
	private static final String POST = "POST";
	private static final String GET = "GET";
	
	@Override
	public void login(Integer configId) {
		MaxviewConfig config = maxviewConfigMapper.selectByPrimaryKey(configId);
		if(config == null) {
			LogUtil.error("登录失败,终端未配置");
			return;
		}
		
		String host = "http://" + config.getIp()+ ":"+ config.getPort() +"/Handler/Security/SecUserHandler.ashx";
		LogUtil.info("host:" + host);
		String path = "";
		Map<String, String> headers = new HashMap<String, String>();
		Map<String, String> querys = new HashMap<String, String>();
		Map<String, String> bodys = new HashMap<String, String>();
		bodys.put("type", "login");
		bodys.put("username", config.getUsername());
		bodys.put("password", config.getPassword());  //14e24dc8cf60446f56eb163a7b85bce8a53d3f766badf15e86a1af857d9a9012
		bodys.put("checked", "checked");
		bodys.put("terminalType", "4");//后台登录
	
		try {
			HttpResponse response = HttpUtil.doPost(host, path, POST, headers, querys, bodys);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			    String resultJson = EntityUtils.toString(response.getEntity(), "utf-8");
			    LogUtil.info("resultJson:" + resultJson);
			    
			    JSONObject retObj = JSON.parseObject(resultJson);
			    Boolean result = retObj.getBoolean("Result");
			    if(result) {
			    	String UserSessionStr = retObj.getString("UserSession");
				    JSONObject UserSession = JSON.parseObject(UserSessionStr);
				    String sessionID = UserSession.getString("ID");
				    LogUtil.info("Get sessionID:" + sessionID );
				    maxviewConfigModuleCenter.setSessionID(configId, sessionID);
				    
			    }else {
			    	Integer errorCode = retObj.getInteger("ErrorCode");
			    	LogUtil.info("登录失败：errorCode = " + errorCode + ",errorMsg = " + ErrorCodeEnum.getEnum(errorCode).getName());
			    }
			    
			}else {
				LogUtil.info("请求出错：" + response.getStatusLine().getStatusCode() );
			}
		} catch (Exception e) {
			LogUtil.info("HTTP请求失败，错误信息：" + e.getMessage());
		}
	}




	@Override
	public void heartBeat(Integer configId) {
		MaxviewConfig config = maxviewConfigMapper.selectByPrimaryKey(configId);
		String sessionID = maxviewConfigModuleCenter.getSessionID(configId);
		String host = "http://" + config.getIp() + ":"+ config.getPort() +"/Handler/CommonService/CommonServiceHandler.ashx";
		String path = "";
		Map<String, String> headers = new HashMap<String, String>();
		Map<String, String> querys = new HashMap<String, String>();
		Map<String, String> bodys = new HashMap<String, String>();
		bodys.put("type", "heartBeat");
		bodys.put("sessionID", sessionID);
	
		try {
			HttpResponse response = HttpUtil.doPost(host, path, POST, headers, querys, bodys);
		    if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
		    	String resultJson = EntityUtils.toString(response.getEntity(), "utf-8");
			    LogUtil.info("发送心跳成功，回执信息:" + resultJson);
		    }else {
				LogUtil.info("请求出错：" + response.getStatusLine().getStatusCode() );
			}
		} catch (Exception e) {
			LogUtil.info("HTTP请求失败，错误信息：" + e.getMessage());
		}
	}
	
	
	@Override
	public boolean doAction(Integer objectId,Integer inputValue) {
		
		MeasObj measObj = measObjModuleCenter.getMeasObj(objectId);
		if(measObj == null) {
			throw new RuntimeException("监测对象[" + objectId + "]不存在");
		}
		
		TunnelDto tunnel = tunnelService.getDtoById(measObj.getTunnelId());
		if(tunnel == null) {
			throw new RuntimeException("管廊不存在");
		}
		
		MaxviewConfig config = maxviewConfigMapper.selectByPrimaryKey(tunnel.getMaxviewConfigId());
		if(config == null) {
			throw new RuntimeException("登录失败：终端未配置");
		}
		
		Integer masObjId, measValue;
		MeasObjMap measObjMap = measObjMapService.getMaxViewMeasObj(objectId, inputValue);
		if(measObjMap != null) {
			masObjId = measObjMap.getObjectId2();
			measValue = measObjMap.getOutputValue();
		}else {
			masObjId = objectId;
			measValue = inputValue;
		}
		
		
		String host = "http://" + config.getIp() + ":"+ config.getPort() +"/Handler/CommonService/CommonServiceHandler.ashx";
		String path = "";
		Map<String, String> headers = new HashMap<String, String>();
		Map<String, String> querys = new HashMap<String, String>();
		Map<String, String> bodys = new HashMap<String, String>();
		bodys.put("type", "updateSwitchMeasValue");
		bodys.put("measObjId", String.valueOf(masObjId));
		bodys.put("measValue", String.valueOf(measValue));
		bodys.put("sessionID", maxviewConfigModuleCenter.getSessionID(config.getId()));
		
		try {
			HttpResponse response = HttpUtil.doPost(host, path, POST, headers, querys, bodys);
		    if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			    String resultJson = EntityUtils.toString(response.getEntity(), "utf-8");
			    LogUtil.info("resultJson:" + resultJson);
			    //{"Description":null,"SucceedNum":0,"FailedNum":0,"Result":true,"ErrorCode":0}
			    JSONObject retObj = JSON.parseObject(resultJson);
			    Boolean result = retObj.getBoolean("Result");
			    return result;
			}else {
				LogUtil.info("请求出错：" + response.getStatusLine().getStatusCode() );
			}
			
		} catch (Exception e) {
			LogUtil.info("HTTP请求失败，错误信息：" + e.getMessage());
		}
		return false;
	
	}




	@Override
	public void add(MaxviewConfig config) {
		config.setPassword(Sha256.getSHA256StrJava(config.getPassword()));
		config.setCrtTime(DateUtil.getCurrentDate());
		maxviewConfigMapper.insertSelective(config);
	}




	@Override
	public void update(MaxviewConfig config) {
		maxviewConfigMapper.updateByPrimaryKeySelective(config);
		
	}




	@Override
	public void delete(Integer id) {
		maxviewConfigMapper.deleteByPrimaryKey(id);
	}




	@Override
	public MaxviewConfig getById(Integer id) {
		return maxviewConfigMapper.selectByPrimaryKey(id);
	}




	@Override
	public void deleteBatch(List<Integer> idList) {
		maxviewConfigMapper.deleteBatch(idList);
	}




	@Override
	public PageInfo<MaxviewConfig> dataGrid(MaxviewConfigVo vo) {
		PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
		List<MaxviewConfig> list = getByCondition(vo);
		return new PageInfo<>(list);
	}




	private List<MaxviewConfig> getByCondition(MaxviewConfigVo vo) {
		List<MaxviewConfig> list = maxviewConfigMapper.getByCondition(vo);
		return list == null ? Collections.emptyList() : list ;
	}




	@Override
	public List<MaxviewConfig> getAll() {
		return getByCondition(new MaxviewConfigVo());
	}
	
}
