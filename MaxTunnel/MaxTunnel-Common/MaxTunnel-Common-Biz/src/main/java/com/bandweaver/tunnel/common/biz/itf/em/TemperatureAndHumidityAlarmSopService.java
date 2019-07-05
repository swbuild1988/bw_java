package com.bandweaver.tunnel.common.biz.itf.em;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface TemperatureAndHumidityAlarmSopService {
    void start();

    void getPng(String processInstance, HttpServletResponse response) ;
}
