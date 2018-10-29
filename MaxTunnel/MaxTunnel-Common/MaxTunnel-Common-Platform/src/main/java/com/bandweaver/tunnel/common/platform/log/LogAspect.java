package com.bandweaver.tunnel.common.platform.log;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

import javax.management.RuntimeErrorException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.xml.sax.SAXException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.dto.UserDTO;
import com.bandweaver.tunnel.common.biz.itf.OperationLogService;
import com.bandweaver.tunnel.common.biz.itf.SecurityLogService;
import com.bandweaver.tunnel.common.biz.itf.UserService;
import com.bandweaver.tunnel.common.biz.pojo.OperationLog;
import com.bandweaver.tunnel.common.biz.pojo.SecurityLog;
import com.bandweaver.tunnel.common.biz.pojo.User;
import com.bandweaver.tunnel.common.platform.constant.Constants;
import com.bandweaver.tunnel.common.platform.constant.StatusCodeEnum;
import com.bandweaver.tunnel.common.platform.util.ContextUtil;

import sun.util.logging.resources.logging;

/**
 * ClassName: LogAspect
 * 
 * @Description: 日志切面
 * @author shaosen
 * @date 2018年5月16日
 */
@Aspect
@Component
public class LogAspect {

	
	private static final String OPTLOGID = "optLogId";
	@Autowired
	private OperationLogService operationLogService;
	@Autowired
	private SecurityLogService securityLogService;
	@Autowired
	private UserService userService;

	/**
	 * @Description: 配置切入点
	 * @param
	 * @return void
	 * @throws 
	 * @author shaosen
	 * @date 2018年5月16日
	 */
	@Pointcut("execution (public * com.bandweaver.tunnel.controller..*.*(..))")
	public void controllerAspect() {
	}

	/**
	 * @Description: 后置通知
	 * @param joinPoint
	 * @return void
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException                           
	 * @author shaosen
	 * @date 2018年5月16日
	 */
	@After("controllerAspect()")
	public void doAfter(JoinPoint joinPoint) throws ParserConfigurationException, SAXException, IOException {

		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();

		WriteLog annotation = checkAnnotation(joinPoint);
		if (annotation == null)
			return;
		
		//模块
		JSONObject jsonObject = LogHandler.parseXML(annotation.value().getId());	// operationID 模块id
		String cname = (String) jsonObject.get("cname");	//模块
//		String oname = (String) jsonObject.get("oname");
		String desc = (String) jsonObject.get("desc");	//带有{}的描述
		
		// ip
		String reqIP = request.getRemoteAddr();
		
		// 请求人
		String reqUser = null;
		if (SecurityUtils.getSubject().getSession().getAttribute(Constants.SESSION_USER_INFO) != null) {
			User user = (User) SecurityUtils.getSubject().getSession().getAttribute(Constants.SESSION_USER_INFO);
			reqUser = user.getName();
		}
		
		// 请求人id
		int reqID = 0;
		if (reqUser != null && reqUser.trim().length() > 0) {
			User user = userService.getByUserName(reqUser);
			reqID = user.getId();
		}

		// 类方法
		String method = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
		
		// 参数
		Object[] arguments = joinPoint.getArgs();
		String params = "";
		for (int i = 0; i < arguments.length; i++) {
			
			//防止密码暴露
			if(arguments[i] instanceof User) {
				User user = (User) arguments[i];
				params = user.getName();
				break;
			}
			params = params + arguments[i] + (i == arguments.length - 1 ? "" : " , ");
		}
		
		
		OperationLog optLog = new OperationLog();
		optLog.setId(UUID.randomUUID().toString().replaceAll("-", ""));
		optLog.setModuleType(cname);
		optLog.setReqIp(reqIP);
		optLog.setReqId(reqID);
		optLog.setReqUser(reqUser);
		optLog.setMethod(method);
		optLog.setParams(params);
		optLog.setCrtTime(new Date());
		optLog.setDesc(desc);
		SaveOptLog(annotation, optLog);

	}


	/**
	 * @Description: 保存操作日志
	 * @param  annotation
	 * @param  optLog   
	 * @return void  
	 * @author shaosen
	 * @date 2018年5月28日
	 */
	private void SaveOptLog(WriteLog annotation, OperationLog optLog) {
		String desc;
		desc = LogHandler.getDesc(annotation.value().getId(),optLog);//对desc中的{0},{1}进行填充
		optLog.setDesc(desc);
		
		LogUtil.info("------------------操作日志--------------------------");
		LogUtil.info("日志 : " + optLog.getDesc());
		LogUtil.info("------------------操作日志--------------------------");

		operationLogService.save(optLog);
		
		//保存这条日志的id，记录日志操作结果
		ContextUtil.getSession().setAttribute(OPTLOGID, optLog.getId());
	}

	
	/**
	 * @Description: 获取注解
	 * @param  joinPoint
	 * @return IgnoreLog
	 * @author shaosen
	 * @date 2018年5月17日
	 */
	private WriteLog checkAnnotation(JoinPoint joinPoint) {

		Signature signature = joinPoint.getSignature();
		MethodSignature methodSignature = (MethodSignature) signature;
		Method method = methodSignature.getMethod();

		if (method != null) {
			return method.getAnnotation(WriteLog.class);
		}
		return null;
	}

	/**
	 * @Description: 使用这个注解可以得到执行方法之后的返回信息
	 * @param  object
	 * @return void
	 * @author shaosen
	 * @date 2018年5月16日
	 */
	@AfterReturning(returning = "object", pointcut = "controllerAspect()")
	public void doAfterReturning(Object object) {
//		LogUtil.info("操作成功 !");
		ContextUtil.getSession().removeAttribute(OPTLOGID);
	}
	
	
    /**在目标方法出现异常时执行的代码,可以访问到异常对象，且可以指定在出现特定异常时在执行通知代码 
     * @param joinPoint
     * @param ex   
     * @author shaosen
     * @Date 2018年9月25日
     */
    @AfterThrowing( value = "controllerAspect()" ,throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint,Exception ex) {
    	HttpSession session = ContextUtil.getSession();
		try {
			LogUtil.debug("操作失败：" + ex );
			String optLogId = (String) session.getAttribute(OPTLOGID);
			if(optLogId != null) 
				operationLogService.updateById("失败", optLogId);
		} catch (Exception e) {
			LogUtil.info("更新失败：" + e.toString());
		}finally {
			session.removeAttribute(OPTLOGID);
			LogUtil.debug("session logID removed !");
		}
    	
    }

}
