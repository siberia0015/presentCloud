package fz.cs.daoyun.data.mapper;

import fz.cs.daoyun.data.domain.StartSign;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface StartSignMapper {
    @Delete({
        "delete from t_sign",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into t_sign (id, user_name, ",
        "class_id, sign_time, ",
        "score, singn_times)",
        "values (#{id,jdbcType=BIGINT}, #{userName,jdbcType=VARCHAR}, ",
        "#{classId,jdbcType=INTEGER}, #{signTime,jdbcType=TIMESTAMP}, ",
        "#{score,jdbcType=INTEGER}, #{singnTimes,jdbcType=INTEGER})"
    })
    int insert(StartSign record);

    @InsertProvider(type=StartSignSqlProvider.class, method="insertSelective")
    int insertSelective(StartSign record);

    @Select({
        "select",
        "id, user_name, class_id, sign_time, score, singn_times",
        "from t_sign",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="user_name", property="userName", jdbcType=JdbcType.VARCHAR),
        @Result(column="class_id", property="classId", jdbcType=JdbcType.INTEGER),
        @Result(column="sign_time", property="signTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="score", property="score", jdbcType=JdbcType.INTEGER),
        @Result(column="singn_times", property="singnTimes", jdbcType=JdbcType.INTEGER)
    })
    StartSign selectByPrimaryKey(Long id);

    @UpdateProvider(type=StartSignSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(StartSign record);

    @Update({
        "update t_sign",
        "set user_name = #{userName,jdbcType=VARCHAR},",
          "class_id = #{classId,jdbcType=INTEGER},",
          "sign_time = #{signTime,jdbcType=TIMESTAMP},",
          "score = #{score,jdbcType=INTEGER},",
          "singn_times = #{singnTimes,jdbcType=INTEGER}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(StartSign record);
}