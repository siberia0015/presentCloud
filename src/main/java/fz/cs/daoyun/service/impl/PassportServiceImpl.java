package fz.cs.daoyun.service.impl;


import fz.cs.daoyun.domain.Passport;
import fz.cs.daoyun.domain.User;
import fz.cs.daoyun.mapper.PassportMapper;
import fz.cs.daoyun.mapper.UserMapper;
import fz.cs.daoyun.service.IPassportService;
import fz.cs.daoyun.service.PasswordHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


@Transactional
@Service
public class PassportServiceImpl  implements IPassportService  {
    @Resource
    private UserMapper userMapper;

    @Resource
    private PassportMapper passportMapper;

    @Autowired
    private PasswordHelper passwordHelper;


    @Override
    public List<Passport> findAll() {
        return passportMapper.selectAll();
    }

    @Override
    public Passport FindByID(Long id) {
        return passportMapper.selectByPrimaryKey((Long) id);
    }

    @Override
    public Passport findByUserId(Long id) {
         return passportMapper.selectByUserId((Long) id);

    }

    @Override
    public Passport findByUserName(String name) {
        User user = userMapper.selectByName(name);

        return passportMapper.selectByUserId(user.getUserId());
    }

    @Override
    public Passport findByPhone(Long phone) {
        User user = userMapper.selectByPhone(phone);

        return passportMapper.selectByUserId(user.getUserId());
    }

    @Override
    public void addPassport(Passport passport, User user ) {

        passwordHelper.encryptPassword(user);
        passportMapper.insert(passport);
    }

    @Override
    public void deletePassportByUserName(String name) {
            User user = userMapper.selectByName(name);
            passportMapper.deleteByUserId(user.getUserId());
    }

    @Override
    public void deletePassportByUserId(Long id) {
        passportMapper.deleteByUserId(id);

    }

    @Override
    public void deletePassportByPhone(Long phone) {
        User user = userMapper.selectByPhone(phone);
        passportMapper.deleteByUserId(user.getUserId());
    }

    @Override
    public Passport findByToken(String token) {
        return passportMapper.selectByToken(token);
    }

    @Override
    public String getSalt(String username) {

        return passportMapper.selectSalt( this.findByUserName(username).getUserId());
    }
}
