package com.bandweaver.tunnel.service.mam.video;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bandweaver.tunnel.common.biz.dto.mam.video.VideoDto;
import com.bandweaver.tunnel.common.biz.dto.mam.video.VideoServerDto;
import com.bandweaver.tunnel.common.biz.itf.mam.video.VideoServerService;
import com.bandweaver.tunnel.common.biz.pojo.mam.video.VideoServer;
import com.bandweaver.tunnel.common.biz.vo.mam.video.VideoServerVo;
import com.bandweaver.tunnel.dao.mam.VideoServerMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class VideoServerServiceImpl implements VideoServerService {
    @Autowired
    private VideoServerMapper videoServerMapper;
    @Autowired
    private VideoModuleCenter videoModuleCenter;
    
    @Override
    public int addVideoServer(VideoServer videoServer) {
        return videoServerMapper.insertVideoServer(videoServer);
    }

    @Override
    public List<VideoServerDto> getAllVideoServers() {
        return videoServerMapper.getAllVideoServers();
    }

	@Override
	public int updateVideoServer(VideoServer videoServer) {
		return videoServerMapper.updateVideoServer(videoServer);
	}

	@Override
	public VideoServerDto getVideoServer(int id) {
		return videoServerMapper.getVideoServer(id);
	}

	@Override
	public List<VideoServerDto> getVideoServersByCondition(VideoServerVo vo) {
		List<VideoServerDto> list = videoServerMapper.getVideoServersByCondition(vo);
		return list;
	}

	@Override
	public PageInfo<VideoServerDto> dataGrid(VideoServerVo vo) {
		PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
		List<VideoServerDto> list = videoServerMapper.getVideoServersByCondition(vo);
		return new PageInfo<>(list);
	}

	@Override
	public int deleteVideoServer(Integer id) {
		return videoServerMapper.deleteVideoServer(id);
	}

	@Override
	public List<VideoDto> getVideosBySection(Integer id) {
    	List<VideoDto> videoDtos = videoModuleCenter.getVideoDtos();
    	videoDtos = videoDtos.stream().filter(v -> v.getSectionId().intValue() == id).collect(Collectors.toList());
		return videoDtos == null ? Collections.emptyList() : videoDtos ;
	}

    
}

