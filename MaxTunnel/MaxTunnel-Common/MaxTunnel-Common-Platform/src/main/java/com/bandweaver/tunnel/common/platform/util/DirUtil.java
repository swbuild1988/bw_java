package com.bandweaver.tunnel.common.platform.util;
/**
 * ClassName: DirUtils
 * @Description: 生成目录的工具类
 * @author shaosen
 * @date 2018年5月31日
 */
public class DirUtil {
   //根据文件名的哈希值，计算文件该存储的位置
   public static String getDir(String fileName){
      //获取哈希值
      int hashCode = fileName.hashCode();
      //开始计算
      //int类型数据，4个字节，32位，每四位获取一个数字，可以获取8个数字
      //这8个数字，都，作为当前文件要存贮的目录
      //获取当前哈希值最低4位，作为一级目录
      int dir1 = hashCode & 15;
      //将哈希值，向右移动4位，更新最低四位
      hashCode = hashCode >> 4;
      //获取第二级目录
      int dir2 = hashCode & 15;
      
      return "\\"+dir1+"\\"+dir2;
   }
}
