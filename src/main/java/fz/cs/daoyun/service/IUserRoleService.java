package fz.cs.daoyun.service;

import fz.cs.daoyun.domain.Role;
import fz.cs.daoyun.domain.User;
import fz.cs.daoyun.domain.UserRole;

import java.util.List;

public interface IUserRoleService {

    public List<UserRole> findByUserId(Long userid);

    public List<UserRole> findByUserName(String name);

    public List<UserRole> findByUserTel(Long tel);

    public List<UserRole> findByRoleName(String rolename);

    public List<UserRole> findByRoleId(Integer roleId);

//    public User findUserByRoleName(String rolename);
//
//    public User findUserByRoleId(Integer id);
//
//    public Role findRoleByUserName(String name);
//
//    public Role findRoleByUserId(Long userid);
//
//    public Role FindRoleByUsertel(Long tel);
    public List<User> findUserByRoleName(String rolename);

    public List<User> findUserByRoleId(Integer id);

    public List<Role> findRoleByUserName(String name);

    public List<Role> findRoleByUserId(Long userid);

    public List<Role> FindRoleByUsertel(Long tel);


    void  deleteByUserId(Long userid) throws Exception;


    void addRoleforUser(Integer userId, Integer roleId) throws  Exception;
}
