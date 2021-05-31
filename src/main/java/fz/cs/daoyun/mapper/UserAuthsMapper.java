package fz.cs.daoyun.mapper;

import fz.cs.daoyun.domain.UserAuths;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by siberia0015 on 2021/4/24.
 */
@Transactional
@Component
@Mapper
public interface UserAuthsMapper {
    @Insert("insert into t_user_auths(user_id, identity_type, identifier, credential) values(#{userId,jdbcType=BIGINT}, " +
            " #{identityType,jdbcType=VARCHAR},#{identifier,jdbcType=VARCHAR}, " +
            " #{credential,jdbcType=VARCHAR})")
    int insert(UserAuths userAuths);

    @Select({
            "select *",
            "from t_user_auths",
            "where identity_type = #{identityType,jdbcType=VARCHAR} and identifier = #{identifier,jdbcType=VARCHAR} ",
            "and user_id = #{userId,jdbcType=BIGINT}"
    })
    @Results({
            @Result(column="id", property="id", jdbcType= JdbcType.INTEGER),
            @Result(column="user_id", property="userId", jdbcType= JdbcType.BIGINT),
            @Result(column="identity_type", property="identityType", jdbcType=JdbcType.VARCHAR),
            @Result(column="identifier", property="identifier", jdbcType=JdbcType.VARCHAR),
            @Result(column="credential", property="credential", jdbcType=JdbcType.VARCHAR)
    })
    UserAuths select(@Param("userId") Long userId, @Param("identityType") String identityType, @Param("identifier") String identifier);
}
