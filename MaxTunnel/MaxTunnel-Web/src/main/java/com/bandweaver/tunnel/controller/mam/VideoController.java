package com.bandweaver.tunnel.controller.mam;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.bandweaver.tunnel.common.biz.constant.PtzDirectionEnum;
import com.bandweaver.tunnel.common.biz.dto.SectionDto;
import com.bandweaver.tunnel.common.biz.itf.SectionService;
import com.bandweaver.tunnel.common.biz.pojo.mam.video.VideoPreset;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.GPSUtil;
import com.bandweaver.tunnel.common.platform.util.MathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.dto.TunnelSimpleDto;
import com.bandweaver.tunnel.common.biz.dto.mam.video.VideoDto;
import com.bandweaver.tunnel.common.biz.dto.mam.video.VideoSceneDto;
import com.bandweaver.tunnel.common.biz.dto.mam.video.VideoServerDto;
import com.bandweaver.tunnel.common.biz.itf.TunnelService;
import com.bandweaver.tunnel.common.biz.itf.mam.video.VideoSceneService;
import com.bandweaver.tunnel.common.biz.itf.mam.video.VideoServerService;
import com.bandweaver.tunnel.common.biz.pojo.mam.video.Video;
import com.bandweaver.tunnel.common.biz.pojo.mam.video.VideoScene;
import com.bandweaver.tunnel.common.biz.pojo.mam.video.VideoServer;
import com.bandweaver.tunnel.common.biz.vo.mam.video.VideoServerVo;
import com.bandweaver.tunnel.common.platform.constant.StatusCodeEnum;
import com.bandweaver.tunnel.common.platform.util.CommonUtil;
import com.bandweaver.tunnel.service.mam.video.VideoModuleCenter;
import com.github.pagehelper.PageInfo;

@Controller
@ResponseBody
public class VideoController {
    @Autowired
    private VideoServerService videoServerService;
    @Autowired
    private VideoSceneService videoSceneService;
    @Autowired
    private TunnelService tunnelService;
    @Autowired
    private VideoModuleCenter videoModuleCenter;
    @Autowired
    private SectionService sectionService;

