package fz.cs.daoyun.data.mapper;

import fz.cs.daoyun.data.domain.LoginLog;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface LoginLogMapper {
    @Delete({
        "delete from t_loginlog",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into t_loginlog (id, username, ",
        "loginTime, location, ",
        "ip, system, browser)",
        "values (#{id,jdbcType=INTEGER}, #{username,jdbcType=VARCHAR}, ",
        "#{logintime,jdbcType=TIMESTAMP}, #{location,jdbcType=VARCHAR}, ",
        "#{ip,jdbcType=VARCHAR}, #{system,jdbcType=VARCHAR}, #{browser,jdbcType=VARCHAR})"
    })
    int insert(LoginLog record);

    @InsertProvider(type=LoginLogSqlProvider.class, method="insertSelective")
    int insertSelective(LoginLog record);

    @Select({
        "select",
        "id, username, loginTime, location, ip, system, browser",
        "from t_loginlog",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="username", property="username", jdbcType=JdbcType.VARCHAR),
        @Result(column="loginTime", property="logintime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="location", property="location", jdbcType=JdbcType.VARCHAR),
        @Result(column="ip", property="ip", jdbcType=JdbcType.VARCHAR),
        @Result(column="system", property="system", jdbcType=JdbcType.VARCHAR),
        @Result(column="browser", property="browser", jdbcType=JdbcType.VARCHAR)
    })
    LoginLog selectByPrimaryKey(Integer id);

    @UpdateProvider(type=LoginLogSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(LoginLog record);

    @Update({
        "update t_loginlog",
        "set username = #{username,jdbcType=VARCHAR},",
          "loginTime = #{logintime,jdbcType=TIMESTAMP},",
          "location = #{location,jdbcType=VARCHAR},",
          "ip = #{ip,jdbcType=VARCHAR},",
          "system = #{system,jdbcType=VARCHAR},",
          "browser = #{browser,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(LoginLog record);
}