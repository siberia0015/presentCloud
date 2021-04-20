package fz.cs.daoyun.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import fz.cs.daoyun.domain.Passport;
import fz.cs.daoyun.domain.Permission;
import fz.cs.daoyun.domain.Role;
import fz.cs.daoyun.domain.User;
import fz.cs.daoyun.mapper.RoleMapper;
import fz.cs.daoyun.mapper.UserMapper;
import fz.cs.daoyun.service.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Transactional
@Service("UserService")
public class UserServiceImpl implements IUserService {
    @Resource
    private UserMapper userMapper;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IPassportService passportService;

    @Autowired
    private IPermissionService permissionService;

    @Autowired
    private IUserRoleService userRoleService;

    @Autowired
    private IRolePermissionService rolePermissionService;

    @Autowired
    private PasswordHelper passwordHelper;


    @Override
    public List<User> findAll() {
        List<User> users = userMapper.selectAll();
        List<User> us = new ArrayList<User>();
        for (User user:users
             ) {
            List<Role> roles = userRoleService.findRoleByUserName(user.getName());
            List<String> rolenames = new ArrayList<String>();
            for (Role r:roles
            ) {
                rolenames.add(r.getRoleName());
            }
            user.setRolenames(rolenames);
            us.add(user);
        }

        return us;
    }

    @Override
    public User findById(Long id) {

        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public User findByName(String name) {
        User user = userMapper.selectByName(name);
        if (user == null) return user;
        List<Role> roles = userRoleService.findRoleByUserName(user.getName());
        List<String> rolenames = new ArrayList<String>();
        for (Role r:roles
             ) {
            rolenames.add(r.getRoleName());
        }
        user.setRolenames(rolenames);

        return user;
    }

    @Override
    public User findByPhone(Long phone) {

        return userMapper.selectByPhone(phone);
    }

    @Override
    public User findByPassportId(Long id) {
        Passport passport = passportService.findByPhone(id);
        User  user = this.findById(passport.getUserId());
        return user;
    }

    @Override
    public User find(Long id) {
        User  user = userMapper.selectByName(id.toString());
        if (user == null) user = userMapper.selectByPhone(id);
        if (user == null) user = userMapper.selectByEmail(id.toString());
        return user;
    }

    @Override
    public User findByToken(String token) {
        Passport passport = passportService.findByToken(token);
        User user = this.findById(passport.getUserId());
        return user;
    }

    @Transactional
    @Override
    public void saveUser(User user) {
        try{
            user = passwordHelper.encryptPassword(user);
            userMapper.insert(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    @Override
    public void saveUserAllInfo(User user) {
        user = passwordHelper.encryptPassword(user);
        userMapper.insertAllinfo(user);
    }

    @Transactional
    @Override
    public void deleteUserByUserId(Long id) {
        userMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    @Override
    public void deleteUserByPhone(Long phone) {
        userMapper.deleteByPhone(phone);
    }

    @Transactional
    @Override
    public void deleteUserByName(String name) {
        userMapper.deleteByName(name);
    }

    @Override
    public String GetPasswordByUserName(String name) {
       Passport passport =  passportService.findByUserName(name);
        return passport.getPassword();
    }

    @Override
    public String GetPasswordByPhone(Long phone) {
        Passport passport = passportService.findByPhone(phone);
        return passport.getPassword();
    }

    @Override
    public Set<String> queryRoles(String username) {
        Set<String> roleName = new HashSet<String>();
        List<Role> roles = userRoleService.findRoleByUserName(username);
        for (Role role: roles
             ) {
            roleName.add(role.getRoleName());
        }
        return roleName;
    }

    @Override
    public Set<String> queryPermissions(String username) {
        Set<String> permissionName = new HashSet<String>();;
        List<Role> roles = userRoleService.findRoleByUserName(username);
        for (Role role :roles
             ) {
            List<Permission> permissions = rolePermissionService.findRolePermissionByRoleId(role.getRoleId());
            for (Permission permission: permissions
                 ) {
               /* permissionName.add(permission.getName());*/
                permissionName.add(permission.getType());
            }
        }
        return permissionName;
    }

    @Override
    public void savePwd(User user) {
        user = passwordHelper.encryptPassword(user);
        userMapper.updateByPrimaryKey(user);
    }

    @Override
    public List<User> findAllGoPage(int page, int size, boolean count) {
        PageHelper.startPage(page, size, true);
        List<User> users = userMapper.selectAll();

        for (User u:users
             ) {
            List<String> rolenames = new ArrayList<String>();
            List<Role> roles = userRoleService.findRoleByUserName(u.getName());
            for (Role r: roles
                 ) {
                rolenames.add(r.getRoleName());
            }
            u.setRolenames(rolenames);
        }
        return users;
    }

    @Override
    public void createUserAllInfo(User user) {
        user = passwordHelper.encryptPassword(user);
        userMapper.saveUserAllInfo(user);
    }

    @Override
    public User exitsUser(String username) {
        return userMapper.selectByName(username);
    }

    @Override
    public User findByEmail(String username) {
        return userMapper.selectByEmail(username);
    }

}
