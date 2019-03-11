package com.bandweaver.tunnel.controller.mam;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.constant.PtzDirectionEnum;
import com.bandweaver.tunnel.common.biz.constant.mam.VideoVendor;
import com.bandweaver.tunnel.common.biz.dto.SectionDto;
import com.bandweaver.tunnel.common.biz.dto.TunnelSimpleDto;
import com.bandweaver.tunnel.common.biz.dto.mam.h5.H5Obj;
import com.bandweaver.tunnel.common.biz.dto.mam.h5.H5Source;
import com.bandweaver.tunnel.common.biz.dto.mam.h5.H5Src;
import com.bandweaver.tunnel.common.biz.dto.mam.video.VideoDto;
import com.bandweaver.tunnel.common.biz.dto.mam.video.VideoExtendSceneDto;
import com.bandweaver.tunnel.common.biz.dto.mam.video.VideoSceneDto;
import com.bandweaver.tunnel.common.biz.dto.mam.video.VideoServerDto;
import com.bandweaver.tunnel.common.biz.itf.SectionService;
import com.bandweaver.tunnel.common.biz.itf.TunnelService;
import com.bandweaver.tunnel.common.biz.itf.mam.OnvifService;
import com.bandweaver.tunnel.common.biz.itf.mam.video.VideoServerService;
import com.bandweaver.tunnel.common.biz.pojo.ListPageUtil;
import com.bandweaver.tunnel.common.biz.pojo.mam.video.Video;
import com.bandweaver.tunnel.common.biz.pojo.mam.video.VideoPreset;
import com.bandweaver.tunnel.common.biz.pojo.mam.video.VideoServer;
import com.bandweaver.tunnel.common.biz.vo.mam.video.VideoServerVo;
import com.bandweaver.tunnel.common.biz.vo.mam.video.VideoVo;
import com.bandweaver.tunnel.common.platform.constant.StatusCodeEnum;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.CommonUtil;
import com.bandweaver.tunnel.common.platform.util.DataTypeUtil;
import com.bandweaver.tunnel.common.platform.util.FileUtil;
import com.bandweaver.tunnel.common.platform.util.GPSUtil;
import com.bandweaver.tunnel.common.platform.util.MathUtil;
import com.bandweaver.tunnel.service.mam.video.H5StreamServiceImpl;
import com.bandweaver.tunnel.service.mam.video.VideoModuleCenter;
import com.github.pagehelper.PageInfo;

@Controller
@ResponseBody
public class VideoController {
    @Autowired
    private VideoServerService videoServerService;
    @Autowired
    private TunnelService tunnelService;
    @Autowired
    private VideoModuleCenter videoModuleCenter;
    @Autowired
    private SectionService sectionService;
    @Resource(name = "H5StreamServiceImpl")
    private OnvifService h5streamService;
    
    /**
     * 添加视频
     * @param id 视频id
     * @param tunnelId 所属管廊 
     * @param storeId 所属管舱
     * @param areaId 所属区域
     * @param name 视频名称
     * @param description 描述
     * @param objtypeId	对象类型
     * @param datatypeId 数据类型
     * @param serverId 所属视频服务
     * @param vendor 所属供应商
     * @param videoSceneId 所属场景
     * @param channelNo 通道号
     * @param actived 是否使用
     * @param ip
     * @param port
     * @param username
     * @param password
     * @return
     * @author ya.liu
     * @Date 2019年2月19日
     */
    @Transactional
    @RequestMapping(value = "videos", method = RequestMethod.POST)
    public JSONObject addVideo(@RequestBody Video video) throws Exception {
    	// 添加视频到数据库以及缓存
    	videoModuleCenter.insertVideo(video);
    	// 添加onvif源
    	setOnvif(video.getId(), video.getUsername(), video.getPassword(), video.getIp(),
    			video.getPort().toString(), video.getChannelNo(), video.getVendor());
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }
    /**
     * h5添加onvif
     * @param id
     * @param user
     * @param password
     * @param ip
     * @param port
     * @param channelNo
     * @param vendor
     * @throws Exception
     * @author ya.liu
     * @Date 2019年2月26日
     */
    private void setOnvif(Integer id, String user, String password, String ip,
    		String port, Integer channelNo, Integer vendor) throws Exception{
    	String url = "";
    	VideoVendor videoVendor = VideoVendor.getEnum(vendor);
		switch (videoVendor) {
    	case DaKang:
    		url = "rtsp://" + ip + ":" + port + "/Streaming/Channels/" + channelNo;
    		break;
    		
    	case HoneyWell_HISD:
    		url = "rtsp://" + ip + ":" + port + "/h264/ch" + channelNo + "/main/av_stream";
    		break;
    		
    	case HoneyWell_HICC:
    		url = "rtsp://" + ip + ":" + port + "/media?stream=0";
    		break;
	
    	default:
    		break;
    	}
		boolean addFlag = h5streamService.addSrc(user,password,ip,id.toString(),url);
		LogUtil.info("相机"+ id +"添加结果：" + addFlag);
    }
    
