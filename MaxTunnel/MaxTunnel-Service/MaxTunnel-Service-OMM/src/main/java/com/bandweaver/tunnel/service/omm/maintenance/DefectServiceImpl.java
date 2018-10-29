package com.bandweaver.tunnel.service.omm.maintenance;

import com.bandweaver.tunnel.common.biz.dto.omm.DefectDto;
import com.bandweaver.tunnel.common.biz.itf.omm.DefectService;
import com.bandweaver.tunnel.common.biz.itf.omm.MaintenanceOrderService;
import com.bandweaver.tunnel.common.biz.pojo.omm.Defect;
import com.bandweaver.tunnel.common.biz.pojo.omm.InspectionPlan;
import com.bandweaver.tunnel.common.biz.vo.omm.DefectVo;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.dao.omm.DefectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DefectServiceImpl implements DefectService {
    @Autowired
    private DefectMapper defectMapper;
    @Autowired
    private MaintenanceOrderService maintenanceOrderService;


    @Override
    public int add(Defect defect) {
        LogUtil.info("准备添加缺陷：" + defect);
        int defectId = defectMapper.add(defect);
        LogUtil.info("缺陷添加完毕，启动维修工单：" + defect);

        // 缺陷添加完毕后，自动添加维修工单
        maintenanceOrderService.add(defect.getTunnelId(), defect.getId());

        return defect.getId();
    }

    @Override
    public int update(Defect defect) {
        return defectMapper.update(defect);
    }

    @Override
    public List<DefectDto> getDefectsOfTunnel(Integer tunnelId) {
        return defectMapper.getDefectsOfTunnel(tunnelId);
    }

    @Override
    public int getCountOfDefectByTunnelAndType(Integer tunnelId, Integer typeValue) {
        return defectMapper.getCountOfDefectByTunnelAndType(tunnelId, typeValue);
    }

    @Override
    public List<DefectDto> getDefectsByCondition(DefectVo vo) {
        return defectMapper.getDefectsByCondition(vo);
    }


    /**
     * 维修完成后，更改缺陷状态
     *
     * @param maintenanceOrderId
     */
    @Override
    public void maintComplete(int maintenanceOrderId) {
        Defect defect = defectMapper.getDefectByOrder(maintenanceOrderId);
        // 更改状态为维修完毕
        defect.setStatus(0);
        defectMapper.update(defect);
    }

	@Override
	public List<InspectionPlan> getDefectCountByTunnelId() {
		return defectMapper.getDefectCountByTunnelId();
	}

	@Override
	public PageInfo<DefectDto> dataGrid(DefectVo vo) {
		PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
		List<DefectDto> list = defectMapper.getDefects(vo);
		return new PageInfo<>(list);
	}

	@Override
	public DefectDto getDefectDto(Integer id) {
		DefectDto defect = defectMapper.getDefectDto(id);
		return defect;
	}

	
    
    
}
