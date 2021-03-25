package fz.cs.daoyun.service.impl;

import fz.cs.daoyun.domain.Permission;
import fz.cs.daoyun.domain.Role;
import fz.cs.daoyun.domain.RolePermission;
import fz.cs.daoyun.mapper.RolePermissionMapper;
import fz.cs.daoyun.service.IPermissionService;
import fz.cs.daoyun.service.IRolePermissionService;
import fz.cs.daoyun.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;



@Transactional
@Service
public class RolePermissionServiceImpl  implements IRolePermissionService {

    @Autowired
    private IPermissionService permissionService;

    @Autowired
    private IRoleService roleService;

    @Resource
    private RolePermissionMapper rolePermissionMapper;




    @Override
    public List<RolePermission> findAll() {
        return rolePermissionMapper.selectALl();
    }

    @Override
    public  List<RolePermission> findByRoleId(Integer id) {
//        List<RolePermission> rolePermissionList = rolePermissionMapper.selectByRoleId(id);
        return rolePermissionMapper.selectByRoleId(id);
    }

    @Override
    public  List<RolePermission> findByPermissionId(Integer id) {
        return rolePermissionMapper.selectByPermissionId(id);
    }

    @Override
    public  List<RolePermission> findByRoleName(String name) {
        Role role = roleService.findByRoleName(name);
        return this.findByRoleId(role.getRoleId());
    }

    @Override
    public  List<RolePermission> findByPermissionName(String name) {
        Permission permission = permissionService.findByName(name);
        return this.findByPermissionId(permission.getPermissionId());
    }

    @Override
    public List<Permission>  findRolePermissionByRoleName(String name) {
        Role role = roleService.findByRoleName(name);
        List<Permission> permissions = null;
        List<RolePermission> rolePermissions = this.findByRoleId(role.getRoleId());
        for (RolePermission rolePermission: rolePermissions
             ) {
            Permission permission = permissionService.findById(rolePermission.getPermissionId());
            permissions.add(permission);
        }

        return permissions;
    }

    @Override
    public List<Permission>  findRolePermissionByRoleId(Integer id) {
        List<Permission> permissions = new ArrayList<Permission>();
        List<RolePermission> rolePermissions = this.findByRoleId(id);
        for (RolePermission rolePermission: rolePermissions
        ) {
            Integer permissionId = rolePermission.getPermissionId();
            Permission permission = permissionService.findById(permissionId);
            permissions.add(permission);
        }

        return permissions;

    }

    @Override
    public List<Role> findPermissionRoleByPermissionId(Integer id) {
        List<Role> roles = null;
        List<RolePermission> rolePermissions = this.findByPermissionId(id);
        for (RolePermission rolePermission: rolePermissions
        ) {
            Role role = roleService.findById(rolePermission.getRoleId());
           roles.add(role);
        }
        return roles;
    }

    @Override
    public List<RolePermission> findRolePermissionsByroleId(int parseInt) throws  Exception{
        List<RolePermission> rolePermissionList = rolePermissionMapper.selectByRoleId(parseInt);
        return rolePermissionList;
    }

    @Override
    public void deleteById(Integer id) throws Exception {
        rolePermissionMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void addPermissionToRole(Integer roleId, Integer permissionId) {
        rolePermissionMapper.insertByParam(roleId, permissionId);
    }
}
