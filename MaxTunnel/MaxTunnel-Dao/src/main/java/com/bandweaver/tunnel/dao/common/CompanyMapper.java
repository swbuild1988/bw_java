package com.bandweaver.tunnel.dao.common;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bandweaver.tunnel.common.biz.pojo.Company;
import com.bandweaver.tunnel.common.biz.vo.CompanyVo;

public interface CompanyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Company record);

    int insertSelective(Company record);

    Company selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Company record);

    int updateByPrimaryKey(Company record);

	/**
	 * @Description: 获取所有公司列表
	 * @param @return   
	 * @return List<Company>  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月25日
	 */
	List<Company> getList();

	/**
	 * @Description: 删除中间表数据
	 * @param @param id   
	 * @return void  
	 * @throws
	 * @author shaosen
	 * @date 2018年6月26日
	 */
	void deleteFromOtherTable(@Param("id")Integer id);

	List<Company> getByCondition(CompanyVo vo);

	void deleteBatch(List<Integer> list);
}