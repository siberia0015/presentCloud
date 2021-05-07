package fz.cs.daoyun.service.impl;

import fz.cs.daoyun.domain.Permission;
import fz.cs.daoyun.domain.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;




public class RolePermissionWrapper {

    public RolePermissionWrapper(Role role, List<Permission> permissions) {
        this.role = role;
        this.permissions = permissions;
    }


    private Role role;
    private List<Permission> permissions;

    @Override
    public String toString() {
        return "RolePermissionWrapper{" +
                "role=" + role +
                ", permissions=" + permissions +
                '}';
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
}
