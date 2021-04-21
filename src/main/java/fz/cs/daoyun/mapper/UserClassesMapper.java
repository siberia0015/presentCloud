package fz.cs.daoyun.mapper;

import fz.cs.daoyun.mapper.provider.UserClassesSqlProvider;
import fz.cs.daoyun.domain.UserClasses;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Transactional
public interface UserClassesMapper {
    @Delete({
        "delete from t_user_class",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into t_user_class (id, class_id, user_name, class_image, user_id, ",
        "user_phone, class_name, identity)",
        "values (#{id,jdbcType=INTEGER}, #{classId,jdbcType=INTEGER}, #{userName,jdbcType=VARCHAR}, ",
        "#{classImage,jdbcType=VARCHAR}, #{userId,jdbcType=BIGINT}, #{userPhone,jdbcType=BIGINT}, ",
        "#{className,jdbcType=VARCHAR}, #{identity,jdbcType=TINYINT}, #{score,jdbcType=INTEGER})"
    })
    int insert(UserClasses record);

    @InsertProvider(type= UserClassesSqlProvider.class, method="insertSelective")
    int insertSelective(UserClasses record);

    @Select({
        "select",
        "id, class_id, user_name, class_image, user_id, user_phone, class_name, identity, score",
        "from t_user_class",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="class_id", property="classId", jdbcType=JdbcType.INTEGER),
        @Result(column="user_name", property="userName", jdbcType=JdbcType.VARCHAR),
        @Result(column="class_image", property="classImage", jdbcType=JdbcType.VARCHAR),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.BIGINT),
        @Result(column="user_phone", property="userPhone", jdbcType=JdbcType.BIGINT),
        @Result(column="class_name", property="className", jdbcType=JdbcType.VARCHAR),
        @Result(column="identity", property="identity", jdbcType=JdbcType.TINYINT),
        @Result(column="score", property="score", jdbcType=JdbcType.INTEGER)
    })
    UserClasses selectByPrimaryKey(Integer id);

    @UpdateProvider(type=UserClassesSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(UserClasses record);

    @Update({
        "update t_user_class",
        "set class_id = #{classId,jdbcType=INTEGER},",
          "user_name = #{userName,jdbcType=VARCHAR}",
            "class_image = #{classImage,jdbcType=VARCHAR}",
            "user_id = #{userId,jdbcType=BIGINT}",
            "user_phone = #{userPhone,jdbcType=BIGINT}",
            "class_name = #{className,jdbcType=VARCHAR}",
            "identity = #{identity,jdbcType=TINYINT}",
            "score = #{score,jdbcType=INTEGER}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(UserClasses record);

    @Select("select * from t_user_class")
    List<UserClasses> selectAll();

    @Select("select * from t_user_class where  class_id = #{classId,jdbcType=INTEGER}")
    List<UserClasses>  selectByClassId(Integer classesId);

    @Select("select * from t_user_class where user_name = #{userName,jdbcType=VARCHAR}")
    List<UserClasses> selectByUserName(String name);

    @Select("select * from t_user_class where user_id = #{userId,jdbcType=BIGINT} and class_id = #{classId,jdbcType=INTEGER}")
    UserClasses selectByUserIdAndClassId(@Param("userId") Long userId, @Param("classId") Integer classId);

    @Delete({
            "delete from t_user_class",
            "where user_name = #{username,jdbcType=VARCHAR}"
    })
    void deleteByUsername(String username);


    @Insert({
            "insert into t_user_class (user_id, user_name, class_id, class_name)",
            "values (#{userId,jdbcType=BIGINT}, #{userName,jdbcType=VARCHAR},",
            "#{classId,jdbcType=INTEGER}, #{className,jdbcType=VARCHAR})"
    })
    void insertClassToUser(@Param("userId") Long userId,
                           @Param("userName") String userName,
                           @Param("classId") Integer classId,
                           @Param("className") String className
    );


    @Delete({
            "delete from t_user_class",
            "where user_name = #{username,jdbcType=VARCHAR} and " +
                    "class_id = #{classId,jdbcType=INTEGER}"
    })
    void deleteClassToUser(String usernmae, Integer classid);


    @Delete({
            "delete from t_user_class",
            "where class_id = #{classId,jdbcType=INTEGER}"
    })
    void deleteByClassId(Integer classesId);
}
