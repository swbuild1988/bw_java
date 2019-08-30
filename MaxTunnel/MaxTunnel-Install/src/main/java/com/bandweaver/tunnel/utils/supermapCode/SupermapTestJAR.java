//package com.bandweaver.tunnel.utils.supermapCode;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.InputStreamReader;
//import java.util.Properties;
//
///**	超图模块工具
// * 执行该代码的前提条件：环境变量配置；build path中Native以及jar包
// * @author ya.liu
// * @Date 2018年11月1日
// */
//public class SupermapTestJAR {
//	public static void main(String [] args) throws Exception {
//		Properties prop = new Properties();
//		// 获取生成的exe文件所在路径
//		String exePath = new File("").getAbsolutePath();
//		String realPath = exePath + "\\set.properties";
//		System.out.println("当前配置文件所在位置：" + realPath);
//		BufferedReader fis = new BufferedReader(new InputStreamReader(new FileInputStream(realPath),"UTF-8")) ;
//		prop.load(fis);
//		String url = prop.getProperty("url");
//		System.out.println("工作空间所在位置：" + url);
//		String datasource = prop.getProperty("datasource");
//		System.out.println("数据源名称：" + datasource);
//		String dataset = prop.getProperty("dataset");
//		System.out.println("数据集名称：" + dataset);
//		fis.close();
//		String [] fields = {"X","Y","Z"};
//		Supermap supermap = new Supermap();
//		supermap.addAndUpdateFields(url, datasource, dataset, fields);
////		supermap.deleteFieldsByURL(url, datasource, dataset, fields);
//	}
//}
