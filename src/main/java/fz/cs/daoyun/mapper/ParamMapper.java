package fz.cs.daoyun.mapper;

import fz.cs.daoyun.mapper.provider.ParamSqlProvider;
import fz.cs.daoyun.domain.Param;
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
public interface ParamMapper {
    @Delete({
        "delete from t_param",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into t_param (id, key_name, ",
        "value)",
        "values (#{id,jdbcType=INTEGER}, #{keyName,jdbcType=VARCHAR}, ",
        "#{value,jdbcType=INTEGER})"
    })
    int insert(Param record);

    @InsertProvider(type= ParamSqlProvider.class, method="insertSelective")
    int insertSelective(Param record);

    @Select({
        "select",
        "id, key_name, value",
        "from t_param",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="key_name", property="keyName", jdbcType=JdbcType.VARCHAR),
        @Result(column="value", property="value", jdbcType=JdbcType.INTEGER)
    })
    Param selectByPrimaryKey(Integer id);

    @UpdateProvider(type=ParamSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Param record);

    @Update({
        "update t_param",
        "set key_name = #{keyName,jdbcType=VARCHAR},",
          "value = #{value,jdbcType=INTEGER}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Param record);


    @Select("select * from t_param")
    List<Param> selectAll();


    @Update({
            "update t_param",
            "set key_name = #{key_name,jdbcType=VARCHAR},",
            "value = #{val,jdbcType=INTEGER}",
            "where id = #{id,jdbcType=INTEGER}"
    })
    void update(Integer id, String key_name, Integer val);
}
