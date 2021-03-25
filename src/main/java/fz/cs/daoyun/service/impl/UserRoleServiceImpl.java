package fz.cs.daoyun.service.impl;


import fz.cs.daoyun.domain.Role;
import fz.cs.daoyun.domain.User;
import fz.cs.daoyun.domain.UserRole;
import fz.cs.daoyun.mapper.UserMapper;
import fz.cs.daoyun.mapper.UserRoleMapper;
import fz.cs.daoyun.service.IRoleService;
import fz.cs.daoyun.service.IUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class UserRoleServiceImpl  implements IUserRoleService {
    @Resource
    private UserRoleMapper userRoleMapper;
    @Resource
    private UserMapper userMapper;
    @Autowired
    private IRoleService roleService;

    @Override
    public List<UserRole> findByUserId(Long userid) {
        return userRoleMapper.selectByUserId(userid);
    }

    @Override
    public List<UserRole> findByUserName(String name) {
        User user = userMapper.selectByName(name);
        return userRoleMapper.selectByUserId(user.getUserId());
    }

    @Override
    public List<UserRole> findByUserTel(Long tel) {
        User user = userMapper.selectByTel(tel);
        return userRoleMapper.selectByUserId(user.getUserId());
    }

    @Override
    public List<UserRole> findByRoleName(String rolename) {
        Role role = roleService.findByRoleName(rolename);
        return (List)userRoleMapper.selectByRoleId(role.getRoleId());
    }

    @Override
    public List<UserRole> findByRoleId(Integer roleId) {
        return userRoleMapper.selectByRoleId(roleId);
    }

    @Override
    public List<User> findUserByRoleName(String rolename) {
        List<User> users = null;
        Role role = roleService.findByRoleName(rolename);
        List<UserRole> userRoles = userRoleMapper.selectByRoleId(role.getRoleId());

        for(UserRole userRole : userRoles){

            User user = userMapper.selectByPrimaryKey(userRole.getUserId());
            users.add(user);

        }
        return users;
    }

    @Override
    public List<User> findUserByRoleId(Integer id) {
        List<User> users = null;
        Role role = roleService.findById(id);
        List<UserRole> userRoles = userRoleMapper.selectByRoleId(role.getRoleId());
        for(UserRole userRole : userRoles){
            User user = userMapper.selectByPrimaryKey(userRole.getUserId());
            users.add(user);

        }

        return users;
    }

    @Override
    public List<Role> findRoleByUserName(String name) {
        List<Role> roles = new ArrayList<Role>();
        User user = userMapper.selectByName(name);
        List<UserRole> userRoles = userRoleMapper.selectByUserId(user.getUserId());
//        System.out.println(userRoles);
        for(UserRole userRole : userRoles){
            Role role = roleService.findById(userRole.getRoleId());
            roles.add(role);
        }
        return roles;
    }

    @Override
    public List<Role> findRoleByUserId(Long userid) {
        List<Role> roles = null;
        User user = userMapper.selectByPrimaryKey(userid);
        List<UserRole> userRoles = userRoleMapper.selectByUserId(user.getUserId());
        for(UserRole userRole : userRoles){
            Role role = roleService.findById(userRole.getRoleId());
            roles.add(role);
        }
        return roles;

    }

    @Override
    public List<Role> FindRoleByUsertel(Long tel) {
        List<Role> roles = null;
        User user = userMapper.selectByTel(tel);
        List<UserRole> userRoles = userRoleMapper.selectByUserId(user.getUserId());
        for(UserRole userRole : userRoles){
            Role role = roleService.findById(userRole.getRoleId());
            roles.add(role);
        }
        return roles;
    }

    @Override
    public void deleteByUserId(Long userid) throws Exception {

        userRoleMapper.deleteByuserId(userid);
    }




    @Override
    public void addRoleforUser(Integer userId, Integer roleId) throws  Exception{

        userRoleMapper.addRole(userId, roleId);
    }

}
