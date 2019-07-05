package com.bandweaver.tunnel.controller.omm;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.itf.FileInfoService;
import com.bandweaver.tunnel.common.biz.itf.omm.MultiFileService;
import com.bandweaver.tunnel.common.biz.pojo.omm.MultiFile;
import com.bandweaver.tunnel.common.platform.constant.StatusCodeEnum;
import com.bandweaver.tunnel.common.platform.util.CommonUtil;
import com.bandweaver.tunnel.common.platform.util.PropertiesUtil;
import com.bandweaver.tunnel.common.platform.util.UploadFile;

@Controller
@ResponseBody
public class MultiFileController {
	
	@Autowired
    private FileInfoService fileInfoService;
	@Autowired
	private MultiFileService multiFileService;
	
	private static final String ATTACHMENT_PATH = "file.path.multi";
	
	/**
	 * 
	 * @param file 多文件
	 * @param id 
	 * @return
	 * @throws Exception
	 * @author ya.liu
	 * @Date 2019年6月18日
	 */
	/*如果只是上传一个文件，则只需要MultipartFile类型接收文件即可，而且无需显式指定@RequestParam注解   
	  如果想上传多个文件，那么这里就要用MultipartFile[]类型来接收文件，并且还要指定@RequestParam注解 
	  并且上传多个文件时，前台表单中的所有<input type="file"/>的name都应该是file，否则参数里的file无法获取到所有上传的文件  */
	@RequestMapping(value = "multifiles/{id}/files", method = {RequestMethod.POST, RequestMethod.GET})
	public JSONObject uploadFile(@RequestParam("file") MultipartFile [] file, @PathVariable("id") Integer id) throws Exception {
	    // 获取保存路径
	    String attachmentPath = PropertiesUtil.getString(ATTACHMENT_PATH);
	    fileInfoService.checkPath(attachmentPath, true);
	    
	    String path = attachmentPath + File.separator;
	    List<String> strs = UploadFile.uploadFiles(Arrays.asList(file), path);
	    
	    if(strs != null && strs.size() > 0) {
	    if(strs.get(0).equals("file_empty") || strs.get(0).equals("wrong_file_extension"))
	  	  return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, strs.get(0));
	    }
	    // 执行数据库操作
	    MultiFile multiFile = new MultiFile();
	    multiFile.setImgUrl(strs.get(0));
	    multiFile.setRelationId(id);
	    // 设置巡检任务的类型为1
	    multiFile.setType(1);
	    multiFile.setCrtTime(new Date());
	    multiFileService.add(multiFile);
	    
	    JSONObject json = new JSONObject();
	    json.put("name", strs.get(0));
	    json.put("url", "/multifiles/" + multiFile.getId() + "/img");
	    return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, json);
	}
	
	/**
	 * 通过图片id获取图片
	 * @param id
	 * @param response
	 * @return
	 * @throws IOException
	 * @author ya.liu
	 * @Date 2019年6月18日
	 */
	@RequestMapping(value = "multifiles/{id}/img", method = RequestMethod.GET)
	public JSONObject getStream(@PathVariable("id") Integer id, HttpServletResponse response) throws IOException {
		FileInputStream is = null;
        ByteArrayOutputStream bos = null;
        ServletOutputStream outputStream = null;

        try {
        	MultiFile file = multiFileService.getMultiFileById(id);
            if (file == null) {
                throw new RuntimeException("没有获取到id=【" + id + "】的图片信息");
            }
            String imgUrl = file.getImgUrl();
            String realPath = PropertiesUtil.getString(ATTACHMENT_PATH) + File.separator + imgUrl;
            is = new FileInputStream(realPath);

            byte[] buffer = new byte[1024];
            int len = 0;
            bos = new ByteArrayOutputStream();
            while ((len = is.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }
            bos.flush();

            outputStream = response.getOutputStream();
            outputStream.write(bos.toByteArray());

        } catch (RuntimeException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                is.close();
            }
            if (bos != null) {
                bos.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        }
        return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}
	
	/**
	 * 通过taskId获取多图信息
	 * @param id 任务id
	 * @return
	 * @author ya.liu
	 * @Date 2019年6月18日
	 */
	@RequestMapping(value = "inspection-tasks/{id}/multifiles", method = RequestMethod.GET)
	public JSONObject getMultiFiles(@PathVariable("id") Integer id) {
		// 默认巡检任务的类型为1
		List<MultiFile> list = multiFileService.selectByCondition(null, id, 1);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, list);
	}
    
}
