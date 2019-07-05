package com.bandweaver.tunnel.common.biz.itf;

import java.util.List;

import com.bandweaver.tunnel.common.biz.dto.AreaDto;
import com.bandweaver.tunnel.common.biz.pojo.Area;
import com.bandweaver.tunnel.common.biz.vo.AreaVo;
import com.github.pagehelper.PageInfo;

public interface AreaService {

    /**
     * @param @param area
     * @return void
     * @throws
     * @Description: 添加区域
     * @author shaosen
     * @date 2018年6月11日
     */
    void addArea(Area area);

    /**
     * @param @param list
     * @return void
     * @throws
     * @Description: 批量添加
     * @author shaosen
     * @date 2018年6月11日
     */
    void addAreaBatch(List<Area> list);

    /**
     * @param @return
     * @return List<AreaDto>
     * @throws
     * @Description: 获取所有区域列表
     * @author shaosen
     * @date 2018年6月11日
     */
    List<AreaDto> getList();


    /**
     * @param @param  areaVo
     * @param @return
     * @return List<AreaDto>
     * @throws
     * @Description: 条件查询列表
     * @author shaosen
     * @date 2018年6月11日
     */
    List<AreaDto> getAreasByCondition(AreaVo areaVo);

    /**
     * @param @param  id
     * @param @return
     * @return AreaDto
     * @throws
     * @Description: 通过id查询区域
     * @author shaosen
     * @date 2018年6月14日
     */
    AreaDto getAreasById(Integer id);

    /**
     * @param @param  id
     * @param @return
     * @return AreaDto
     * @throws
     * @Description: 通过管廊id查询区域列表
     * @author shaosen
     * @date 2018年6月21日
     */
    List<AreaDto> getAreasByTunnelId(Integer id);

    /**
     * @param @param  areaVo
     * @param @return
     * @return PageInfo<AreaDto>
     * @throws
     * @Description: 分页
     * @author shaosen
     * @date 2018年7月22日
     */
    PageInfo<AreaDto> dataGrid(AreaVo areaVo);

    void update(Area area);

    void delete(Integer id);

    void deleteBatch(List<Integer> list);

    Area getByName(String name);

    Area getByTunnelAndSN(Integer tunnelId, String sn);

    int getTotalCount();

    String calAllAreasStartPointAndEndPointByTunnel(Integer tunnelId);
}
