package com.bandweaver.tunnel.utils.supermapTest;

import com.bandweaver.tunnel.utils.supermapCode.Supermap;

/**	超图模块工具
 * 执行该代码的前提条件：环境变量配置；build path中Native以及jar包
 * @author ya.liu
 * @Date 2018年11月1日
 */
public class SupermapTest {
	public static void main(String [] args) {
		String url = "E:\\gcdj\\gcdj.sxwu";
		String datasource = "古城大街";
		String dataset = "安全设备";
		String [] fields = {"X","Y","Z"};
		Supermap supermap = new Supermap();
		supermap.addAndUpdateFields(url, datasource, dataset, fields);
		//supermap.deleteFieldsByURL(url, datasource, dataset, fields);
	}
}
