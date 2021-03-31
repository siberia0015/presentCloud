package fz.cs.daoyun.service;

import fz.cs.daoyun.domain.Role;

import java.io.Serializable;
import java.util.List;

public interface IRoleService  {

    public List<Role> findAll();

    public  Role findByRoleName(String name);

    public Role findById(Integer id);

    public void saveRole(Role role);

    public void deleteRole(String name);

    void update(Role role) throws Exception;
}
