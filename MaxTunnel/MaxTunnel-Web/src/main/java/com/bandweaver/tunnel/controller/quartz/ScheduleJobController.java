package com.bandweaver.tunnel.controller.quartz;

import java.util.List;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.constant.JobStatusEnum;
import com.bandweaver.tunnel.common.biz.dto.ScheduleJobDto;
import com.bandweaver.tunnel.common.biz.itf.ScheduleJobService;
import com.bandweaver.tunnel.common.biz.pojo.ScheduleJob;
import com.bandweaver.tunnel.common.biz.vo.ScheduleJobVo;
import com.bandweaver.tunnel.common.platform.constant.StatusCodeEnum;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.CommonUtil;
import com.bandweaver.tunnel.controller.common.BaseController;
import com.github.pagehelper.PageInfo;

/**定时任务调度管理
 * @author shaosen
 * @date 2018年9月6日
 */
@Controller
@ResponseBody
public class ScheduleJobController extends BaseController<ScheduleJob>{
	
	@Autowired
	private ScheduleJobService scheduleJobService;

	/**job分页查询 
	 * @param jobStatus 枚举，见接口文档
	 * @param pageNum 必须
	 * @param pageSize 必须
	 * @return   {"msg":"请求成功","code":"200","data":{"total":1,"list":[{"jobId":1,"jobName":"job1","jobGroup":"group1","jobStatusName":"启用","jobClass":"com.bandweaver.tunnel.service.mam.measobj.MeasObjModuleCenter","jobMethod":"saveDataScheduleBatch","cronExpression":"0 0/1 * * * ?","description":"监测对象数据定时保存"}],"pageNum":1,"pageSize":5,"size":1,"startRow":1,"endRow":1,"pages":1,"prePage":0,"nextPage":0,"isFirstPage":true,"isLastPage":true,"hasPreviousPage":false,"hasNextPage":false,"navigatePages":8,"navigatepageNums":[1],"navigateFirstPage":1,"navigateLastPage":1,"firstPage":1,"lastPage":1}}
	 * @author shaosen
	 * @Date 2018年9月7日
	 */
	@RequestMapping(value="schedulejobs/datagrid",method=RequestMethod.POST)
	public JSONObject dataGrid(@RequestBody ScheduleJobVo vo) {
		PageInfo<ScheduleJobDto> pageInfo = scheduleJobService.dataGrid(vo);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, pageInfo);
	}
	
	
	/**添加job
	 * @param jobName job名称
	 * @param jobClass  任务类 例：com.bandweaver.tunnel.service.mam.measobj.MeasObjModuleCenter
	 * @param jobMethod  任务方法 例：saveDataScheduleBatch
	 * @param cronExpression 调度表达式 0/5 * * * * ?
	 * @param description Job描述
	 * @throws SchedulerException
	 * @author shaosen
	 * @throws ClassNotFoundException 
	 * @Date 2018年9月6日
	 */
	@RequestMapping(value = "schedulejobs", method = RequestMethod.POST)
	public JSONObject add(@RequestBody ScheduleJob job) throws SchedulerException, ClassNotFoundException {
		job.setJobStatus(JobStatusEnum.ENABLED.getValue());
		job.setJobGroup("group1");//页面上只需用户填写jobName即可
		scheduleJobService.addJob(job,true);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);

	}
	
	/**ajax校验任务是否唯一 
	 * @param jobName
	 * @return  true or false
	 * @author shaosen
	 * @Date 2018年9月20日
	 */
	@RequestMapping(value="schedulejobs/ajax/{jobName}",method=RequestMethod.GET)
	public JSONObject ajaxCheck(@PathVariable("jobName") String jobName) {
		ScheduleJob job = scheduleJobService.getByJobNameAndJobGroup(jobName);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,job == null ? true : false );
	}
	
	
	
	/**更新Job调度时间/任务类/任务方法/任务描述
	 * @param jobId 不可更改
	 * @param jobName job名称，不可更改
	 * @param jobGroup  job组，不可更改
	 * @param jobClass  任务类  可更改
	 * @param jobMethod  任务方法  可更改
	 * @param cronExpression 调度表达式 可更改
	 * @param description Job描述 可更改
	 * @return
	 * @throws SchedulerException   
	 * @author shaosen
	 * @Date 2018年9月7日
	 */
	@RequestMapping(value="schedulejobs",method=RequestMethod.PUT)
	public JSONObject update(@RequestBody ScheduleJob job) throws SchedulerException {
		scheduleJobService.update(job);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}
	
	
	/**禁用/启用Job 
	 * @param id JobId
	 * @param jobStatus job状态 枚举，见接口
	 * @return   
	 * @author shaosen
	 * @throws SchedulerException 
	 * @throws ClassNotFoundException 
	 * @Date 2018年9月7日
	 */
	@RequestMapping(value="schedulejobs/{id}/jobstatus/{jobstatus}",method=RequestMethod.GET)
	public JSONObject updateJobStatus(@PathVariable("id")Integer id,@PathVariable("jobstatus")Integer jobstatus) throws SchedulerException, ClassNotFoundException {
		LogUtil.info("禁用/启用Job ");
		scheduleJobService.updateJobStatus(id,jobstatus);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}

	
	/**通过jobId获取Job 
	 * @param id jobId
	 * @return  {"msg":"请求成功","code":"200","data":{"jobId":1,"jobName":"job1","jobGroup":"group1","jobStatus":1,"jobClass":"com.bandweaver.tunnel.service.mam.measobj.MeasObjModuleCenter","jobMethod":"saveDataScheduleBatch","cronExpression":"0 0/1 * * * ?","description":"监测对象数据定时保存"}}
	 * @author shaosen
	 * @Date 2018年9月7日
	 */
	@RequestMapping(value="aa/{id}",method=RequestMethod.GET)
	public JSONObject getById(@PathVariable("id") Integer id) {
		ScheduleJob job = scheduleJobService.getById(id);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, job);
	}
	
	
	/**删除job 
	 * @param id jobId
	 * @return   
	 * @author shaosen
	 * @throws SchedulerException 
	 * @Date 2018年9月21日
	 */
	@RequestMapping(value="schedulejobs/{id}",method=RequestMethod.DELETE)
	public JSONObject delete(@PathVariable Integer id) throws SchedulerException {
		scheduleJobService.delete(id);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}

	/**
	 * 批量删除
	 */
	@Override
	@RequestMapping(value="schedulejobs/batch/{ids}",method=RequestMethod.DELETE)
	public JSONObject deleteBatch(@PathVariable String ids) throws SchedulerException {
		List<Integer> list = convertStringToList(ids);
		for (Integer id : list) {
			scheduleJobService.delete(id);
		}
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}

}
