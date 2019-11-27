package com.bandweaver.tunnel.service.oam.space;

import com.bandweaver.tunnel.common.biz.itf.oam.CableTypeService;
import com.bandweaver.tunnel.common.biz.pojo.oam.CableType;
import com.bandweaver.tunnel.dao.oam.CableTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CableTypeServiceImpl implements CableTypeService {

    @Autowired
    private CableTypeMapper cableTypeMapper;

    @Override
    public List<CableType> getAllTypes() {
        return cableTypeMapper.getAllCables();
    }

    @Override
    public CableType getType(int id) {
        return cableTypeMapper.selectByPrimaryKey(id);
    }

    @Override
    public void insertRecord(CableType type) {
        type.setCrtTime(new Date());
        cableTypeMapper.insert(type);
    }

    @Override
    public void deleteRecord(int id) {
        cableTypeMapper.deleteByPrimaryKey(id);

    }

    @Override
    public void updateRecord(CableType type) {
        cableTypeMapper.updateByPrimaryKey(type);
    }
}
