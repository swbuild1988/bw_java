//package com.bandweaver.tunnel.common.biz.itf;
//
//import com.supermap.data.*;
//
//public interface SupermapService {
//
//	/**
//	 * 超图查询符合条件的数据集，并添加字段修改字段值
//	 * @param url  指定数据源的位置（如“E:\\tunnelCBD\\tunnelCBD.sxwu”）这里默认工作空间的类型为SXWU
//	 * @param datasource  指定数据源的名称
//	 * @param fieldName  指定需要查询的字段名
//	 * @param values  指定需要查询的字段值，数组格式
//	 * @param fields  指定添加的字段名称，数组格式
//	 */
//	void queryFieldsAndUpdateFieldsByURL(String url, String datasource, String fieldName, int [] values, String [] fields);
//
//	/**
//	 * 超图删除字段
//	 * @param url  指定数据源的位置（如“E:\\tunnelCBD\\tunnelCBD.sxwu”）这里默认工作空间的类型为SXWU
//	 * @param datasource  指定数据源的名称
//	 * @param fields  指定删除的字段名称，数组格式
//	 */
//	void deleteFieldsByURL(String url, String datasource, String [] fields);
//
//	/**
//	 * 根据地质获取DataSources
//	 * @param path
//	 * @return
//	 */
//	Datasources getDatasources(String path);
//
//	/**
//	 * 根据文件夹和文件名获取数据源群
//	 * @param folder
//	 * @param fileName
//	 * @return
//	 */
//	Datasources getDatasources(String folder, String fileName);
//
//	/**
//	 * 获取数据源群中的某个数据源
//	 * @param datasources
//	 * @param datasourceName
//	 * @return
//	 */
//	Datasource getDatasource(Datasources datasources, String datasourceName);
//
//	/**
//	 * 根据数据源获取所有的数据集
//	 * @param datasource
//	 * @return
//	 */
//	Datasets getDatasets(Datasource datasource);
//
//	/**
//	 * 获取数据集中的所有字段
//	 * @param dataset
//	 * @return
//	 */
//	FieldInfos getFieldInfos(Dataset dataset);
//
//	/**
//	 * 获取数据集中的所有字段名
//	 * @param dataset
//	 * @return
//	 */
//	String[] getFieldInfoNames(Dataset dataset);
//
//	/**
//	 * 向数据集中添加新的字段
//	 * @param dataset
//	 * @param fields
//	 */
//	void addNewField(Dataset dataset, String[] fields);
//
//}
