package fz.cs.daoyun.web.controller;


import fz.cs.daoyun.domain.Menu;
import fz.cs.daoyun.domain.Permission;
import fz.cs.daoyun.service.IMenuService;
import fz.cs.daoyun.service.IPermissionService;
import fz.cs.daoyun.service.impl.MenuUtils;
import fz.cs.daoyun.utils.tools.Result;

import fz.cs.daoyun.utils.tools.ResultCodeEnum;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/permission")
public class PermissionController {
    @Autowired
    private IPermissionService permissionService;

    @Autowired
    private IMenuService menuService;

    /*
    * 查询所有的权限
    * */
    @ResponseBody
    @RequestMapping("/findAllPermission")
    @RequiresPermissions("permission:select")
    public Result getAllPermission(){
        List<Permission> permissions = permissionService.findAll();
        return Result.success(permissions);
    }

    /* 通过权限名查询*/
    @RequiresPermissions("permission:select")
    @RequestMapping("/findByName")
    public Result findByName(@RequestParam("authName")String authName){
        Permission permission = permissionService.findByName(authName);
        return Result.success(permission);
    }

    /*通过Id查询*/
    @RequiresPermissions("permission:select")
    @RequestMapping("/findById")
    public Result findByName(@RequestParam("id")Integer id){
        Permission permission = permissionService.findById(id);
        return Result.success(permission);
    }


    /*添加权限， 传入实体*/
    @RequiresPermissions("permission:add")
    @PostMapping("/create")
    public Result createPermission(@RequestParam("auth") Permission auth){
        try {
            Menu byName = menuService.findByName(auth.getName());
            if(byName != null){
                permissionService.AddPermission(auth);
                return Result.success();
            }

            return Result.failure(ResultCodeEnum.NOT_IMPLEMENTED);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(ResultCodeEnum.NOT_IMPLEMENTED);
        }
    }

    /*添加权限，出入具体属性*/
    @RequiresPermissions("permission:add")
    @PostMapping("/add")
    public Result addPermission(@RequestParam("authName") String authName, @RequestParam("module") String module){
        try {

            Menu byName = menuService.findByName(authName);
            if(byName != null){
                Permission p = new Permission();
                p.setName(authName);
                p.setType(module);
                permissionService.AddPermission(p);
                return Result.success();
            }
           return  Result.failure(ResultCodeEnum.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(ResultCodeEnum.NOT_IMPLEMENTED);
        }
    }

    /*更新权限
    * @Param authName: name
    * @Param module: type
    * */
    @RequiresPermissions("permission:update")
    @PostMapping("/edit")
    public Result updatePermission(@RequestParam("id") Integer id, @RequestParam("authName") String authName, @RequestParam("module") String module){
        try {

            Menu byName = menuService.findByName(authName);
            if(byName != null){

                Permission p = permissionService.findById(id);
                p.setName(authName);
                p.setType(module);
                permissionService.update(p);
                return Result.success();
            }

            return  Result.failure(ResultCodeEnum.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(ResultCodeEnum.NOT_IMPLEMENTED);
        }
    }

    /*删除权限*/
    @RequiresPermissions("permission:delete")
    @PostMapping("/delete")
    public Result deletePermission(@RequestBody Permission permission){
        Integer id = permission.getPermissionId();
        try {
            permissionService.deletePermission(id);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(ResultCodeEnum.NOT_IMPLEMENTED);
        }
    }


    /*获取所有的权限类型*/
    @RequiresPermissions("permission:select")
    @GetMapping("/getTypes")
    public Result<List<String>> getTypes(){
        try {
            List<String> types = permissionService.getTypes();
            return Result.success(types);
        } catch (Exception e) {
            e.printStackTrace();
            return  Result.failure(ResultCodeEnum.BAD_REQUEST);
        }
    }

    /*获取所有可以添加的name*/
    @RequiresPermissions("permission:select")
    @GetMapping("/getNames")
    public Result<List<String>> getNames(){
        try {
            List<String> names = new ArrayList<String>();
            List<MenuUtils> menus = menuService.findAllMenus();
            for (MenuUtils n: menus
                 ) {
                if(n.getMenu().getParentMenuId() == 0){
                    names.add(n.getMenu().getMenuName());
                }
            }
            return Result.success(names);
        } catch (Exception e) {
            e.printStackTrace();
            return  Result.failure(ResultCodeEnum.BAD_REQUEST);
        }
    }


    /*添加权限(多个)*/
    @RequiresPermissions("permission:add")
    @PostMapping("/addPermissions")
    public Result addPermissions(@RequestParam("type")String type, @RequestParam("names")  Object names){
        List<String> nameList = new ArrayList<String>();
        String[] pre_names = ((String) names).replace("[", "").replace("]", "").split(",");
        List<String> ns = Arrays.asList(pre_names);
        for (String m : ns
             ) {
            m = m.replace("\"", "");
            nameList.add(m);
            Menu menu = menuService.findByName((String) m);
            if(menu == null){
                return  Result.failure(ResultCodeEnum.BAD_REQUEST);
            }
        }
        System.out.println(nameList);

        try {
            for (String p: nameList
            ) {

                Permission permission = new Permission();
                permission.setType(type);
                permission.setName(p);
                permission.setCreator((String)SecurityUtils.getSubject().getPrincipal());
                permission.setModificationdate(new Date());
                permission.setModifier((String)SecurityUtils.getSubject().getPrincipal());
                permission.setCreationdate(new Date());
                permissionService.AddPermission(permission);
            }
            return   Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return  Result.failure(ResultCodeEnum.BAD_REQUEST);
        }
    }
}
