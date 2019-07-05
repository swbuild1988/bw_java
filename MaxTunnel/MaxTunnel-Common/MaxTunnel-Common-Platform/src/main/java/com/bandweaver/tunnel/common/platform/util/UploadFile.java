package com.bandweaver.tunnel.common.platform.util;
 
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.bandweaver.tunnel.common.platform.log.LogUtil;

 
public class UploadFile {
	/**
     * 多张/单张都可以用这个
	 * 保存图片   
	 * 
	 * @param List<MultipartFile>
	 *            要批量上传的文件
	 * @param path
	 *            图片保存的路径
	 * @return "WRONG_FILE_EXTENSION"-错误的后缀, "FILE_EMPTY"-空文件 或者 保存后的绝对路径
	 * @throws Exception
	 */
	public static List<String> uploadFiles(List<MultipartFile> files, String path) throws Exception {
		List<String> msgs = new ArrayList<>();
		if (files.size() < 1) {
			msgs.add("file_empty");
			return msgs;
		}
		for (int i = 0; i < files.size(); ++i) {
			MultipartFile file = files.get(i);
			if (!file.isEmpty()) {
				String filename = file.getOriginalFilename();
				String type = filename.indexOf(".") != -1
						? filename.substring(filename.lastIndexOf("."), filename.length())
						: null;
				if (type == null) {
					msgs.add("file_empty");
					return msgs;
				}
 
				if (!(".PNG".equals(type.toUpperCase()) || ".JPG".equals(type.toUpperCase()))) {
					msgs.add("wrong_file_extension");
					return msgs;
				}
			}
		}
		for (int i = 0; i < files.size(); ++i) {
			MultipartFile file = files.get(i);
			String fileName = file.getOriginalFilename();
			// 考虑到并发的情况下可能发生文件名称重复，使用uuid表示文件名
			fileName = UUID.randomUUID() + fileName.substring(fileName.lastIndexOf("."), fileName.length());
			path = path + fileName;
			try {
			      file.transferTo(new File(path));
			      // 用fileName表示相对路径，读取文件时，加上配置的attachment路径即可
				msgs.add(fileName);
			} catch (Exception e) {
				// 没有上传成功
				e.printStackTrace();
				LogUtil.info("上传失败！");
			} 
		}
		return msgs;
	}

}