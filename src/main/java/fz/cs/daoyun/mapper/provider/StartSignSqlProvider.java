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

        if (record.getUserName() != null) {
            sql.VALUES("user_name", "#{userName,jdbcType=VARCHAR}");
        }

        if (record.getClassId() != null) {
            sql.VALUES("class_id", "#{classId,jdbcType=INTEGER}");
        }

        if (record.getSingnNum() != null) {
            sql.VALUES("singn_num", "#{singnNum,jdbcType=INTEGER}");
        }

        if (record.getSignTime() != null) {
            sql.VALUES("sign_time", "#{signTime,jdbcType=TIMESTAMP}");
        }

        if (record.getScore() != null) {
            sql.VALUES("score", "#{score,jdbcType=INTEGER}");
        }

        if (record.getDistance() != null) {
            sql.VALUES("distance", "#{distance,jdbcType=INTEGER}");
        }

        if (record.getType() != null) {
            sql.VALUES("type", "#{type,jdbcType=INTEGER}");
        }

        if (record.getTime() != null) {
            sql.VALUES("time", "#{time,jdbcType=INTEGER}");
        }

        if (record.getLatitude() != null) {
            sql.VALUES("latitude", "#{latitude,jdbcType=DOUBLE}");
        }

        if (record.getLongitude() != null) {
            sql.VALUES("longitude", "#{longitude,jdbcType=DOUBLE}");
        }

        return sql.toString();
    }

    public String updateByPrimaryKeySelective(StartSign record) {
        SQL sql = new SQL();
        sql.UPDATE("t_start_sign");

        if (record.getUserName() != null) {
            sql.SET("user_name = #{userName,jdbcType=VARCHAR}");
        }

        if (record.getClassId() != null) {
            sql.SET("class_id = #{classId,jdbcType=INTEGER}");
        }

        if (record.getSingnNum() != null) {
            sql.SET("singn_num = #{singnNum,jdbcType=INTEGER}");
        }

        if (record.getSignTime() != null) {
            sql.SET("sign_time = #{signTime,jdbcType=TIMESTAMP}");
        }

        if (record.getScore() != null) {
            sql.SET("score = #{score,jdbcType=INTEGER}");
        }

        if (record.getDistance() != null) {
            sql.SET("distance = #{distance,jdbcType=INTEGER}");
        }

        if (record.getType() != null) {
            sql.SET("type = #{type,jdbcType=INTEGER}");
        }

        if (record.getTime() != null) {
            sql.SET("time = #{time,jdbcType=INTEGER}");
        }

        if (record.getLatitude() != null) {
            sql.SET("latitude = #{latitude,jdbcType=DOUBLE}");
        }

        if (record.getLongitude() != null) {
            sql.SET("longitude = #{longitude,jdbcType=DOUBLE}");
        }

        sql.WHERE("id = #{id,jdbcType=INTEGER}");

        return sql.toString();
    }
}
