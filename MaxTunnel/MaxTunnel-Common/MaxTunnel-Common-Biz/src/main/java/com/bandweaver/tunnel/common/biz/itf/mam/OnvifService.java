package com.bandweaver.tunnel.common.biz.itf.mam;


import com.bandweaver.tunnel.common.biz.constant.PtzDirectionEnum;
import com.bandweaver.tunnel.common.biz.dto.mam.video.VideoDto;
import com.bandweaver.tunnel.common.biz.pojo.mam.video.VideoServer;
import de.onvif.soap.devices.PtzDevices;

import java.util.Date;
import java.util.List;

public interface OnvifService {

    /**
     * 获取ptz控制
     *
     * @param videoDto
     * @return
     */
    PtzDevices getPtzDevices(VideoDto videoDto);

    /**
     * 获取权限
     *
     * @param videoDto
     * @return
     */
    String getProfileToken(VideoDto videoDto);

    /**
     * 获取session
     *
     * @param videoServer
     * @return
     */
    String getSession(VideoServer videoServer);

    /**
     * 是否支持ptz相机控制
     *
     * @param videoDto
     * @return
     */
    boolean isPtzOperationsSupported(VideoDto videoDto);

    /**
     * 开始移动
     *
     */
    void startMove(int id, PtzDirectionEnum direction);

    /**
     * 停止移动
     *
     */
    void stopMove(int id);

    /**
     * 获得相机所有的预置位
     *
     * @param id
     * @return
     */
    List<String> getPresets(int id);

    /**
     * 添加预置位
     *
     * @param id
     * @param presetName
     * @return
     */
    boolean addPreset(int id, String presetName);

    /**
     * 删除预置位
     *
     * @param id
     * @param presetName
     * @return
     */
    boolean delPreset(int id, String presetName);

    /**
     * 更新预置位
     *
     * @param id
     * @param presetName
     * @return
     */
    boolean updatePreset(int id, String presetName);

    /**
     * 移动到预置位
     *
     * @param id
     * @param presetName
     */
    void gotoPreset(int id, String presetName);

	/**添加相机源
	 * @param user
	 * @param password
	 * @param ip
	 * @param id
	 * @param url
	 * @return   
	 * @author shaosen
	 * @throws Exception 
	 * @Date 2018年12月4日
	 */
	boolean addSrc(String user, String password, String ip, String id, String url) throws Exception;

	/**删除相机源 
	 * @param id
	 * @return   
	 * @author shaosen
	 * @throws Exception 
	 * @Date 2018年12月4日
	 */
	boolean delSrc(String id) throws Exception;

	void delSrcList();

	void addSnap();

	String getSnapJSON(VideoDto dto, Date start, Date end) throws Exception;

}
