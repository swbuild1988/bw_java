/*package com.bandweaver.tunnel.service.common;

import com.supermap.data.*;


public class SupermapServiceImpl implements SupermapService {

	@Override
	public void queryFieldsAndUpdateFieldsByURL(String url, String datasource, String fieldName, int[] values,
			String[] fields) {
		//通过url获取数据源集合
		Datasources datasources = init(url);
		//判断该数据源是否为空
		if(null == datasources) {
			System.out.println("该数据源不存在！");
			return;
		}
	     
	     //获取单个数据源以及后续工作
	     getDatasourceAandothers(datasources,datasource,fieldName,values,fields);
	        

	}

	@Override
	public void deleteFieldsByURL(String url, String datasource, String[] fields) {

		//通过url获取数据源集合
		Datasources datasources = init(url);
		//判断该数据源是否为空
		if(null == datasources) {
			System.out.println("该数据源不存在！");
			return;
		}

		//获取数据源对象
		Datasource d = datasources.get(datasource);
		if(null == d) {
			System.out.println("请确认该数据源存在！");
			return;
		}
		//获取数据集集合
		Datasets datasets = d.getDatasets();
		int count = datasets.getCount();

		//遍历数据集集合
		for(int i=0;i<count;i++) {
			Dataset dset = datasets.get(i);
			//打开数据集
			datasets.get(i).open();

			//去除IMAGE类型
			if("IMAGE".equals(dset.getType().toString())) {
				continue;
			}
			//转换成矢量数据集对象
			DatasetVector dv = (DatasetVector)dset;
			//获取字段信息集合对象
			FieldInfos fieldinfos = dv.getFieldInfos();


			//删除指定字段名的字段信息
			remove(fieldinfos,fields);

			//关闭资源
			dv.close();
			dset.close();
		}

	}


	private Datasources init(String url) {
		Workspace workspace = new Workspace();
        // 定义并获得一个工作空间连接信息的实例
		WorkspaceConnectionInfo workspaceConnectionInfo = new WorkspaceConnectionInfo();

        // 定义连接工作空间的类型为SXWU
        workspaceConnectionInfo.setType(WorkspaceType.SXWU);

        // 设置连接工作空间文件的路径名
        String filename = "tunnelCBD.sxwu";
        String filedir = "E:\\tunnelCBD\\";
        
        // 指定的url路径名
        String fileworkspace = filedir + filename;
        workspaceConnectionInfo.setServer(fileworkspace);

        // 判断是否成功打开工作空间文件
	     if ( null == workspaceConnectionInfo || "".equals(workspaceConnectionInfo) ) {
	            System.out.println("工作空间不存在");
	            return null;
	        }
     
	     //打开已有的工作空间文件
	     workspace.open(workspaceConnectionInfo);
	     
	     //获取该工作空间的数据源集合
	     Datasources datasources = workspace.getDatasources();
	     return datasources;
	}

	*//**
	 * 在指定数据源中获取数据集集合，并在每个数据集中添加字段，在每条记录中获取相应的Position并赋值
	 * @param datasources  数据源集合
	 * @param datasource  数据源名称
	 * @param typeID  查询字段名称
	 * @param values  对应查询字段的值集合
	 * @param fields  要添加字段的名称集合
	 *//*
	private void getDatasourceAandothers(Datasources datasources, String datasource, String typeID, int[] values, String[] fields) {
		//获取数据源对象
	     Datasource d = datasources.get(datasource);
	     if(null == d) {
	    	 System.out.println("请确认该数据源存在！");
	    	 return;
	     }
	     //获取数据集集合
	     Datasets datasets = d.getDatasets();
	     int count = datasets.getCount();
	     
	     //遍历数据集集合
	     for(int i=0;i<count;i++) {
	    	 Dataset dset = datasets.get(i);
	    	//打开数据集
	    	 datasets.get(i).open();
	    	 
	    	 //去除IMAGE类型
	    	 if("IMAGE".equals(dset.getType().toString())) {
	    		 continue;
	    	 }
	         //转换成矢量数据集对象
	         DatasetVector dv = (DatasetVector)dset;
	         //获取字段信息集合对象
	         FieldInfos fieldinfos = dv.getFieldInfos();
	         
	         
        	//添加字段信息集合
	         addFieldArray(fieldinfos,fields);
	         
	         //条件查询并修改其相应字段的值
	         queryAndUpdate(dv,typeID,values,fields);
	         
	         //关闭资源
	         dv.close();
	         dset.close();
	     }
	     
	}

	
	private void remove(FieldInfos fieldinfos, String[] fields) {
		int count = fields.length;
		if(count <= 0) {
			System.out.println("请查看要删除的字段数组是否存在！");
			return;
		}
		for(int i=0;i<count;i++) {
			fieldinfos.remove(fields[i]);
		}
		
	}

	*//**
	 * 查询符合条件的记录集，并填充某些字段的值
	 * @param dataset_world 矢量数据集对象
	 * @param typeID  要查询的字段名称
	 * @param values  对应字段名的数组值
	 * @param fields  需要添加的字段值
	 *//*
	private void queryAndUpdate(DatasetVector dataset_world, String typeID, int[] values, String[] fields) {
		
		//查询符合条件的记录集
		Recordset recordset = dataset_world.query(values,typeID,CursorType.DYNAMIC);//静态是只读，不能修改
        //System.out.println(recordset.getRecordCount());
        
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

       
        dataset_world.close();
        recordset.dispose();

		
	}

	*//**
	 * 在数据集中添加字段
	 * @param fieldinfos  一个数据集中的所有字段信息对象
	 * @param fields  要添加的字段数组
	 *//*
	private void addFieldArray(FieldInfos fieldinfos, String[] fields) {
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
	     //System.out.println(fieldinfos.getCount());
		
	}
	
}
*/