package fz.cs.daoyun.mapper.provider;


import fz.cs.daoyun.domain.StartSign;
import org.apache.ibatis.jdbc.SQL;

public class StartSignSqlProvider {

    public String insertSelective(StartSign record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("t_start_sign");

        if (record.getId() != null) {
            sql.VALUES("id", "#{id,jdbcType=INTEGER}");
        }

        if (record.getUserId() != null) {
            sql.VALUES("user_id", "#{userId,jdbcType=LONG}");
        }

        if (record.getClassId() != null) {
            sql.VALUES("class_id", "#{classId,jdbcType=INTEGER}");
        }

        if (record.getStartTime() != null) {
            sql.VALUES("start_time", "#{startTime,jdbcType=TIMESTAMP}");
        }

        if (record.getType() != null) {
            sql.VALUES("type", "#{type,jdbcType=INTEGER}");
        }

        if (record.getTimeLimit() != null) {
            sql.VALUES("time_limit", "#{timeLimit,jdbcType=INTEGER}");
        }

        if (record.getEndTime() != null) {
            sql.VALUES("end_time", "#{endTime,jdbcType=TIMESTAMP}");
        }

        sql.VALUES("over", "#{over,jdbcType=TINYINT}");

        if (record.getScore() != null) {
            sql.VALUES("score", "#{score,jdbcType=INTEGER}");
        }

        if (record.getDistance() != null) {
            sql.VALUES("distance", "#{distance,jdbcType=INTEGER}");
        }

        if (record.getLongitude() != null) {
            sql.VALUES("longitude", "#{longitude,jdbcType=DOUBLE}");
        }

        if (record.getLatitude() != null) {
            sql.VALUES("latitude", "#{latitude,jdbcType=DOUBLE}");
        }

        return sql.toString();
    }

    public String updateByPrimaryKeySelective(StartSign record) {
        SQL sql = new SQL();
        sql.UPDATE("t_start_sign");

        if (record.getId() != null) {
            sql.VALUES("id", "#{id,jdbcType=INTEGER}");
        }

        if (record.getUserId() != null) {
            sql.VALUES("user_id", "#{userId,jdbcType=LONG}");
        }

        if (record.getClassId() != null) {
            sql.VALUES("class_id", "#{classId,jdbcType=INTEGER}");
        }

        if (record.getStartTime() != null) {
            sql.VALUES("start_time", "#{startTime,jdbcType=TIMESTAMP}");
        }

        if (record.getType() != null) {
            sql.VALUES("type", "#{type,jdbcType=INTEGER}");
        }

        if (record.getTimeLimit() != null) {
            sql.VALUES("time_limit", "#{timeLimit,jdbcType=INTEGER}");
        }

        if (record.getEndTime() != null) {
            sql.VALUES("end_time", "#{endTime,jdbcType=TIMESTAMP}");
        }

        sql.VALUES("over", "#{over,jdbcType=TINYINT}");

        if (record.getScore() != null) {
            sql.VALUES("score", "#{score,jdbcType=INTEGER}");
        }

        if (record.getDistance() != null) {
            sql.VALUES("distance", "#{distance,jdbcType=INTEGER}");
        }

        if (record.getLongitude() != null) {
            sql.VALUES("longitude", "#{longitude,jdbcType=DOUBLE}");
        }

        if (record.getLatitude() != null) {
            sql.VALUES("latitude", "#{latitude,jdbcType=DOUBLE}");
        }

        sql.WHERE("id = #{id,jdbcType=INTEGER}");

        return sql.toString();
    }
}
