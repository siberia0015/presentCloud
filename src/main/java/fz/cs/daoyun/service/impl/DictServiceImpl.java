package fz.cs.daoyun.service.impl;

import fz.cs.daoyun.domain.Dict;
import fz.cs.daoyun.domain.DictInfo;
import fz.cs.daoyun.mapper.DictInfoMapper;
import fz.cs.daoyun.mapper.DictMapper;
import fz.cs.daoyun.service.IDictService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Transactional
@Service
public class DictServiceImpl implements IDictService {

    @Resource
    private DictMapper dictMapper;
    @Resource
    private DictInfoMapper dictInfoMapper;

    @Override
    public List<Dict> findAllDict() {
        return dictMapper.findall();
    }

    @Override
    public List<Dict> findDictByDictName(String name) {
        return dictMapper.findDictByDictName(name);
    }

    @Override
    public DictInfo findByItemKey(String itemKey) {
        return dictInfoMapper.selectByItemKey(itemKey);
    }

    @Override
    public boolean updateDictInfo(DictInfo dictinfo) {
        String eng = dictinfo.getDictEng();
        Boolean flag = dictinfo.getIsdefault();
        if (flag == true) {
            dictInfoMapper.updateDefault(false, eng);
        }
        dictInfoMapper.updateByPrimaryKeySelective(dictinfo);
        return true;
    }

    @Override
    public boolean alterItemValue(Integer infoId, String itemValue) {
        DictInfo dictInfo = dictInfoMapper.selectByPrimaryKey(infoId);
        dictInfo.setItemValue(itemValue);
        dictInfoMapper.updateByPrimaryKey(dictInfo);

        return true;
    }

    @Override
    public boolean altertype(Integer dictId, String type) {
        return false;
    }

    @Override
    public boolean addDict(Dict dict) {
        dictMapper.insert(dict);
        return true;
    }

    @Override
    public boolean deleteDict(String dictEng) throws Exception {
        dictMapper.deleteByEng(dictEng);
        return true;
    }

    @Override
    public boolean addDictInfo(DictInfo dictinfo) {
        String eng = dictinfo.getDictEng();
        Boolean flag = dictinfo.getIsdefault();
        if (flag == true) {
            dictInfoMapper.updateDefault(false, eng);
        }
        dictInfoMapper.insert(dictinfo);
        return true;
    }

    @Override
    public boolean deleteDictInfo(Integer dictinfoId) throws  Exception {
        Integer id =  dictInfoMapper.deleteByPrimaryKey(dictinfoId);

        if (id!=0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<DictInfo> findDictInfoByDictEng(String dictEng) {
        return dictInfoMapper.selectByDictEng(dictEng);
    }
}
