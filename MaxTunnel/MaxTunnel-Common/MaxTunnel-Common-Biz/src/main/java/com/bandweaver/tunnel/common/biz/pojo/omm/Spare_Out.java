package com.bandweaver.tunnel.common.biz.pojo.omm;

import java.io.Serializable;
import java.util.Date;

public class Spare_Out implements Serializable{
    
	private static final long serialVersionUID = 1L;
	private Equipment equipment;
    private SpareOut spareOut;
    
	public Equipment getEquipment() {
		return equipment;
	}
	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}
	public SpareOut getSpareOut() {
		return spareOut;
	}
	public void setSpareOut(SpareOut spareOut) {
		this.spareOut = spareOut;
	}
    
}
