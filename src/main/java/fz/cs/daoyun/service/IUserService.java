package fz.cs.daoyun.service;

import fz.cs.daoyun.domain.Role;
import fz.cs.daoyun.domain.User;
import fz.cs.daoyun.domain.UserAuths;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public interface IUserService  {
    public List<User> findAll();

    public User findById(Long id);

    public User findByName(String name);

    public User findByPhone(Long phone);

    public User find(Long id);

    public User findByPassportId(Long id);

    public User findByToken(String token);

    public void saveUser(User user);

    @Transactional
    void update(User user);

    public void deleteUserByUserId(Long id);

    public void deleteUserByPhone(Long phone);

    public void deleteUserByName(String name);

    public String GetPasswordByUserName(String name);

    public String GetPasswordByPhone(Long phone);

    Set<String> queryRoles(String username);

    Set<String> queryPermissions(String username);

    void savePwd(User user);

    List<User> findAllGoPage(int page, int size, boolean count);

    void createUserAllInfo(User user);

    User exitsUser(String username);

    User findByEmail(String username);

    UserAuths oauthMobile(Long userId, String identityType, String identifier);
}
