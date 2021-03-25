package fz.cs.daoyun.web.controller;


import fz.cs.daoyun.domain.Menu;
import fz.cs.daoyun.service.IMenuService;
import fz.cs.daoyun.service.impl.MenuUtils;
import fz.cs.daoyun.utils.tools.Result;
import fz.cs.daoyun.utils.tools.ResultCodeEnum;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private IMenuService menuService;


    /*查询所有菜单, 不包括子菜单*/
//    @RequiresUser
    @GetMapping("/findAll")
    public Result<List<Menu>> findAll(){
        List<Menu> all = menuService.findAll();
        return Result.success(all);
    }

    /*查询所有菜单， 包括子菜单*/
//    @RequiresUser
    @GetMapping("/findAllMenus")
    public Result<List<MenuUtils>> findAllMenus(){
        List<MenuUtils> all = menuService.findAllMenus();
        return Result.success(all);
    }

    /*根据父菜单id查询所有子菜单*/
//    @RequiresUser
    @PostMapping("/findAllSubMenuByParentId")
    public Result<List<Menu>> findAllSubMenuByParentId(@RequestParam("menuId") Object menuid){
        Integer parentid = Integer.parseInt((String)menuid);
        try {
            List<Menu> allSubMenus = menuService.findAllSubMenus(parentid);
            return Result.success(allSubMenus);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(ResultCodeEnum.BAD_REQUEST);
        }
    }


    /*根据menuname查询*/
    @RequiresUser
    @RequestMapping("/findByName")
    public Result findByName(@RequestParam("name")String name){
        Menu menu = menuService.findByName(name);
        return Result.success(menu);
    }

    /*根据Id查询*/
    @RequiresPermissions("menu:select")
    @RequestMapping("/findById")
    public Result findById(@RequestParam("id") Integer id){
        Menu menu = menuService.findById(id);
        return Result.success(id);
    }

    /*更新父菜单， 出入参数为实体*/
    @RequiresPermissions("menu:update")
    @PostMapping("/edit")
    public Result editMenu( @RequestBody Menu menu){
        try {
            menuService.updateMenu(menu);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(ResultCodeEnum.NOT_IMPLEMENTED);
        }
    }

    /*根据父菜单id， 更新子菜单*/
    @RequiresPermissions("menu:update")
    @PostMapping("updateByparentId")
    public  Result updateByParentId( @RequestBody Menu submenu){
        try {
            menuService.updateSubMenu(submenu);
            return  Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(ResultCodeEnum.NOT_IMPLEMENTED);
        }
    }

    /*更新父菜单， 请求为具体属性值*/
    @RequiresPermissions("menu:update")
    @PostMapping("/editByParam")
    public Result editByParam(@RequestParam("id") Integer id, @RequestParam("menuName")String menuName,
                              @RequestParam("menuIcon")String menuIcon,@RequestParam("menuLink")String menuLink,
                              @RequestParam("menuName")Integer menuSort,@RequestParam("isshow")Boolean isshow,
                              @RequestParam("ispage")Boolean ispage,@RequestParam("parentMenuId")Integer parentMenuId){
        Menu menu = menuService.findById(id);
        menu.setMenuName(menuName);
        menu.setMenuLink(menuLink);
        menu.setMenuIcon(menuIcon);
        menu.setMenuSort(menuSort);
        menu.setIspage(ispage);
        menu.setIsshow(isshow);
        menu.setParentMenuId(parentMenuId);
        try {
            menuService.updateMenu(menu);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(ResultCodeEnum.NOT_IMPLEMENTED);
        }
    }

    /*添加父级菜单*/
    @RequiresPermissions("menu:add")
    @PostMapping("/add")
    public Result addMenu(@RequestBody Menu menu){
        try {
            menuService.addMenu(menu);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(ResultCodeEnum.NOT_IMPLEMENTED);
        }
    }

    /*添加子级菜单*/
    @RequiresPermissions("menu:add")
    @PostMapping("/addSubMenu")
    public Result addSubMenu(@RequestBody Menu submenu){
        try {
            System.out.println(submenu);
            menuService.addSubMenu(submenu);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(ResultCodeEnum.NOT_IMPLEMENTED);
        }
    }


    /*删除菜单*/
    @RequiresPermissions("menu:delete")
    @GetMapping("delete")
    public Result deleteMenu(@RequestParam("id") Integer id){
        try {
            menuService.deleteSubMenu(id);
            menuService.deleteMenu(id);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(ResultCodeEnum.NOT_IMPLEMENTED);
        }
    }

    /*删除子菜单*/
    @RequiresPermissions("menu:delete")
    @GetMapping("deletesubmenu")
    public Result deletesubmenu(@RequestParam("subid") Integer subid){
        try {
            menuService.deleteSubMenu(subid);
            menuService.deleteMenu(subid);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(ResultCodeEnum.NOT_IMPLEMENTED);
        }
    }



}
