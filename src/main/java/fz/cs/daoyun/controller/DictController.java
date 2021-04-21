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
    @GetMapping("/findAllDict")
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
    @GetMapping("/findDictByDictName")
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
     * 通过字典查询字典项
     * @param dict
     * @return
     */
    //@RequiresUser
    @GetMapping("/findDictInfoByDict")
    public Result<List<DictInfo>> findDictInfoByDict(@RequestBody Dict dict){
        logger.info("/findDictInfoByDict");
        try {
            List<DictInfo> dictIns = dictService.findDictInfoByDictId(dict.getId());
            return Result.success(dictIns);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(ResultCodeEnum.NOT_IMPLEMENTED);
        }
    }

    /**
     * 通过字典名字查询字典项
     * @param dictName
     * @return
     */
    @GetMapping("/findDictInfoByDictName")
    public Result<List<DictInfo>> findDictInfoByDictName(@RequestParam("dictName") String dictName){
        logger.info("/findDictInfoByDictName");
        logger.info("通过字典名查询字典ID");
        Integer dictId = null;
        try {
            List<Dict> dict = dictService.findDictByDictName(dictName);
            if (dict == null) {
                return Result.failure(ResultCodeEnum.NO_DATA);
            } else if (dict.size() != 1) {
                return Result.failure(ResultCodeEnum.MORE_THAN_ONE);
            }
            dictId = dict.get(0).getId();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(ResultCodeEnum.BAD_REQUEST);
        }
        logger.info("通过字典ID查询字典项");
        try {
            List<DictInfo> dictInfo = dictService.findDictInfoByDictId(dictId);
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
    @GetMapping("/findDictInfoByItemKey")
    public Result<DictInfo> findByItemKey(@RequestParam("itemKey")String itemKey){
        logger.info("/findDictInfoByItemKey");
        DictInfo dictinfo = dictService.findByItemKey(itemKey);
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
        logger.info("/addDictinfo");
        try {
            dictInfo.setItemValue(String.valueOf(new Random().nextInt(999999)));
            dictInfo.setSequence(1);
            dictService.addDictInfo(dictInfo);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(ResultCodeEnum.NOT_IMPLEMENTED);
        }
    }

    /**
     * 更新字典
     * @param dictInfo
     * @return
     */
    //@RequiresPermissions("dict:update")
    @PutMapping("/updateDictInfo")
    public Result update(@RequestBody DictInfo dictInfo){
        logger.info("/updateDictInfo");
        try {
            dictService.updateDictInfo(dictInfo);
            logger.info("修改dictInfo");
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(ResultCodeEnum.NOT_IMPLEMENTED);
        }

    }

    /**
     * 更新字典项的value值
     * @param id
     * @param value
     * @return
     */
    //@RequiresPermissions("dict:update")
    @PutMapping("/updateValue")
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
     * @param dictId
     * @return
     */
    //@RequiresPermissions("dict:update")
    @DeleteMapping("/deleteDict")
    public Result delete(@RequestParam("dictId")Integer dictId){
        logger.info("/deleteDict");
        List<DictInfo> dictInfos = dictService.findDictInfoByDictId(dictId);
        try {
            for (DictInfo dictinfo :dictInfos
                 ) {
                dictService.deleteDictInfo(dictinfo.getId());
            }

            dictService.deleteDict(dictId);
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
    @DeleteMapping("/deleteDictInfo")
    public Result deleteDictInfo(@RequestParam("dictInfoId")Integer dictInfoId){
        logger.info("/deleteDictInfo");
        try {
            dictService.deleteDictInfo(dictInfoId);
            return Result.success();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Result.failure(ResultCodeEnum.NOT_IMPLEMENTED);
        }
    }

}
