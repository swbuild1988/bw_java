package com.bandweaver.tunnel.common.platform.util;

import org.apache.log4j.Logger;

import com.bandweaver.tunnel.common.platform.log.LogUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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
	
}
