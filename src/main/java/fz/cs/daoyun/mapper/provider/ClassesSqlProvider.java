package fz.cs.daoyun.mapper.provider;

import fz.cs.daoyun.domain.Classes;
import fz.cs.daoyun.domain.Dict;
import org.apache.ibatis.jdbc.SQL;
public class ClassesSqlProvider {

    public String insertSelective(fz.cs.daoyun.domain.Classes record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("t_class");

        if (record.getId() != null) {
            sql.VALUES("id", "#{id,jdbcType=INTEGER}");
        }

        if (record.getClassesId() != null) {
            sql.VALUES("classes_id", "#{classesId,jdbcType=INTEGER}");
        }

        if (record.getClassesName() != null) {
            sql.VALUES("classes_name", "#{classesName,jdbcType=VARCHAR}");
        }

        if (record.getSchool() != null) {
            sql.VALUES("school", "#{school,jdbcType=VARCHAR}");
        }

        if (record.getDepartment() != null) {
            sql.VALUES("department", "#{department,jdbcType=VARCHAR}");
        }

        if (record.getTeacherName() != null) {
            sql.VALUES("teacher_name", "#{teacher_name,jdbcType=VARCHAR}");
        }

        if (record.getTeacherId() != null) {
            sql.VALUES("teacher_id", "#{teacher_id,jdbcType=INTEGER}");
        }



        return sql.toString();
    }

    public String updateByPrimaryKeySelective(Classes record) {
        SQL sql = new SQL();
        sql.UPDATE("t_class");

        if (record.getClassesId() != null) {
            sql.SET("classes_id = #{classesId,jdbcType=INTEGER}");
        }

        if (record.getClassesName() != null) {
            sql.SET("classes_name = #{classesName,jdbcType=VARCHAR}");
        }

        if (record.getSchool() != null) {
            sql.SET("school = #{school,jdbcType=VARCHAR}");
        }

        if (record.getDepartment() != null) {
            sql.SET("department = #{department,jdbcType=VARCHAR}");
        }



        if (record.getTeacherName() != null) {
            sql.SET("teacher_name = #{teacher_name,jdbcType=VARCHAR}");
        }

        if (record.getTeacherId() != null) {
            sql.SET("teacher_id = #{getTeacher_id,jdbcType=INTEGER}");
        }

        sql.WHERE("id = #{id,jdbcType=INTEGER}");

        return sql.toString();
    }

    public static class DictSqlProvider {

        public String insertSelective(Dict record) {
            SQL sql = new SQL();
            sql.INSERT_INTO("t_dict");

            if (record.getId() != null) {
                sql.VALUES("id", "#{id,jdbcType=INTEGER}");
            }

            if (record.getName() != null) {
                sql.VALUES("name", "#{name,jdbcType=VARCHAR}");
            }

            if (record.getDescribe() != null) {
                sql.VALUES("describe", "#{describe,jdbcType=VARCHAR}");
            }

            return sql.toString();
        }

        public String updateByPrimaryKeySelective(Dict record) {
            SQL sql = new SQL();
            sql.UPDATE("t_dict");

            if (record.getName() != null) {
                sql.SET("name = #{name,jdbcType=VARCHAR}");
            }

            if (record.getDescribe() != null) {
                sql.SET("describe = #{describe,jdbcType=VARCHAR}");
            }

            sql.WHERE("id = #{id,jdbcType=INTEGER}");

            return sql.toString();
        }
    }
}