    @RequestMapping(value = "videos/testadd", method = RequestMethod.GET)
    public JSONObject testAdd() {

        // 添加视频服务
        VideoServer videoServer = new VideoServer();
        videoServer.setName("视频服务1");
        videoServer.setVendor(1);
        videoServer.setVendorVersion(1);
        videoServer.setIp("192.168.7.202");
        videoServer.setPort(554);
        videoServer.setUsername("admin");
        videoServer.setPassword("bw123456");
        videoServer.setChannelNum(16);
        videoServerService.addVideoServer(videoServer);

        // 添加场景
        for (int i = 0; i < 3; i++) {
            VideoScene videoScene = new VideoScene();
            videoScene.setName("场景" + i);
            videoScene.setTunnelId(1);
            videoScene.setLoop(true);
            videoScene.setLoopIndex(i + 1);
            videoSceneService.addVideoScene(videoScene);
        }

        // 添加视频
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                Video video = new Video();
                int index = i * 4 + j + 1;
                video.setId(3000 + index);
                video.setTunnelId(3000 + index);
                video.setStoreId(3000 + index);
                video.setSectionId(1);
                video.setName("视频" + index);
                video.setDatatypeId(7);
                video.setObjtypeId(7);
                video.setActived(true);
                video.setDescription("");
                video.setLongitude("1");
                video.setLatitude("1");
                video.setHeight("1");
                video.setDeviation(1.0);
                video.setChannelNo(index);
                video.setServerId(1);
                video.setVideoSceneId(i + 1);
                videoModuleCenter.insertVideo(video);
            }
        }

        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }

    /**
     * 添加视频服务
     * @param videoServer
     * @return
     * @author ya.liu
     * @Date 2018年11月7日
     */
    @RequestMapping(value = "video_servers", method = RequestMethod.POST)
    public JSONObject insertVideoServer(@RequestBody VideoServer videoServer) {
    	videoServerService.addVideoServer(videoServer);
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }
    
    /**
     * 修改视频服务
     * @param videoServer
     * @return
     * @author ya.liu
     * @Date 2018年11月7日
     */
    @RequestMapping(value = "video_servers", method = RequestMethod.PUT)
    public JSONObject updateVideoServer(@RequestBody VideoServer videoServer) {
    	videoServerService.updateVideoServer(videoServer);
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }
    
    /**
     * 获取所有视频服务，不分页
     * @return
     * @author ya.liu
     * @Date 2018年11月7日
     */
    @RequestMapping(value = "video_servers", method = RequestMethod.GET)
    public JSONObject getAllVideoServers() {
    	List<VideoServerDto> list = videoServerService.getAllVideoServers();
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, list);
    }
    
    /**
     * 通过id获取视频服务
     * @param id
     * @return
     * @author ya.liu
     * @Date 2018年11月7日
     */
    @RequestMapping(value = "video_servers/{id}", method = RequestMethod.GET)
    public JSONObject getVideoServer(@PathVariable("id") Integer id) {
    	VideoServerDto videoServer = videoServerService.getVideoServer(id);
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, videoServer);
    }
    
    /**
     * 获取符合条件的视频服务，不分页
     * @param name 支持模糊查询
     * @param vendor 供应商
     * @param vendorVersion 版本号
     * @param ip ip地址
     * @param port 端口号
     * @param username 用户名
     * @return
     * @author ya.liu
     * @Date 2018年11月7日
     */
    @RequestMapping(value = "video_servers/condition", method = RequestMethod.POST)
    public JSONObject getVideoServers(@RequestBody VideoServerVo vo) {
    	List<VideoServerDto> list = videoServerService.getVideoServersByCondition(vo);
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, list);
    }
    
    /**
     * 分页获取视频服务
     * @param name 支持模糊查询
     * @param vendor 供应商
     * @param vendorVersion 版本号
     * @param ip ip地址
     * @param port 端口号
     * @param username 用户名
     * @return
     * @author ya.liu
     * @Date 2018年11月7日
     */
    @RequestMapping(value = "video_servers/datagrid", method = RequestMethod.POST)
    public JSONObject dataGrid(@RequestBody VideoServerVo vo) {
    	PageInfo<VideoServerDto> pageInfo = videoServerService.dataGrid(vo);
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, pageInfo);
    }
    
    /**
     * 删除视频服务
     * @param id
     * @return
     * @author ya.liu
     * @Date 2018年11月7日
     */
    @RequestMapping(value = "video_servers/{id}", method = RequestMethod.DELETE)
    public JSONObject deleteVideoServer(@PathVariable("id") Integer id) {
    	videoServerService.deleteVideoServer(id);
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }
    
    @RequestMapping(value = "tunnels/{tunnelId}/video_scenes")
    public JSONObject getScenesByTunnel(@PathVariable("tunnelId") int tunnelId) {
        List<VideoSceneDto> videoSceneDtos = videoModuleCenter.getVideoSceneDtosByTunnel(tunnelId);
        videoSceneDtos.sort((a, b) -> a.getLoopIndex().intValue() - b.getLoopIndex().intValue());
        videoSceneDtos = videoSceneDtos.stream().filter(a -> a.getLoop()).collect(Collectors.toList());

        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, videoSceneDtos);
    }

    @RequestMapping(value = "video_scenes")
    public JSONObject getAllScenes() {
        List<TunnelSimpleDto> tunnels = tunnelService.getList();
        List<VideoSceneDto> videoSceneDtos = new ArrayList<>();
        for (TunnelSimpleDto t : tunnels) {
            List<VideoSceneDto> tmp = videoModuleCenter.getVideoSceneDtosByTunnel(t.getId());
            if (tmp.size() > 0) videoSceneDtos.addAll(tmp);
        }
        if(videoSceneDtos != null && videoSceneDtos.size() > 0) {
        	 videoSceneDtos.sort((a, b) -> a.getLoopIndex().intValue() - b.getLoopIndex().intValue());
             videoSceneDtos = videoSceneDtos.stream().filter(a -> a.getLoop()).collect(Collectors.toList());
        }
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, videoSceneDtos);
    }

    @RequestMapping(value = "videos", method = RequestMethod.GET)
    public JSONObject getAllVideos() {
        List<VideoDto> videoDtos = videoModuleCenter.getVideoDtos();

        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, videoDtos);
    }


    @RequestMapping(value = "tunnels/{tunnelId}/videos", method = RequestMethod.GET)
    public JSONObject getVideosByTunnel(@PathVariable("tunnelId") int tunnelId) {
        List<VideoDto> videoDtos = videoModuleCenter.getVideoDtos().stream()
                .filter(a -> a.getTunnelId() == tunnelId).collect(Collectors.toList());

        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, videoDtos);
    }

    /**
     * 条件查询视频列表
     *
     * @param tunnelI	管廊id
     * @param storeId	管仓id
     * @param areaId	区段id
     * @param sectionId	仓段id
     * @return
     * @author liuya
     * @Date 2018年9月7日
     */
    @RequestMapping(value = "videos/condition", method = RequestMethod.POST)
    public JSONObject getVideosByParams(@RequestBody JSONObject object) {
        List<VideoDto> videoDtos = videoModuleCenter.getVideoDtos();
        Integer tunnelId = object.getInteger("tunnelId");
        Integer storeId = object.getInteger("storeId");
        Integer areaId = object.getInteger("areaId");
        Integer sectionId = object.getInteger("sectionId");

        if (tunnelId != null)
            videoDtos = videoDtos.stream().filter(a -> a.getTunnelId() == tunnelId).collect(Collectors.toList());
        if (storeId != null)
            videoDtos = videoDtos.stream().filter(a -> a.getStoreId() == storeId).collect(Collectors.toList());
        if (areaId != null)
            videoDtos = videoDtos.stream().filter(a -> a.getAreaId() == areaId).collect(Collectors.toList());
        if (sectionId != null)
            videoDtos = videoDtos.stream().filter(a -> a.getSectionId() == sectionId).collect(Collectors.toList());

        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, videoDtos);
    }

    @RequestMapping(value = "videos/{id}/move/{direction}", method = RequestMethod.GET)
    public JSONObject startCameraMove(@PathVariable("id") int id, @PathVariable("direction") int direction) {

        PtzDirectionEnum ptzDirectionEnum = PtzDirectionEnum.getEnum(direction);

        videoModuleCenter.startMove(id, ptzDirectionEnum);

        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }

    @RequestMapping(value = "videos/{id}/stop", method = RequestMethod.GET)
    public JSONObject stopCameraMove(@PathVariable("id") int id) {

        videoModuleCenter.stopMove(id);

        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }


    /**
     * 获得相机所有的预置位
     */
    @RequestMapping(value = "videos/{id}/presets", method = RequestMethod.GET)
    public JSONObject getPresets(@PathVariable("id") int id) {
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, videoModuleCenter.getPresets(id));
    }

    /**
     * 添加预置位
     *
     * @return
     */
    @RequestMapping(value = "videos/{id}/presets", method = RequestMethod.POST)
    public JSONObject addPreset(@PathVariable("id") int id, @RequestBody JSONObject object) {
        String presetName = object.getString("presetName");
        boolean f = videoModuleCenter.addPreset(id, presetName);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, f);
    }

    /**
     * 删除预置位
     */
    @RequestMapping(value = "videos/{id}/presets", method = RequestMethod.DELETE)
    public JSONObject delPreset(@PathVariable("id") int id, @RequestBody JSONObject object) {
        String presetName = object.getString("presetName");
        boolean f = videoModuleCenter.delPreset(id, presetName);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, f);
    }

    /**
     * 更新预置位
     *
     * @return
     */
    @RequestMapping(value = "videos/{id}/presets", method = RequestMethod.PUT)
    public JSONObject updatePreset(@PathVariable("id") int id, @RequestBody JSONObject object) {
        String presetName = object.getString("presetName");
        boolean f = videoModuleCenter.updatePreset(id, presetName);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, f);
    }

    /**
     * 移动到预置位
     *
     * @param id
     * @param presetName
     */
    @RequestMapping(value = "videos/{id}/presets/{presetName}/goto", method = RequestMethod.GET)
    public JSONObject gotoPreset(@PathVariable("id") int id, @PathVariable("presetName") String presetName) {
        // 如果预置位名是default，则不用动
        // default是相机的默认位置，主要用在固定相机上
        if (!presetName.equals("default")) videoModuleCenter.gotoPreset(id, presetName);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }

    /**
     * 更新预置位的3D参数
     *
     * @param videoPreset
     * @return
     */
    @RequestMapping(value = "preset-3d", method = RequestMethod.POST)
    public JSONObject updatePreset3DParam(@RequestBody VideoPreset videoPreset) {
        LogUtil.info("preset-3d:" + videoPreset);
        videoModuleCenter.updateVideoPreset(videoPreset);
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }

    /**
     * 将传过来的相机位置和相机预置位比较，找到最接近的一个相机和预置位
     * <p>
     * 先找到对应的管仓和其中的相机，默认赋值第一个相机（万一没有匹配的预置位）
     * 然后找到匹配度最高的预置位
     * 最后返回
     *
     * @param object
     * @return
     */
    @RequestMapping(value = "virual-inspection/video", method = RequestMethod.POST)
    public JSONObject getVideoAndPresetByCameraPosition(@RequestBody JSONObject object) {
        LogUtil.info("virual-inspection/video post: " + object.toJSONString());
        Double longitude = Double.parseDouble(object.getString("longitude"));
        Double latitude = Double.parseDouble(object.getString("latitude"));
        Double height = Double.parseDouble(object.getString("height"));
        Double pitch = Double.parseDouble(object.getString("pitch"));
        Double roll = Double.parseDouble(object.getString("roll"));
        Double heading = Double.parseDouble(object.getString("heading"));
        LogUtil.info("step 2 ");
        JSONObject result = new JSONObject();
        // 默认赋值第一个
        result.put("video", null);
        result.put("preset", null);

        // 现获取属于哪个管廊section
        SectionDto sectionDto = sectionService.getSectionByGPS(longitude, latitude, height);
        if (sectionDto == null)
            return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);

        LogUtil.info("step 3 ");
        LogUtil.info("section:" + sectionDto);

        int sectionId = sectionDto.getId();
        // 获取管廊的相机
        List<VideoDto> videoDtos = videoModuleCenter.getVideoDtos();
        videoDtos = videoDtos.stream().filter(a -> a.getSectionId() == sectionId).collect(Collectors.toList());

        if (videoDtos.size() > 0) {
            // 默认赋值第一个
            result.put("video", videoDtos.get(0));

            // 找到最高匹配度的那个相机
            double match = 0;
            // 根据位置信息获取相机及预置位信息
            for (VideoDto videoDto : videoDtos) {
                List<VideoPreset> presets = videoDto.getVideoPresets();

                for (VideoPreset preset : presets) {
                    // 找匹配度最高的那个预置位
                    LogUtil.info("video " + preset.getVideoId() + " " + preset.getPresetName());
                    double tmp_match = getMatchOfPreset(object, preset);
                    LogUtil.info(preset.getPresetName() + " 匹配度： " + tmp_match);
                    if (tmp_match == 0 || tmp_match < match) continue;

                    match = tmp_match;
                    result = new JSONObject();
                    result.put("video", videoDto);
                    result.put("preset", preset.getPresetName());

                }
            }

        }

        LogUtil.info("step 4 " + result.toJSONString());
