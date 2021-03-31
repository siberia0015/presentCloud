package fz.cs.daoyun.data.mapper;

import fz.cs.daoyun.data.domain.LoginLog;
import org.apache.ibatis.jdbc.SQL;

public class LoginLogSqlProvider {

    public String insertSelective(LoginLog record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("t_loginlog");
        
        if (record.getId() != null) {
            sql.VALUES("id", "#{id,jdbcType=INTEGER}");
        }
        
        if (record.getUsername() != null) {
            sql.VALUES("username", "#{username,jdbcType=VARCHAR}");
        }
        
        if (record.getLogintime() != null) {
            sql.VALUES("loginTime", "#{logintime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getLocation() != null) {
            sql.VALUES("location", "#{location,jdbcType=VARCHAR}");
        }
        
        if (record.getIp() != null) {
            sql.VALUES("ip", "#{ip,jdbcType=VARCHAR}");
        }
        
        if (record.getSystem() != null) {
            sql.VALUES("system", "#{system,jdbcType=VARCHAR}");
        }
        
        if (record.getBrowser() != null) {
            sql.VALUES("browser", "#{browser,jdbcType=VARCHAR}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(LoginLog record) {
        SQL sql = new SQL();
        sql.UPDATE("t_loginlog");
        
        if (record.getUsername() != null) {
            sql.SET("username = #{username,jdbcType=VARCHAR}");
        }
        
        if (record.getLogintime() != null) {
            sql.SET("loginTime = #{logintime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getLocation() != null) {
            sql.SET("location = #{location,jdbcType=VARCHAR}");
        }
        
        if (record.getIp() != null) {
            sql.SET("ip = #{ip,jdbcType=VARCHAR}");
        }
        
        if (record.getSystem() != null) {
            sql.SET("system = #{system,jdbcType=VARCHAR}");
        }
        
        if (record.getBrowser() != null) {
            sql.SET("browser = #{browser,jdbcType=VARCHAR}");
        }
        
        sql.WHERE("id = #{id,jdbcType=INTEGER}");
        
        return sql.toString();
    }
}