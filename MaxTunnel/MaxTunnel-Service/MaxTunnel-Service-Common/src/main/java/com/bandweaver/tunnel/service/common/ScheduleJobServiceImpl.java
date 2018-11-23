package com.bandweaver.tunnel.service.common;

import java.util.Collections;
import java.util.List;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import com.bandweaver.tunnel.common.biz.constant.JobStatusEnum;
import com.bandweaver.tunnel.common.biz.dto.ScheduleJobDto;
import com.bandweaver.tunnel.common.biz.itf.ScheduleJobService;
import com.bandweaver.tunnel.common.biz.pojo.ScheduleJob;
import com.bandweaver.tunnel.common.biz.vo.ScheduleJobVo;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.dao.common.ScheduleJobMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class ScheduleJobServiceImpl implements ScheduleJobService {

	@Autowired
	private SchedulerFactoryBean schedulerFactoryBean;
	@Autowired
	private ScheduleJobMapper scheduleJobMapper;

	@SuppressWarnings("unchecked")
	@Override
	public void addJob(ScheduleJob job,boolean isSaveToDb) throws SchedulerException, ClassNotFoundException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
		// 获取trigger，即在spring配置文件中定义的 bean id="myTrigger"
		CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

		// 不存在，创建一个
		if (null == trigger) {
			Class<? extends Job> clazz = (Class<? extends Job>) Class.forName("com.bandweaver.tunnel.controller.quartz.QuartzJobFactory");
			JobDetail jobDetail = JobBuilder.newJob(clazz)
					.withIdentity(job.getJobName(), job.getJobGroup()).build();
			jobDetail.getJobDataMap().put("scheduleJob", job);
			// 表达式调度构建器
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());

			// 按新的cronExpression表达式构建一个新的trigger
			trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName(), job.getJobGroup())
					.withSchedule(scheduleBuilder).build();

			scheduler.scheduleJob(jobDetail, trigger);
			LogUtil.info("Add job  : " + job);

		} else {
			// Trigger已存在，那么更新相应的定时设置
			// 表达式调度构建器
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());

			// 按新的cronExpression表达式重新构建trigger
			trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

			// 按新的trigger重新设置job执行
			scheduler.rescheduleJob(triggerKey, trigger);
			LogUtil.info("RescheduleJob job  : " + job);
		}
		
		if(isSaveToDb) { scheduleJobMapper.insertSelective(job); }
		
	}


	@Override
	public PageInfo<ScheduleJobDto> dataGrid(ScheduleJobVo vo) {
		PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
		List<ScheduleJobDto> list = scheduleJobMapper.getDtoListByCondition(vo);
		return new PageInfo<>(list == null ? Collections.emptyList() : list );
	}

	
	
	@Override
	public void init() throws SchedulerException, ClassNotFoundException {
		List<ScheduleJob> list = scheduleJobMapper.getByJobStatus(JobStatusEnum.ENABLED.getValue());
		for (ScheduleJob scheduleJob : list) {
			addJob(scheduleJob,false);
		}
	}

	
	
	@Override
	public ScheduleJob getById(Integer id) {
		return scheduleJobMapper.selectByPrimaryKey(id);
	}
	
	

	@Override
	public void update(ScheduleJob scheduleJob) throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		TriggerKey triggerKey = TriggerKey.triggerKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression());
		trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
		scheduler.rescheduleJob(triggerKey, trigger);
		LogUtil.info("更新调度时间为： " + scheduleJob.getCronExpression());
		LogUtil.info("更新任务类为： " + scheduleJob.getJobClass());
		LogUtil.info("更新任务方法为： " + scheduleJob.getJobMethod());
		//update db
		scheduleJobMapper.updateByPrimaryKeySelective(scheduleJob);
	}

	@Override
	public void updateJobStatus(Integer id, Integer jobstatus) throws SchedulerException, ClassNotFoundException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		
		ScheduleJob scheduleJob = scheduleJobMapper.selectByPrimaryKey(id);
		if(scheduleJob == null) {
			LogUtil.info("没有找到id=[" + id + "]的job");
			return;
		}
			
		scheduleJob.setJobStatus(jobstatus);
		
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		
		if(jobstatus == JobStatusEnum.ENABLED.getValue()) {
			addJob(scheduleJob, false);
			LogUtil.info("启用Job[" + scheduleJob + "]成功！");
		}else if(jobstatus == JobStatusEnum.DISABLED.getValue()) {
			scheduler.deleteJob(jobKey);
			LogUtil.info("禁用Job[" + scheduleJob + "]成功！");
		}
		
		scheduleJobMapper.updateByPrimaryKeySelective(scheduleJob);
	}


	@Override
	public ScheduleJob getByJobNameAndJobGroup(String jobName) {
		return scheduleJobMapper.getByJobNameAndJobGroup(jobName);
	}


	@Override
	public void delete(Integer id) throws SchedulerException {
		ScheduleJob scheduleJob = scheduleJobMapper.selectByPrimaryKey(id);
		
		if(scheduleJob != null ) {
			Scheduler scheduler = schedulerFactoryBean.getScheduler();
			JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
			scheduler.deleteJob(jobKey);
			scheduleJobMapper.deleteByPrimaryKey(id);
		}
		
	}

}
