package com.bandweaver.tunnel.common.biz.itf;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.bandweaver.tunnel.common.biz.dto.FileInfoDto;
import com.bandweaver.tunnel.common.biz.pojo.FileInfo;
import com.bandweaver.tunnel.common.biz.vo.FileInfoVo;
import com.github.pagehelper.PageInfo;

public interface FileInfoService {

	/**保存 
	 * @param fileInfo   
	 * @author shaosen
	 * @Date 2018年8月27日
	 */
	boolean save(FileInfo fileInfo);

	/**分页查询 
	 * @param vo
	 * @return   
	 * @author shaosen
	 * @Date 2018年8月28日
	 */
	PageInfo<FileInfoDto> dataGrid(FileInfoVo vo);
	
	/**条件查询 
	 * @param vo
	 * @return   
	 * @author shaosen
	 * @Date 2018年8月28日
	 */
	List<FileInfoDto> getDtoListByCondition(FileInfoVo vo);

	FileInfo getById(Integer id);

	/**删除文件，只删除数据库文件信息
	 * @param id 文件id
	 * @return   
	 * @author shaosen
	 * @Date 2018年8月30日
	 */
	boolean deleteFileOnDB(Integer id);
	
	/**删除文件
	 * @param id 文件id
	 * @param diskPath 硬盘路径，如果为null，则只删除DB数据
	 * @throws Exception   
	 * @author shaosen
	 * @Date 2018年8月30日
	 */
	void deleteFile(Integer id, String diskPath) throws Exception;

	/**上传文件 
	 * @param uploadPath 文件上传路径
	 * @param files 文件
	 * @param fileType 文件类型 1：文档；2：视频；3：图片
	 * @param docType 资料类型 1：管廊本体资料；2：其他资料
	 * @param isSaveToDB 是否保存到文件信息表
	 * @throws Exception   
	 * @author shaosen
	 * @Date 2018年8月30日
	 */
	void upload(String uploadPath, MultipartFile[] files, int fileType, int docType, boolean isSaveToDB)
			throws Exception;

	/**保存到数据库 
	 * @param fileType 文件类型 1：文档；2：视频；3：图片
	 * @param docType 资料类型 1：管廊本体资料；2：其他资料
	 * @param fileName 文件名
	 * @param suffix 文件后缀
	 * @return   
	 * @author shaosen
	 * @throws Exception 
	 * @Date 2018年8月30日
	 */
	boolean saveToDB(int fileType, int docType, String fileName, String suffix) throws Exception;

	/**检验文件格式 
	 * @param fileType  文件类型 1：文档；2：视频；3：图片
	 * @param suffix 文件后缀
	 * @throws Exception   
	 * @author shaosen
	 * @Date 2018年8月30日
	 */
	void checkFileFormat(int fileType, String suffix) throws Exception;
	

	/**文件下载 
	 * @param response HttpServletResponse
	 * @param realPath 文件绝对路径
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws Exception   
	 * @author shaosen
	 * @Date 2018年8月30日
	 */
	void download(HttpServletResponse response, String realPath) throws FileNotFoundException, IOException, Exception;

	/**检验文件路径 
	 * @param path 路径
	 * @param isCreate 如果不存在是否创建
	 * @throws Exception   
	 * @author shaosen
	 * @Date 2018年8月30日
	 */
	void checkPath(String path, boolean isCreate) throws Exception;

	

}
