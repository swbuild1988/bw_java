package com.bandweaver.tunnel.common.biz.itf.oam;

import com.bandweaver.tunnel.common.biz.dto.oam.CableContractDto;
import com.bandweaver.tunnel.common.biz.dto.oam.ContractDto;
import com.bandweaver.tunnel.common.biz.vo.oam.CableContractVo;
import com.bandweaver.tunnel.common.biz.vo.oam.ContractVo;
import com.github.pagehelper.PageInfo;

public interface ContractService {

	/**
	 * 获取合同详情（包括管线和section信息）
	 * @param @param id
	 * @param @return   
	 * @return ContractDto  
	 * @throws
	 * @author shaosen
	 * @Date 2018年7月29日
	 */
	ContractDto getContractDetById(String id);

	PageInfo<CableContractDto> dataGrid(CableContractVo vo);
	
	void add(ContractVo vo);

	void delete(String id);

	void update(ContractVo vo);

}
