package com.bandweaver.tunnel.common.biz.itf;

import com.bandweaver.tunnel.common.biz.constant.ProcessTypeEnum;
import com.bandweaver.tunnel.common.biz.pojo.ProcessBase;

public interface ProcessBaseService {
    ProcessBase getProcessBaseByTypeAndProcessInstance(ProcessTypeEnum typeEnum, String processInstanceId);

    Object getProcessByTypeAndProcessInstance(ProcessTypeEnum typeEnum, String processInstanceId);

    void updateProcessStatus(ProcessTypeEnum typeEnum, String processInstanceId, String processStatus);

    void finishProcess(ProcessTypeEnum typeEnum, String processIntanceId);
}
