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
    public List<Dict> findByDictType(String type) {
        return dictMapper.findByType(type);
    }

    @Override
    public DictInfo findByItemKey(String itemKey) {
        return dictInfoMapper.selectByItemKey(itemKey);
    }

    @Override
    public DictInfo findDictInfoById(Integer dictId) {
        return null;
    }

    @Override
    public boolean updateDictInfo(DictInfo dictinfo) {
         dictInfoMapper.updateByPrimaryKey(dictinfo);
         return true;
    }

    @Override
    public boolean alteritemValue(Integer infoid, String itemValue) {
        DictInfo dictInfo = dictInfoMapper.selectByPrimaryKey(infoid);
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
    public boolean deleteDict(Integer dictId) throws Exception {
        dictMapper.deleteByPrimaryKey(dictId);
        return true;
    }

    @Override
    public boolean addDictInfo(DictInfo dictinfo) {
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
    public List<Map<String, String>> findAllKV() {
        return null;
    }

    @Override
    public List<Map<String, String>> findByKVDictType(String type) {
        return null;
    }

    @Override
    public List<Map<String, String>> findKVByItemKey(String itemKey) {
        return null;
    }

    @Override
    public boolean alterKV(Integer id, String key, String value) {
        return false;
    }

    @Override
    public List<DictInfo> findDictInfoByDictId(Integer dictid) {

        return dictInfoMapper.selectByDictId(dictid);
    }
}
