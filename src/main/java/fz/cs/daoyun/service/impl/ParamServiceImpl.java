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
        return params;
    }

    @Override
    public void update(String key_eng, String key_name, Integer val) throws Exception{
        paramMapper.update(key_eng, key_name, val);
    }

    @Override
    public Param findById(Integer id) throws  Exception{
        return paramMapper.selectByPrimaryKey(id);
    }

    @Override
    public Param findByKeyEng(String keyEng) throws Exception{
        return paramMapper.selectByKeyEng(keyEng);
    }

    @Override
    public void updateByRecord(Param param) {
        paramMapper.updateByPrimaryKey(param);
    }

    @Override
    public void insert(Param param) { paramMapper.insertSelective(param); }

    @Override
    public void delete(String keyEng) { paramMapper.deleteByKeyEng(keyEng); }
}
