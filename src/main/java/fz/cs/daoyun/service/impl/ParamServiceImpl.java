package fz.cs.daoyun.service.impl;


import fz.cs.daoyun.domain.Param;
import fz.cs.daoyun.mapper.ParamMapper;
import fz.cs.daoyun.service.IParamService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
@Transactional
@Service
public class ParamServiceImpl  implements IParamService {

    @Resource
    private ParamMapper paramMapper;

    @Override
    public List<Param> getAll() throws  Exception{
        List<Param> params =  paramMapper.selectAll();
        System.out.println(params);
        return params;
    }

    @Override
    public void update(Integer id, String key, Integer val) throws Exception{
        paramMapper.update(id, key, val);
    }

    @Override
    public Param findById(Integer id) throws  Exception{
        return paramMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateByRecord(Param param) {
        paramMapper.updateByPrimaryKey(param);
    }

    @Override
    public void insert(Param param) { paramMapper.insertSelective(param); }

    @Override
    public void delete(Integer id) { paramMapper.deleteByPrimaryKey(id); }
}
