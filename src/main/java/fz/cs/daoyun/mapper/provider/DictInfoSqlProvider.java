package fz.cs.daoyun.mapper.provider;

import fz.cs.daoyun.domain.DictInfo;
import org.apache.ibatis.jdbc.SQL;

public class DictInfoSqlProvider {

    public String insertSelective(DictInfo record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("t_dict_info");

        if (record.getId() != null) {
            sql.VALUES("id", "#{id,jdbcType=INTEGER}");
        }

        if (record.getDictId() != null) {
            sql.VALUES("dict_id", "#{dictId,jdbcType=INTEGER}");
        }

        if (record.getItemKey() != null) {
            sql.VALUES("item_key", "#{itemKey,jdbcType=VARCHAR}");
        }

        if (record.getItemValue() != null) {
            sql.VALUES("item_value", "#{itemValue,jdbcType=VARCHAR}");
        }

        if (record.getSequence() != null) {
            sql.VALUES("sequence", "#{sequence,jdbcType=INTEGER}");
        }

        if (record.getIsdefault() != null) {
            sql.VALUES("isdefault", "#{isdefault,jdbcType=BIT}");
        }

        return sql.toString();
    }

    public String updateByPrimaryKeySelective(DictInfo record) {
        SQL sql = new SQL();
        sql.UPDATE("t_dict_info");

        if (record.getDictId() != null) {
            sql.SET("dict_id = #{dictId,jdbcType=INTEGER}");
        }

        if (record.getItemKey() != null) {
            sql.SET("item_key = #{itemKey,jdbcType=VARCHAR}");
        }

        if (record.getItemValue() != null) {
            sql.SET("item_value = #{itemValue,jdbcType=VARCHAR}");
        }

        if (record.getSequence() != null) {
            sql.SET("sequence = #{sequence,jdbcType=INTEGER}");
        }

        if (record.getIsdefault() != null) {
            sql.SET("isdefault = #{isdefault,jdbcType=BIT}");
        }

        sql.WHERE("id = #{id,jdbcType=INTEGER}");

        return sql.toString();
    }
}
