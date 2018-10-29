package com.bandweaver.tunnel.service.mam.video;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.constant.PtzDirectionEnum;
import com.bandweaver.tunnel.common.biz.dto.H5StreamHttpResponseDto;
import com.bandweaver.tunnel.common.biz.dto.mam.video.VideoDto;
import com.bandweaver.tunnel.common.biz.itf.mam.OnvifService;
import com.bandweaver.tunnel.common.biz.pojo.mam.video.VideoServer;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.HttpUtil;
import de.onvif.soap.devices.PtzDevices;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.CharsetDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        VideoServer videoServer = videoDto.getVideoServer();

        String server = "http://" + videoServer.getIp() + ":" + videoServer.getPort();
        String url = "/api/v1/Ptz";
        Map<String, String> headers = new HashMap<>();
        Map<String, String> querys = new HashMap<>();
        querys.put("token", "onvif_" + videoDto.getId());
        querys.put("speed", "0.5");
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

        VideoDto videoDto = videoModuleCenter.getVideoDto(id);
        VideoServer videoServer = videoDto.getVideoServer();

        String server = "http://" + videoServer.getIp() + ":" + videoServer.getPort();
        String url = "/api/v1/GetPresets";
        Map<String, String> headers = new HashMap<>();
        Map<String, String> querys = new HashMap<>();
        querys.put("token", "onvif_" + videoDto.getId());
        querys.put("session", videoServer.getSession());

        try {
            HttpResponse response = HttpUtil.doGet(server, url, "GET", headers, querys);
            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                return result;
            } else {
                return result;
            }

        } catch (Exception e) {

            return result;
        }
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
        VideoServer videoServer = videoDto.getVideoServer();

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
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {

            return false;
        }
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

        VideoDto videoDto = videoModuleCenter.getVideoDto(id);
        VideoServer videoServer = videoDto.getVideoServer();

        String server = "http://" + videoServer.getIp() + ":" + videoServer.getPort();
        String url = "/api/v1/DelPreset";
        Map<String, String> headers = new HashMap<>();
        Map<String, String> querys = new HashMap<>();
        querys.put("token", "onvif_" + videoDto.getId());
        querys.put("presettoken", presetName);
        querys.put("session", videoServer.getSession());

        try {
            HttpResponse response = HttpUtil.doGet(server, url, "GET", headers, querys);
            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {

            return false;
        }
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
        VideoServer videoServer = videoDto.getVideoServer();

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
