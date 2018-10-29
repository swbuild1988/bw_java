package com.bandweaver.tunnel.controller.common;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	
	private static final String FILEPATH = "path.file.upload";
	
	
	@Autowired
	private FileInfoService fileInfoService;
	
	
	
	/**文件上传 (最大支持30M)
	 * @param files 支持多个文件上传
	 * @param fileType 文件类型 枚举
	 * @param docType 资料类型 枚举
	 * @return   
	 * @author shaosen
	 * @throws Exception 
	 * @Date 2018年8月27日
	 */
	@WriteLog(DescEnum.FILE_UPLOAD)
	@RequestMapping(value="files/upload",method=RequestMethod.POST)
	 /*如果只是上传一个文件，则只需要MultipartFile类型接收文件即可，而且无需显式指定@RequestParam注解   
	  如果想上传多个文件，那么这里就要用MultipartFile[]类型来接收文件，并且还要指定@RequestParam注解 
	  并且上传多个文件时，前台表单中的所有<input type="file"/>的name都应该是file，否则参数里的file无法获取到所有上传的文件  */
	public JSONObject upload(@RequestParam("file")MultipartFile[] files,HttpServletRequest request) throws Exception {
		
		Integer fileType = DataTypeUtil.toInteger(request.getParameter("fileType"));
		Integer docType = DataTypeUtil.toInteger(request.getParameter("docType"));
		if(fileType == null || docType == null) {throw new Exception("文件类型和资料类型不能为空！");}
		
		String fileTypeName = FileTypeEnum.getEnum(fileType).getName();
		String docTypeName = DocTypeEnum.getEnum(docType).getName();
		
		//判断存放文件的文件夹是否存在
		String diskPath = DataTypeUtil.toString(PropertiesUtil.getValue(FILEPATH));
		if(diskPath == null || diskPath.trim().length()==0) {
			throw new Exception("文件保存路径未设定！");
		}
		
		String uploadPath = diskPath + "\\" + docTypeName + "\\" + fileTypeName;
		fileInfoService.upload(uploadPath, files, fileType, docType, true);
		
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}
	
	
	/**文件信息分页查询 
	 * @param name 文件名称（支持模糊查询）
	 * @param fileType 文件类型 枚举
	 * @param docType 资料类型 枚举
	 * @param startTime 
	 * @param endTime
	 * @param pageNum 必须
	 * @param pageSize 必须
	 * @return   {"msg":"请求成功","code":"200","data":{"total":28,"list":[{"id":75,"name":"Lighthouse-20180829125649","fileTypeName":"图片","docTypeName":"管廊本体资料","crtTime":1535518609000},{"id":76,"name":"Penguins-20180829125649","fileTypeName":"图片","docTypeName":"管廊本体资料","crtTime":1535518609000},{"id":73,"name":"Lighthouse-20180829110827","fileTypeName":"图片","docTypeName":"管廊本体资料","crtTime":1535512107000},{"id":74,"name":"Penguins-20180829110827","fileTypeName":"图片","docTypeName":"管廊本体资料","crtTime":1535512107000},{"id":71,"name":"Lighthouse-20180829110757","fileTypeName":"图片","docTypeName":"管廊本体资料","crtTime":1535512077000}],"pageNum":1,"pageSize":5,"size":5,"startRow":1,"endRow":5,"pages":6,"prePage":0,"nextPage":2,"isFirstPage":true,"isLastPage":false,"hasPreviousPage":false,"hasNextPage":true,"navigatePages":8,"navigatepageNums":[1,2,3,4,5,6],"navigateFirstPage":1,"navigateLastPage":6,"firstPage":1,"lastPage":6}}
	 * @author shaosen
	 * @Date 2018年8月28日
	 */
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
	@WriteLog(DescEnum.FILE_DOWNLOAD)
	@RequestMapping(value="files/download/{id}",method=RequestMethod.GET)
	public JSONObject download(@PathVariable("id")Integer id,HttpServletResponse response,HttpServletRequest request) throws Exception {
		LogUtil.info("文件下载 ");
		FileInfo fileInfo = fileInfoService.getById(id);
		if(fileInfo == null) {
			throw new Exception("资源不存在！");
		}
		String diskPath = DataTypeUtil.toString(PropertiesUtil.getValue(FILEPATH));
		String realPath = diskPath + fileInfo.getPath();
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
	@WriteLog(DescEnum.FILE_DELETE)
	@RequestMapping(value="files/{ids}",method=RequestMethod.DELETE)
	public JSONObject deleteFile(@PathVariable("ids")String ids,HttpServletRequest request) throws Exception {
		String[] arr = ids.split(",");
		String diskPath = DataTypeUtil.toString(PropertiesUtil.getValue(FILEPATH));
		fileInfoService.checkPath(diskPath, false);
		for (String str : arr) {
			Integer id = DataTypeUtil.toInteger(str);
			fileInfoService.deleteFile(id, diskPath);
		}
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}
	
	

}
