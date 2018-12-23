package com.bandweaver.tunnel.utils.supermapCode;

import com.supermap.data.*;

public class Supermap {

	/**
	 * 超图添加字段，修改字段值
	 * @param url  指定数据源的位置（如“E:\\tunnelCBD\\tunnelCBD.sxwu”）这里默认工作空间的类型为SXWU
	 * @param datasource  指定数据源的名称
	 * @param dataset  指定数据集的名称
	 * @param fields  指定添加的字段名称，数组格式
	 */
	public void addAndUpdateFields(String url, String datasource, String dataset, String[] fields) {
		//通过url获取数据源集合
		Datasources datasources = init(url);
		//判断数据源集合是否为空
		if(null == datasources) {
			System.out.println("数据源集合不存在！");
			return;
		}
		
		//获取数据源对象
	     Datasource d = datasources.get(datasource);
	     if(null == d) {
	    	 System.out.println("该数据源不存在！");
	    	 return;
	     }
	     //获取数据集
	     Dataset dset = d.getDatasets().get(dataset);
	     if(null == dset) {
	    	 System.out.println("该数据集不存在！");
	    	 return;
	     }
	     
	     //向数据集中添加新字段
	     addNewField(dset, fields);
	     
	     //修改数据集中的字段值
	     queryAndUpdate(dset, fields);
	     
	     //关闭资源
	     dset.close();
	}

	/**
	 * 超图删除字段
	 * @param url  指定数据源的位置（如“E:\\tunnelCBD\\tunnelCBD.sxwu”）这里默认工作空间的类型为SXWU
	 * @param datasource  指定数据源的名称
	 * @param dataset  指定数据集的名称
	 * @param fields  指定删除的字段名称，数组格式
	 */
	public void deleteFieldsByURL(String url, String datasource,String dataset, String[] fields) {

		//通过url获取数据源集合
		Datasources datasources = init(url);
		//判断数据源集合是否为空
		if(null == datasources) {
			System.out.println("数据源集合不存在！");
			return;
		}

		//获取数据源对象
		Datasource d = datasources.get(datasource);
		if(null == d) {
			System.out.println("该数据源不存在！");
			return;
		}
		//获取指定数据集
		Dataset dset = d.getDatasets().get(dataset);
		
		//打开数据集
	     dset.open();
	     //转换成矢量数据集对象
	     DatasetVector dv = (DatasetVector)dset;
	     System.out.println(dv.getFieldCount());
	     //获取字段信息集合对象
	     FieldInfos fieldinfos = dv.getFieldInfos();

		int count = fields.length;
		if(count <= 0) {
			System.out.println("请查看要删除的字段数组是否存在！");
			return;
		}
		for(int i=0;i<count;i++) {
			fieldinfos.remove(fields[i]);
		}

	}

	/** 超图初始化数据
	 * @param url	数据源的路径
	 * @return	Datasources	数据源集合
	 */
	private Datasources init(String url) {
		Workspace workspace = new Workspace();
        // 定义并获得一个工作空间连接信息的实例
		WorkspaceConnectionInfo workspaceConnectionInfo = new WorkspaceConnectionInfo();

        // 定义连接工作空间的类型为SXWU
        workspaceConnectionInfo.setType(WorkspaceType.SXWU);

        // 指定的url路径名
        String fileworkspace = null;
        if(null == url) {
        	// 设置连接工作空间文件的路径名
        	String filedir = "E:\\tunnelCBD\\";
        	String filename = "tunnelCBD.sxwu";
        	fileworkspace = filedir + filename;
        }else
        	fileworkspace = url;
        
        workspaceConnectionInfo.setServer(fileworkspace);
        
	     //打开已有的工作空间文件
	     workspace.open(workspaceConnectionInfo);
	     
	     //获取该工作空间的数据源集合
	     Datasources datasources = workspace.getDatasources();
	     return datasources;
	}

	/**
	 * 查询符合条件的记录集，并填充某些字段的值
	 * @param dataset	数据集
	 * @param fields  需要添加的字段值
	 */
	private void queryAndUpdate(Dataset dataset, String[] fields) {
		//转换成矢量数据集对象
        DatasetVector dv = (DatasetVector)dataset;
		
		//查询符合条件的记录集
		Recordset recordset = dv.getRecordset(false,CursorType.DYNAMIC);//静态是只读，不能修改
        System.out.println(recordset.getRecordCount());
        
        //判断查询结果记录集中是否存在记录集
        if(recordset.getRecordCount() <=0) {
        	System.out.println("该数据集中没有查询到符合条件的记录！");
        	return;
        }
        // 将记录位置移到第一位
        recordset.moveFirst();

        //遍历记录集并修改每条记录中的坐标
        do {
        	// 锁定当前记录位置
            recordset.edit();
        	//判断当前记录是否是最后一条记录
        	if(recordset.getObject(0) == null) {
        		return;
        	}
            //获取当前记录的Point3D
        	Geometry g = recordset.getGeometry();
        	GeoModel3D g3 = (GeoModel3D)g;
        	Point3D point = g3.getBoundingBox().getCenter();
            //修改字段的值
            recordset.setDouble(fields[0], point.getX());
            recordset.setDouble(fields[1], point.getY());
            recordset.setDouble(fields[2], point.getZ());
            recordset.update();
            //System.out.println(Arrays.toString(recordset.getValues()));
            //释放资源
            g3.dispose();
        	g.dispose();
        }while(recordset.moveNext());

       
        dv.close();
        recordset.dispose();
	}
	
	/**
	 * 超图添加字段
	 * @param dataset	数据集
	 * @param fields	字段，数组格式
	 * @author ya.liu
	 * @Date 2018年11月1日
	 */
	private void addNewField(Dataset dataset, String[] fields) {
		
		//转换成矢量数据集对象
        DatasetVector dv = (DatasetVector)dataset;
        //获取字段信息集合对象
        FieldInfos fieldinfos = dv.getFieldInfos();
        System.out.println(fieldinfos.getCount());
        int len = fields.length;
		//判断添加的字段数组是否为空
		if(len <= 0) {
			System.out.println("请查看添加的字段数组是否存在！");
			return;
		}
		//判断当前字段中是否已经存在所添加的字段
		int count = 0;
		for(int i=0;i<len;i++) {
			if(fieldinfos.get(fields[i]) != null) {
				System.out.print("‘"+fields[i]+"’ ");
				count++;
			}
		}
		if(count != 0) {
			System.out.println("已存在当前字段中！");
			return;
		}
		
		//创建一个FieldInfo数组
		FieldInfo[] fieldinfoarray = new FieldInfo[len];
		//实例化字段信息对象数组并
		for(int i=0;i<len;i++) {
			FieldInfo fieldinfo = new FieldInfo();
		    fieldinfo.setCaption(fields[i]);
		    fieldinfo.setName(fields[i]);
		    fieldinfo.setDefaultValue("0");
		    fieldinfo.setType(FieldType.DOUBLE);
		    fieldinfoarray[i] = fieldinfo;
		}
	     
	     //将数组添加到当前字段信息集合对象中
	     fieldinfos.addRange(fieldinfoarray);
	     System.out.println(fieldinfos.getCount());
	}
}

