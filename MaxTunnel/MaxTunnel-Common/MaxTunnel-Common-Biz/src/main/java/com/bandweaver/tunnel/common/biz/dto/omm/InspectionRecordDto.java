package com.bandweaver.tunnel.common.biz.dto.omm;

import com.bandweaver.tunnel.common.biz.dto.AreaDto;
import com.bandweaver.tunnel.common.biz.dto.StoreDto;
import com.bandweaver.tunnel.common.biz.pojo.omm.InspectionRecord;

public class InspectionRecordDto extends InspectionRecord{
	
	private static final long serialVersionUID = 1L;

    private DefectDto defect;

    private Boolean hasDefect;
    
    private AreaDto area;
    
    private StoreDto store;

    public DefectDto getDefect() {
        return defect;
    }

    public void setDefect(DefectDto defect) {
        this.defect = defect;
    }

    public Boolean getHasDefect() {
        return this.defect != null;
    }

    public void setHasDefect(Boolean hasDefect) {
        this.hasDefect = hasDefect;
    }


	public AreaDto getArea() {
		return area;
	}

	public void setArea(AreaDto area) {
		this.area = area;
	}

	public StoreDto getStore() {
		return store;
	}

	public void setStore(StoreDto store) {
		this.store = store;
	}

	@Override
	public String toString() {
		return "InspectionRecordDto [defect=" + defect + 
				", hasDefect=" + hasDefect + ", area=" + 
				area + ", store="
				+ store + "]" + super.toString();
	}

	
}
