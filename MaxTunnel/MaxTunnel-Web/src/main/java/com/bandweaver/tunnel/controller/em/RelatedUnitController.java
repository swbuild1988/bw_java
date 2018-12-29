package com.bandweaver.tunnel.controller.em;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.bandweaver.tunnel.common.biz.dto.SectionDto;
import com.bandweaver.tunnel.common.biz.dto.em.RelatedUnitDto;
import com.bandweaver.tunnel.common.biz.itf.SectionService;
import com.bandweaver.tunnel.common.biz.itf.em.RelatedUnitService;
import com.bandweaver.tunnel.common.biz.pojo.em.RelatedUnit;
import com.bandweaver.tunnel.common.biz.vo.em.RelatedUnitVo;
import com.bandweaver.tunnel.common.platform.constant.StatusCodeEnum;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.CommonUtil;
import com.bandweaver.tunnel.common.platform.util.DataTypeUtil;
import com.github.pagehelper.PageInfo;

/**
 * 应急-相关单位
 * @author shaosen
 * @date 2018年7月9日
 */
@Controller
@ResponseBody
public class RelatedUnitController {

	
	@Autowired
	private RelatedUnitService relatedUnitService;
	@Autowired
	private SectionService sectionService;
	
	
	/**获取所有相关单位 
	 * @return   {"msg":"请求成功","code":"200","data":[{"id":7,"name":"浦东国际机场","contact":"小壮","tel":"(021)68347575","unitType":6,"unitTypeName":"景点","address":"上海市浦东新区迎宾大道6000号","longitude":"112.494082","latitude":"37.706135","sectionIds":"[15]","sectionList":null,"crtTime":1531113558000},{"id":6,"name":"世纪公园","contact":"大壮","tel":"18702563039","unitType":6,"unitTypeName":"景点","address":"上海市浦东新区向城路80号","longitude":"112.494090","latitude":"37.706188","sectionIds":"[1],[9]","sectionList":null,"crtTime":1531113475000},{"id":5,"name":"山姆大叔会员店","contact":"大刘","tel":"18702563019","unitType":4,"unitTypeName":"商场","address":"锦绣路190号","longitude":"112.494085","latitude":"37.706156","sectionIds":"[2],[4]","sectionList":null,"crtTime":1531113383000},{"id":4,"name":"上海市黄埔区人民政府","contact":"李二亚","tel":"18702569019","unitType":3,"unitTypeName":"机关单位","address":"南京西路100号","longitude":"112.494084","latitude":"37.706150","sectionIds":"[1],[4]","sectionList":null,"crtTime":1531113314000},{"id":3,"name":"上海市浦东中学","contact":"张大烛","tel":"13702569017","unitType":2,"unitTypeName":"学校","address":"浦东新区张恒路988号","longitude":"112.494081","latitude":"37.706120","sectionIds":"[1]","sectionList":null,"crtTime":1531113224000},{"id":2,"name":"上海市第一人民医院","contact":"王梦飞","tel":"13702569817","unitType":1,"unitTypeName":"医院","address":"徐汇区人民路001号","longitude":"112.494080","latitude":"37.706119","sectionIds":"[1],[2],[3],[4],[5]","sectionList":null,"crtTime":1531105196000}]}
	 * @author shaosen
	 * @Date 2018年8月30日
	 */
	@RequestMapping(value="relatedunits",method=RequestMethod.GET)
	public JSONObject getAll() {
		List<RelatedUnitDto> list = relatedUnitService.getDtoListByCondition(new RelatedUnitVo());
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, list);
	}
	
	/**添加相关单位
	 * @param    name 单位名称
	 * @param    contact 联系人
	 * @param    tel 电话
	 * @param    unitType 单位类型（枚举）
	 * @param    address 地址
	 * @param    longitude 经度
	 * @param    latitude 维度
	 * @param    sectionIds 仓段id，使用多个id使用逗号分隔
	 * @return {"msg":"请求成功","code":"200","data":{}}  
	 * @throws
	 * @author shaosen
	 * @date 2018年7月9日
	 */
	@RequestMapping(value="relatedunits",method=RequestMethod.POST)
	public JSONObject add(@RequestBody RelatedUnit pojo) {
		relatedUnitService.add(pojo);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}
	
	/**删除
	 * 
	 * @param  id 单位id
	 * @return {"msg":"请求成功","code":"200","data":{}}  
	 * @throws
	 * @author shaosen
	 * @date 2018年7月9日
	 */
	@RequestMapping(value="relatedunits/{id}",method=RequestMethod.DELETE)
	public JSONObject delete(@PathVariable Integer id ) {
		relatedUnitService.delete(id);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}
	
	/**修改
	 * 
	 * @param  pojo 参考添加接口 
	 * @return  {"msg":"请求成功","code":"200","data":{}}    
	 * @throws
	 * @author shaosen
	 * @date 2018年7月9日
	 */
	@RequestMapping(value="relatedunits",method=RequestMethod.PUT)
	public JSONObject update(@RequestBody RelatedUnit pojo) {
		relatedUnitService.update(pojo);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200);
	}
	
	
	/**分页查询
	 *  
	 * @param sectionIds useless:使用三级联动，从管廊开始选仓，从仓在选择section（单选）
	 * @param name 单位名称，支持模糊查询
	 * @param contact 联系人，支持模糊查询
	 * @param  unitType 单位类别（枚举）
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @param  pageNum 必须
	 * @param  pageSize 必须
	 * @return {"msg":"请求成功","code":"200","data":{"total":7,"list":[{"id":8,"name":"test","contact":"llllllllllllllllllllll","tel":"(021)68347575","unitType":6,"unitTypeName":"景点","address":"上海市浦东新区迎宾大道6000号","sectionIds":"[15],[5]","sectionList":[{"id":15,"name":"电力仓仓段22","totalCableNumber":5,"camera":null,"crtTime":1530082636000,"store":{"id":9,"name":"电力仓xxxx","tunnel":{"id":3,"name":"高坡岭路"},"storeTypeId":2,"storeTypeName":"电力仓","camera":null,"crtTime":1529298749000},"area":{"id":22,"name":"高坡岭路-区域-0","location":"高坡岭路-区域-0-位置","tunnelId":3,"camera":null,"crtTime":1529903396000}},{"id":5,"name":"电力仓仓段13","totalCableNumber":5,"camera":null,"crtTime":1530169036000,"store":{"id":3,"name":"电力仓xxxx","tunnel":{"id":1,"name":"凤岭北路"},"storeTypeId":2,"storeTypeName":"电力仓","camera":null,"crtTime":1529557949000},"area":{"id":13,"name":"凤岭北路-区域-1","location":"凤岭北路-区域-1-位置","tunnelId":1,"camera":null,"crtTime":1529816996000}}],"crtTime":1531113568000},{"id":7,"name":"浦东国际机场","contact":"小壮","tel":"(021)68347575","unitType":6,"unitTypeName":"景点","address":"上海市浦东新区迎宾大道6000号","sectionIds":"[15]","sectionList":[{"id":15,"name":"电力仓仓段22","totalCableNumber":5,"camera":null,"crtTime":1530082636000,"store":{"id":9,"name":"电力仓xxxx","tunnel":{"id":3,"name":"高坡岭路"},"storeTypeId":2,"storeTypeName":"电力仓","camera":null,"crtTime":1529298749000},"area":{"id":22,"name":"高坡岭路-区域-0","location":"高坡岭路-区域-0-位置","tunnelId":3,"camera":null,"crtTime":1529903396000}}],"crtTime":1531113558000},{"id":6,"name":"世纪公园","contact":"大壮","tel":"18702563039","unitType":6,"unitTypeName":"景点","address":"上海市浦东新区向城路80号","sectionIds":"[3],[9]","sectionList":[{"id":3,"name":"电力仓仓段11","totalCableNumber":5,"camera":null,"crtTime":1530687436000,"store":{"id":12,"name":"电力仓xxxx","tunnel":{"id":4,"name":"凤凰岭路"},"storeTypeId":2,"storeTypeName":"电力仓","camera":null,"crtTime":1529298749000},"area":{"id":11,"name":"凤凰岭路-区域-4","location":"凤凰岭路-区域-4-位置","tunnelId":4,"camera":null,"crtTime":1529557796000}},{"id":9,"name":"电力仓仓段17","totalCableNumber":5,"camera":null,"crtTime":1530428236000,"store":{"id":15,"name":"电力仓xxxx","tunnel":{"id":5,"name":"佛子岭路"},"storeTypeId":2,"storeTypeName":"电力仓","camera":null,"crtTime":1529385149000},"area":{"id":17,"name":"佛子岭路-区域-0","location":"佛子岭路-区域-0-位置","tunnelId":5,"camera":null,"crtTime":1529903396000}}],"crtTime":1531113475000},{"id":5,"name":"山姆大叔会员店","contact":"大刘","tel":"18702563019","unitType":4,"unitTypeName":"商场","address":"锦绣路190号","sectionIds":"[2],[4]","sectionList":[{"id":2,"name":"电力仓仓段10","totalCableNumber":5,"camera":null,"crtTime":1529996236000,"store":{"id":12,"name":"电力仓xxxx","tunnel":{"id":4,"name":"凤凰岭路"},"storeTypeId":2,"storeTypeName":"电力仓","camera":null,"crtTime":1529298749000},"area":{"id":10,"name":"凤凰岭路-区域-3","location":"凤凰岭路-区域-3-位置","tunnelId":4,"camera":null,"crtTime":1529644196000}},{"id":4,"name":"电力仓仓段12","totalCableNumber":5,"camera":null,"crtTime":1530601036000,"store":{"id":3,"name":"电力仓xxxx","tunnel":{"id":1,"name":"凤岭北路"},"storeTypeId":2,"storeTypeName":"电力仓","camera":null,"crtTime":1529557949000},"area":{"id":12,"name":"凤岭北路-区域-0","location":"凤岭北路-区域-0-位置","tunnelId":1,"camera":null,"crtTime":1529903396000}}],"crtTime":1531113383000},{"id":4,"name":"上海市黄埔区人民政府","contact":"李二亚","tel":"18702569019","unitType":3,"unitTypeName":"机关单位","address":"南京西路100号","sectionIds":"[1],[4]","sectionList":[null,{"id":4,"name":"电力仓仓段12","totalCableNumber":5,"camera":null,"crtTime":1530601036000,"store":{"id":3,"name":"电力仓xxxx","tunnel":{"id":1,"name":"凤岭北路"},"storeTypeId":2,"storeTypeName":"电力仓","camera":null,"crtTime":1529557949000},"area":{"id":12,"name":"凤岭北路-区域-0","location":"凤岭北路-区域-0-位置","tunnelId":1,"camera":null,"crtTime":1529903396000}}],"crtTime":1531113314000}],"pageNum":1,"pageSize":5,"size":5,"startRow":1,"endRow":5,"pages":2,"prePage":0,"nextPage":2,"isFirstPage":true,"isLastPage":false,"hasPreviousPage":false,"hasNextPage":true,"navigatePages":8,"navigatepageNums":[1,2],"navigateFirstPage":1,"navigateLastPage":2,"firstPage":1,"lastPage":2}}  
	 * @throws
	 * @author shaosen
	 * @date 2018年7月9日
	 */
	@RequestMapping(value="relatedunits/datagrid",method=RequestMethod.POST)
	public JSONObject dataGrid(@RequestBody RelatedUnitVo vo) {
		PageInfo<RelatedUnitDto> pageInfo = relatedUnitService.dataGrid(vo);
		doOwnBusi(pageInfo);
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200, pageInfo);
	}

	private void doOwnBusi(PageInfo<RelatedUnitDto> pageInfo) {
		List<RelatedUnitDto> list = pageInfo.getList();
		
		if(list != null && list.size()>0) {
			for (RelatedUnitDto relatedUnitDto : list) {
				
				List<SectionDto> sectionList = new ArrayList<>();
				
				//对sectionIds做处理
				String[] arr = relatedUnitDto.getSectionIds().replaceAll("\\[", "").replaceAll("\\]", "").split(",");
				for (String sid : arr) {
					 SectionDto sdto = sectionService.getSectionById(DataTypeUtil.toInteger(sid));
					 sectionList.add(sdto);
				}
				 
				relatedUnitDto.setSectionList(sectionList);
			}
		}
	}
	
	/** 根据主键查询单位信息
	 *
	 * @param  id 单位id
	 * @return {"msg":"请求成功","code":"200","data":{"id":2,"name":"上海市第一人民医院","contact":"王梦飞","tel":"13702569817","unitType":1,"unitTypeName":"医院","address":"徐汇区人民路001号","longitude":"112.494080","latitude":"37.706119","sectionIds":"1,2,3,4,5","sectionList":null,"crtTime":1531105196000}}  
	 * @throws
	 * @author shaosen
	 * @date 2018年7月9日
	 */
	@RequestMapping(value="relatedunits/{id}",method=RequestMethod.GET)
	public JSONObject getDtoById(@PathVariable Integer id ) {
		RelatedUnitDto dto = relatedUnitService.getDtoById(id);
		
		if(dto != null) {
			String ids = dto.getSectionIds().replaceAll("\\[", "");
			ids = ids.replaceAll("\\]", "");
			dto.setSectionIds(ids);
		}
		return CommonUtil.returnStatusJson(StatusCodeEnum.S_200,dto);
	}
	
	/**根据sectionId查询单位列表
	 * 
	 * @param  id 仓段id（sectionId）
	 * @return {"msg":"请求成功","code":"200","data":[{"id":5,"name":"山姆大叔会员店","contact":"大刘","tel":"18702563019","unitType":4,"unitTypeName":"商场","address":"锦绣路190号","longitude":"112.494085","latitude":"37.706156","sectionIds":"2,4","sectionList":null,"crtTime":1531113383000},{"id":2,"name":"上海市第一人民医院","contact":"王梦飞","tel":"13702569817","unitType":1,"unitTypeName":"医院","address":"徐汇区人民路001号","longitude":"112.494080","latitude":"37.706119","sectionIds":"1,2,3,4,5","sectionList":null,"crtTime":1531105196000}]}  
	 * @throws
	 * @author shaosen
	 * @date 2018年7月9日
	 */
	@RequestMapping(value="sections/{id}/relatedunits",method=RequestMethod.GET)
	public JSONObject getDtoListBySectionId(@PathVariable Integer id) {
		List<RelatedUnitDto> list = relatedUnitService.getDtoListBySectionId(id);
		return  CommonUtil.returnStatusJson(StatusCodeEnum.S_200,list);
	}
	
	/**根据sectionId和单位类型查询单位列表 
	 * @param id
	 * @param unitType
	 * @return   {"msg":"请求成功","code":"200","data":[{"id":2,"name":"上海市第一人民医院","contact":"王梦飞","tel":"13702569817","unitType":1,"unitTypeName":"医院","address":"徐汇区人民路001号","longitude":"112.49326617606197","latitude":"37.70596780087469","sectionIds":"1,2,3,4,5","sectionList":null,"crtTime":1531105196000}]}
	 * @author shaosen
	 * @Date 2018年11月23日
	 */
	@RequestMapping(value="sections/{id}/relatedunits/unitType/{unitType}",method=RequestMethod.GET)
	public JSONObject getDtoListBySecionIdAndUnitType(@PathVariable("id") Integer id,
			@PathVariable("unitType")Integer unitType) {
		List<RelatedUnitDto> list = relatedUnitService.getDtoListBySecionIdAndUnitType(id,unitType);
		return  CommonUtil.returnStatusJson(StatusCodeEnum.S_200,list);
	}
	
}
