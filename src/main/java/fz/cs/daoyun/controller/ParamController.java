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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ParamController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IParamService paramService;

    /**
     * 新增参数
     * @param param
     * @return
     */
    @PostMapping("/addParam")
    public Result addParam(@RequestBody Param param) {
        logger.info("/addParam");
        try {
            if (paramService.findByKeyEng(param.getKeyEng()) != null) {
                return Result.failure(ResultCodeEnum.KEY_DUPLICATE);
            }
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
     * @param keyEng
     * @param keyName
     * @param value
     * @return
     */
    //@RequiresPermissions("param:update")
    @PostMapping("/updateParam")
    public Result update(@RequestParam("keyEng")String keyEng, @RequestParam("keyName")String keyName, @RequestParam("value") Integer value){
        logger.info("/updateParam");
        try {
            paramService.update(keyEng, keyName, value);
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
    @PostMapping("/paramByRecord")
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
    @PostMapping("/deleteParam")
    public Result delete(@RequestParam("keyEng") String keyEng) {
        logger.info("/deleteParam");
        try {
            paramService.delete(keyEng);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(ResultCodeEnum.BAD_REQUEST);
        }
    }
}
