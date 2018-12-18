package com.bandweaver.tunnel.common.platform.util;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.dto.mam.h5.H5Obj;
import com.bandweaver.tunnel.common.biz.dto.mam.h5.H5Source;
import com.bandweaver.tunnel.common.biz.dto.mam.h5.H5Src;

public class FileUtil {

    static Logger log = Logger.getLogger(FileUtil.class);

    /**
     * 判断文件是否存在
     * @param path
     * @return
     */
    public static boolean isExit(String path){
        File file = new File(path);
        return file.exists();
    }
    
    /**创建路径 
     * @param path   
     * @author shaosen
     * @Date 2018年8月29日
     */
    public static void createPath(String path){
    	File file = new File(path);
    	file.mkdirs();
    }

    /**
     * 返回html文件夹所在的目录
     * @return
     */
    public static String htmlDir(){
        String classePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        // 单纯去掉最后一个“/”，方便后面截取字符
        classePath = classePath.substring(0,classePath.lastIndexOf('/'));
        String htmlPath = classePath.substring(0,classePath.lastIndexOf('/')) + "/html";
        return  htmlPath;
    }

	/**
	 * @Description: 复制文件
	 * @param @param in
	 * @param @param out   
	 * @return void  
	 * @throws IOException 
	 * @throws
	 * @author shaosen
	 * @date 2018年7月5日
	 */
	public static void copyStream(InputStream in, OutputStream out) throws IOException {
		
		byte[] buf = new byte[1024];
		int len = 0;
		while((len = in.read(buf)) != -1 ) {
			out.write(buf, 0, len);
		}
		//关闭流
		in.close();
		out.close();
	}
	
	
	
	/**读取文件中的内容 
	 * @param filePath 文件路径
	 * @return   
	 * @author shaosen
	 * @Date 2018年12月3日
	 */
	public static String readContent(String filePath) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(filePath);
			int size = fis.available();
			byte[] array = new byte[size];
			fis.read(array);
			String result = new String(array);
			return result;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	
	/**写入文件 
	 * @param filePath 目标文件
	 * @param content  文件内容
	 * @author shaosen
	 * @Date 2018年12月3日
	 */
	public static void writeContent(String filePath,String content) {
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(new File(filePath)));
			bw.write(content);
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		
	
		
	}
	
	
	
	
	public static void main(String[] args) {
		
		String user = "admin";
    	String password = "123456";
    	String ip = "192.168.3.201";
    	String id = "7001";
    	String filePath = "D:\\dev\\h5s-r7.0.1012.18-win64-release\\h5s-r7.0.1012.18-win64-release\\conf\\h5ss.conf";
    	String dest = "D:\\dev\\h5s-r7.0.1012.18-win64-release\\h5s-r7.0.1012.18-win64-release\\conf\\dest.conf";
    	
    	
		String content = FileUtil.readContent(filePath);
		H5Obj h5Obj = JSONObject.parseObject(content,H5Obj.class);
		H5Source source = h5Obj.getSource();
		
		List<H5Src> list =  new ArrayList<>();
		//stream
		H5Src stream = new H5Src();
		stream.setStrName(id);
		stream.setStrToken(id);
		stream.setnType("H5_STREAM");
		stream.setStrUrl(ip);//todo
    	stream.setStrUser(user);
    	stream.setStrPasswd(password);
    	stream.setStrSrcIpAddress(ip);
    	
    	//onvif
    	H5Src onvif = new H5Src();
    	onvif.setStrName("onvif_" + id);
    	onvif.setStrToken("onvif_" + id);
    	onvif.setnType("H5_ONVIF");
    	onvif.setStrUrl(ip);//todo
    	onvif.setStrUser(user);
    	onvif.setStrPasswd(password);
    	onvif.setStrSrcIpAddress(ip);
		
		list.add(stream);
		list.add(onvif);
		source.setSrc(list);
		h5Obj.setSource(source);
		
		String jsonString = JSONObject.toJSONString(h5Obj);
		System.out.println(jsonString);
		
		FileUtil.writeContent(filePath, jsonString);
		
	}


	
}
