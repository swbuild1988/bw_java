package com.bandweaver.tunnel.service.oam.contract;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bandweaver.tunnel.common.biz.constant.oam.CableStatusEnum;
import com.bandweaver.tunnel.common.biz.constant.oam.ContractStatusEnum;
import com.bandweaver.tunnel.common.biz.dto.SectionDto;
import com.bandweaver.tunnel.common.biz.dto.TunnelSimpleDto;
import com.bandweaver.tunnel.common.biz.dto.oam.CableContractDto;
import com.bandweaver.tunnel.common.biz.dto.oam.CableDto;
import com.bandweaver.tunnel.common.biz.dto.oam.ContractDto;
import com.bandweaver.tunnel.common.biz.itf.SectionService;
import com.bandweaver.tunnel.common.biz.itf.oam.ContractService;
import com.bandweaver.tunnel.common.biz.pojo.Section;
import com.bandweaver.tunnel.common.biz.pojo.oam.Cable;
import com.bandweaver.tunnel.common.biz.pojo.oam.CableContract;
import com.bandweaver.tunnel.common.biz.pojo.oam.CableSection;
import com.bandweaver.tunnel.common.biz.vo.oam.CableContractVo;
import com.bandweaver.tunnel.common.biz.vo.oam.ContractVo;
import com.bandweaver.tunnel.dao.oam.CableContractMapper;
import com.bandweaver.tunnel.dao.oam.CableMapper;
import com.bandweaver.tunnel.dao.oam.CableSectionMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
@Service
public class ContractServiceImpl implements ContractService {

	@Autowired
	private CableMapper cableMapper;
	@Autowired
	private CableContractMapper CableContractMapper;
	@Autowired
	private CableSectionMapper cableSectionMapper;
	@Autowired
	private SectionService sectionService;
	
	@Transactional
	@Override
	public void add(ContractVo vo) {
		
		//获取合同信息
		CableContract cableContract = vo.toCableContract();
		cableContract.setId(UUID.randomUUID().toString().replaceAll("-", ""));
		cableContract.setContractStatus(ContractStatusEnum.NORMAL.getValue());
		CableContractMapper.insert(cableContract);
	
		//获取管线信息
		Cable cable = vo.toCable();
		cable.setId(UUID.randomUUID().toString().replaceAll("-", ""));
		cable.setCableStatus(CableStatusEnum.RUNNING.getValue());
		cable.setContractId(cableContract.getId());
		cableMapper.insert(cable);
		
		//获取section信息
//		List<Integer> sectionIds =vo.getSectionIds();
//		for (Integer sectionId : sectionIds) {
//			CableSection cableSection = new CableSection();
//			cableSection.setSectionId(sectionId);
//			cableSection.setCableId(cable.getId());
//			cableSectionMapper.insert(cableSection);
//		}
		Integer storeId = vo.getStoreId();
		List<Integer> areaIds = vo.getAreaIds();
		for (Integer areaId : areaIds) {
			Section section = sectionService.getSectionByStoreAndArea(storeId,areaId);
			
			CableSection cableSection = new CableSection();
			cableSection.setSectionId(section.getId());
			cableSection.setCableId(cable.getId());
			cableSectionMapper.insert(cableSection);
		}
		
		
		
		
	}

	@Override
	public ContractDto getContractDetById(String id) {
		
		ContractDto dto = new ContractDto(); 
		CableDto cableDto = cableMapper.getCableDetByContractId(id);
		
		//获取部署区域集合
		List<Integer> sectionIds = cableSectionMapper.getSectionIdsByCableId(cableDto.getId());

		dto.setCableDto(cableDto);

		List<Integer> areaIds = new ArrayList<>();
		for (Integer sectionId : sectionIds) {
			SectionDto sec = sectionService.getSectionById(sectionId);
			areaIds.add(sec.getArea().getId());
			dto.setStoreId(sec.getStore().getId());
		}
		dto.setAreaIds(areaIds);
		
		//查询所在管廊信息
		TunnelSimpleDto tunnel = sectionService.getTunnelBySectionId(sectionIds.get(0));
		dto.setTunnelId(tunnel.getId());
		
		return dto;
	}

	@Override
	public PageInfo<CableContractDto> dataGrid(CableContractVo vo) {
		PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
		List<CableContractDto> list = CableContractMapper.getByCondition(vo);
		return new PageInfo<>(list);
	}

	@Override
	@Transactional
	public void delete(String id) {
		//删除合同表
		CableContractMapper.deleteByPrimaryKey(id);
		
		//删除管线section中间表
		CableDto cableDto = cableMapper.getCableDetByContractId(id);
		cableSectionMapper.deleteByCableId(cableDto.getId());
		
		//删除管线表
		cableMapper.deleteByContractId(id);
	}

	@Override
	@Transactional
	public void update(ContractVo vo) {
	
		//更新合同表
		CableContract cableContract = vo.toCableContract();
		CableContractMapper.updateByPrimaryKeySelective(cableContract);
		
		//更新管线表
		Cable cable = vo.toCable();
		cableMapper.updateByPrimaryKeySelective(cable);
		
		//更新管线section中间表
		//先删除旧的关系数据，然后再添加新的关系数据
		cableSectionMapper.deleteByCableId(cable.getId());
		/*List<Integer> sectionIds =vo.getSectionIds();
		for (Integer sectionId : sectionIds) {
			CableSection cableSection = new CableSection();
			cableSection.setSectionId(sectionId);
			cableSection.setCableId(cable.getId());
			cableSectionMapper.insert(cableSection);
		}*/
		
		Integer storeId = vo.getStoreId();
		List<Integer> areaIds = vo.getAreaIds();
		for (Integer areaId : areaIds) {
			Section section = sectionService.getSectionByStoreAndArea(storeId,areaId);
			
			CableSection cableSection = new CableSection();
			cableSection.setSectionId(section.getId());
			cableSection.setCableId(cable.getId());
			cableSectionMapper.insert(cableSection);
		}
	}

}
