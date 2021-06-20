package fz.cs.daoyun.mapper;

import fz.cs.daoyun.domain.User;
import fz.cs.daoyun.mapper.provider.UserSqlProvider;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;



@Transactional
@Component
@Mapper
public interface UserMapper {
    @Delete({
        "delete from t_user",
        "where user_id = #{userId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long userId);

    @Insert("insert into t_user(name, password, salt, phone, identity, CreationDate) values(#{name,jdbcType=VARCHAR}, " +
            " #{password,jdbcType=VARCHAR},#{salt,jdbcType=VARCHAR},#{phone,jdbcType=BIGINT}, " +
            " #{identity,jdbcType=TINYINT},#{creationdate,jdbcType=TIMESTAMP})")
    int insert(User record);


    @Insert({
            "insert into t_user (" +
                    "user_id, nickname, name, sex, email, password, " +
                    "salt,  phone, school, classes, school_number, CreationDate, ",
                    "Creator, Modifier, ModificationDate",
                    "college, birthday, identity",
            ") " +
            "values(" +
                    "#{userId,jdbcType=BIGINT}, #{nickname,jdbcType=VARCHAR}," +
                    "#{name,jdbcType=VARCHAR}, #{sex,jdbcType=CHAR}, #{email,jdbcType=VARCHAR}, #{password,jdbcType=VARCHAR},#{salt,jdbcType=VARCHAR},",
                    "#{phone,jdbcType=BIGINT}, #{school,jdbcType=VARCHAR}, #{classes,jdbcType=VARCHAR}, ",
                    "#{schoolNumber,jdbcType=VARCHAR}, #{creationdate,jdbcType=TIMESTAMP}, ",
                    "#{creator,jdbcType=VARCHAR}, #{modifier,jdbcType=VARCHAR}, ",
                    "#{modificationdate,jdbcType=TIMESTAMP}, #{birthday,jdbcType=TIMESTAMP}",
                    "#{college,jdbcType=VARCHAR}, #{identity,jdbcType=TINYINT}",
            ")"
    })
    void saveUserAllInfo(User user);

    @Update({
                "update t_user",
                "set nickname = #{nickname,jdbcType=VARCHAR},",
                "name = #{name,jdbcType=VARCHAR},",
                "sex = #{sex,jdbcType=CHAR},",
                "email = #{email,jdbcType=VARCHAR},",
                "password = #{password,jdbcType=VARCHAR},",
                "salt = #{salt,jdbcType=VARCHAR},",
                "phone = #{phone,jdbcType=BIGINT},",
                "school = #{school,jdbcType=VARCHAR},",
                "classes = #{classes,jdbcType=VARCHAR},",
                "school_number = #{schoolNumber,jdbcType=VARCHAR},",
                "CreationDate = #{creationdate,jdbcType=TIMESTAMP},",
                "Creator = #{creator,jdbcType=VARCHAR},",
                "Modifier = #{modifier,jdbcType=VARCHAR},",
                "ModificationDate = #{modificationdate,jdbcType=TIMESTAMP},",
                "college = #{college,jdbcType=VARCHAR},",
                "identity = #{identity,jdbcType=TINYINT}",
                "where user_id = #{userId,jdbcType=BIGINT}"
    })
    int insertAllinfo(User record);


    @InsertProvider(type= UserSqlProvider.class, method="insertSelective")
    int insertSelective(User record);

    @Select({
        "select",
            "user_id, nickname, name, sex, email, password, salt, phone, school, classes, school_number, CreationDate, ",
            "Creator, Modifier, ModificationDate, college, birthday, identity",
        "from t_user",
        "where user_id = #{userId,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="user_id", property="userId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="nickname", property="nickname", jdbcType=JdbcType.VARCHAR),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="sex", property="sex", jdbcType=JdbcType.CHAR),
        @Result(column="email", property="email", jdbcType=JdbcType.VARCHAR),
        @Result(column = "password", property = "password", jdbcType=JdbcType.VARCHAR),
        @Result(column = "salt", property = "salt", jdbcType=JdbcType.VARCHAR),
        @Result(column="phone", property="phone", jdbcType=JdbcType.BIGINT),
        @Result(column="school", property="school", jdbcType=JdbcType.VARCHAR),
        @Result(column="classes", property="classes", jdbcType=JdbcType.VARCHAR),
        @Result(column="school_number", property="schoolNumber", jdbcType=JdbcType.VARCHAR),
        @Result(column="CreationDate", property="creationdate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="Creator", property="creator", jdbcType=JdbcType.VARCHAR),
        @Result(column="Modifier", property="modifier", jdbcType=JdbcType.VARCHAR),
        @Result(column="ModificationDate", property="modificationdate", jdbcType=JdbcType.TIMESTAMP)
    })
    User selectByPrimaryKey(Long userId);

    @UpdateProvider(type=UserSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(User record);

    @Update({
        "update t_user",
        "set nickname = #{nickname,jdbcType=VARCHAR},",
          "name = #{name,jdbcType=VARCHAR},",
          "sex = #{sex,jdbcType=CHAR},",
          "email = #{email,jdbcType=VARCHAR},",
          "password = #{password,jdbcType=VARCHAR},",
          "salt = #{salt,jdbcType=VARCHAR},",
          "phone = #{phone,jdbcType=BIGINT},",
          "school = #{school,jdbcType=VARCHAR},",
          "classes = #{classes,jdbcType=VARCHAR},",
          "school_number = #{schoolNumber,jdbcType=VARCHAR},",
          "CreationDate = #{creationdate,jdbcType=TIMESTAMP},",
          "Creator = #{creator,jdbcType=VARCHAR},",
          "Modifier = #{modifier,jdbcType=VARCHAR},",
          "ModificationDate = #{modificationdate,jdbcType=TIMESTAMP}",
            "birthday = #{birthday,jdbcType=TIMESTAMP}",
            "college = #{college,jdbcType=VARCHAR},",
            "identity = #{identity,jdbcType=TINYINT},",
        "where user_id = #{userId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(User record);


    @Select({
            "select",
            "user_id, nickname, name, sex, email, password, salt, phone, school, classes, school_number, CreationDate, ",
            "Creator, Modifier, ModificationDate, college, birthday, identity",
            "from t_user",
            "where name = #{name,jdbcType=VARCHAR}"
    })
    @Results({
            @Result(column="user_id", property="userId", jdbcType=JdbcType.BIGINT, id=true),
            @Result(column="nickname", property="nickname", jdbcType=JdbcType.VARCHAR),
            @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
            @Result(column="sex", property="sex", jdbcType=JdbcType.CHAR),
            @Result(column="email", property="email", jdbcType=JdbcType.VARCHAR),
            @Result(column = "password", property = "password", jdbcType=JdbcType.VARCHAR),
            @Result(column = "salt", property = "salt", jdbcType=JdbcType.VARCHAR),
            @Result(column="phone", property="phone", jdbcType=JdbcType.BIGINT),
            @Result(column="school", property="school", jdbcType=JdbcType.VARCHAR),
            @Result(column="classes", property="classes", jdbcType=JdbcType.VARCHAR),
            @Result(column="school_number", property="schoolNumber", jdbcType=JdbcType.VARCHAR),
            @Result(column="CreationDate", property="creationdate", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="Creator", property="creator", jdbcType=JdbcType.VARCHAR),
            @Result(column="Modifier", property="modifier", jdbcType=JdbcType.VARCHAR),
            @Result(column="ModificationDate", property="modificationdate", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="college", property="college", jdbcType=JdbcType.VARCHAR),
            @Result(column="birthday", property="birthday", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="identity", property="identity", jdbcType=JdbcType.TINYINT)
    })
    User selectByName(String name);


    @Select({
            "select",
            "user_id, nickname, name, sex, email, password, salt, phone, school, classes, school_number, CreationDate, ",
            "Creator, Modifier, ModificationDate, college, birthday, identity",
            "from t_user",
            "where phone = #{phone,jdbcType=BIGINT}"
    })
    @Results({
            @Result(column="user_id", property="userId", jdbcType=JdbcType.BIGINT, id=true),
            @Result(column="nickname", property="nickname", jdbcType=JdbcType.VARCHAR),
            @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
            @Result(column="sex", property="sex", jdbcType=JdbcType.CHAR),
            @Result(column="email", property="email", jdbcType=JdbcType.VARCHAR),
            @Result(column = "password", property = "password", jdbcType=JdbcType.VARCHAR),
            @Result(column = "salt", property = "salt", jdbcType=JdbcType.VARCHAR),
            @Result(column="phone", property="phone", jdbcType=JdbcType.BIGINT),
            @Result(column="school", property="school", jdbcType=JdbcType.VARCHAR),
            @Result(column="classes", property="classes", jdbcType=JdbcType.VARCHAR),
            @Result(column="school_number", property="schoolNumber", jdbcType=JdbcType.VARCHAR),
            @Result(column="CreationDate", property="creationdate", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="Creator", property="creator", jdbcType=JdbcType.VARCHAR),
            @Result(column="Modifier", property="modifier", jdbcType=JdbcType.VARCHAR),
            @Result(column="ModificationDate", property="modificationdate", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="college", property="college", jdbcType=JdbcType.VARCHAR),
            @Result(column="birthday", property="birthday", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="identity", property="identity", jdbcType=JdbcType.TINYINT)
    })
    User selectByPhone(Long phone);

    @Select("Select * from t_user")
    List<User> selectAll();

    @Select({
            "select",
            "user_id, nickname, name, sex, email, password, salt, phone, school, classes, school_number, CreationDate, ",
            "Creator, Modifier, ModificationDate, college, birthday, identity",
            "from t_user",
            "where phone = #{token,jdbcType=BIGINT}"
    })
    @Results({
            @Result(column="user_id", property="userId", jdbcType=JdbcType.BIGINT, id=true),
            @Result(column="nickname", property="nickname", jdbcType=JdbcType.VARCHAR),
            @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
            @Result(column="sex", property="sex", jdbcType=JdbcType.CHAR),
            @Result(column="email", property="email", jdbcType=JdbcType.VARCHAR),
            @Result(column = "password", property = "password", jdbcType=JdbcType.VARCHAR),
            @Result(column = "salt", property = "salt", jdbcType=JdbcType.VARCHAR),
            @Result(column="phone", property="phone", jdbcType=JdbcType.BIGINT),
            @Result(column="school", property="school", jdbcType=JdbcType.VARCHAR),
            @Result(column="classes", property="classes", jdbcType=JdbcType.VARCHAR),
            @Result(column="school_number", property="schoolNumber", jdbcType=JdbcType.VARCHAR),
            @Result(column="CreationDate", property="creationdate", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="Creator", property="creator", jdbcType=JdbcType.VARCHAR),
            @Result(column="Modifier", property="modifier", jdbcType=JdbcType.VARCHAR),
            @Result(column="ModificationDate", property="modificationdate", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="college", property="college", jdbcType=JdbcType.VARCHAR),
            @Result(column="birthday", property="birthday", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="identity", property="identity", jdbcType=JdbcType.TINYINT)
    })
    User selectByToken(String token);

    @Delete({
            "delete from t_user",
            "where phone = #{phone,jdbcType=BIGINT}"
    })
    void deleteByPhone(Long phone);

    @Delete({
            "delete from t_user",
            "where name = #{name,jdbcType=VARCHAR}"
    })
    void deleteByName(String name);

    @Select({
            "select",
            "user_id, nickname, name, sex, email, password, salt, phone, school, classes, school_number, CreationDate, ",
            "Creator, Modifier, ModificationDate, college, birthday, identity",
            "from t_user",
            "where email = #{username,jdbcType=VARCHAR}"
    })

    User selectByEmail(String username);
}
