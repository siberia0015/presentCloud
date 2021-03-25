package fz.cs.daoyun.service;

import fz.cs.daoyun.domain.Passport;
import fz.cs.daoyun.domain.User;
import fz.cs.daoyun.mapper.PassportMapper;
import fz.cs.daoyun.mapper.UserMapper;
import fz.cs.daoyun.utils.tools.Md5Util;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-1-28
 * <p>Version: 1.0
 */
@Component
public class PasswordHelper {

    private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();

    @Value("${password.algorithmName}")
    private String algorithmName = "md5";
    @Value("${password.hashIterations}")
    private int hashIterations = 2;
    @Resource
    private UserMapper userMapper;

    @Resource
    private PassportMapper passportMapper;



    public void setRandomNumberGenerator(RandomNumberGenerator randomNumberGenerator) {
        this.randomNumberGenerator = randomNumberGenerator;
    }

    public void setAlgorithmName(String algorithmName) {
        this.algorithmName = algorithmName;
    }

    public void setHashIterations(int hashIterations) {
        this.hashIterations = hashIterations;
    }

    public User encryptPassword( User user) {
        String password = user.getPassword();
        String salt = user.getSalt();
//        String CredentialsSalt = user.getName() + salt;
        String CredentialsSalt = user.getName();
        user.setSalt(randomNumberGenerator.nextBytes().toHex());
        String username = user.getName();

//        String newPassword = new SimpleHash(
//                algorithmName,
//                password,
//                ByteSource.Util.bytes(CredentialsSalt),
//                hashIterations).toHex();
        String newPassword = Md5Util.encrypt(username, password);
        user.setPassword(newPassword);
        return user;
    }
}
