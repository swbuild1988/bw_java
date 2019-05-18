package com.bandweaver.tunnel.service.common;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import com.bandweaver.tunnel.common.platform.exception.BandWeaverException;
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
        int i = fileInfoMapper.insertSelective(fileInfo);
        return i > 0 ? true : false;
    }

    @Override
    public PageInfo<FileInfoDto> dataGrid(FileInfoVo vo) {
        PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
        List<FileInfoDto> list = getDtoListByCondition(vo);
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
        return i > 0 ? true : false;
    }


    @Override
    public void upload(String uploadPath, MultipartFile[] files, Integer fileType, Integer docType, Integer docTypeSon, boolean isSaveToDB) throws Exception {

        //检查文件路径是否存在
        checkPath(uploadPath, true);

        //上传多个文件
        for (MultipartFile file : files) {
            String originalFilename = file.getOriginalFilename();

            int index = originalFilename.lastIndexOf(".");
            String suffix = originalFilename.substring(index, originalFilename.length());

            //检验文件格式
            checkFileFormat(fileType, suffix);

            // 考虑到并发上传的发生，使用uuid
            String newFileName = UUID.randomUUID() + suffix;

            file.transferTo(new File(uploadPath + File.separator + newFileName));
            LogUtil.info("Upload file[" + originalFilename + "] success !");

            if (isSaveToDB) {
                saveToDB(fileType, docType, docTypeSon, originalFilename, newFileName, suffix);
            }
        }

    }

    @Override
    public void checkPath(String path, boolean isCreate) throws Exception {
        if (!FileUtil.isExit(path)) {
            if (isCreate) {
                FileUtil.createPath(path);
                LogUtil.info("Create new path : " + path);
            } else {
                throw new BandWeaverException("资源路径[" + path + "]不存在！");
            }

        }
    }


    @Override
    public boolean saveToDB(Integer fileType, Integer docType, Integer docTypeSon, String originalFilename, String newFileName, String suffix) throws Exception {

//        String fileTypeName = FileTypeEnum.getEnum(fileType).getName();
//        String docTypeName = DocTypeEnum.getEnum(docType).getName();

        //检验文件格式
        checkFileFormat(fileType, suffix);

        //保存到数据库
        FileInfo fileInfo = new FileInfo();
        fileInfo.setDocType(docType);
        fileInfo.setDocTypeSon(docTypeSon);
        fileInfo.setFileType(fileType);
        fileInfo.setName(originalFilename);
        fileInfo.setPath(newFileName);
        return this.save(fileInfo);

    }

    @Override
    public void checkFileFormat(Integer fileType, String suffix) throws Exception {
        String str = (String) PropertiesUtil.getValue(FileTypeEnum.getEnum(fileType).getType());
        String[] strArr = str.toLowerCase().split(",");

        if (!Arrays.asList(strArr).contains(suffix.toLowerCase())) {
            throw new Exception("文件格式 [" + suffix + "]不支持 !");
        }
    }

    @Override
    public void download(HttpServletResponse response, String realPath) throws Exception {
        InputStream is = null;
        ByteArrayOutputStream bos = null;
        OutputStream outputStream = null;
        try {
            //检验文件路径
            checkPath(realPath, false);

            is = new FileInputStream(new File(realPath));

            //控制文件下载速度
            byte[] buffer = new byte[2048];
            int len = 0;
            bos = new ByteArrayOutputStream();//转化成字节输出流
            while ((len = is.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }
            bos.flush();

            outputStream = response.getOutputStream();
            outputStream.write(bos.toByteArray());
            outputStream.flush();

        } catch (Exception e) {
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
    }

    @Override
    public boolean deleteFile(Integer id, String diskPath) throws Exception {

        FileInfo fileInfo = getById(id);
        if (fileInfo == null) {
            throw new Exception("资源不存在！");
        }

        if (deleteFileOnDB(id)) {
            LogUtil.info("数据库文件 [" + fileInfo.getName() + "] 已删除！");
        }

        if (diskPath != null) {
            String path = fileInfo.getPath();
            File file = new File(diskPath + path);
            return  file.delete();
        }
        return false;
    }

}
