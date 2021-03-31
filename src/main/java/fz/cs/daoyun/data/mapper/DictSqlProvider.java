package fz.cs.daoyun.data.mapper;

import fz.cs.daoyun.data.domain.Dict;
import org.apache.ibatis.jdbc.SQL;

public class DictSqlProvider {

    public String insertSelective(Dict record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("t_dict");
        
        if (record.getId() != null) {
            sql.VALUES("id", "#{id,jdbcType=INTEGER}");
        }
        
        if (record.getName() != null) {
            sql.VALUES("name", "#{name,jdbcType=VARCHAR}");
        }
        
        if (record.getDescribe() != null) {
            sql.VALUES("describe", "#{describe,jdbcType=VARCHAR}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(Dict record) {
        SQL sql = new SQL();
        sql.UPDATE("t_dict");
        
        if (record.getName() != null) {
            sql.SET("name = #{name,jdbcType=VARCHAR}");
        }
        
        if (record.getDescribe() != null) {
            sql.SET("describe = #{describe,jdbcType=VARCHAR}");
        }
        
        sql.WHERE("id = #{id,jdbcType=INTEGER}");
        
        return sql.toString();
    }
}