package fz.cs.daoyun.mapper.provider;

import fz.cs.daoyun.domain.Sign;
import org.apache.ibatis.jdbc.SQL;

public class SignSqlProvider {

    public String insertSelective(Sign record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("t_sign");

        if (record.getId() != null) {
            sql.VALUES("id", "#{id,jdbcType=BIGINT}");
        }

        if (record.getUserName() != null) {
            sql.VALUES("user_name", "#{userName,jdbcType=VARCHAR}");
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

        if (record.getSingnTimes() != null) {
            sql.VALUES("singn_times", "#{singnTimes,jdbcType=INTEGER}");
        }


        if (record.getStartSignId() != null) {
            sql.VALUES("start_sign_id", "#{startSignId,jdbcType=INTEGER}");
        }

        return sql.toString();
    }

    public String updateByPrimaryKeySelective(Sign record) {
        SQL sql = new SQL();
        sql.UPDATE("t_sign");

        if (record.getUserName() != null) {
            sql.SET("user_name = #{userName,jdbcType=VARCHAR}");
        }

        if (record.getClassId() != null) {
            sql.SET("class_id = #{classId,jdbcType=INTEGER}");
        }

        if (record.getSignTime() != null) {
            sql.SET("sign_time = #{signTime,jdbcType=TIMESTAMP}");
        }

        if (record.getScore() != null) {
            sql.SET("score = #{score,jdbcType=INTEGER}");
        }

        if (record.getSingnTimes() != null) {
            sql.SET("singn_times = #{singnTimes,jdbcType=INTEGER}");
        }

        if (record.getStartSignId() != null) {
            sql.SET("start_sign_id = #{startSignId,jdbcType=INTEGER}");
        }

        sql.WHERE("id = #{id,jdbcType=BIGINT}");

        return sql.toString();
    }
}