    /**
     * 更新视频
     * @param id 视频id
     * @param tunnelId 所属管廊 
     * @param storeId 所属管舱
     * @param areaId 所属区域
     * @param name 视频名称
     * @param description 描述
     * @param objtypeId	对象类型
     * @param datatypeId 数据类型
     * @param serverId 所属视频服务
     * @param vendor 所属供应商
     * @param videoSceneId 所属场景
     * @param channelNo 通道号
     * @param actived 是否使用
     * @param ip
     * @param port
     * @param username
     * @param password
     * @return
     * @author ya.liu
     * @Date 2019年2月20日
     */
    @Transactional
    @RequestMapping(value = "videos", method = RequestMethod.PUT)
    public JSONObject updateVideo(@RequestBody Video video) throws Exception {
    	// 修改视频信息
    	videoModuleCenter.updateVideo(video);
    	// 先删除onvif源
    	h5streamService.delSrc(video.getId().toString());
    	// 再添加onvif源
    	setOnvif(video.getId(), video.getUsername(), video.getPassword(), video.getIp(),
    			video.getPort().toString(), video.getChannelNo(), video.getVendor());
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }
    
    /**
     * 分页查询视频列表
     * @param id
     * @param tunnelId	管廊
     * @param storeId	管舱
     * @param areaId	区域
     * @param vendor	供应商
     * @param serverId	服务商
     * @param actived	是否使用
     * @param channelNo	通道号
     * @param videoSceneId 场景
     * @return
     * @author ya.liu
     * @Date 2019年2月22日
     */
    @RequestMapping(value = "videos/datagrid", method = RequestMethod.POST)
    public JSONObject getVideoDtos(@RequestBody VideoVo vo) {
    	List<VideoDto> videoDtos = videoModuleCenter.getVideoDtos();
    	if (vo.getId() != null)
            videoDtos = videoDtos.stream().filter(a -> a.getId().intValue() == vo.getId()).collect(Collectors.toList());
    	if (vo.getTunnelId() != null)
            videoDtos = videoDtos.stream().filter(a -> a.getTunnelId().intValue() == vo.getTunnelId()).collect(Collectors.toList());
        if (vo.getStoreId() != null)
            videoDtos = videoDtos.stream().filter(a -> a.getStoreId().intValue() == vo.getStoreId()).collect(Collectors.toList());
        if (vo.getAreaId() != null)
            videoDtos = videoDtos.stream().filter(a -> a.getAreaId().intValue() == vo.getAreaId()).collect(Collectors.toList());
        if (vo.getVendor() != null)
            videoDtos = videoDtos.stream().filter(a -> a.getVendor().intValue() == vo.getVendor()).collect(Collectors.toList());
        if (vo.getServerId() != null)
            videoDtos = videoDtos.stream().filter(a -> a.getServerId().intValue() == vo.getServerId()).collect(Collectors.toList());
        if (vo.getActived() != null)
            videoDtos = videoDtos.stream().filter(a -> a.isActived() == vo.getActived()).collect(Collectors.toList());
        if (vo.getChannelNo() != null)
            videoDtos = videoDtos.stream().filter(a -> a.getChannelNo() == vo.getChannelNo()).collect(Collectors.toList());
        if (vo.getVideoSceneId() != null)
            videoDtos = videoDtos.stream().filter(a -> a.getVideoSceneId() == vo.getVideoSceneId()).collect(Collectors.toList());

    	ListPageUtil<VideoDto> list = new ListPageUtil<>(videoDtos, vo.getPageNum(), vo.getPageSize());
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, list);
    }
    
    /**
     * id获取视频信息
     * @param id
     * @return
     * @author ya.liu
     * @Date 2019年2月20日
     */
    @RequestMapping(value = "videos/{id}", method = RequestMethod.GET)
    public JSONObject getVideo(@PathVariable("id") Integer id) {
    	VideoDto dto = videoModuleCenter.getVideoDto(id);
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, dto);
    }
    
    /**
     * 批量删除视频
     * @param ids 
     * @return
     * @author ya.liu
     * @Date 2019年2月20日
     */
    @RequestMapping(value = "videos/{ids}", method = RequestMethod.DELETE)
    public JSONObject deleteVideo(@PathVariable("ids") String ids) throws Exception {
    	String [] strs = ids.split(",");
    	LogUtil.info("strs.length:" + strs.length);
    	for(String id : strs) {
    		// 先删除onvif源
        	h5streamService.delSrc(id);
        	// 再删除视频信息
    		videoModuleCenter.deleteVideo(DataTypeUtil.toInteger(id));
    	}
    	return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
    }
    
    /**通过sectionId获取视频列表 
     * @param id
     * @return   
     * @author shaosen
     * @Date 2018年12月27日
     */
    @RequestMapping(value="sections/{id}/videos",method=RequestMethod.GET)
    public JSONObject getVideosBySection(@PathVariable Integer id) {
    	List<VideoDto> videoDtos = videoServerService.getVideosBySection(id);
    	return CommonUtil.success(videoDtos);
    }
    
    
    /**通过api添加rtsp和onvif源
     * @param user 
     * @param password
     * @param ip
     * @param port
     * @param channelNo
     * @param id
     * @param vendor
     * @return  true or false
     * @author shaosen
     * @throws Exception 
     * @Date 2018年12月4日
     */
    @Transactional
    @RequestMapping(value="h5/api/addsrc",method=RequestMethod.POST)
    public JSONObject addH5ConfigByAPI(@RequestBody List<Map<String, String>> list) throws Exception {
    	//删除所有视频源
    	h5streamService.delSrcList();
    	
    	for (Map<String, String> map : list) {
    		String user = map.get("user");
			String password = map.get("password");
			String ip = map.get("ip");
			String port = "554";
			String channelNo = map.get("channelNo");
			String id = map.get("id");
			String vendor = map.get("vendor");
			
			setOnvif(DataTypeUtil.toInteger(id), user, password, ip,
	    			port, DataTypeUtil.toInteger(channelNo), DataTypeUtil.toInteger(vendor));
			
			// 修改视频表数据，以便后续维护
			Video video = new Video();
			video.setId(DataTypeUtil.toInteger(id));
			video.setUsername(user);
			video.setPassword(password);
			video.setIp(ip);
			video.setPort(DataTypeUtil.toInteger(port));
			video.setChannelNo(DataTypeUtil.toInt(channelNo));
			video.setVendor(DataTypeUtil.toInteger(vendor));
			videoModuleCenter.updateVideo(video);
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
            if (tmp != null && tmp.size() > 0) videoSceneDtos.addAll(tmp);
        }
        if(videoSceneDtos != null && videoSceneDtos.size() > 0) {
        	 videoSceneDtos.sort((a, b) -> a.getLoopIndex().intValue() - b.getLoopIndex().intValue());
             videoSceneDtos = videoSceneDtos.stream().filter(a -> a.getLoop()).collect(Collectors.toList());
        }
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, videoSceneDtos);
    }


    @RequestMapping(value = "video_extend_scenes")
    public JSONObject getAllExtendScenes() {
        List<TunnelSimpleDto> tunnels = tunnelService.getList();
        List<VideoExtendSceneDto> videoExtendSceneDtos = new ArrayList<>();
        for (TunnelSimpleDto t : tunnels) {
            List<VideoExtendSceneDto> tmp = videoModuleCenter.getVideoExtendSceneDtosByTunnel(t.getId());
            if (tmp.size() > 0) videoExtendSceneDtos.addAll(tmp);
        }
        if(videoExtendSceneDtos != null && videoExtendSceneDtos.size() > 0) {
            videoExtendSceneDtos.sort((a, b) -> a.getLoopIndex().intValue() - b.getLoopIndex().intValue());
            videoExtendSceneDtos = videoExtendSceneDtos.stream().filter(a -> a.getLoop()).collect(Collectors.toList());
        }
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, videoExtendSceneDtos);
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
            videoDtos = videoDtos.stream().filter(a -> a.getTunnelId().intValue() == tunnelId.intValue()).collect(Collectors.toList());
        if (storeId != null)
            videoDtos = videoDtos.stream().filter(a -> a.getStoreId().intValue() == storeId.intValue()).collect(Collectors.toList());
        if (areaId != null)
            videoDtos = videoDtos.stream().filter(a -> a.getAreaId().intValue() == areaId.intValue()).collect(Collectors.toList());
        if (sectionId != null)
            videoDtos = videoDtos.stream().filter(a -> a.getSectionId().intValue() == sectionId.intValue()).collect(Collectors.toList());

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

