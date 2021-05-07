package fz.cs.daoyun.service;

import fz.cs.daoyun.domain.Passport;
import fz.cs.daoyun.domain.User;

import java.util.List;

public interface IPassportService {
    public List<Passport> findAll();

    public Passport FindByID(Long id);

    public Passport findByUserId(Long id);

    public Passport findByUserName(String name);

    public Passport findByPhone(Long phone);

    public void addPassport(Passport passport , User user);

    public void deletePassportByUserName(String name);

    public void deletePassportByUserId(Long id);

    public void deletePassportByPhone(Long phone);

    Passport findByToken(String token);

    public String getSalt(String userName);
}
