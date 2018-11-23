package com.bandweaver.tunnel.common.biz.itf.mam.maxview;

import java.util.List;

import com.bandweaver.tunnel.common.biz.pojo.Tunnel;
import com.bandweaver.tunnel.common.biz.pojo.mam.MaxviewConfig;
import com.bandweaver.tunnel.common.biz.vo.mam.MaxviewConfigVo;
import com.github.pagehelper.PageInfo;

public interface MaxviewConfigService {

	/**登录maxview 
	 * @param configId   
	 * @author shaosen
	 * @Date 2018年11月15日
	 */
	void login(Integer configId);

	/**联动控制 
	 * @param objectId   
	 * @param inputValue   
	 * @author shaosen
	 * @return 
	 * @Date 2018年11月15日
	 */
	boolean doAction(Integer objectId,Integer inputValue);

	/**发送心跳 
	 * @param tunnelId   
	 * @author shaosen
	 * @Date 2018年11月15日
	 */
	void heartBeat(Integer configId);

	/**添加终端 
	 * @param config   
	 * @author shaosen
	 * @Date 2018年11月16日
	 */
	void add(MaxviewConfig config);

	/**更新配置 
	 * @param config   
	 * @author shaosen
	 * @Date 2018年11月16日
	 */
	void update(MaxviewConfig config);

	/**删除终端 
	 * @param id   
	 * @author shaosen
	 * @Date 2018年11月16日
	 */
	void delete(Integer id);

	/**通过主键获取 
	 * @param id
	 * @return   
	 * @author shaosen
	 * @Date 2018年11月16日
	 */
	MaxviewConfig getById(Integer id);

	/**批量删除 
	 * @param idList   
	 * @author shaosen
	 * @Date 2018年11月16日
	 */
	void deleteBatch(List<Integer> idList);

	/**分页查询 
	 * @param vo
	 * @return   
	 * @author shaosen
	 * @Date 2018年11月16日
	 */
	PageInfo<MaxviewConfig> dataGrid(MaxviewConfigVo vo);

	/**获取所有终端 
	 * @return   
	 * @author shaosen
	 * @Date 2018年11月21日
	 */
	List<MaxviewConfig> getAll();

}
