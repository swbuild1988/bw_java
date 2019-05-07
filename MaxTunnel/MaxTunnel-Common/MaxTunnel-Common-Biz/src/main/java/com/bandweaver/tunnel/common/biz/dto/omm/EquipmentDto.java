package com.bandweaver.tunnel.common.biz.dto.omm;

import java.io.Serializable;

import com.bandweaver.tunnel.common.biz.constant.omm.EquipmentStatusEnum;
import com.bandweaver.tunnel.common.biz.dto.SectionDto;
import com.bandweaver.tunnel.common.biz.dto.TunnelSimpleDto;
import com.bandweaver.tunnel.common.biz.pojo.mam.measobj.MeasObj;
import com.bandweaver.tunnel.common.biz.pojo.omm.Equipment;
import com.bandweaver.tunnel.common.biz.pojo.omm.EquipmentModel;
import com.bandweaver.tunnel.common.biz.pojo.omm.EquipmentVender;


/**
 * ClassName: EquipmentDto
 * 
 * @Description: 返回前端页面的数据
 * @author shaosen
 * @date 2018年5月31日
 */
public class EquipmentDto extends Equipment implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -2181426117543507255L;
	/**设备类型*/
	private String typeName;
	/**设备状态*/
	private String statusName;
    
	/** 舱段名称 */
	private SectionDto section;
	/** 设备型号名称 */
	private EquipmentModel model;
	/** 供应商名称名称 */
	private EquipmentVender vender;
	
	/** 设备对应的对象名称 */
	private MeasObj obj;
	
	private TunnelSimpleDto tunnel;
	
	public TunnelSimpleDto getTunnel() {
		return tunnel;
	}
	public void setTunnel(TunnelSimpleDto tunnel) {
		this.tunnel = tunnel;
	}
	public SectionDto getSection() {
		return section;
	}
	public void setSection(SectionDto section) {
		this.section = section;
	}

	/**
	 * @Description: 获取设备类型名称
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author shaosen
	 * @date 2018年5月31日
	 */
	public String getTypeName() {
		return typeName;
	}
	
	/**
	 * @Description: 获取设备状态
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author shaosen
	 * @date 2018年5月31日
	 */
	public String getStatusName() {
		EquipmentStatusEnum en = EquipmentStatusEnum.getEnum(super.getStatus());
		return en == null ? String.valueOf(super.getStatus()) : en.getName();
	}
	

	public EquipmentModel getModel() {
		return model;
	}
	public void setModel(EquipmentModel model) {
		this.model = model;
	}
	public EquipmentVender getVender() {
		return vender;
	}
	public void setVender(EquipmentVender vender) {
		this.vender = vender;
	}
	public MeasObj getObj() {
		return obj;
	}
	public void setObj(MeasObj obj) {
		this.obj = obj;
	}
	
}
