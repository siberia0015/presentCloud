package fz.cs.daoyun.web.controller;

import fz.cs.daoyun.domain.Classes;
import fz.cs.daoyun.domain.User;
import fz.cs.daoyun.domain.UserClasses;
import fz.cs.daoyun.service.IClassesService;
import fz.cs.daoyun.service.IUserService;
import fz.cs.daoyun.service.impl.ClassUserUtils;
import fz.cs.daoyun.utils.tools.Result;
import fz.cs.daoyun.utils.tools.ResultCodeEnum;
import org.apache.shiro.authz.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/webClass")
public class WebClassesController {
    /*班课管理模块*/

    @Autowired
    private IClassesService classesService;

    @Autowired
    private IUserService userService;

    /*查询所有班课*/
    @GetMapping("/findAll")
    @RequiresUser
    public Result<List<Classes>> findAll(){
        try {
            List<Classes> classes = classesService.findAll();
            return Result.success(classes);

        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(ResultCodeEnum.BAD_REQUEST);
        }

    }


    /*根据班课号查询当前班课的所有用户 @RequestParam("classes_id")*/
    @RequiresUser
    @PostMapping("/findAllUserInCurrentClass")
    public  Result<List<User>> findAllUserInCurrentClass(@RequestParam("classes_id") String classes_id){

        Integer classId = Integer.parseInt((String)classes_id);
        try {
            List<UserClasses> userClasses =  classesService.findUser_ClassByClassid(classId);
            List<User> users = new ArrayList<User>();
            for (UserClasses u:userClasses
                 ) {
                String username = u.getUserName();
                User user = userService.findByName(username);

                users.add(user);
            }
            return Result.success(users);
        } catch (Exception e) {

            e.printStackTrace();
            return Result.failure(ResultCodeEnum.BAD_REQUEST);
        }

    }

    /*编辑班课*/
    @RequiresPermissions("class:update")
    @PostMapping("/update")
    public Result update(@RequestBody Classes classes){
        try {
            classesService.update(classes);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(ResultCodeEnum.BAD_REQUEST);
        }
    }
}
