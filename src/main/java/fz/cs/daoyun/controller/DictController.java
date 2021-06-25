package fz.cs.daoyun.controller;


import fz.cs.daoyun.domain.Dict;
import fz.cs.daoyun.domain.DictInfo;
import fz.cs.daoyun.service.IDictService;
import fz.cs.daoyun.utils.tools.Result;
import fz.cs.daoyun.utils.tools.ResultCodeEnum;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/dict")
public class DictController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IDictService dictService;

    /**
     * 查看所有字典
     * @return
     */
    @PostMapping("/findAllDict")
    public Result<List<Dict>> findAll(){
        logger.info("/findAllDict");
        List<Dict> dicts = new ArrayList<>();
        try{
              dicts = dictService.findAllDict();
            if (dicts != null) {
                logger.info(dicts.toString());
            } else {
                logger.info("无数据");
                return Result.failure(ResultCodeEnum.NO_DATA);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
       return Result.success(dicts);
    }

    /**
     * 通过字典的名字查询字典（为了获取字典ID和描述）
     * @param name
     * @return
     */
    //@RequiresUser
    @PostMapping("/findDictByDictName")
    public Result<List<Dict>> findDictByDictName(@RequestParam("name") String name){
        logger.info("/findDictByDictName");
        try {
            List<Dict> dicts = dictService.findDictByDictName(name);
            if (dicts.size() != 1) {
                return Result.failure(ResultCodeEnum.MORE_THAN_ONE);
            } else {
                return Result.success(dicts);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(ResultCodeEnum.BAD_REQUEST);
        }
    }

    /**
     * 通过字典名字查询字典项
     * @param dictEng
     * @return
     */
    @PostMapping("/findDictInfoByDictEng")
    public Result<List<DictInfo>> findDictInfoByDictName(@RequestParam("dictEng") String dictEng){
        logger.info("通过字典ID查询字典项");
        try {
            List<DictInfo> dictInfo = dictService.findDictInfoByDictEng(dictEng);
            return Result.success(dictInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(ResultCodeEnum.BAD_REQUEST);
        }
    }

    /**
     * 通过字典项的Key查询字典项
     * @param itemKey
     * @return
     */
    //@RequiresUser
    @PostMapping("/findDictInfoByItemKey")
    public Result findByItemKey(@RequestParam("itemKey")String itemKey){
        logger.info("/findDictInfoByItemKey");
        List<DictInfo> dictinfo = dictService.findByItemKey(itemKey);
        return Result.success(dictinfo);
    }

    /**
     * 添加字典
     * @param dict
     * @return
     */
    //@RequiresPermissions("dict:add")
    @PostMapping("/addDict")
    public Result addDict(@RequestBody Dict dict){
        logger.info("/addDict");
        try {
            dictService.addDict(dict);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(ResultCodeEnum.NOT_IMPLEMENTED);
        }
    }

    /**
     * 添加字典项
     * @param dictInfo
     * @return
     */
    //@RequiresPermissions("dict:add")
    @PostMapping("/addDictinfo")
    public Result addDictinfo(@RequestBody DictInfo dictInfo){
        logger.info("添加字典项");
        try {
            List<DictInfo> list = dictService.findByItemKey(dictInfo.getItemKey());
            for (DictInfo i : list) {
                if (Objects.equals(i.getDictEng(), dictInfo.getDictEng())) {
                    return Result.failure(ResultCodeEnum.KEY_DUPLICATE);
                }
            }
            dictService.addDictInfo(dictInfo);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(ResultCodeEnum.BAD_REQUEST);
        }
    }

    /**
     * 更新字典
     * @param dictInfo
     * @return
     */
    //@RequiresPermissions("dict:update")
    @PostMapping("/updateDictInfo")
    public Result update(@RequestBody DictInfo dictInfo){
        logger.info("更新字典");
        try {
            if (dictService.findByItemKey(dictInfo.getItemKey()) != null) {
                return Result.failure(ResultCodeEnum.KEY_DUPLICATE);
            }
            dictService.updateDictInfo(dictInfo);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(ResultCodeEnum.BAD_REQUEST);
        }

    }

    /**
     * 更新字典项的value值
     * @param id
     * @param value
     * @return
     */
    //@RequiresPermissions("dict:update")
    @PostMapping("/updateValue")
    public Result updateKeyValue(@RequestParam("id")Integer id,@RequestParam("value")String value){
        logger.info("updateValue");
        boolean b = dictService.alterItemValue(id, value);
        if(b == false){
            return Result.failure(ResultCodeEnum.PARAM_ERROR);
        }
        return Result.success();
    }

    /**
     * 删除字典
     * @param dictEng
     * @return
     */
    //@RequiresPermissions("dict:update")
    @PostMapping("/deleteDict")
    public Result delete(@RequestParam("dictEng")String dictEng){
        logger.info("/deleteDict");
        List<DictInfo> dictInfos = dictService.findDictInfoByDictEng(dictEng);
        try {
            for (DictInfo dictinfo :dictInfos
                 ) {
                dictService.deleteDictInfo(dictinfo.getId());
            }

            dictService.deleteDict(dictEng);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(ResultCodeEnum.NOT_IMPLEMENTED);
        }
    }

    /**
     * 删除字典项
     * @param dictInfoId
     * @return
     */
    //@RequiresPermissions("dict:delete")
    @PostMapping("/deleteDictInfo")
    public Result deleteDictInfo(@RequestParam("dictInfoId")Integer dictInfoId){
        logger.info("/deleteDictInfo");
        try {
            dictService.deleteDictInfo(dictInfoId);
            return Result.success();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Result.failure(ResultCodeEnum.BAD_REQUEST);
        }
    }

    /**
     * 更改排序
     */
    @PostMapping("/upward")
    public Result upward(@RequestParam("dictInfoId")Integer dictInfoId) {
        logger.info("上移1位");
        try{
            dictService.upward(dictInfoId);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(ResultCodeEnum.BAD_REQUEST);
        }
    }
}