//        if (result.get("video") != null && result.getString("preset") != null)
//            videoModuleCenter.gotoPreset(((VideoDto) result.get("video")).getId(), result.getString("preset"));

        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, result);
    }

    /**
     * 获得和预置位的匹配度
     *
     * @param object
     * @param videoPreset
     * @return
     */
    private double getMatchOfPreset(JSONObject object, VideoPreset videoPreset) {
        Double longitude = object.getDouble("longitude");
        Double latitude = object.getDouble("latitude");
        Double height = object.getDouble("height");
        Double pitch = object.getDouble("pitch");
        Double roll = object.getDouble("roll");
        Double heading = object.getDouble("heading");

        Double preset_lon = Double.parseDouble(videoPreset.getLongitude());
        Double preset_lat = Double.parseDouble(videoPreset.getLatitude());
        Double preset_heading = Double.parseDouble(videoPreset.getHeading());


        double dis = GPSUtil.GetDistance(preset_lon, preset_lat, longitude, latitude);
        double rad = 0.0;

        // 现在位置距离预置位的距离和角度偏差
        double tmp_dis = 30;
        double tmp_rad = GPSUtil.rad(15);
        double min_rad = preset_heading - tmp_rad;
        double max_rad = preset_heading + tmp_rad;
        LogUtil.info("distance:" + dis);
        LogUtil.info("min_rad:" + min_rad);
        LogUtil.info("max_rad:" + max_rad);
        if (min_rad < 0) {      // 预置位角度在（0-15°）
            if (heading < GPSUtil.rad(360) + min_rad && heading > max_rad) return 0.0;
            if (heading >= GPSUtil.rad(360) + min_rad) {
                rad = Math.abs(preset_heading + GPSUtil.rad(360) - heading);
            } else {
                rad = Math.abs(preset_heading - heading);
            }
        } else if (max_rad > GPSUtil.rad(360)) {        // 预置位角度在（345-360°）
            if (heading > max_rad - GPSUtil.rad(360) && heading < min_rad) return 0.0;
            if (heading <= max_rad - GPSUtil.rad(360)) {
                rad = Math.abs(heading + GPSUtil.rad(360) - preset_heading);
            } else {
                rad = Math.abs(preset_heading - heading);
            }
        } else {
            if (dis > tmp_dis || heading > max_rad || heading < min_rad) return 0.0;
            rad = Math.abs(preset_heading - heading);
        }
        LogUtil.info("rad: " + rad);

        // 获得现在位置，相对于预置位的heading角度，判断位置在预置位的前面还是后面
        double angle = MathUtil.getAngleOf3Point(GPSUtil.MillierConvertion(longitude, latitude),
                GPSUtil.MillierConvertion(preset_lon, preset_lat),
                GPSUtil.MillierConvertion(preset_lon, preset_lat - 1));
        LogUtil.info("new angle: " + angle);
        double preset_angle = GPSUtil.degree(preset_heading);
        while (preset_angle > 360) preset_angle -= 360;
        LogUtil.info("preset angle: " + preset_angle);
        // 判断现在位置是否在预置位的正前面
        if (preset_angle - 90 < 0) {
            if (angle > preset_angle + 90 && angle < preset_angle + 270) return 0.0;
        } else if (preset_angle + 90 > 360) {
            if (angle > preset_angle - 270 && angle < preset_angle - 90) return 0.0;
        } else {
            if (angle < preset_angle - 90 || angle > preset_angle + 90) return 0.0;
        }

        // 如果距离或者弧度差为0，则得到的值越大，反之则越小，*50则表示权重
        return (tmp_dis - dis) / tmp_dis * 20 + (tmp_rad - rad) / tmp_rad * 80;
    }


}
