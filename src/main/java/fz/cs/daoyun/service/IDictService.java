package fz.cs.daoyun.service;


import fz.cs.daoyun.domain.Dict;
import fz.cs.daoyun.domain.DictInfo;

import java.util.List;
import java.util.Map;

public interface IDictService {
    /*查询所有的字典（返回列表）*/
    public List<Dict> findAllDict();

    /*根据字典类型获取字典信息（返回列表）*/
    public List<Dict> findDictByDictName(String name);

    /*根据字典ItemKey获取字典值*/
    public DictInfo findByItemKey(String itemKey);

    /*修改字典信息*/
    public boolean updateDictInfo(DictInfo dictinfo);

    /*修改字典的itemValue*/
    public boolean alterItemValue(Integer infoId, String itemValue);

    /*修改字典类型*/
    public boolean altertype(Integer dictId, String type);

    /*添加字典*/
    public boolean addDict(Dict dict);

    /*删除字典根据ID*/
    public boolean deleteDict(String dictEng) throws Exception;

    /*新增字典项*/
    boolean addDictInfo(DictInfo dictinfo);

    /*删除字典项*/
    boolean deleteDictInfo(Integer dictinfoId) throws Exception;

    /*根据字典查找字典项*/
    List<DictInfo> findDictInfoByDictEng(String dictEng);
}
