package com.bandweaver.tunnel.service.common;

import java.util.*;

import com.bandweaver.tunnel.common.biz.constant.ShiroOperateType;
import com.bandweaver.tunnel.common.platform.exception.BandWeaverException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bandweaver.tunnel.common.biz.dto.SectionDto;
import com.bandweaver.tunnel.common.biz.dto.StoreDto;
import com.bandweaver.tunnel.common.biz.dto.TunnelDto;
import com.bandweaver.tunnel.common.biz.dto.TunnelSimpleDto;
import com.bandweaver.tunnel.common.biz.itf.SectionService;
import com.bandweaver.tunnel.common.biz.itf.StoreService;
import com.bandweaver.tunnel.common.biz.itf.TunnelService;
import com.bandweaver.tunnel.common.biz.pojo.Tunnel;
import com.bandweaver.tunnel.common.biz.pojo.common.TunnelRun;
import com.bandweaver.tunnel.common.biz.vo.TunnelVo;
import com.bandweaver.tunnel.common.platform.util.StringTools;
import com.bandweaver.tunnel.dao.common.TunnelMapper;
import com.bandweaver.tunnel.dao.common.TunnelRunMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class TunnelServiceImpl implements TunnelService {
    @Autowired
    private TunnelMapper tunnelMapper;
    @Autowired
    private StoreService storeService;
    @Autowired
    private SectionService sectionService;
    @Autowired
    private TunnelRunMapper tunnelRunMapper;


    private boolean checkQueryPermission(Integer tunnelId) {
        Subject currentUser = SecurityUtils.getSubject();
        return currentUser.isPermitted("tunnel:list:" + tunnelId) ? true : false;
    }

    @Override
    public List<TunnelSimpleDto> getListWithoutPermission() {
        List<TunnelSimpleDto> list = tunnelMapper.getList();
        return list == null ? Collections.emptyList() : list;
    }

    @Override
    public String getNameById(Integer id) {
        if (checkQueryPermission(id)) {
            return tunnelMapper.getNameById(id);
        } else {
            throw new BandWeaverException("permission denied");
        }
    }

    @Override
    public TunnelDto getDtoById(Integer id) {
        if (checkQueryPermission(id)) {
            return tunnelMapper.getDtoById(id);
        }
        return null;
    }

    @Override
    public TunnelSimpleDto getSimpleDtoById(Integer id) {
        if (checkQueryPermission(id)) {
            return tunnelMapper.getSimpleDtoById(id);
        }
        return null;
    }

    @Override
    public TunnelSimpleDto getSimpleDtoByIdWithoutPermissioin(Integer id) {
        return tunnelMapper.getSimpleDtoById(id);
    }

    @Override
    public List<TunnelDto> getDtoList() {
        List<TunnelDto> list = new ArrayList<>();
        List<TunnelSimpleDto> tunnelList = getListWithoutPermission();
        for (TunnelSimpleDto tunnel : tunnelList) {
            TunnelDto dto = getDtoById(tunnel.getId());
            if (dto != null) {
                list.add(dto);
            }
        }

        return list;
    }

    @Override
    public List<TunnelSimpleDto> getList() {
        List<TunnelSimpleDto> list = new ArrayList<>();
        List<TunnelSimpleDto> tunnelList = getListWithoutPermission();
        for (TunnelSimpleDto tunnel : tunnelList) {
            TunnelSimpleDto simpleDto = getSimpleDtoById(tunnel.getId());
            if (simpleDto != null) {
                list.add(simpleDto);
            }
        }

        return list;
    }


    @Override
    public List<SectionDto> getSectionList(Integer id) {
        if (checkQueryPermission(id)) {
            List<SectionDto> list = new ArrayList<>();
            List<StoreDto> storeList = storeService.getStoresByTunnelId(id);
            for (StoreDto storeDto : storeList) {
                List<SectionDto> sectionList = sectionService.getSectionsByStoreId(storeDto.getId());
                list.addAll(sectionList);
            }
            return list;
        } else {
            throw new BandWeaverException("permission denied");
        }

    }


    @Override
    public void add(Tunnel tunnel) {
        tunnel.setCrtTime(new Date());
        tunnelMapper.insertSelective(tunnel);
    }


    @Override
    public void update(Tunnel tunnel) {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isPermitted("tunnel:" + ShiroOperateType.UPDATE.getValue() + ":" + tunnel.getId())) {
            tunnelMapper.updateByPrimaryKeySelective(tunnel);
        } else {
            throw new BandWeaverException("permission denied");
        }

    }


    @Override
    public void delete(Integer id) {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isPermitted("tunnel:" + ShiroOperateType.DELETE.getValue() + ":" + id)) {
            tunnelMapper.deleteByPrimaryKey(id);
        } else {
            throw new BandWeaverException("permission denied");
        }
    }


    @Override
    public PageInfo<TunnelDto> dataGrid(TunnelVo vo) {

        List<Integer> idList = new ArrayList<>();
        List<TunnelSimpleDto> tunnelSimpleDtoList = getListWithoutPermission();
        for (TunnelSimpleDto simpleDto : tunnelSimpleDtoList) {
            if (checkQueryPermission(simpleDto.getId())) {
                idList.add(simpleDto.getId());
            }
        }
        if(idList.size() > 0) vo.setIds(idList);

        PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
        List<TunnelDto> list = tunnelMapper.getByCondition(vo);
        return new PageInfo<>(list);
    }


    @Override
    public void deleteBatch(List<Integer> list) {

        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            buffer.append(list.get(i) + ((i == list.size() -1) ? "" : ","));
        }
        String idString = buffer.toString();

        Subject subject = SecurityUtils.getSubject();
        if (subject.isPermitted("tunnel:" + ShiroOperateType.DELETE.getValue() + ":" + idString)) {
            tunnelMapper.deleteBatch(list);
        } else {
            throw new BandWeaverException("permission denied");
        }

    }

    @Override
    public Tunnel getByName(String name) {
        return tunnelMapper.getByName(name);
    }

    @Override
    public Tunnel getBySN(String sn) {
        return tunnelMapper.getBySN(sn);
    }

    @Override
    public TunnelRun getTunnelRunInfo() {
        return tunnelRunMapper.getRunInfo();
    }

    @Override
    public void updateTunnelRunInfo(TunnelRun runInfo) {
        if (StringTools.isNullOrEmpty(runInfo)) {
            TunnelRun tr = new TunnelRun();
            tr.setId(1);
            tr.setRunDays(1);
            tr.setSafeDyas(1);
            runInfo = tr;
            addTunnelRun(tr);
        }
        runInfo.setRunDays(runInfo.getRunDays() + 1);
        runInfo.setSafeDyas(runInfo.getSafeDyas() + 1);
        tunnelRunMapper.updateByPrimaryKeySelective(runInfo);
    }

    @Override
    public void addTunnelRun(TunnelRun tr) {
        tunnelRunMapper.insertSelective(tr);
    }

    @Override
    public List<Integer> getSubSystemIdList() {
        return tunnelMapper.getSubSystemIdList();
    }
}
