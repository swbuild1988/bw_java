package com.bandweaver.tunnel.common.biz.dto.mam.substation;

import com.bandweaver.tunnel.common.biz.dto.TunnelSimpleDto;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObjAI;
import com.bandweaver.tunnel.common.biz.pojo.mam.substation.Substation;

public class SubstationDto extends Substation {
	
	private static final long serialVersionUID = 1L;
	
	private MeasObjAI voltage;
	private MeasObjAI current;
	private MeasObjAI power;
	private MeasObjAI unpower;
	private MeasObjAI powerFactor;
	private MeasObjAI powerDay;
	private MeasObjAI unpowerDay;
	private MeasObjAI powerEle;
	private MeasObjAI unpowerEle;
	
	private TunnelSimpleDto tunnel;

	public MeasObjAI getVoltage() {
		return voltage;
	}

	public void setVoltage(MeasObjAI voltage) {
		this.voltage = voltage;
	}

	public MeasObjAI getCurrent() {
		return current;
	}

	public void setCurrent(MeasObjAI current) {
		this.current = current;
	}

	public MeasObjAI getPower() {
		return power;
	}

	public void setPower(MeasObjAI power) {
		this.power = power;
	}

	public MeasObjAI getUnpower() {
		return unpower;
	}

	public void setUnpower(MeasObjAI unpower) {
		this.unpower = unpower;
	}

	public MeasObjAI getPowerFactor() {
		return powerFactor;
	}

	public void setPowerFactor(MeasObjAI powerFactor) {
		this.powerFactor = powerFactor;
	}

	public MeasObjAI getPowerDay() {
		return powerDay;
	}

	public void setPowerDay(MeasObjAI powerDay) {
		this.powerDay = powerDay;
	}

	public MeasObjAI getUnpowerDay() {
		return unpowerDay;
	}

	public void setUnpowerDay(MeasObjAI unpowerDay) {
		this.unpowerDay = unpowerDay;
	}

	public MeasObjAI getPowerEle() {
		return powerEle;
	}

	public void setPowerEle(MeasObjAI powerEle) {
		this.powerEle = powerEle;
	}

	public MeasObjAI getUnpowerEle() {
		return unpowerEle;
	}

	public void setUnpowerEle(MeasObjAI unpowerEle) {
		this.unpowerEle = unpowerEle;
	}

	public TunnelSimpleDto getTunnel() {
		return tunnel;
	}

	public void setTunnel(TunnelSimpleDto tunnel) {
		this.tunnel = tunnel;
	}
	
}
