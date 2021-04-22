package fz.cs.daoyun.mapper;


import fz.cs.daoyun.domain.StartSign;
import fz.cs.daoyun.mapper.provider.StartSignSqlProvider;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

public interface StartSignMapper {
    @Delete({
        "delete from t_start_sign",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into t_start_sign ( user_id, ",
        "class_id, start_time, type, time_limit, end_time, ",
        "over, score, distance, latitude, longitude)",
        "values (#{userId,jdbcType=BIGINT}, ",
        "#{classId,jdbcType=INTEGER}, #{startTime,jdbcType=TIMESTAMP}, ",
        "#{type,jdbcType=INTEGER}, #{timeLimit,jdbcType=INTEGER}, ",
        "#{endTime,jdbcType=TIMESTAMP}, #{over,jdbcType=TINYINT}, ",
        "#{score,jdbcType=INTEGER}, #{distance,jdbcType=INTEGER}, ",
        "#{latitude,jdbcType=DOUBLE}, #{longitude,jdbcType=DOUBLE})"
    })
    int insert(StartSign record);

    @InsertProvider(type= StartSignSqlProvider.class, method="insertSelective")
    int insertSelective(StartSign record);

    @Select({
        "select",
        "id, user_id, class_id, start_time, type, time_limit, end_time, ",
        "over, score, distance, latitude, longitude",
        "from t_start_sign",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.BIGINT),
        @Result(column="class_id", property="classId", jdbcType=JdbcType.INTEGER),
        @Result(column="start_time", property="startTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="type", property="type", jdbcType=JdbcType.INTEGER),
        @Result(column="time_limit", property="timeLimit", jdbcType=JdbcType.INTEGER),
        @Result(column="end_time", property="endTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="over", property="over", jdbcType=JdbcType.TINYINT),
        @Result(column="score", property="score", jdbcType=JdbcType.INTEGER),
        @Result(column="distance", property="distance", jdbcType=JdbcType.INTEGER),
        @Result(column="latitude", property="latitude", jdbcType=JdbcType.DOUBLE),
        @Result(column="longitude", property="longitude", jdbcType=JdbcType.DOUBLE)
    })
    StartSign selectByPrimaryKey(Integer id);

    @UpdateProvider(type=StartSignSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(StartSign record);

    @Update({
        "update t_start_sign",
        "set user_id = #{userId,jdbcType=BIGINT},",
          "class_id = #{classId,jdbcType=INTEGER},",
          "start_time = #{startTime,jdbcType=TIMESTAMP},",
            "type = #{type,jdbcType=INTEGER},",
            "time_limit = #{timeLimit,jdbcType=INTEGER},",
            "end_time = #{endTime,jdbcType=TIMESTAMP},",
            "over = #{over,jdbcType=TINYINT},",
          "score = #{score,jdbcType=INTEGER},",
          "distance = #{distance,jdbcType=INTEGER},",
          "latitude = #{latitude,jdbcType=DOUBLE},",
          "longitude = #{longitude,jdbcType=DOUBLE}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(StartSign record);

    @Select({
            "select",
            "id, user_id, class_id, start_time, type, time_limit, end_time, ",
            "over, score, distance, latitude, longitude",
            "from t_start_sign",
            "where  class_id = #{classId,jdbcType=INTEGER} " +
                    "and start_time like CONCAT(#{dateString,jdbcType=VARCHAR}, '%') "
    })
    StartSign findByParams(Integer classId, String dateString);

    @Update({
            "update t_start_sign",
            "set over = 1, end_time = #{endTime,jdbcType=TIMESTAMP}",
            "where id = #{startSignId,jdbcType=INTEGER}"
    })
    int endSign(@Param("startSignId") Integer startSignId, @Param("endTime")Date endTime);

    @Update({
            "update t_start_sign",
            "set over = 0",
            "where id = #{startSignId,jdbcType=INTEGER}"
    })
    int startSign(Integer startSignId);

}
