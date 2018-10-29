package com.bandweaver.tunnel.common.biz.dto;

public class H5StreamHttpResponseDto {
    private boolean bStatus;
    private String strSession;
    private Integer nTimeout;
    private String strCode;

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

    @Override
    public String toString() {
        return "H5StreamHttpResponseDto{" +
                "bStatus=" + bStatus +
                ", strSession='" + strSession + '\'' +
                ", nTimeout=" + nTimeout +
                ", strCode='" + strCode + '\'' +
                '}';
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
