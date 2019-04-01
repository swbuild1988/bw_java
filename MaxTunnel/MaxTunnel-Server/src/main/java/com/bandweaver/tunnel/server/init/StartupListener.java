package com.bandweaver.tunnel.server.init;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.bandweaver.tunnel.common.platform.constant.Constants;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.DateUtil;
import com.bandweaver.tunnel.common.platform.util.PropertiesUtil;
import com.bandweaver.tunnel.service.common.module.COMModuleCenter;
import com.bandweaver.tunnel.service.em.EMModuleCenter;
import com.bandweaver.tunnel.service.mam.module.MAMModuleCenter;
import com.bandweaver.tunnel.service.oam.module.OAMModuleCenter;
import com.bandweaver.tunnel.service.omm.module.OMMModuleCenter;

@Component("StartupListener")
public class StartupListener implements ApplicationContextAware {
    @Autowired
    private MAMModuleCenter mamModuleCenter;
    @Autowired
    private COMModuleCenter comModuleCenter;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        long beginTime = System.currentTimeMillis();
        mamModuleCenter.start();
        comModuleCenter.start();
        long endTime = System.currentTimeMillis();
        
        String version = PropertiesUtil.getValue(Constants.MAXTUNNEL_VERSION);
        String company = PropertiesUtil.getString(Constants.MAXTUNNEL_COMPANY);
        String address = PropertiesUtil.getString(Constants.MAXTUNNEL_ADDRESS);

        LogUtil.info("----------------------------------------------------");
        LogUtil.info("描  述：初始化结束");
        LogUtil.info("项目名：太原市综合管廊管理平台");
        LogUtil.info("开发商：" + company);
        LogUtil.info("地  址：" + address);
        LogUtil.info("版本号：" + version );
        LogUtil.info("耗  时：" + (endTime - beginTime) +"ms");
        LogUtil.info("----------------------------------------------------");

        LogUtil.info("					_ooOoo_                   ");
        LogUtil.info("				   o8888888o                  ");
        LogUtil.info("				   88\" . \"88                  ");
        LogUtil.info("				   (| -_- |)                  ");
        LogUtil.info("				   O\\  =  /O                  ");
        LogUtil.info("				____/`---'\\____               ");
        LogUtil.info("			  .'  \\|     |//  `.             ");
        LogUtil.info("			 /  \\\\|||  :  |||//  \\            ");
        LogUtil.info("			/  _||||| -:- |||||-  \\           ");
        LogUtil.info("			|   | \\\\\\  -  /// |   |           ");
        LogUtil.info("			| \\_|  ''\\---/''  |   |           ");
        LogUtil.info("			\\  .-\\__  `-`  ___/-. /           ");
        LogUtil.info("		  ___`. .'  /--.--\\  `. . __          ");
        LogUtil.info("	   .\"\" '<  `.___\\_<|>_/___.'  >'\"\".       ");
        LogUtil.info("	  | | :  `- \\`.;`\\ _ /`;.`/ - ` : | |     ");
        LogUtil.info("	  \\  \\ `-.   \\_ __\\ /__ _/   .-` /  /     ");
        LogUtil.info(" ======`-.____`-.___\\_____/___.-`____.-'======");
        LogUtil.info("                    `=---='                   ");
        LogUtil.info(" ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
        LogUtil.info("			佛祖保佑		永无BUG                 ");




    }
}
