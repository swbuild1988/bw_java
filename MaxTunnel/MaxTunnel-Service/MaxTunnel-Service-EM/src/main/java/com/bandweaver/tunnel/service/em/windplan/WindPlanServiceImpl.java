package com.bandweaver.tunnel.service.em.windplan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.constant.em.WindPlanNodeEnum;
import com.bandweaver.tunnel.common.biz.itf.em.WindPlanService;

@Service
public class WindPlanServiceImpl implements WindPlanService {

	@Autowired
	private WindPlanProducer windPlanProducer;

	@Override
	public void sendMsg(JSONObject json ) {
		windPlanProducer.sendMessage(json);
	}


	@Override
	public void doBusiness(WindPlanNodeEnum nodeEnum) {

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
		case shutter:
			startShutter();
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




	private void startShutter() {
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
