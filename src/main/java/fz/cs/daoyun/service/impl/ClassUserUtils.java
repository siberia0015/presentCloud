package fz.cs.daoyun.service.impl;

import fz.cs.daoyun.domain.Classes;
import fz.cs.daoyun.domain.User;

public class ClassUserUtils {
    private Integer id;

    private Integer classesId;

    private String classesName;

    private String school;

    private String department;

    private String teacher;

    private Integer teacherId;




    public void addClasses(Classes classes) {
        this.classesId = classes.getClassesId();
        this.classesName = classes.getClassesName();
        this.department = classes.getDepartment();

        this.id = classes.getId();
        this.school = classes.getSchool();

    }

    public void addUser(User user){
        this.teacher = user.getName();
        this.teacherId = Math.toIntExact(user.getUserId());

    }

    @Override
    public String toString() {
        return "ClassUser{" +
                "id=" + id +
                ", classesId=" + classesId +
                ", classesName='" + classesName + '\'' +
                ", school='" + school + '\'' +
                ", department='" + department + '\'' +
                ", teacher='" + teacher + '\'' +
                ", teacherId=" + teacherId +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClassesId() {
        return classesId;
    }

    public void setClassesId(Integer classesId) {
        this.classesId = classesId;
    }

    public String getClassesName() {
        return classesName;
    }

    public void setClassesName(String classesName) {
        this.classesName = classesName;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }




}
