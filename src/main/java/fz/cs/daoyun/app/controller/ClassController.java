package fz.cs.daoyun.app.controller;


import fz.cs.daoyun.domain.Classes;
import fz.cs.daoyun.domain.User;
import fz.cs.daoyun.domain.UserClasses;
import fz.cs.daoyun.service.IClassesService;
import fz.cs.daoyun.service.ISignService;
import fz.cs.daoyun.service.IUserService;
import fz.cs.daoyun.service.impl.ClassUserUtils;
import fz.cs.daoyun.utils.tools.Result;
import fz.cs.daoyun.utils.tools.ResultCodeEnum;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;


@RestController
@RequestMapping("/app/class")
public class ClassController {
    /*班课管理模块*/

    @Autowired
    private IClassesService classesService;

    @Autowired
    private IUserService userService;
    @Autowired
    private ISignService iSignService;

    /*生成随机数*/
    public Integer getRandomNumber(){
        Integer START = 10000;   //定义范围开始数字

        Integer END =100000; //定义范围结束数字


        //创建Random类对象
        Random random = new Random();

        //产生随机数
        Integer number = random.nextInt(END - START + 1) + START;

        //打印随机数
//        System.out.println("产生一个"+START+"到"+END+"之间的随机整数："+number);
        return number;
    }





    /*创建班课（教师用户）*/
    @PostMapping("/createClass")
    @RequiresRoles(value = {"teacher"})
    public Result createClass(@RequestBody Classes myclass){
        try {
            Integer number = null;
            while (true){
                number = (int)getRandomNumber();
                Classes classes = classesService.findByClassID(number);
                if(classes == null){
                    break;
                }

            }
            myclass.setClassesId(number);
            String username = (String)SecurityUtils.getSubject().getPrincipal();
            User user = userService.findByName(username);
            myclass.setTeacherId(user.getName());
            myclass.setTeacherName(user.getNickname());
            classesService.addClasses(myclass);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(ResultCodeEnum.BAD_REQUEST);
        }
    }


    /*删除班课（教师用户）*/
    @RequiresPermissions("class:delete")
    @PostMapping("/deleteClass")
    public Result deleteClass(@RequestParam("classId") Object classId){
        System.out.println(classId);

        Integer classid = Integer.parseInt((String)classId);
//        Classes classes = classesService.findByClassID(classid);
        try {
//
//            iSignService.deleteByClassid(classes.getId());
//            classesService.deleteUser_Class(classid);
            classesService.delete(classid);

            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(ResultCodeEnum.BAD_REQUEST);
        }
    }

    /*为给定用户（当前用户）添加班课（学生用户）*/
    @RequiresPermissions("class:add")
    @PostMapping("/addClasstoUser")
    public Result addClasstoUser(@RequestParam("usernmae") String usernmae, @RequestParam("classesId") String classesId){
        Integer classid = Integer.parseInt(classesId);
        try {
            List<UserClasses> user_classByClassid = classesService.findUser_ClassByClassid(classid);
            for (UserClasses uc :user_classByClassid
                 ) {
                if(uc.getUserName().equals(usernmae)){
                    return Result.failure(ResultCodeEnum.BAD_REQUEST);
                }
            }
            classesService.addClassToUser(usernmae, classid);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(ResultCodeEnum.BAD_REQUEST);
        }
    }



    /*为给定用户（当前用户）删除指定班课（学生用户）*/
    @RequiresPermissions("class:delete")
    @PostMapping("/deleteClasstoUser")
    public Result deleteClasstoUser(@RequestParam("username") String username, @RequestParam("classesId") String classesId){
        Integer classid = Integer.parseInt(classesId);
        try {
            classesService.deleteClassToUser(username, classid);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(ResultCodeEnum.BAD_REQUEST);
        }
    }


    /*查询所有班课*/
    @RequiresPermissions("class:select")
    @GetMapping("/findAll")
    public Result<List<Classes>> findAll(){
        try {
            List<Classes> classes = classesService.findAll();
            return Result.success(classes);

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


    /*查询当前用户的班课*/
    @RequiresUser
    @GetMapping("getCurrentusertClass")
    public Result<List<Classes> > getCurrentusertClass(){
        String principal = (String) SecurityUtils.getSubject().getPrincipal();
        User user = userService.findByName(principal);
        try {
            List<Classes> currentusertClass = classesService.getCurrentusertClass(user.getName());
            return Result.success(currentusertClass);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(ResultCodeEnum.BAD_REQUEST);
        }

    }


    /*获取当前用户创建的课程列表*/
    @RequiresRoles(value = {"teacher"})
    @GetMapping("getCurrentUserCreateClass")
    public Result<List<Classes>> getCurrentUserCreateClass(){
        String principal = (String) SecurityUtils.getSubject().getPrincipal();
        User user = userService.findByName(principal);
        try {
            List<Classes> classess = classesService.getCurrentUserCreateClass(user.getName());
            return Result.success(classess);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(ResultCodeEnum.BAD_REQUEST);
        }
    }



}
