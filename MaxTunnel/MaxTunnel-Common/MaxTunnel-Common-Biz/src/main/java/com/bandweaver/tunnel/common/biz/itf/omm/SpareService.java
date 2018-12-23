package com.bandweaver.tunnel.common.biz.itf.omm;

import java.util.List;

import com.bandweaver.tunnel.common.biz.dto.CommonDto;
import com.bandweaver.tunnel.common.biz.dto.omm.SpareDto;
import com.bandweaver.tunnel.common.biz.pojo.omm.Spare;
import com.bandweaver.tunnel.common.biz.vo.omm.SpareVo;
import com.github.pagehelper.PageInfo;

public interface SpareService {

	int add(Spare spare);

    int update(Spare spare);

    int addBatch(List<Spare> list);
    /**
     * 获取所有未出库的备品
     * @return
     * @author ya.liu
     * @Date 2018年11月26日
     */
    List<SpareDto> getSpareDto();
    /**
     * 获取所有已出库的备品
     * @return
     * @author ya.liu
     * @Date 2018年11月26日
     */
    List<SpareDto> getSpareDtoByStatus();
    /**
     * 条件查询列表
     * @param vo
     * @return
     * @author ya.liu
     * @Date 2018年11月26日
     */
    PageInfo<SpareDto> dataGrid(SpareVo vo);
    
    SpareDto getSpareDtoById(Integer id);

    int deleteByIds(List<Integer> list);
    
    List<CommonDto> getCountGroupByTypeId(Boolean status);
}
