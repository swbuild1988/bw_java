package com.bandweaver.tunnel.controller.common;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.util.TypeUtils;
import com.bandweaver.tunnel.common.platform.exception.BandWeaverException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.constant.DocTypeEnum;
import com.bandweaver.tunnel.common.biz.constant.FileTypeEnum;
import com.bandweaver.tunnel.common.biz.dto.FileInfoDto;
import com.bandweaver.tunnel.common.biz.itf.FileInfoService;
import com.bandweaver.tunnel.common.biz.pojo.FileInfo;
import com.bandweaver.tunnel.common.biz.vo.FileInfoVo;
import com.bandweaver.tunnel.common.platform.constant.Constants;
import com.bandweaver.tunnel.common.platform.constant.StatusCodeEnum;
import com.bandweaver.tunnel.common.platform.log.DescEnum;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.log.WriteLog;
import com.bandweaver.tunnel.common.platform.util.CommonUtil;
import com.bandweaver.tunnel.common.platform.util.DataTypeUtil;
import com.bandweaver.tunnel.common.platform.util.PropertiesUtil;
import com.github.pagehelper.PageInfo;


/**文件信息管理
 * @author shaosen
 * @date 2018年8月27日
 */
@Controller
@ResponseBody
public class FileInfoController {

	@Autowired
	private FileInfoService fileInfoService;



	/**文件上传 (最大支持30M,可在配置文件spring-controller.xml中修改上限值)
	 * 	如果只是上传一个文件，则只需要MultipartFile类型接收文件即可，而且无需显式指定@RequestParam注解
	 *	如果想上传多个文件，那么这里就要用MultipartFile[]类型来接收文件，并且还要指定@RequestParam注解
	 *	并且上传多个文件时，前台表单中的所有<input type="file"/>的name都应该是file，否则参数里的file无法获取到所有上传的文件
	 * @param files 支持多个文件上传
	 * 	fileType 文件类型 枚举
	 * 	docType 资料类型 枚举
	 * @return
	 * @author shaosen
	 * @throws Exception
	 * @Date 2018年8月27日
	 */
	@RequiresPermissions("file:upload")
	@WriteLog(DescEnum.FILE_UPLOAD)
	@RequestMapping(value="files/upload",method=RequestMethod.POST)
	public JSONObject upload(@RequestParam("file")MultipartFile[] files,HttpServletRequest request) throws Exception {

		Integer fileType = TypeUtils.castToInt(request.getParameter("fileType"));
		Integer docType = TypeUtils.castToInt(request.getParameter("docType"));
		Integer docTypeSon = TypeUtils.castToInt(request.getParameter("docTypeSon"));

		if (fileType == null) {
			throw new BandWeaverException("fileType must not be null");
		}
		if (docType == null) {
			throw new BandWeaverException("docType must not be null");
		}

		if (docType != null && DocTypeEnum.getEnum(docType) == DocTypeEnum.PROJECT_FILE && docTypeSon == null) {
			throw new BandWeaverException("when docType = 1 , docTypeSon must not be null");
		}

		// 判断存放文件的文件夹是否存在
		String diskPath = PropertiesUtil.getValue(Constants.FILE_PATH);
		if(diskPath == null || diskPath.trim().length()==0) {
			throw new Exception("文件保存路径未设定！");
		}

		String uploadPath = diskPath;
		fileInfoService.upload(uploadPath, files, fileType, docType, docTypeSon, true);

		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}


	/**文件信息分页查询
	 * @param vo
	 * 	name 文件名称（支持模糊查询）
	 *  fileType 文件类型 枚举
	 *  docType 资料类型 枚举
	 *  docTypeSon
	 *  startTime
	 *  endTime
	 *  pageNum 必须
	 *  pageSize 必须
	 * @return
	 * @author shaosen
	 * @Date 2018年8月28日
	 */
	@RequiresPermissions("file:list")
	@RequestMapping(value="files/datagrid",method=RequestMethod.POST)
	public JSONObject dataGrid(@RequestBody FileInfoVo vo) {
		PageInfo<FileInfoDto> pageInfo = fileInfoService.dataGrid(vo);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,pageInfo);
	}


	/**文件下载
	 * @param id 文件id
	 * @return
	 * @author shaosen
	 * @throws Exception
	 * @Date 2018年8月28日
	 */
	@RequiresPermissions("file:download")
	@WriteLog(DescEnum.FILE_DOWNLOAD)
	@RequestMapping(value="files/download/{id}",method=RequestMethod.GET)
	public JSONObject download(@PathVariable("id")Integer id,HttpServletResponse response,HttpServletRequest request) throws Exception {
		LogUtil.info("文件下载 ");
		FileInfo fileInfo = fileInfoService.getById(id);
		if(fileInfo == null) {
			throw new BandWeaverException("资源不存在！");
		}
		String diskPath = PropertiesUtil.getValue(Constants.FILE_PATH);
		String realPath = diskPath + File.separator + fileInfo.getPath();
		fileInfoService.download(response, realPath);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}


	/**文件删除
	 * @param ids 多个文件id拼接的字符串（1,2,3....）
	 * @return
	 * @author shaosen
	 * @throws Exception
	 * @Date 2018年8月29日
	 */
	@RequiresPermissions("file:delete")
	@WriteLog(DescEnum.FILE_DELETE)
	@RequestMapping(value="files/{ids}",method=RequestMethod.DELETE)
	public JSONObject deleteFile(@PathVariable("ids")String ids,HttpServletRequest request) throws Exception {
		List<Integer> arr = CommonUtil.convertStringToList(ids);
		String diskPath = DataTypeUtil.toString(PropertiesUtil.getValue(Constants.FILE_PATH));
		fileInfoService.checkPath(diskPath, false);
		for (Integer id : arr) {
			fileInfoService.deleteFile(id, diskPath);
		}
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}



}
