package com.bandweaver.tunnel.common.biz.itf;

import java.util.List;

import org.quartz.SchedulerException;

import com.bandweaver.tunnel.common.biz.dto.ScheduleJobDto;
import com.bandweaver.tunnel.common.biz.pojo.ScheduleJob;
import com.bandweaver.tunnel.common.biz.vo.ScheduleJobVo;
import com.github.pagehelper.PageInfo;

public interface ScheduleJobService {
	
	/**添加Job 
	 * @param job
	 * @param isSaveToDb 是否保存到数据库
	 * @throws SchedulerException   
	 * @author shaosen
	 * @throws ClassNotFoundException 
	 * @Date 2018年9月7日
	 */
	public void addJob(ScheduleJob job,boolean isSaveToDb) throws SchedulerException, ClassNotFoundException;

	/**Job分页查询 
	 * @param vo
	 * @return   
	 * @author shaosen
	 * @Date 2018年9月7日
	 */
	public PageInfo<ScheduleJobDto> dataGrid(ScheduleJobVo vo);

	/**服务器启动时查询job状态为启用并执行  
	 * @throws SchedulerException   
	 * @author shaosen
	 * @throws ClassNotFoundException 
	 * @Date 2018年9月7日
	 */
	public void init() throws SchedulerException, ClassNotFoundException;

	public ScheduleJob getById(Integer id);

	/**更新cronExpression 
	 * @param job
	 * @throws SchedulerException   
	 * @author shaosen
	 * @Date 2018年9月7日
	 */
	public void update(ScheduleJob job) throws SchedulerException;

	/**更新Job状态 
	 * @param id jobId
	 * @param jobstatus 0:禁用；1：启用
	 * @throws SchedulerException   
	 * @author shaosen
	 * @throws ClassNotFoundException 
	 * @Date 2018年9月7日
	 */
	public void updateJobStatus(Integer id, Integer jobstatus) throws SchedulerException, ClassNotFoundException;

	public ScheduleJob getByJobNameAndJobGroup(String jobName);

	public void delete(Integer id) throws SchedulerException;
}
