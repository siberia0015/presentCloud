package fz.cs.daoyun.service.impl;


import fz.cs.daoyun.domain.Classes;
import fz.cs.daoyun.domain.User;
import fz.cs.daoyun.domain.UserClasses;
import fz.cs.daoyun.mapper.ClassesMapper;
import fz.cs.daoyun.mapper.UserClassesMapper;
import fz.cs.daoyun.mapper.UserMapper;
import fz.cs.daoyun.service.IClassesService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Transactional
@Service
public class ClassesServiceImpl  implements IClassesService {
    @Resource
    private ClassesMapper classesMapper;
    @Resource
    private UserClassesMapper userClassesMapper;
    @Resource
    private UserMapper userMapper;

    @Override
    public List<Classes> findAll() {
        return classesMapper.selectAll();
    }

    @Override
    public List<ClassUserUtils> findAllUserClasses() {
        List<ClassUserUtils> classusers = null;
        ClassUserUtils classUserUtils = new ClassUserUtils();
        List<UserClasses> userClasses = userClassesMapper.selectAll();
        for (UserClasses uc : userClasses
             ) {
            Integer classId = uc.getClassId();
            String userName = uc.getUserName();
            Classes classes = classesMapper.selectByPrimaryKey(classId);
            User user = userMapper.selectByName(userName);
            classUserUtils.addClasses(classes);
            classUserUtils.addUser(user);
            classusers.add(classUserUtils);
        }
        return classusers;
    }

    @Override
    public Classes findByClassID(Integer classesId) {
        return  classesMapper.selectByClassId(classesId);
    }

    @Override
    public ClassUserUtils findUserClassByClassID(Integer classesId) {
        ClassUserUtils classUserUtils = new ClassUserUtils();
        List<UserClasses> userClasses = userClassesMapper.selectByClassId(classesId);
        for (UserClasses u :userClasses
             ) {
            String userName = u.getUserName();
            User user = userMapper.selectByName(userName);
            classUserUtils.addUser(user);
        }

        Classes classes = classesMapper.selectByPrimaryKey(classesId);

        classUserUtils.addClasses(classes);


        return classUserUtils;
    }

    @Override
    public ClassUserUtils findByUserName(String name) {
        User user = userMapper.selectByName(name);
        List<UserClasses> userClasses = userClassesMapper.selectByUserName(name);
        ClassUserUtils classUserUtils = new ClassUserUtils();
        for (UserClasses u :userClasses
             ) {
            Integer classId = u.getClassId();
            Classes classes = classesMapper.selectByClassId(classId);

            classUserUtils.addUser(user);
            classUserUtils.addClasses(classes);
        }



        return classUserUtils;
    }

    @Override
    public void addClasses(Classes classes) throws Exception {
        classesMapper.insert(classes);
    }

    @Override
    public void update(Classes classes) throws Exception {
        classesMapper.updateByPrimaryKey(classes);
    }

    @Override
    public void updateByParam(Integer id, String classesName, String school, String department, String teacher, String desc) throws Exception {
        Classes classes = classesMapper.selectByPrimaryKey(id);
        classes.setClassesName(classesName);
        classes.setDepartment(department);
        classes.setSchool(school);
        List<UserClasses> userClasses = userClassesMapper.selectByClassId(classes.getClassesId());
        classesMapper.updateByPrimaryKey(classes);
        for (UserClasses u : userClasses
             ) {
            u.setUserName(teacher);

            userClassesMapper.updateByPrimaryKey(u);
        }



    }

    @Override
    public void delete(Integer classesId)throws  Exception {
        classesMapper.deleteByClassId(classesId);
    }

    @Override
    public void deleteByUsername(String username) {
        userClassesMapper.deleteByUsername(username);
    }

    @Override
    public UserClasses addClassToUser(Long userId, Integer classId)  throws  Exception{
        User user = userMapper.selectByPrimaryKey(userId);
        Classes classes = classesMapper.selectByClassId(classId);
        String userName = user.getName();
        String className = classes.getClassesName();
        userClassesMapper.insertClassToUser(userId, userName, classId, className);
        return userClassesMapper.selectByUserIdAndClassId(userId, classId);
    }

    @Override
    public void deleteClassToUser(String usernmae, Integer classid) throws  Exception{
        userClassesMapper.deleteClassToUser(usernmae, classid);
    }

    @Override
    public  List<UserClasses> findUser_ClassByClassid(Integer classes_id) throws Exception{
        return userClassesMapper.selectByClassId(classes_id);
    }

    @Override
    public UserClasses findUser_Class(Long user_id, Integer classes_id) throws Exception{
        return userClassesMapper.selectByUserIdAndClassId(user_id, classes_id);
    }

    @Override
    public void deleteUser_Class(Integer classesId)  throws  Exception{
        userClassesMapper.deleteByClassId(classesId);
    }

    @Override
    public List<Classes>  getCurrentusertClass(String name) throws  Exception {
        List<Classes> classesList = new ArrayList<Classes>();
        List<UserClasses> userClasses = userClassesMapper.selectByUserName(name);
        for (UserClasses uc:userClasses
             ) {
            Classes classes = classesMapper.selectByClassId(uc.getClassId());
            classesList.add(classes);
        }
        return classesList;
    }

    @Override
    public List<Classes> getCurrentUserCreateClass(Long id) throws  Exception {
        return classesMapper.selectByTeacherId(id);
    }
}
