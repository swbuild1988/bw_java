package com.bandweaver.tunnel.service.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bandweaver.tunnel.common.biz.constant.OutsideEnum;
import com.bandweaver.tunnel.common.biz.dto.StaffDto;
import com.bandweaver.tunnel.common.biz.itf.StaffService;
import com.bandweaver.tunnel.common.biz.pojo.Staff;
import com.bandweaver.tunnel.common.biz.pojo.common.User;
import com.bandweaver.tunnel.common.biz.vo.StaffVo;
import com.bandweaver.tunnel.common.platform.constant.Constants;
import com.bandweaver.tunnel.common.platform.constant.StatusCodeEnum;
import com.bandweaver.tunnel.common.platform.exception.BandWeaverException;
import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.DateUtil;
import com.bandweaver.tunnel.common.platform.util.PinyinUtil;
import com.bandweaver.tunnel.common.platform.util.PropertiesUtil;
import com.bandweaver.tunnel.common.platform.util.Sha256;
import com.bandweaver.tunnel.common.platform.util.StringTools;
import com.bandweaver.tunnel.dao.common.StaffMapper;
import com.bandweaver.tunnel.dao.common.UserMapper;
import com.github.pagehelper.Constant;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Service
public class StaffServiceImpl implements StaffService {


    @Autowired
    private StaffMapper staffMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public StaffDto getDtoById(Integer id) {
        return staffMapper.getDtoById(id);
    }

    @Override
    public List<StaffDto> getDtoListByCondition(StaffVo vo) {
        return staffMapper.getDtoListByCondition(vo);
    }

    @Override
    @Deprecated
    public void deleteById(Integer id) {
        staffMapper.deleteByPrimaryKey(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Staff staff) {

        //检查修改的登录账号是否被占用
        String account = staff.getAccount();
        User u = userMapper.getByName(account);
        if (!StringTools.isNullOrEmpty(u)) {
            if (u.getId().intValue() != staff.getId().intValue()) {
                throw new BandWeaverException(StatusCodeEnum.E_20009);
            }
        }

        staffMapper.updateByPrimaryKeySelective(staff);

        //同时修改账号表信息
        User user = new User();
        user.setId(staff.getId());
        user.setName(staff.getAccount());
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(Staff staff) {
    	staff.setCrtTime(new Date());
        try {
            //判断是否为外来人员，如果是则不生成账号信息
            if (staff.getOutside() != OutsideEnum.STAFF.getValue()) {
                staffMapper.insert(staff);
                return;
            }

            //如果是内部员工则需要生成账号

            //员工表和账号表为一对一关系，他们的id也相同
            String account = PinyinUtil.ToPinyin(staff.getName());//将员工姓名转换为拼音作为其登陆账号

            //保证账号唯一，如果有姓名相同的员工，把账号+ID作为新员工账号
            List<Staff> list = staffMapper.getByName(staff.getName());

            if (list != null && list.size() > 0) {
                int maxId = staffMapper.getMaxID();
                account = account + (maxId + 1);
            }

            staff.setCrtTime(DateUtil.getCurrentDate());
            staff.setAccount(account);
            staffMapper.insert(staff);

            String initPassword = PropertiesUtil.getString(Constants.INIT_PASSWORD);
            User user = new User();
            user.setId(staff.getId());
            user.setName(account);
            user.setPassword(Sha256.getSHA256StrJava(initPassword));//设置初始密码
            user.setCrtTime(staff.getCrtTime());
            userMapper.insert(user);
            LogUtil.info(user.getName());
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.error("插入员工数据失败：" + e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
    }

    @Override
    public List<StaffDto> getDtoList() {
        return getDtoListByCondition(new StaffVo());
    }

    @Override
    public List<StaffDto> getStaffsByCompanyIdAndDeptId(Integer companyId, Integer deptId) {
        return staffMapper.getStaffsByCompanyIdAndDeptId(companyId, deptId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(List<Integer> list) {
    	List<Integer> userIds = new ArrayList<>();
    	for(Integer id : list) {
    		StaffDto dto = getDtoById(id);
    		if(dto.getOutside().equals(OutsideEnum.STAFF.getValue()))
    			userIds.add(id);
    	}
        staffMapper.deleteBatch(list);
        if(userIds.size() > 0)
        	userMapper.deleteBatch(userIds);
    }

    @Override
    public PageInfo<StaffDto> dataGrid(StaffVo vo) {
        PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
        List<StaffDto> list = staffMapper.getDtoListByCondition(vo);
        return new PageInfo<>(list);
    }


}
