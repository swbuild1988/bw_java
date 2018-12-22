package com.bandweaver.tunnel.controller.quartz;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.bandweaver.tunnel.common.biz.pojo.ScheduleJob;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.SpringContextHolder;
import com.bandweaver.tunnel.service.mam.measobj.MeasObjModuleCenter;
/**DisallowConcurrentExecution注解表示禁用并发执行
 * @author shaosen
 * @date 2018年9月10日
 */
@DisallowConcurrentExecution
public class QuartzJobFactory implements Job {
 
	
    /* 定时任务入口
     * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
   
    ScheduleJob scheduleJob = (ScheduleJob)context.getMergedJobDataMap().get("scheduleJob");
    // LogUtil.info("Job:[" +scheduleJob.getJobName() + "."+ scheduleJob.getJobGroup() + "]正在执行...");
    
    //利用反射技术获取指定job方法并执行
    String jobClass = scheduleJob.getJobClass();
    String jobMethod = scheduleJob.getJobMethod();
    String tmp = jobClass.substring(jobClass.lastIndexOf(".") + 1, jobClass.length());
    // LogUtil.info("before : " + tmp);
    String firstChar = tmp.substring(0, 1);
    String otherStr = tmp.substring(1,tmp.length());
    String bean = firstChar.toLowerCase() + otherStr;
    // LogUtil.info("after : " + bean);
		
	try {
		Class<?> clazz = Class.forName(jobClass);
		Method method = clazz.getMethod(jobMethod);
		// LogUtil.info("Get method ： " + method);
		method.setAccessible(true);
		method.invoke(SpringContextHolder.getBean(bean));
	} catch (ClassNotFoundException e) {
		e.printStackTrace();
	} catch (NoSuchMethodException e) {
		e.printStackTrace();
	} catch (SecurityException e) {
		e.printStackTrace();
	} catch (IllegalAccessException e) {
		e.printStackTrace();
	} catch (IllegalArgumentException e) {
		e.printStackTrace();
	} catch (InvocationTargetException e) {
		e.printStackTrace();
	}
    
    
    
  }
}