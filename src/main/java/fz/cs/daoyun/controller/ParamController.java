package fz.cs.daoyun.controller;


import fz.cs.daoyun.domain.Param;
import fz.cs.daoyun.service.IParamService;
import fz.cs.daoyun.utils.tools.Result;
import fz.cs.daoyun.utils.tools.ResultCodeEnum;
import io.swagger.models.auth.In;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.subject.Subject;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ParamController {
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IParamService paramService;

    /**
     * 新增参数
     */
    @PostMapping("/param")
    public Result addParam(@RequestBody Param param) {
        logger.info("/addParam");
        try {
            paramService.insert(param);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(ResultCodeEnum.BAD_REQUEST);
        }
    }

    /**
     * 获取所有参数
     * @return
     */
    // @RequiresUser
    @GetMapping("allParam")
    public Result<List<Param>> getAllParam(){
        logger.info("/getAllParam");
        try {
            List<Param> params = paramService.getAll();
            return Result.success(params);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(ResultCodeEnum.BAD_REQUEST);
        }
    }

    /**
     * 修改参数
     * @param id
     * @param key
     * @param value
     * @return
     */
    //@RequiresPermissions("param:update")
    @PutMapping("/paramByID")
    public Result update(@RequestParam("id") Integer id, @RequestParam("key")String key, @RequestParam("value") Integer value){
        logger.info("/updateParamByID");
        try {
            paramService.update(id, key , value);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(ResultCodeEnum.BAD_REQUEST);
        }
    }

    /**
     * 修改参数
     * @param param
     * @return
     */
    //@RequiresPermissions("param:update")
    @PutMapping("/paramByRecord")
    public  Result update(@RequestBody Param param) {
        logger.info("/updateByRecord");
        try {
            paramService.updateByRecord(param);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(ResultCodeEnum.BAD_REQUEST);
        }
    }

    /**
     * 删除参数
     */
    @DeleteMapping("/param")
    public Result delete(@RequestParam("id") Integer id) {
        logger.info("/deleteParam");
        try {
            paramService.delete(id);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(ResultCodeEnum.BAD_REQUEST);
        }
    }
}
