package com.bandweaver.tunnel.service.em.fireplan;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.history.HistoricTaskInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.constant.em.FirePlanNodeEnum;
import com.bandweaver.tunnel.common.biz.dto.em.RelatedUnitDto;
import com.bandweaver.tunnel.common.biz.itf.em.FirePlanService;
import com.bandweaver.tunnel.common.biz.itf.em.RelatedUnitService;
import com.bandweaver.tunnel.common.platform.log.LogUtil;

@Service
public class FirePlanServiceImpl implements FirePlanService {


	@Override
	public void sendMsg(JSONObject json ) {
	}


	@Override
	public void doBusiness(FirePlanNodeEnum nodeEnum) {

		switch (nodeEnum) {
		case alarm:
			startAlarm();
			break;
		case camera:
			startCamera();
			break;
		case confirm:
			startConfirm();
			break;
		case fan:
			startFan();
			break;
		case wind_valve:
			startWindValue();
			break;
		case shutter:
			startShutter();
			break;
		case dry_powder:
			startDryPowder();
			break;
		case inform_relatedunit:
			startInformRelatedUnit();
			break;

		default:
			break;
		}

	}


	private void startInformRelatedUnit() {
		// TODO Auto-generated method stub
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	private void startDryPowder() {
		// TODO Auto-generated method stub
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	private void startShutter() {
		// TODO Auto-generated method stub
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	private void startWindValue() {
		// TODO Auto-generated method stub
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	private void startFan() {
		// TODO Auto-generated method stub
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	private void startConfirm() {
		// TODO Auto-generated method stub
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	private void startCamera() {
		// TODO Auto-generated method stub
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	private void startAlarm() {
		// TODO Auto-generated method stub
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



}
