package fz.cs.daoyun.service;

import fz.cs.daoyun.domain.Permission;
import fz.cs.daoyun.domain.Role;
import fz.cs.daoyun.domain.RolePermission;

import java.util.List;

public interface IRolePermissionService {

    public List<RolePermission> findAll();

    public List<RolePermission> findByRoleId(Integer id);

    public List<RolePermission> findByPermissionId(Integer id);

    public List<RolePermission> findByRoleName(String name);

    public List<RolePermission> findByPermissionName(String name);

    public List<Permission> findRolePermissionByRoleName(String name);

    public List<Permission> findRolePermissionByRoleId(Integer id);

    public List<Role> findPermissionRoleByPermissionId(Integer id);

    List<RolePermission> findRolePermissionsByroleId(int parseInt) throws Exception;

    void deleteById(Integer id) throws Exception;

    void addPermissionToRole(Integer roleId, Integer permissionId);
}
