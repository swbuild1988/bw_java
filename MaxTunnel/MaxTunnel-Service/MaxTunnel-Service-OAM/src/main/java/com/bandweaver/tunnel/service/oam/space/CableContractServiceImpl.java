package com.bandweaver.tunnel.service.oam.space;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bandweaver.tunnel.common.biz.itf.oam.CableContractService;
import com.bandweaver.tunnel.dao.oam.CableContractMapper;
@Service
public class CableContractServiceImpl implements CableContractService {

	@Autowired 
	private CableContractMapper cableContractMapper;
}
