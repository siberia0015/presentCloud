package fz.cs.daoyun.controller;

import fz.cs.daoyun.domain.Permission;
import fz.cs.daoyun.domain.Role;
import fz.cs.daoyun.domain.RolePermission;
import fz.cs.daoyun.mapper.RolePermissionMapper;
import fz.cs.daoyun.service.IPermissionService;
import fz.cs.daoyun.service.IRolePermissionService;
import fz.cs.daoyun.service.IRoleService;
import fz.cs.daoyun.service.impl.RolePermissionWrapper;
import fz.cs.daoyun.utils.tools.Result;
import fz.cs.daoyun.utils.tools.ResultCodeEnum;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private IRoleService roleService;

    @Autowired
    private IRolePermissionService rolePermissionService;

    @Autowired
    private IPermissionService permissionService;


    /*
    *查询所有的角色
     */
    // @RequiresPermissions("role:select")
    @RequestMapping("/findAllRoles")
    @ResponseBody
    public Result<List<Role>> findAllRoles(){
        List<Role> roles = roleService.findAll();
        return Result.success(roles);
    }

    /*
    * 添加角色*/
    // @RequiresPermissions("role:add")
    @ResponseBody
    @PostMapping("/create")
    public Result creteRole(@RequestParam("role")String role){
       Role r =  roleService.findByRoleName(role);
       if(r != null){
           return Result.failure(ResultCodeEnum.FAILED_USER_ALREADY_EXIST);
       }
       Role rol = new Role();
       rol.setRoleName(role);
       rol.setCreator((String) SecurityUtils.getSubject().getPrincipal());
       rol.setModificationdate(new Date());
       rol.setModifier((String)SecurityUtils.getSubject().getPrincipal());
        rol.setCreationdate(new Date());
       roleService.saveRole(rol);
       return Result.success();
    }

    /*
    * 删除角色
    * */
    @ResponseBody
    // @RequiresPermissions("role:delete")
    @PostMapping("/delete")
    public Result deleteBatchByIds(@NotNull @RequestParam("rolename") String rolename) {
        roleService.deleteRole(rolename);
        return Result.success();
    }

    /*
    * 修改角色
    * */
    @ResponseBody
    // @RequiresPermissions("role:update")
    @PostMapping("/update")
    public Result update(@RequestParam("id")Integer id, @RequestParam("rolename")String rolename) {
        Role role = new Role();
        try {
            role.setRoleId(id);
            role.setRoleName(rolename);
            role.setModificationdate(new Date());
            role.setModifier((String)SecurityUtils.getSubject().getPrincipal());
            roleService.update(role);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(ResultCodeEnum.BAD_REQUEST);
        }

    }


    /*
    * 查询角色权限
    * */
    @ResponseBody
    // @RequiresPermissions("role:select")
    @GetMapping("/getRolePermission")
    public Result getRolePermission(@RequestParam("rolename")String rolename){
        List<RolePermission>  permissions = rolePermissionService.findByRoleName(rolename);
        return  Result.success(permissions);
    }

    /*查詢角色权限（2）*/
    // @RequiresPermissions("role:select")
    @PostMapping("/findRolePermission")
    public Result findRolePermission(@RequestParam("rolename")String rolename){
        try {
            Role role = roleService.findByRoleName(rolename);
            List<Permission> permissions = rolePermissionService.findRolePermissionByRoleId(role.getRoleId());
            RolePermissionWrapper rolePermissionWrapper = new RolePermissionWrapper(role, permissions);
            return Result.success(rolePermissionWrapper);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(ResultCodeEnum.NOT_IMPLEMENTED);
        }

    }


    /*添加角色和权限*/
    // @RequiresPermissions("role:add")
    @PostMapping("/addRolePermission")
    public  Result addRolePermission(@RequestParam("rolename")  Object rolename,@RequestParam("permissions") List<?> permissions){
        List<String> permissionList = new ArrayList<String>();
        Role role = new  Role();
        role.setRoleName((String)rolename);
        role.setCreator((String) SecurityUtils.getSubject().getPrincipal());
        role.setModificationdate(new Date());
        role.setModifier((String)SecurityUtils.getSubject().getPrincipal());
        role.setCreationdate(new Date());
        if(permissions instanceof List<?>){

            for(Object o: (List<?>) permissions){
                String p = (String)o;
                p = p.replace("[", "").replace("]", "");
                p = p.replace("\"", "");
                p = p.replace("\"", "");
                permissionList.add((String)p);
            }
        }else {
            return Result.failure(ResultCodeEnum.BAD_REQUEST);
        }
        Role r = null;
        try {
            r = roleService.findByRoleName(role.getRoleName());
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(ResultCodeEnum.BAD_REQUEST);
        }
        if(r != null){
            return Result.failure(ResultCodeEnum.FAILED_USER_ALREADY_EXIST);
        }
        try {
            roleService.saveRole(role);

            for (String p:permissionList
                 ) {
                Permission permission = permissionService.findByType(p);
                Role tempRole = roleService.findByRoleName(role.getRoleName());
                rolePermissionService.addPermissionToRole(tempRole.getRoleId(), permission.getPermissionId());
            }
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(ResultCodeEnum.NOT_IMPLEMENTED);
        }
    }



    /*更新角色及权限*/
    // @RequiresPermissions("role:update")
    @PostMapping("/updateRolePermission")
    public  Result updateRolePermission(@RequestParam("roleId") Integer roleId, @RequestParam("rolename")String rolename, @RequestParam("permissions") List<?> permissions){
        try {
            Role role = new Role();
            role.setRoleName(rolename);
            role.setRoleId((Integer)roleId);
            roleService.update(role);
            List<String> permissionList = new ArrayList<String>();
            if(permissions instanceof List<?>){

                for(Object o: (List<?>) permissions){
                    String p = (String)o;
                    p = p.replace("[", "").replace("]", "");
                    p = p.replace("\"", "");
                    permissionList.add((String)p);
                }
            }else {
                return Result.failure(ResultCodeEnum.BAD_REQUEST);
            }
            List<RolePermission> rolePermissionList = null;
            try {
                rolePermissionList = rolePermissionService.findRolePermissionsByroleId(roleId);
            } catch (Exception e) {
                e.printStackTrace();
                return Result.failure(ResultCodeEnum.BAD_REQUEST);
            }
            for (RolePermission r:rolePermissionList
                 ) {
                try {
                    rolePermissionService.deleteById(r.getId());
                } catch (Exception e) {
                    e.printStackTrace();
                    return Result.failure(ResultCodeEnum.BAD_REQUEST);
                }
            }
            for (String p:permissionList
                 ) {
                try {
                    Permission permission = permissionService.findByType(p);
                    Role tempRole = roleService.findByRoleName(role.getRoleName());
                    rolePermissionService.addPermissionToRole(tempRole.getRoleId(), permission.getPermissionId());
                } catch (Exception e) {
                    e.printStackTrace();
                    return Result.failure(ResultCodeEnum.BAD_REQUEST);
                }
            }

            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(ResultCodeEnum.NOT_IMPLEMENTED);
        }
    }

    /*删除角色权限*/
    // @RequiresPermissions("role:delete")
    @PostMapping("/deleteRolePermission")
    public  Result deleteRolePermission(@RequestParam("roleid") String Roleid, @RequestParam("permissionid") String permissionid){
        try {
            List<RolePermission> rolePermissions = rolePermissionService.findRolePermissionsByroleId(Integer.parseInt(Roleid));
            for (RolePermission rolepermission:rolePermissions
                 ) {
                rolePermissionService.deleteById(rolepermission.getId());
            }
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(ResultCodeEnum.NOT_IMPLEMENTED);
        }
    }


}
