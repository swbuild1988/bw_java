package com.bandweaver.tunnel.service.mam.video;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.constant.PtzDirectionEnum;
import com.bandweaver.tunnel.common.biz.dto.H5StreamHttpResponseDto;
import com.bandweaver.tunnel.common.biz.dto.HttpResponsePresetDto;
import com.bandweaver.tunnel.common.biz.dto.mam.video.VideoDto;
import com.bandweaver.tunnel.common.biz.dto.mam.video.VideoServerDto;
import com.bandweaver.tunnel.common.biz.itf.mam.OnvifService;
import com.bandweaver.tunnel.common.biz.pojo.mam.video.VideoServer;
import com.bandweaver.tunnel.common.platform.constant.StatusCodeEnum;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.CommonUtil;
import com.bandweaver.tunnel.common.platform.util.HttpUtil;

import de.onvif.soap.devices.PtzDevices;

@Service("H5StreamServiceImpl")
public class H5StreamServiceImpl implements OnvifService {

    @Autowired
    private VideoModuleCenter videoModuleCenter;

    /**
     * 获取ptz控制
     *
     * @param videoDto
     * @return
     */
    @Override
    public PtzDevices getPtzDevices(VideoDto videoDto) {
        return null;
    }

    /**
     * 获取权限
     *
     * @param videoDto
     * @return
     */
    @Override
    public String getProfileToken(VideoDto videoDto) {
        return null;
    }

    /**
     * 获取session
     *
     * @param videoServer
     * @return
     */
    @Override
    public String getSession(VideoServer videoServer) {

        String server = "http://" + videoServer.getIp() + ":" + videoServer.getPort();
        String url = "/api/v1/Login";
        Map<String, String> headers = new HashMap<>();
        Map<String, String> querys = new HashMap<>();
        querys.put("user", videoServer.getUsername());
        querys.put("password", videoServer.getPassword());

        String session = null;

        try {
            HttpResponse response = HttpUtil.doGet(server, url, "GET", headers, querys);
            HttpEntity entity = response.getEntity();

            // 出错了
            if (response.getStatusLine().getStatusCode() >= 300) {
                return null;
            }

            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                String result = EntityUtils.toString(entity, "utf-8");
                session = ((H5StreamHttpResponseDto) JSONObject.parseObject(result, H5StreamHttpResponseDto.class)).getStrSession();
                return session;
            }
        } catch (Exception e) {
            LogUtil.info("获取server session 出错：" + e.toString());
            return null;
        }

