package fz.cs.daoyun.data.mapper;

import fz.cs.daoyun.data.domain.Sign;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface SignMapper {
    @Delete({
        "delete from t_sign",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into t_sign (id, user_name, ",
        "class_id, sign_time, ",
        "score, singn_times, ",
        "start_sign_id)",
        "values (#{id,jdbcType=BIGINT}, #{userName,jdbcType=VARCHAR}, ",
        "#{classId,jdbcType=INTEGER}, #{signTime,jdbcType=TIMESTAMP}, ",
        "#{score,jdbcType=INTEGER}, #{singnTimes,jdbcType=INTEGER}, ",
        "#{startSignId,jdbcType=INTEGER})"
    })
    int insert(Sign record);

    @InsertProvider(type=SignSqlProvider.class, method="insertSelective")
    int insertSelective(Sign record);

    @Select({
        "select",
        "id, user_name, class_id, sign_time, score, singn_times, start_sign_id",
        "from t_sign",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="user_name", property="userName", jdbcType=JdbcType.VARCHAR),
        @Result(column="class_id", property="classId", jdbcType=JdbcType.INTEGER),
        @Result(column="sign_time", property="signTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="score", property="score", jdbcType=JdbcType.INTEGER),
        @Result(column="singn_times", property="singnTimes", jdbcType=JdbcType.INTEGER),
        @Result(column="start_sign_id", property="startSignId", jdbcType=JdbcType.INTEGER)
    })
    Sign selectByPrimaryKey(Long id);

    @UpdateProvider(type=SignSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Sign record);

    @Update({
        "update t_sign",
        "set user_name = #{userName,jdbcType=VARCHAR},",
          "class_id = #{classId,jdbcType=INTEGER},",
          "sign_time = #{signTime,jdbcType=TIMESTAMP},",
          "score = #{score,jdbcType=INTEGER},",
          "singn_times = #{singnTimes,jdbcType=INTEGER},",
          "start_sign_id = #{startSignId,jdbcType=INTEGER}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(Sign record);
}