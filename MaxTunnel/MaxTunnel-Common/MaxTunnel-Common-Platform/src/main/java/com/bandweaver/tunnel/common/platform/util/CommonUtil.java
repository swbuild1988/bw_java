package com.bandweaver.tunnel.common.platform.util;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.platform.constant.StatusCodeEnum;
import com.bandweaver.tunnel.common.platform.exception.BandWeaverException;

public class CommonUtil {
	
	//private static final String[] EMPTY_STRING_ARRAY = new String[0];

    /**
     * 返回格式都是以（code,msg,data）形式返回
     * @param code
     * @param msg
     * @param data
     * @return
     */
    public static JSONObject returnJson(String code, String msg, Object data){
        JSONObject resultJson = new JSONObject();
        resultJson.put("code", code);
        resultJson.put("msg", msg);
        resultJson.put("data", data);
        return resultJson;
    }

    /**
     *
     * @param statusCodeEnum
     * @return
     */
    public static JSONObject returnStatusJson(StatusCodeEnum statusCodeEnum){
        return returnJson(statusCodeEnum.getCode(), statusCodeEnum.getMsg(), new JSONObject());
    }

    /**
     *
     * @param statusCodeEnum
     * @param obj
     * @return
     */
    public static JSONObject returnStatusJson(StatusCodeEnum statusCodeEnum, Object obj){
        return returnJson(statusCodeEnum.getCode(), statusCodeEnum.getMsg(), obj);
    }
    
    
    /**返回成功，推荐使用
     * @return   
     * @author shaosen
     * @Date 2018年12月17日
     */
    public static JSONObject success() {
    	return returnStatusJson(StatusCodeEnum.S_200);
    }
    
    /**带有数据的成功，推荐使用
     * @param obj
     * @return   
     * @author shaosen
     * @Date 2018年12月17日
     */
    public static JSONObject success(Object obj) {
    	return returnStatusJson(StatusCodeEnum.S_200,obj);
    }
    
    
    
    /**验证是否含有全部必填字段 
     * @param requiredColumns 必填的参数字段名称 逗号隔开 比如"userId,name,telephone"
     * @param jsonObject   
     * @author shaosen
     * @Date 2018年12月17日
     */
    public static void hasAllRequired(final JSONObject jsonObject, String requiredColumns) {
        if (!StringTools.isNullOrEmpty(requiredColumns)) {
            //验证字段非空
            String[] columns = requiredColumns.split(",");
            String missCol = "";
            for (String column : columns) {
                Object val = jsonObject.get(column.trim());
                if (StringTools.isNullOrEmpty(val)) {
                    missCol += column + "  ";
                }
            }
            if (!StringTools.isNullOrEmpty(missCol)) {
                jsonObject.clear();
                throw new BandWeaverException("缺少必填参数:" + missCol.trim());
                
            }
        }
    }
    
    
    /**把id字符串转化成Integer类型的List集合
	 * @param ids 1,2,3,4....
	 * @return  List<Integer>
	 * @author shaosen
	 * @Date 2018年9月25日
	 */
	public static List<Integer> convertStringToList(String ids){
		List<Integer> list = new ArrayList<>();
		if(ids == null || ids.length() ==0) {
		    return list;
        }
		String[] arr = ids.split(",");
		
		for (String str : arr) {
			Integer i = DataTypeUtil.toInteger(str);
			if(i != null) {
			    list.add(i);
            }
		}
		return list;
	}
	
	
	/**转化成需要的对象类型
	 * @param reqJson
	 * @param clazz
	 * @return   
	 * @author shaosen
	 * @Date 2018年12月18日
	 */
	public static <T> T parse2Obj(final JSONObject reqJson,Class<T> clazz) {
		return JSONObject.parseObject(reqJson.toJSONString(), clazz);
	}
}
