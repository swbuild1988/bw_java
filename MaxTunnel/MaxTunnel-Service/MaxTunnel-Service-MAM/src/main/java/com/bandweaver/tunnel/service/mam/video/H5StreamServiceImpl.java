package com.bandweaver.tunnel.service.mam.video;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.constant.PtzDirectionEnum;
import com.bandweaver.tunnel.common.biz.dto.H5StreamHttpResponseDto;
import com.bandweaver.tunnel.common.biz.dto.HttpResponsePresetDto;
import com.bandweaver.tunnel.common.biz.dto.mam.h5.H5Src;
import com.bandweaver.tunnel.common.biz.dto.mam.video.VideoDto;
import com.bandweaver.tunnel.common.biz.dto.mam.video.VideoServerDto;
import com.bandweaver.tunnel.common.biz.itf.mam.OnvifService;
import com.bandweaver.tunnel.common.biz.pojo.mam.video.VideoServer;
import com.bandweaver.tunnel.common.platform.constant.StatusCodeEnum;
import com.bandweaver.tunnel.common.platform.exception.BandWeaverException;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.CommonUtil;
import com.bandweaver.tunnel.common.platform.util.DataTypeUtil;
import com.bandweaver.tunnel.common.platform.util.HttpUtil;
import com.bandweaver.tunnel.common.platform.util.StringTools;

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
            LogUtil.error("获取server session 出错：" + e.toString());
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
        querys.put("token", videoDto.getId().toString());
        querys.put("speed", "0.3");
        querys.put("action", action);
        querys.put("session", videoServer.getSession());

        try {
            HttpResponse response = HttpUtil.doGet(server, url, "GET", headers, querys);

        } catch (Exception e) {
            LogUtil.error("start move 出错:" + direction.getName());
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
        querys.put("token", videoDto.getId().toString());
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
        querys.put("token", videoDto.getId().toString());
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
        querys.put("token", videoDto.getId().toString());
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
        querys.put("token", videoDto.getId().toString());
        querys.put("speed", "1");
        querys.put("action", "preset");
        querys.put("session", videoServer.getSession());
        querys.put("preset", presetName);

        try {
            HttpResponse response = HttpUtil.doGet(server, url, "GET", headers, querys);
            HttpEntity entity = response.getEntity();

        } catch (Exception e) {
            LogUtil.error("goto preset 出错：" + presetName);
        }
    }

	@Override
	public boolean addSrc(String user, String password, String ip, String id, String url) throws Exception {
		//boolean addSrcRTSP = addSrcRTSP(user,password,ip,id,url);
		boolean addSrcONVIF = addSrcONVIF(user,password,ip,id,url);
		//return addSrcRTSP && addSrcONVIF ? true : false;
		return addSrcONVIF;
	}
	
	@Override
	public void addSnap() {
		List<VideoDto> list = videoModuleCenter.getVideoDtos();
		LogUtil.info("视频总数: " + list.size());
		for(VideoDto dto : list) {
			try {
				addSnap(dto);
			} catch(Exception e) {
				LogUtil.error("拍照出错： " + e.getMessage());
			}
			
		}
	}
	
	@Override
	public boolean delSrc(String id) throws Exception {
		VideoDto videoDto = videoModuleCenter.getVideoDto(DataTypeUtil.toInteger(id));
	    VideoServerDto videoServer = videoDto.getVideoServerDto();
	    
	    String server = "http://" + videoServer.getIp() + ":" + videoServer.getPort();
        String _url = "/api/v1/DelSrc";
        Map<String, String> headers = new HashMap<>();
        Map<String, String> querys = new HashMap<>();
        querys.put("token",id);
        querys.put("session", videoServer.getSession());
//        Map<String, String> querys2 = new HashMap<>();
//        querys2.put("token", id);
//        querys2.put("session", videoServer.getSession());
        
        //boolean delSrcRTSP = httpGet(server, _url, headers, querys);
        boolean delSrcONVIF = httpGet(server, _url, headers, querys);
        //return delSrcRTSP&&delSrcONVIF ? true : false ;
        return delSrcONVIF;
	}

	public boolean httpGet(String server, String _url, Map<String, String> headers, Map<String, String> querys) throws Exception {
	
		HttpResponse response = HttpUtil.doGet(server, _url, "GET", headers, querys);
		if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
        	String str = EntityUtils.toString(response.getEntity(), "utf-8");
            return JSONObject.parseObject(str, H5StreamHttpResponseDto.class).isbStatus();
        }else {
        	LogUtil.warn("请求出错：" + response.getStatusLine().getStatusCode() );
        }
		return false;
	}

	private boolean addSrcONVIF(String user, String password, String ip, String id, String url) throws Exception {
		VideoDto videoDto = videoModuleCenter.getVideoDto(DataTypeUtil.toInteger(id));
	    VideoServerDto videoServer = videoDto.getVideoServerDto();

		String server = "http://" + videoServer.getIp() + ":" + videoServer.getPort();
        String _url = "/api/v1/AddSrcONVIF";
        Map<String, String> headers = new HashMap<>();
        Map<String, String> querys = new HashMap<>();
        querys.put("name", id);
        querys.put("token", id);
        querys.put("user", user);
        querys.put("password", password);
        querys.put("ip", ip);
        querys.put("port", String.valueOf(80));
        querys.put("session", videoServer.getSession());
        return httpGet(server, _url, headers, querys);
		
	}
	
	
	/**
	 * 添加视频
	 * @param user
	 * @param password
	 * @param ip
	 * @param id
	 * @param url
	 * @return
	 * @throws Exception
	 * @Date 2019年3月21日
	 */
	private boolean addSrcRTSP(String user, String password, String ip, String id, String url) throws Exception {
		VideoDto videoDto = videoModuleCenter.getVideoDto(DataTypeUtil.toInteger(id));
	    VideoServerDto videoServer = videoDto.getVideoServerDto();

		String server = "http://" + videoServer.getIp() + ":" + videoServer.getPort();
        String _url = "/api/v1/AddSrcRTSP";
        Map<String, String> headers = new HashMap<>();
        Map<String, String> querys = new HashMap<>();
        querys.put("name", id);
        querys.put("token", id);
        querys.put("user", user);
        querys.put("password", password);
        querys.put("url", url);
        querys.put("session", videoServer.getSession());
        return httpGet(server, _url, headers, querys);
	}
	
	/**
	 * 视频录像拍快照
	 * @return
	 * @author ya.liu
	 * @Date 2019年3月23日
	 */
	private boolean addSnap(VideoDto dto) throws Exception {
	    VideoServerDto videoServer = dto.getVideoServerDto();
	    //String name = dto.getName() == null ? "" : dto.getName() + "-";
	    //String filename = name + dto.getIp();
	    
	    String server = "http://" + videoServer.getIp() + ":" + videoServer.getPort();
        String url = "/api/v1/Snapshot";
        Map<String, String> headers = new HashMap<>();
        Map<String, String> querys = new HashMap<>();
        querys.put("token", dto.getId().toString());
        //querys.put("filename", filename);
        querys.put("session", videoServer.getSession());
        return httpGet(server, url, headers, querys);
		
	}
	
	public String httpGetList(String server, String _url, Map<String, String> headers, Map<String, String> querys) throws Exception {
		
		HttpResponse response = HttpUtil.doGet(server, _url, "GET", headers, querys);
		if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
			return EntityUtils.toString(response.getEntity(), "utf-8");
             
        }else {
        	LogUtil.warn("请求出错：" + response.getStatusLine().getStatusCode() );
        }
		return null;
	}
	
	@Override
	public String getSnapJSON(VideoDto dto, Date start, Date end) throws Exception{
		VideoServerDto videoServer = dto.getVideoServerDto();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
        String s = sdf.format(start);
        String e = sdf.format(end);
	    String server = "http://" + videoServer.getIp() + ":" + videoServer.getPort();
        String url = "/api/v1/Search";
        Map<String, String> headers = new HashMap<>();
        Map<String, String> querys = new HashMap<>();
        querys.put("token", dto.getId().toString());
        querys.put("session", videoServer.getSession());
        querys.put("type", "snapshot");
        querys.put("start", s);
        querys.put("end", e);
        return httpGetList(server, url, headers, querys);
	}

	@Override
	public void delSrcList() {
		//Get videoServer
		List<VideoDto> videoDtos = videoModuleCenter.getVideoDtos();
		VideoServerDto videoServer = new VideoServerDto() ;
		for (VideoDto videoDto : videoDtos) {
			VideoServerDto videoServerDto = videoDto.getVideoServerDto();
			if(!StringTools.isNullOrEmpty(videoServerDto)) {
				videoServer = videoServerDto;
				break;
			}
		}
		
		String server = "http://" + videoServer.getIp() + ":" + videoServer.getPort();
		String delUrl = "/api/v1/DelSrc";
        Map<String, String> headers = new HashMap<>();
        Map<String, String> querys = new HashMap<>();
        querys.put("session", videoServer.getSession());
		List<String> tokenList = new ArrayList<>();
    	try {
    		List<H5Src> srcList = getSrc();
    		if(srcList == null) return;
    		srcList.forEach(src -> tokenList.add(src.getStrToken()));
			for (String token : tokenList) {
				querys.put("token",token);
				boolean delResult = httpGet(server, delUrl, headers, querys);
				LogUtil.info("视频源token: " + token + " 删除结果：" + delResult );
			}
		} catch (Exception e) {
			throw new BandWeaverException("调用API删除视频源失败");
		}
	}

	@Override
	public List<H5Src> getSrc() throws Exception {
		//Get videoServer
		List<VideoDto> videoDtos = videoModuleCenter.getVideoDtos();
		VideoServerDto videoServer = new VideoServerDto() ;
		for (VideoDto videoDto : videoDtos) {
			VideoServerDto videoServerDto = videoDto.getVideoServerDto();
			if(!StringTools.isNullOrEmpty(videoServerDto)) {
				videoServer = videoServerDto;
				break;
			}
		}
		
		String server = "http://" + videoServer.getIp() + ":" + videoServer.getPort();
        String _url = "/api/v1/GetSrc";
        Map<String, String> headers = new HashMap<>();
        Map<String, String> querys = new HashMap<>();
        querys.put("session", videoServer.getSession());
        
    	try {
			HttpResponse response = HttpUtil.doGet(server, _url, "GET", headers, querys);
			if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
				String resp = EntityUtils.toString(response.getEntity(), "utf-8");
				JSONObject respJson = (JSONObject) JSONObject.parse(resp);
				List<H5Src> srcList = JSONObject.parseArray(respJson.getString("src"), H5Src.class);
				return srcList;
			}
		} catch (Exception e) {
			throw new BandWeaverException("调用API获取视频源列表失败");
		}
		return null;
	}

	
}
