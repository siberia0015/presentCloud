package fz.cs.daoyun.mapper;

import fz.cs.daoyun.mapper.provider.UserClassesSqlProvider;
import fz.cs.daoyun.domain.UserClasses;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
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
        "insert into t_user_class (id, class_id, ",
        "user_name)",
        "values (#{id,jdbcType=INTEGER}, #{classId,jdbcType=INTEGER}, ",
        "#{userName,jdbcType=VARCHAR})"
    })
    int insert(UserClasses record);

    @InsertProvider(type= UserClassesSqlProvider.class, method="insertSelective")
    int insertSelective(UserClasses record);

    @Select({
        "select",
        "id, class_id, user_name",
        "from t_user_class",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="class_id", property="classId", jdbcType=JdbcType.INTEGER),
        @Result(column="user_name", property="userName", jdbcType=JdbcType.VARCHAR)
    })
    UserClasses selectByPrimaryKey(Integer id);

    @UpdateProvider(type=UserClassesSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(UserClasses record);

    @Update({
        "update t_user_class",
        "set class_id = #{classId,jdbcType=INTEGER},",
          "user_name = #{userName,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(UserClasses record);


    @Select("select * from t_user_class")
    List<UserClasses> selectAll();

    @Select("select * from t_user_class where  class_id = #{classId,jdbcType=INTEGER}")
    List<UserClasses>  selectByClassId(Integer classesId);

    @Select("select * from t_user_class where user_name = #{userName,jdbcType=VARCHAR}")
    List<UserClasses> selectByUserName(String name);


    @Delete({
            "delete from t_user_class",
            "where user_name = #{username,jdbcType=VARCHAR}"
    })
    void deleteByUsername(String username);


    @Insert({
            "insert into t_user_class ( class_id, ",
            "user_name)",
            "values ( #{classid,jdbcType=INTEGER}, ",
            "#{usernmae,jdbcType=VARCHAR})"
    })
    void insertClassToUser(String usernmae, Integer classid);


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
