package com.bandweaver.tunnel.service.common;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bandweaver.tunnel.common.biz.constant.DocTypeEnum;
import com.bandweaver.tunnel.common.biz.constant.FileTypeEnum;
import com.bandweaver.tunnel.common.biz.dto.FileInfoDto;
import com.bandweaver.tunnel.common.biz.itf.FileInfoService;
import com.bandweaver.tunnel.common.biz.pojo.FileInfo;
import com.bandweaver.tunnel.common.biz.vo.FileInfoVo;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.FileUtil;
import com.bandweaver.tunnel.common.platform.util.PropertiesUtil;
import com.bandweaver.tunnel.dao.common.FileInfoMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
@Service
public class FileInfoServiceImpl implements FileInfoService {
	@Autowired
	private FileInfoMapper fileInfoMapper;

	@Override
	public boolean save(FileInfo fileInfo) {
		int i = fileInfoMapper.insert(fileInfo);
		return i > 0 ? true : false;
	}

	@Override
	public PageInfo<FileInfoDto> dataGrid(FileInfoVo vo) {
		PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
		List<FileInfoDto> list =getDtoListByCondition(vo);
		return new PageInfo<>(list);
	}

	@Override
	public List<FileInfoDto> getDtoListByCondition(FileInfoVo vo) {
		return fileInfoMapper.getDtoListByCondition(vo);
	}

	@Override
	public FileInfo getById(Integer id) {
		return fileInfoMapper.selectByPrimaryKey(id);
	}

	@Override
	public boolean deleteFileOnDB(Integer id) {
		int i = fileInfoMapper.deleteByPrimaryKey(id);
		return i>0 ? true : false;
	}

	
	@Override
	public void upload(String uploadPath,MultipartFile[] files,int fileType,int docType,boolean isSaveToDB) throws Exception {
		
		//检查文件路径是否存在
		checkPath(uploadPath,true);
		
		//上传多个文件
		for (MultipartFile file : files) {
			String originalFilename = file.getOriginalFilename();
			String path = uploadPath + "\\" + originalFilename;
			
			int index = originalFilename.lastIndexOf(".");
			String fileName = originalFilename.substring( 0 , index );
			String suffix = originalFilename.substring( index , originalFilename.length());
			
			//检验文件格式
			checkFileFormat(fileType, suffix);
			
			//判断相同路径下是否有同名文件，如果有则在后面加上时间戳
			if((new File(path)).exists()) {
				LogUtil.info("Get same fileName : " + originalFilename);
				fileName = fileName + new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
				path = uploadPath + "\\" + fileName + suffix;
				LogUtil.info("Change fileName to  : " + fileName + suffix);
			}
			file.transferTo(new File(path));
			LogUtil.info("Upload file[" + originalFilename + "] success !");
			
			if(isSaveToDB) {
				saveToDB(fileType, docType, fileName, suffix);
			}
		}
		
	}

	@Override
	public void checkPath(String path,boolean isCreate) throws Exception {
		if(!FileUtil.isExit(path)) {
			if(isCreate) {
				FileUtil.createPath(path);
				LogUtil.info("Create new path : " + path );
			}else {
				throw new Exception("资源路径[" + path + "]不存在！");
			}
			
		}
	}

	
	@Override
	public boolean saveToDB(int fileType, int docType, String fileName, String suffix) throws Exception {
		
		String fileTypeName = FileTypeEnum.getEnum(fileType).getName();
		String docTypeName = DocTypeEnum.getEnum(docType).getName();
		
		//检验文件格式
		checkFileFormat(fileType, suffix);
		
		//保存到数据库
		FileInfo fileInfo = new FileInfo();
		fileInfo.setDocType(docType);
		fileInfo.setFileType(fileType);
		fileInfo.setName(fileName + suffix);
		fileInfo.setPath("\\" + docTypeName + "\\" + fileTypeName + "\\" + fileName + suffix);
		fileInfo.setCrtTime(new Date());
		return this.save(fileInfo);
		
	}

	@Override
	public void checkFileFormat(int fileType, String suffix) throws Exception {
		String str = (String) PropertiesUtil.getValue(FileTypeEnum.getEnum(fileType).getType());
		String[] strArr = str.toLowerCase().split(",");
	
		if(!Arrays.asList(strArr).contains(suffix.toLowerCase())) {
			throw new Exception("文件格式 [" + suffix + "]不支持 !");
		}
	}

	@Override
	public void download(HttpServletResponse response, String realPath) throws Exception {
		
		//检验文件路径
		checkPath(realPath, false);
		
		InputStream is = new FileInputStream(new File(realPath));
		
		//控制文件下载速度
		byte[] buffer=new byte[2048];
		int len=0;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();//转化成字节输出流
		while((len=is.read(buffer))!=-1){
			bos.write(buffer,0,len);
		}
		bos.flush();
		
		OutputStream outputStream = response.getOutputStream();
		outputStream.write(bos.toByteArray());
		
		//Close IO
		is.close();
		bos.close();
		outputStream.close();
	}
	
	@Override
	public void deleteFile(Integer id,String diskPath) throws Exception {
		
		FileInfo fileInfo = getById(id);
		if(fileInfo == null) {throw new Exception("资源不存在！");}
		
		if(deleteFileOnDB(id)) {
			LogUtil.info("数据库文件 [" + fileInfo.getName() + "] 已删除！");
		}
		
		if(diskPath != null) {
			String path = fileInfo.getPath();
			File file = new File(diskPath+path);
			file.delete();
			LogUtil.info("硬盘文件 [" + fileInfo.getName() + "] 已删除！");
		}
		
	}
	
}
