package fz.cs.daoyun.mapper;

import fz.cs.daoyun.mapper.provider.DictInfoSqlProvider;
import fz.cs.daoyun.domain.DictInfo;
import org.apache.ibatis.annotations.*;
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
        "insert into t_dict_info (id, dict_eng, ",
        "item_key, item_value, ",
        "isdefault)",
        "values (#{id,jdbcType=INTEGER}, #{dictEng,jdbcType=VARCHAR}, ",
        "#{itemKey,jdbcType=VARCHAR}, #{itemValue,jdbcType=VARCHAR}, ",
        "#{isdefault,jdbcType=BIT})"
    })
    int insert(DictInfo record);

    @InsertProvider(type= DictInfoSqlProvider.class, method="insertSelective")
    int insertSelective(DictInfo record);

    @Select({
        "select",
        "id, dict_eng, item_key, item_value, isdefault",
        "from t_dict_info",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="dict_eng", property="dictEng", jdbcType=JdbcType.VARCHAR),
        @Result(column="item_key", property="itemKey", jdbcType=JdbcType.VARCHAR),
        @Result(column="item_value", property="itemValue", jdbcType=JdbcType.VARCHAR),
        @Result(column="isdefault", property="isdefault", jdbcType=JdbcType.BIT)
    })
    DictInfo selectByPrimaryKey(Integer id);

    @UpdateProvider(type=DictInfoSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(DictInfo record);

    @Update({
        "update t_dict_info",
        "set dict_eng = #{dictEng,jdbcType=VARCHAR},",
          "item_key = #{itemKey,jdbcType=VARCHAR},",
          "item_value = #{itemValue,jdbcType=VARCHAR},",
          "isdefault = #{isdefault,jdbcType=BIT}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(DictInfo record);

    @Update({
            "update t_dict_info",
            "set isdefault = #{isDefault,jdbcType=BIT}",
            "where dict_eng = #{dictEng,jdbcType=VARCHAR}"
    })
    int updateDefault(@Param("isDefault") Boolean isDefault, @Param("dictEng") String dictEng);

    @Select("select * from t_dict_info where item_key = #{itemKey,jdbcType=VARCHAR}")
    List<DictInfo> selectByItemKey(String itemKey);

    @Select("select * from t_dict_info where dict_eng = #{dictEng,jdbcType=VARCHAR} order by sequence desc")
    List<DictInfo> selectByDictEng(String dictEng);

    @Update({
            "update t_dict_info",
            "set sequence = sequence + 1",
            "where id = #{dictInfoId,jdbcType=INTEGER}"
    })
    int upward(@Param("dictInfoId")Integer dictInfoId);
}