        return session;
    }

    /**
     * 是否支持ptz相机控制
     *
     * @param videoDto
     * @return
     */
    @Override
    public boolean isPtzOperationsSupported(VideoDto videoDto) {
        return true;
    }

    /**
     * 开始移动
     *
     * @param id
     * @param direction
     */
    @Override
    public void startMove(int id, PtzDirectionEnum direction) {

        LogUtil.info("start move:" + direction.getName());
        String action = "";
        switch (direction) {
            case UP:
                action = "up";
                break;
            case DOWN:
                action = "down";
                break;
            case LEFT:
                action = "left";
                break;
            case RIGHT:
                action = "right";
                break;
            case ZOOM_IN:
                action = "zoomin";
                break;
            case ZOOM_OUT:
                action = "zoomout";
                break;
            case STOP:
                action = "stop";
                break;
            default:
                return;
        }

        VideoDto videoDto = videoModuleCenter.getVideoDto(id);
        VideoServerDto videoServer = videoDto.getVideoServerDto();

        String server = "http://" + videoServer.getIp() + ":" + videoServer.getPort();
        String url = "/api/v1/Ptz";
        Map<String, String> headers = new HashMap<>();
        Map<String, String> querys = new HashMap<>();
        querys.put("token", "onvif_" + videoDto.getId());
        querys.put("speed", "0.3");
        querys.put("action", action);
        querys.put("session", videoServer.getSession());

        try {
            HttpResponse response = HttpUtil.doGet(server, url, "GET", headers, querys);

        } catch (Exception e) {
            LogUtil.info("start move 出错:" + direction.getName());
        }
    }

    /**
     * 停止移动
     *
     * @param id
     */
    @Override
    public void stopMove(int id) {
        startMove(id, PtzDirectionEnum.STOP);
    }

    /**
     * 获得相机所有的预置位
     *
     * @param id
     * @return
     */
    @Override
    public List<String> getPresets(int id) {
        List<String> result = new ArrayList<>();
        List<JSONObject> list = getPresetByKey(id);
        for (JSONObject jsonObject : list) {
        	result.add(jsonObject.getString("presetName"));
		}
        return result;
    }

	public List<JSONObject> getPresetByKey(int id) {
		List<JSONObject> result = new ArrayList<>();
		VideoDto videoDto = videoModuleCenter.getVideoDto(id);
		VideoServerDto videoServer = videoDto.getVideoServerDto();

        String server = "http://" + videoServer.getIp() + ":" + videoServer.getPort();
        String url = "/api/v1/GetPresets";
        Map<String, String> headers = new HashMap<>();
        Map<String, String> querys = new HashMap<>();
        querys.put("token", "onvif_" + videoDto.getId());
        querys.put("session", videoServer.getSession());

        try {
            HttpResponse response = HttpUtil.doGet(server, url, "GET", headers, querys);
            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
	        	String str = EntityUtils.toString(response.getEntity(), "utf-8");
	        	List<HttpResponsePresetDto> list = JSONObject.parseObject(str, H5StreamHttpResponseDto.class).getPreset();
	        	
	        	if(list == null || list.isEmpty()) {
	        		return Collections.emptyList();
	        	}
	        	for (HttpResponsePresetDto httpResponsePresetDto : list) {
	        		JSONObject json = new JSONObject();
	        		json.put("presetName", httpResponsePresetDto.getStrName());
	        		json.put("presetToken", httpResponsePresetDto.getStrToken());
	        		result.add(json);
				}
            }
        } catch (Exception e) {
        	LogUtil.error("获取预置位失败：" + e.toString() );
        }
        return result;
	}

    /**
     * 添加预置位
     *
     * @param id
     * @param presetName
     * @return
     */
    @Override
    public boolean addPreset(int id, String presetName) {

        VideoDto videoDto = videoModuleCenter.getVideoDto(id);
        VideoServerDto videoServer = videoDto.getVideoServerDto();

        String server = "http://" + videoServer.getIp() + ":" + videoServer.getPort();
        String url = "/api/v1/SetPreset";
        Map<String, String> headers = new HashMap<>();
        Map<String, String> querys = new HashMap<>();
        querys.put("token", "onvif_" + videoDto.getId());
        querys.put("presetname", presetName);
        querys.put("presettoken", presetName);
        querys.put("session", videoServer.getSession());

        try {
            HttpResponse response = HttpUtil.doGet(server, url, "GET", headers, querys);
            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
            	String str = EntityUtils.toString(response.getEntity(), "utf-8");
                return JSONObject.parseObject(str, H5StreamHttpResponseDto.class).isbStatus();
            } 

        } catch (Exception e) {
        	LogUtil.error("添加预置位失败：" + e.toString() );
        }
        return false;
    }

   

	/**
     * 删除预置位
     *
     * @param id
     * @param presetName
     * @return
     */
    @Override
    public boolean delPreset(int id, String presetName) {

    	String presetToken = getProsetTokenByPresetName(id,presetName);
    	if(presetToken == null)
    		throw new RuntimeException("预置位不存在");
    	
        VideoDto videoDto = videoModuleCenter.getVideoDto(id);
        VideoServerDto videoServer = videoDto.getVideoServerDto();

        String server = "http://" + videoServer.getIp() + ":" + videoServer.getPort();
        String url = "/api/v1/DelPreset";
        Map<String, String> headers = new HashMap<>();
        Map<String, String> querys = new HashMap<>();
        querys.put("token", "onvif_" + videoDto.getId());
        querys.put("presettoken", presetToken);
        querys.put("session", videoServer.getSession());

        try {
            HttpResponse response = HttpUtil.doGet(server, url, "GET", headers, querys);
            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
            	String str = EntityUtils.toString(response.getEntity(), "utf-8");
                return JSONObject.parseObject(str, H5StreamHttpResponseDto.class).isbStatus();
            } 

        } catch (Exception e) {
        	LogUtil.error("删除预置位失败：" + e.toString() );
        }
        return false;
    }

    /**通过预置位名称获取token 
     * @param id
     * @param presetName
     * @return   
     * @author shaosen
     * @Date 2018年10月31日
     */
    private String getProsetTokenByPresetName(int id, String presetName) {
    	List<JSONObject> list = getPresetByKey(id);
    	for (JSONObject jsonObject : list) {
    		String preset_name = (String) jsonObject.get("presetName");
    		if(preset_name.equals(presetName))
    			return (String) jsonObject.get("presetToken");
		}
		return null;
	}
    
    
    /**
     * 检查预置位是否存在 
     * @param id
     * @param presetName
     * @return   
     * @author shaosen
     * @Date 2018年10月31日
     */
    private boolean checkPresetExsit(int id, String presetName) {
		String token = getProsetTokenByPresetName(id, presetName);
		return token == null ? true : false ;
	}

	/**
     * 更新预置位
     *
     * @param id
     * @param presetName
     * @return
     */
    @Override
    public boolean updatePreset(int id, String presetName) {
    	return addPreset(id, presetName);
    }

    /**
     * 移动到预置位
     *
     * @param id
     * @param presetName
     */
    @Override
    public void gotoPreset(int id, String presetName) {

        VideoDto videoDto = videoModuleCenter.getVideoDto(id);
        VideoServerDto videoServer = videoDto.getVideoServerDto();

        String server = "http://" + videoServer.getIp() + ":" + videoServer.getPort();
        String url = "/api/v1/Ptz";
        Map<String, String> headers = new HashMap<>();
        Map<String, String> querys = new HashMap<>();
        querys.put("token", "onvif_" + videoDto.getId());
        querys.put("speed", "1");
        querys.put("action", "preset");
        querys.put("session", videoServer.getSession());
        querys.put("preset", presetName);

        try {
            HttpResponse response = HttpUtil.doGet(server, url, "GET", headers, querys);
            HttpEntity entity = response.getEntity();

        } catch (Exception e) {
            LogUtil.info("goto preset 出错：" + presetName);
        }
    }
}
