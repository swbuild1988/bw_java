package com.bandweaver.tunnel.service.mam.video;

import com.bandweaver.tunnel.common.biz.constant.PtzDirectionEnum;
import com.bandweaver.tunnel.common.biz.dto.mam.video.VideoDto;
import com.bandweaver.tunnel.common.biz.itf.mam.OnvifService;
import com.bandweaver.tunnel.common.biz.pojo.mam.video.VideoServer;
import de.onvif.soap.OnvifDevice;
import de.onvif.soap.devices.PtzDevices;
import org.onvif.ver10.schema.PTZPreset;
import org.onvif.ver10.schema.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.soap.SOAPException;

@Service("OnvifServiceImpl")
public class OnvifServiceImpl implements OnvifService {

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
        OnvifDevice onvifDevice = getDevice(videoDto);
        if (onvifDevice == null) return null;
        return getPtzDevices(onvifDevice);
    }

    /**
     * 获取权限
     *
     * @param videoDto
     * @return
     */
    @Override
    public String getProfileToken(VideoDto videoDto) {
        OnvifDevice onvifDevice = getDevice(videoDto);
        if (onvifDevice == null) return null;
        return getProfileToken(onvifDevice);
    }

    /**
     * 获取session
     *
     * @param videoServer
     * @return
     */
    @Override
    public String getSession(VideoServer videoServer) {
        return null;
    }

    /**
     * 是否支持ptz相机控制
     *
     * @param videoDto
     * @return
     */
    @Override
    public boolean isPtzOperationsSupported(VideoDto videoDto) {
        OnvifDevice device = getDevice(videoDto);
        PtzDevices ptzDevices = getPtzDevices(device);
        if (ptzDevices == null) return false;

        String profileToken = getProfileToken(device);
        return ptzDevices.isPtzOperationsSupported(profileToken);
    }

    /**
     * 开始移动
     */
    @Override
    public void startMove(int id, PtzDirectionEnum direction) {
        PtzDevices ptzDevices = videoModuleCenter.getPtzDevices(id);
        String profileToken = videoModuleCenter.getProfileToken(id);

        if (ptzDevices == null || profileToken == null) return;

        // 如果停止，则停，然后退出
        if (direction == PtzDirectionEnum.STOP) {
            ptzDevices.stopMove(profileToken);
            return;
        }

        float speed = 0.2f;
        float zoom = (direction.getValue() / 9 - 1) * speed;
        float x = ((direction.getValue() % 9) / 3 - 1) * speed;
        float y = (direction.getValue() % 3 - 1) * speed;

        if (ptzDevices.isContinuosMoveSupported(profileToken)) {
            ptzDevices.continuousMove(profileToken, x, y, zoom);
        }
    }

    /**
     * 停止移动
     */
    @Override
    public void stopMove(int id) {
        PtzDevices ptzDevices = videoModuleCenter.getPtzDevices(id);
        String profileToken = videoModuleCenter.getProfileToken(id);

        if (ptzDevices == null || profileToken == null) return;

        ptzDevices.stopMove(profileToken);
    }

    /**
     * 获得相机所有的预置位
     *
     * @param id
     * @return
     */
    @Override
    public List<String> getPresets(int id) {
        PtzDevices ptzDevices = videoModuleCenter.getPtzDevices(id);
        String profileToken = videoModuleCenter.getProfileToken(id);

        if (ptzDevices == null || profileToken == null) return new ArrayList<>();

        List<PTZPreset> ptzPresets = ptzDevices.getPresets(profileToken);
        List<String> result = new ArrayList<>();
        for (PTZPreset ptzPreset : ptzPresets) {
            result.add(ptzPreset.getName());
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
        PtzDevices ptzDevices = videoModuleCenter.getPtzDevices(id);
        String profileToken = videoModuleCenter.getProfileToken(id);

        if (ptzDevices == null || profileToken == null) return false;

        List<PTZPreset> ptzPresets = ptzDevices.getPresets(profileToken);
        for (PTZPreset ptzPreset : ptzPresets) {
            if (ptzPreset.getName().equals(presetName)) return false;
        }

        ptzDevices.setPreset(presetName, profileToken);
        return true;
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
        PtzDevices ptzDevices = videoModuleCenter.getPtzDevices(id);
        String profileToken = videoModuleCenter.getProfileToken(id);

        if (ptzDevices == null || profileToken == null) return false;

        List<PTZPreset> ptzPresets = ptzDevices.getPresets(profileToken);
        for (PTZPreset ptzPreset : ptzPresets) {
            if (ptzPreset.getName().equals(presetName)) {
                ptzDevices.removePreset(ptzPreset.getToken(), profileToken);
                return true;
            }
        }

        return false;
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
        PtzDevices ptzDevices = videoModuleCenter.getPtzDevices(id);
        String profileToken = videoModuleCenter.getProfileToken(id);

        if (ptzDevices == null || profileToken == null) return false;

        List<PTZPreset> ptzPresets = ptzDevices.getPresets(profileToken);
        for (PTZPreset ptzPreset : ptzPresets) {
            if (ptzPreset.getName().equals(presetName)) {
                ptzDevices.setPreset(ptzPreset.getName(), ptzPreset.getToken(), profileToken);
                return true;
            }
        }
        return false;
    }

    /**
     * 移动到预置位
     *
     * @param id
     * @param presetName
     */
    @Override
    public void gotoPreset(int id, String presetName) {PtzDevices ptzDevices = videoModuleCenter.getPtzDevices(id);
        String profileToken = videoModuleCenter.getProfileToken(id);

        if (ptzDevices == null || profileToken == null) return;

        List<PTZPreset> ptzPresets = ptzDevices.getPresets(profileToken);
        for (PTZPreset ptzPreset : ptzPresets) {
            if (ptzPreset.getName().equals(presetName)) {
                ptzDevices.gotoPreset(ptzPreset.getToken(), profileToken);
                return;
            }
        }
        return;

    }

    /**
     * 获取onvif的驱动
     *
     * @param video
     * @return
     */
    private OnvifDevice getDevice(VideoDto video) {
        String hostIp = video.getVideoServerDto().getIp();
        String username = video.getVideoServerDto().getUsername();
        String password = video.getVideoServerDto().getPassword();

        if (hostIp == null || username == null || password == null) return null;

        try {
            OnvifDevice nvt = new OnvifDevice(hostIp, username, password);
            return nvt;
        } catch (ConnectException ce) {
            return null;
        } catch (SOAPException se) {
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    private PtzDevices getPtzDevices(OnvifDevice device) {
        try {
            PtzDevices ptzDevices = device.getPtz();
            return ptzDevices;
        } catch (Exception e) {
            return null;
        }
    }

    private String getProfileToken(OnvifDevice device) {
        try {
            List<Profile> profiles = device.getDevices().getProfiles();
            if (profiles.size() == 0) return null;

            String profileToken = profiles.get(0).getToken();

            return profileToken;
        } catch (Exception e) {
            return null;
        }
    }

}
