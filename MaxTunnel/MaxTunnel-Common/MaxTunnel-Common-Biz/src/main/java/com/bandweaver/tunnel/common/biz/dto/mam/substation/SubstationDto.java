package com.bandweaver.tunnel.common.biz.dto.mam.substation;

import com.bandweaver.tunnel.common.biz.dto.TunnelSimpleDto;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObjAI;
import com.bandweaver.tunnel.common.biz.pojo.mam.substation.Substation;

public class SubstationDto extends Substation {
	
	private static final long serialVersionUID = 1L;
	
	private MeasObjAI inv;
	private MeasObjAI ina;
	private MeasObjAI outv;
	private MeasObjAI outa;
	private MeasObjAI power;
	private TunnelSimpleDto tunnel;
	
	public TunnelSimpleDto getTunnel() {
		return tunnel;
	}
	public void setTunnel(TunnelSimpleDto tunnel) {
		this.tunnel = tunnel;
	}
	public MeasObjAI getInv() {
		return inv;
	}
	public void setInv(MeasObjAI inv) {
		this.inv = inv;
	}
	public MeasObjAI getIna() {
		return ina;
	}
	public void setIna(MeasObjAI ina) {
		this.ina = ina;
	}
	public MeasObjAI getOutv() {
		return outv;
	}
	public void setOutv(MeasObjAI outv) {
		this.outv = outv;
	}
	public MeasObjAI getOuta() {
		return outa;
	}
	public void setOuta(MeasObjAI outa) {
		this.outa = outa;
	}
	public MeasObjAI getPower() {
		return power;
	}
	public void setPower(MeasObjAI power) {
		this.power = power;
	}
	
}
