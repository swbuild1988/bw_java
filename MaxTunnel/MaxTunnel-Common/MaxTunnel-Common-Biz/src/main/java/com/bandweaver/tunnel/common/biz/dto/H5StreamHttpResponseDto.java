package com.bandweaver.tunnel.common.biz.dto;

import java.util.List;

import javax.print.attribute.standard.PresentationDirection;

public class H5StreamHttpResponseDto {
    private boolean bStatus;
    private String strSession;
    private Integer nTimeout;
    private String strCode;
    private List<HttpResponsePresetDto> preset;

    public boolean isbStatus() {
        return bStatus;
    }

    public void setbStatus(boolean bStatus) {
        this.bStatus = bStatus;
    }

    public String getStrSession() {
        return strSession;
    }

    public void setStrSession(String strSession) {
        this.strSession = strSession;
    }

    public Integer getnTimeout() {
        return nTimeout;
    }


	public List<HttpResponsePresetDto> getPreset() {
		return preset;
	}

	public void setPreset(List<HttpResponsePresetDto> preset) {
		this.preset = preset;
	}

	@Override
	public String toString() {
		return "H5StreamHttpResponseDto [bStatus=" + bStatus + ", strSession=" + strSession + ", nTimeout=" + nTimeout
				+ ", strCode=" + strCode + ", preset=" + preset + "]";
	}

    public void setnTimeout(Integer nTimeout) {
        this.nTimeout = nTimeout;
    }

    public String getStrCode() {
        return strCode;
    }

    public void setStrCode(String strCode) {
        this.strCode = strCode;
    }
}
