package fz.cs.daoyun.service;

import fz.cs.daoyun.domain.Classes;
import fz.cs.daoyun.domain.UserClasses;
import fz.cs.daoyun.service.impl.ClassUserUtils;

import java.util.HashMap;
import java.util.List;

public interface IClassesService {
    /*查询所有的班课*/
    public List<Classes> findAll();

    List<ClassUserUtils> findAllUserClasses();

    /*根据班课号查询班课*/
    public Classes findByClassID(Integer classesId);

    public ClassUserUtils findUserClassByClassID(Integer classesId);

    /*根据教师编号查询（用户名）*/
    public ClassUserUtils findByUserName(String name);

    /*添加班课*/
    public void addClasses(Classes classes) throws  Exception;

    /*更新班课（参数为实体）*/
    public void update(Classes classes) throws  Exception;

    /*更新班课， 参数为具体属性*/
    public void updateByParam(Integer id, String classesName, String school, String department, String teacher, String desc) throws  Exception;

    /*删除班课*/
    public void delete(Integer classesId) throws Exception;

    void deleteByUsername(String username);

    UserClasses addClassToUser(Long userId, Integer classId) throws Exception;

    void deleteClassToUser(String usernmae, Integer classid) throws Exception;

    List<UserClasses> findUser_ClassByClassid(Integer classes_id) throws Exception;

    UserClasses findUser_Class(Long user_id, Integer classes_id) throws Exception;

    void deleteUser_Class(Integer classesId) throws Exception;

    List<Classes>  getCurrentusertClass(String name) throws Exception;

    List<Classes> getCurrentUserCreateClass(Long id) throws Exception;
}
