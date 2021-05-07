package fz.cs.daoyun.mapper;

import fz.cs.daoyun.mapper.provider.DictInfoSqlProvider;
import fz.cs.daoyun.domain.DictInfo;
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
public interface DictInfoMapper {
    @Delete({
        "delete from t_dict_info",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into t_dict_info (id, dict_id, ",
        "item_key, item_value, ",
        "sequence, isdefault)",
        "values (#{id,jdbcType=INTEGER}, #{dictId,jdbcType=INTEGER}, ",
        "#{itemKey,jdbcType=VARCHAR}, #{itemValue,jdbcType=VARCHAR}, ",
        "#{sequence,jdbcType=INTEGER}, #{isdefault,jdbcType=BIT})"
    })
    int insert(DictInfo record);

    @InsertProvider(type= DictInfoSqlProvider.class, method="insertSelective")
    int insertSelective(DictInfo record);

    @Select({
        "select",
        "id, dict_id, item_key, item_value, sequence, isdefault",
        "from t_dict_info",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="dict_id", property="dictId", jdbcType=JdbcType.INTEGER),
        @Result(column="item_key", property="itemKey", jdbcType=JdbcType.VARCHAR),
        @Result(column="item_value", property="itemValue", jdbcType=JdbcType.VARCHAR),
        @Result(column="sequence", property="sequence", jdbcType=JdbcType.INTEGER),
        @Result(column="isdefault", property="isdefault", jdbcType=JdbcType.BIT)
    })
    DictInfo selectByPrimaryKey(Integer id);

    @UpdateProvider(type=DictInfoSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(DictInfo record);

    @Update({
        "update t_dict_info",
        "set dict_id = #{dictId,jdbcType=INTEGER},",
          "item_key = #{itemKey,jdbcType=VARCHAR},",
          "item_value = #{itemValue,jdbcType=VARCHAR},",
          "sequence = #{sequence,jdbcType=INTEGER},",
          "isdefault = #{isdefault,jdbcType=BIT}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(DictInfo record);

    @Select("select * from t_dict_info where item_key = #{itemKey,jdbcType=VARCHAR}")
    DictInfo selectByItemKey(String itemKey);

    @Select("select * from t_dict_info where dict_id = #{dictId,jdbcType=INTEGER}")
    List<DictInfo> selectByDictId(Integer dictid);
}
