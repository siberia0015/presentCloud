package fz.cs.daoyun.mapper.provider;

import fz.cs.daoyun.domain.Sign;
import fz.cs.daoyun.domain.UserClasses;
import org.apache.ibatis.jdbc.SQL;

public class UserClassesSqlProvider {

    public String insertSelective(UserClasses record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("t_user_class");

        if (record.getId() != null) {
            sql.VALUES("id", "#{id,jdbcType=INTEGER}");
        }

        if (record.getClassId() != null) {
            sql.VALUES("class_id", "#{classId,jdbcType=INTEGER}");
        }

        if (record.getUserName() != null) {
            sql.VALUES("user_name", "#{userName,jdbcType=VARCHAR}");
        }

        if (record.getClassesName() != null) {
            sql.VALUES("class_name", "#{class_name,jdbcType=VARCHAR}");
        }

        if (record.getUserId() != null) {
            sql.VALUES("user_id", "#{userName,jdbcType=BIGINT}");
        }

        if (record.getScore() != null) {
            sql.VALUES("score", "#{score,jdbcType=INTEGER}");
        }

        sql.VALUES("identity", "#{identity,jdbcType=TINYINT}");

        return sql.toString();
    }

    public String updateByPrimaryKeySelective(UserClasses record) {
        SQL sql = new SQL();
        sql.UPDATE("t_user_class");

        if (record.getClassId() != null) {
            sql.SET("class_id = #{classId,jdbcType=INTEGER}");
        }

        if (record.getUserName() != null) {
            sql.SET("user_name = #{userName,jdbcType=VARCHAR}");
        }

        if (record.getClassesName() != null) {
            sql.VALUES("class_name", "#{class_name,jdbcType=VARCHAR}");
        }

        if (record.getUserId() != null) {
            sql.VALUES("user_id", "#{userName,jdbcType=BIGINT}");
        }

        if (record.getScore() != null) {
            sql.VALUES("score", "#{score,jdbcType=INTEGER}");
        }

        sql.VALUES("identity", "#{identity,jdbcType=TINYINT}");

        sql.WHERE("id = #{id,jdbcType=INTEGER}");

        return sql.toString();
    }

    public static class SignSqlProvider {

        public String insertSelective(Sign record) {
            SQL sql = new SQL();
            sql.INSERT_INTO("t_sign");

            if (record.getId() != null) {
                sql.VALUES("id", "#{id,jdbcType=BIGINT}");
            }

            if (record.getUserId() != null) {
                sql.VALUES("user_id", "#{userId,jdbcType=BIGINT}");
            }

            if (record.getClassId() != null) {
                sql.VALUES("class_id", "#{classId,jdbcType=INTEGER}");
            }

            if (record.getSignTime() != null) {
                sql.VALUES("sign_time", "#{signTime,jdbcType=TIMESTAMP}");
            }

            if (record.getScore() != null) {
                sql.VALUES("score", "#{score,jdbcType=INTEGER}");
            }

            if (record.getStartSignId() != null) {
                sql.VALUES("start_sign_id", "#{startSignId,jdbcType=INTEGER}");
            }

            if (record.getLongitude() != null) {
                sql.VALUES("longitude", "#{longitude,jdbcType=DOUBLE}");
            }

            if (record.getLatitude() != null) {
                sql.VALUES("latitude", "#{latitude,jdbcType=DOUBLE}");
            }

            return sql.toString();
        }

        public String updateByPrimaryKeySelective(Sign record) {
            SQL sql = new SQL();
            sql.UPDATE("t_sign");

            if (record.getId() != null) {
                sql.VALUES("id", "#{id,jdbcType=BIGINT}");
            }

            if (record.getUserId() != null) {
                sql.VALUES("user_id", "#{userId,jdbcType=BIGINT}");
            }

            if (record.getClassId() != null) {
                sql.VALUES("class_id", "#{classId,jdbcType=INTEGER}");
            }

            if (record.getSignTime() != null) {
                sql.VALUES("sign_time", "#{signTime,jdbcType=TIMESTAMP}");
            }

            if (record.getScore() != null) {
                sql.VALUES("score", "#{score,jdbcType=INTEGER}");
            }

            if (record.getStartSignId() != null) {
                sql.VALUES("start_sign_id", "#{startSignId,jdbcType=INTEGER}");
            }

            if (record.getLongitude() != null) {
                sql.VALUES("longitude", "#{longitude,jdbcType=DOUBLE}");
            }

            if (record.getLatitude() != null) {
                sql.VALUES("latitude", "#{latitude,jdbcType=DOUBLE}");
            }

            sql.WHERE("id = #{id,jdbcType=BIGINT}");

            return sql.toString();
        }
    }
}
