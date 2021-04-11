package fz.cs.daoyun.web.controller;


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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/param")
public class ParamController {

    @Autowired
    private IParamService paramService;


    /*获取所有参数*/
    // @RequiresUser
    @GetMapping("/getAllParam")
    public Result<List<Param>> getAllParam(){
        try {
            List<Param> params = paramService.getAll();
            return Result.success(params);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(ResultCodeEnum.BAD_REQUEST);
        }
    }


    /*修改参数*/
    @RequiresPermissions("param:update")
    @PostMapping("update")
    public  Result update(@RequestParam("id") Integer id, @RequestParam("key")String key, @RequestParam("value") Integer value){
//        Integer id = Integer.parseInt(id);
//        Integer val = Integer.parseInt(value);
        try {
            paramService.update(id, key , value);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(ResultCodeEnum.BAD_REQUEST);
        }
    }


    /*修改参数*/
    @RequiresPermissions("param:update")
    @PostMapping("updateByRecord")
    public  Result update(@RequestBody Param param) {

        try {
            paramService.updateByRecord(param);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(ResultCodeEnum.BAD_REQUEST);
        }
    }
}
