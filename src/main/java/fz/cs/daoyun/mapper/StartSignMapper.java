package fz.cs.daoyun.mapper;


import fz.cs.daoyun.domain.StartSign;
import fz.cs.daoyun.mapper.provider.StartSignSqlProvider;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

public interface StartSignMapper {
    @Delete({
        "delete from t_start_sign",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into t_start_sign ( user_name, ",
        "class_id, singn_num, ",
        "sign_time, score, ",
        "distance, type, ",
        "time, latitude, longitude)",
        "values ( #{userName,jdbcType=VARCHAR}, ",
        "#{classId,jdbcType=INTEGER}, #{singnNum,jdbcType=INTEGER}, ",
        "#{signTime,jdbcType=TIMESTAMP}, #{score,jdbcType=INTEGER}, ",
        "#{distance,jdbcType=INTEGER}, #{type,jdbcType=INTEGER}, ",
        "#{time,jdbcType=INTEGER}, #{latitude,jdbcType=DOUBLE}, #{longitude,jdbcType=DOUBLE})"
    })
    int insert(StartSign record);

    @InsertProvider(type= StartSignSqlProvider.class, method="insertSelective")
    int insertSelective(StartSign record);

    @Select({
        "select",
        "id, user_name, class_id, singn_num, sign_time, score, distance, type, time, ",
        "latitude, longitude",
        "from t_start_sign",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="user_name", property="userName", jdbcType=JdbcType.VARCHAR),
        @Result(column="class_id", property="classId", jdbcType=JdbcType.INTEGER),
        @Result(column="singn_num", property="singnNum", jdbcType=JdbcType.INTEGER),
        @Result(column="sign_time", property="signTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="score", property="score", jdbcType=JdbcType.INTEGER),
        @Result(column="distance", property="distance", jdbcType=JdbcType.INTEGER),
        @Result(column="type", property="type", jdbcType=JdbcType.INTEGER),
        @Result(column="time", property="time", jdbcType=JdbcType.INTEGER),
        @Result(column="latitude", property="latitude", jdbcType=JdbcType.DOUBLE),
        @Result(column="longitude", property="longitude", jdbcType=JdbcType.DOUBLE)
    })
    StartSign selectByPrimaryKey(Integer id);

    @UpdateProvider(type=StartSignSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(StartSign record);

    @Update({
        "update t_start_sign",
        "set user_name = #{userName,jdbcType=VARCHAR},",
          "class_id = #{classId,jdbcType=INTEGER},",
          "singn_num = #{singnNum,jdbcType=INTEGER},",
          "sign_time = #{signTime,jdbcType=TIMESTAMP},",
          "score = #{score,jdbcType=INTEGER},",
          "distance = #{distance,jdbcType=INTEGER},",
          "type = #{type,jdbcType=INTEGER},",
          "time = #{time,jdbcType=INTEGER},",
          "latitude = #{latitude,jdbcType=DOUBLE},",
          "longitude = #{longitude,jdbcType=DOUBLE}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(StartSign record);


    @Select({
            "select",
            "id, user_name, class_id, singn_num, sign_time, score, distance, type, time, ",
            "latitude, longitude",
            "from t_start_sign",
            "where singn_num = #{sign_num,jdbcType=INTEGER} " +
                    "and class_id = #{classid,jdbcType=INTEGER} " +
                    "and sign_time like CONCAT(#{dateString,jdbcType=VARCHAR}, '%') "
    })
    StartSign findBySignNum(Integer sign_num, Integer classid, String dateString);


    @Select({
            "select",
            "id, user_name, class_id, singn_num, sign_time, score, distance, type, time, ",
            "latitude, longitude",
            "from t_start_sign",
            "where  class_id = #{classId,jdbcType=INTEGER} " +
                    "and sign_time like CONCAT(#{dateString,jdbcType=VARCHAR}, '%') "
    })
    StartSign findByParams(Integer classId, String dateString);
}
