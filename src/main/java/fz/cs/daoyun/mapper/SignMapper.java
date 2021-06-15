package fz.cs.daoyun.mapper;




import fz.cs.daoyun.domain.Sign;
import fz.cs.daoyun.domain.StudentSignInfo;
import fz.cs.daoyun.mapper.provider.SignSqlProvider;
import fz.cs.daoyun.mapper.provider.UserClassesSqlProvider;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Transactional
public interface SignMapper {
    @Delete({
            "delete from t_sign",
            "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
            "insert into t_sign (id, user_id, ",
            "class_id, sign_time, ",
            "score, start_sign_id, ",
            "longitude, latitude)",
            "values (#{id,jdbcType=BIGINT}, #{userId,jdbcType=BIGINT}, ",
            "#{classId,jdbcType=INTEGER}, #{signTime,jdbcType=TIMESTAMP}, ",
            "#{score,jdbcType=INTEGER}, #{startSignId,jdbcType=INTEGER}, ",
            "#{longitude,jdbcType=DOUBLE}, #{latitude,jdbcType=DOUBLE})"
    })
    int insert(Sign record);

    @InsertProvider(type=SignSqlProvider.class, method="insertSelective")
    int insertSelective(Sign record);

    @Select({
            "select",
            "id, user_id, class_id, sign_time, score, start_sign_id, longitude, latitude",
            "from t_sign",
            "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
            @Result(column="user_id", property="userId", jdbcType=JdbcType.BIGINT),
            @Result(column="class_id", property="classId", jdbcType=JdbcType.INTEGER),
            @Result(column="sign_time", property="signTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="score", property="score", jdbcType=JdbcType.INTEGER),
            @Result(column="start_sign_id", property="startSignId", jdbcType=JdbcType.INTEGER),
            @Result(column="longitude", property="longitude", jdbcType=JdbcType.DOUBLE),
            @Result(column="latitude", property="latitude", jdbcType=JdbcType.DOUBLE)
    })
    Sign selectByPrimaryKey(Long id);

    @UpdateProvider(type=SignSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Sign record);

    @Update({
            "update t_sign",
            "set user_id = #{userId,jdbcType=BIGINT},",
            "class_id = #{classId,jdbcType=INTEGER},",
            "sign_time = #{signTime,jdbcType=TIMESTAMP},",
            "score = #{score,jdbcType=INTEGER},",
            "start_sign_id = #{startSignId,jdbcType=INTEGER}",
            "longitude = #{longitude,jdbcType=DOUBLE}",
            "latitude = #{latitude,jdbcType=DOUBLE}",
            "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(Sign record);

    @Select({
            "select",
            "id, user_id, class_id, sign_time, score, start_sign_id, longitude, latitude",
            "from t_sign",
            "where user_id = #{userId,jdbcType=BIGINT} and class_id = #{classId,jdbcType=INTEGER}"
    })
    List<Sign> findByUserId(Long userId, Integer classId);

    @Select({
            "select",
            "id, user_id, class_id, sign_time, score, start_sign_id, longitude, latitude",
            "from t_sign",
            "where sign_time like CONCAT(#{date,jdbcType=VARCHAR}, '%') and class_id = #{classId,jdbcType=INTEGER}"
    })
    List<Sign> selectAllByDate(String date, Integer classId);

    @Select({
            "select *",
            "from t_sign",
            "where  user_id = #{userId,jdbcType=VARCHAR} and class_id = #{classId,jdbcType=INTEGER} "
    })
    List<Sign> findCurrentRecord(Long userId, Integer classId);

    @Delete({
            "delete from t_sign",
            "where class_id = #{classId,jdbcType=INTEGER}"
    })
    void deleteByClassId(Integer classId);

    @Select({
            "select *",
            "from t_sign",
            "where  start_sign_id = #{startSignId,jdbcType=INTEGER} " +
                    "and user_id = #{userId,jdbcType=VARCHAR}"
    })
    Sign selectByStartSignId(@Param("startSignId") Integer startSignId, @Param("userId") Long userId);

    @Select({
            "select *",
            "from t_sign",
            "where class_id = #{classId,jdbcType=INTEGER}"
    })
    List<Sign> findAlltime(Integer classId);

    /*根据签到号查询已签到的学生*/
    @Select({
            "select t_sign.user_id userId, sign_time, start_sign_id, longitude, latitude, name, school_number, image",
            "from t_sign left join t_user",
            "on t_sign.user_id = t_user.user_id",
            "where start_sign_id = #{startSignId,jdbcType=INTEGER}",
            "order by userId"
    })
    @Results({
            @Result(column="userId", property="studentId", jdbcType=JdbcType.BIGINT),
            @Result(column="name", property="studentName", jdbcType=JdbcType.VARCHAR),
            @Result(column="school_number", property="studentNumber", jdbcType=JdbcType.VARCHAR),
            @Result(column="image", property="studentImage", jdbcType=JdbcType.VARCHAR),
            @Result(column="sign_time", property="signTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="start_sign_id", property="startSignId", jdbcType=JdbcType.INTEGER),
            @Result(column="latitude", property="latitude", jdbcType=JdbcType.DOUBLE),
            @Result(column="longitude", property="longitude", jdbcType=JdbcType.DOUBLE),
    })
    List<StudentSignInfo> selectSigned(Integer startSignId);

    /*根据签到号查询未签到的学生*/
    @Select({
            "select * from t_user_class left join t_user",
            "on t_user_class.user_id = t_user.user_id",
            "where (not exists",
            "(select *",
            "from t_sign",
            "where t_user.user_id = t_sign.user_id and start_sign_id = #{startSignId,jdbcType=INTEGER}))",
            "and class_id = #{classId,jdbcType=INTEGER}"
    })
    @Results({
            @Result(column="user_id", property="studentId", jdbcType=JdbcType.BIGINT),
            @Result(column="name", property="studentName", jdbcType=JdbcType.VARCHAR),
            @Result(column="school_number", property="studentNumber", jdbcType=JdbcType.VARCHAR),
            @Result(column="image", property="studentImage", jdbcType=JdbcType.VARCHAR),
            @Result(column="sign_time", property="signTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="start_sign_id", property="startSignId", jdbcType=JdbcType.INTEGER),
            @Result(column="latitude", property="latitude", jdbcType=JdbcType.DOUBLE),
            @Result(column="longitude", property="longitude", jdbcType=JdbcType.DOUBLE),
    })
    List<StudentSignInfo> selectUnsigned(@Param("startSignId") Integer startSignId, @Param("classId") Integer classId);
}
