package fz.cs.daoyun.mapper.provider;

import fz.cs.daoyun.domain.Param;
import org.apache.ibatis.jdbc.SQL;

public class ParamSqlProvider {

    public String insertSelective(Param record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("t_param");

        if (record.getId() != null) {
            sql.VALUES("id", "#{id,jdbcType=INTEGER}");
        }

        if (record.getKeyEng() != null) {
            sql.VALUES("key_eng", "#{keyEng,jdbcType=VARCHAR}");
        }

        if (record.getKeyName() != null) {
            sql.VALUES("key_name", "#{keyName,jdbcType=VARCHAR}");
        }

        if (record.getValue() != null) {
            sql.VALUES("value", "#{value,jdbcType=INTEGER}");
        }

        return sql.toString();
    }

    public String updateByPrimaryKeySelective(Param record) {
        SQL sql = new SQL();
        sql.UPDATE("t_param");

        if (record.getKeyEng() != null) {
            sql.VALUES("key_eng", "#{keyEng,jdbcType=VARCHAR}");
        }

        if (record.getKeyName() != null) {
            sql.SET("key_name = #{keyName,jdbcType=VARCHAR}");
        }

        if (record.getValue() != null) {
            sql.SET("value = #{value,jdbcType=INTEGER}");
        }

        sql.WHERE("id = #{id,jdbcType=INTEGER}");

        return sql.toString();
    }
}
