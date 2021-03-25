package fz.cs.daoyun.service;

import fz.cs.daoyun.domain.Role;
import fz.cs.daoyun.domain.User;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public interface IUserService  {
    public List<User> findAll();

    public User findById(Long id);

    public User findByName(String name);

    public User findByTel(Long tel);

    public User findByPassportId(Long id);

    public User findByToken(String token);

    public void saveUser(User user);

    @Transactional
    void saveUserAllInfo(User user);

    public void deleteUserByUserId(Long id);

    public void deleteUserByTel(Long tel);

    public void deleteUserByName(String name);

    public String GetPasswordByUserName(String name);

    public String GetPasswordByTel(Long tel);

    Set<String> queryRoles(String username);

    Set<String> queryPermissions(String username);

    void savePwd(User user);



    List<User> findAllGoPage(int page, int size, boolean count);

    void createUserAllInfo(User user);

    User exitsUser(String username);

    User findByEmail(String username);
}
