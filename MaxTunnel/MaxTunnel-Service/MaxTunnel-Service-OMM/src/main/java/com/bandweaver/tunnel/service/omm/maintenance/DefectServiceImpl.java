package com.bandweaver.tunnel.service.omm.maintenance;

import com.bandweaver.tunnel.common.biz.constant.omm.DefectType;
import com.bandweaver.tunnel.common.biz.dto.omm.DefectDto;
import com.bandweaver.tunnel.common.biz.dto.omm.EquipmentDto;
import com.bandweaver.tunnel.common.biz.itf.omm.DefectService;
import com.bandweaver.tunnel.common.biz.itf.omm.MaintenanceOrderService;
import com.bandweaver.tunnel.common.biz.pojo.omm.Buy;
import com.bandweaver.tunnel.common.biz.pojo.omm.Defect;
import com.bandweaver.tunnel.common.biz.vo.omm.DefectVo;
import com.bandweaver.tunnel.common.biz.vo.omm.EquipmentVo;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.dao.omm.BuyMapper;
import com.bandweaver.tunnel.dao.omm.DefectMapper;
import com.bandweaver.tunnel.dao.omm.EquipmentMapper;
import com.bandweaver.tunnel.dao.omm.SpareMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class DefectServiceImpl implements DefectService {
    @Autowired
    private DefectMapper defectMapper;
    @Autowired
    private MaintenanceOrderService maintenanceOrderService;
    @Autowired
	private SpareMapper spareMapper;
	@Autowired
	private EquipmentMapper equipmentMapper;
	@Autowired
	private BuyMapper buyMapper;


    @Override
    @Transactional
    public int add(Defect defect) {
        LogUtil.info("准备添加缺陷：" + defect);
        defectMapper.add(defect);
        LogUtil.info("缺陷添加完毕，启动维修工单：" + defect);

        // 缺陷添加完毕后，自动添加维修工单
        maintenanceOrderService.add(defect.getTunnelId(), defect.getId());

        // 如果是设备缺陷，自动查看是否有备品，没有会生成一条采购记录
        if(defect.getType() != null && defect.getType().equals(DefectType.Equipment.getValue())) {
        	// 通过设备对应objId查找设备类型，然后去备品表查
        	EquipmentVo vo = new EquipmentVo();
        	vo.setObjId(defect.getObjectId());
        	List<EquipmentDto> list = equipmentMapper.getEquipmentListByCondition(vo);
        	if(list != null && list.size() > 0) {
        		int count = spareMapper.getCountByTypeIdAndStatus(true, list.get(0).getType());
        		// 没有备品时添加采购记录
        		if(count < 0) {
        			Buy buy = new Buy();
        			buy.setTypeId(list.get(0).getType());
        			buy.setCrtTime(new Date());
        			buy.setIsFinished(false);
        			buyMapper.add(buy);
        		}
        	}
        }
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
    public int getCountByCondition(DefectVo vo) {
        return defectMapper.getCountByCondition(vo);
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
