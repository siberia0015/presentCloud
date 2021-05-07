package fz.cs.daoyun.mapper.provider;

import fz.cs.daoyun.domain.Pagebutton;
import org.apache.ibatis.jdbc.SQL;

public class PagebuttonSqlProvider {

    public String insertSelective(Pagebutton record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("t_pagebutton");

        if (record.getPagebuttonId() != null) {
            sql.VALUES("pageButton_id", "#{pagebuttonId,jdbcType=INTEGER}");
        }

        if (record.getButtonId() != null) {
            sql.VALUES("button_id", "#{buttonId,jdbcType=INTEGER}");
        }

        if (record.getMenuId() != null) {
            sql.VALUES("menu_id", "#{menuId,jdbcType=INTEGER}");
        }

        return sql.toString();
    }

    public String updateByPrimaryKeySelective(Pagebutton record) {
        SQL sql = new SQL();
        sql.UPDATE("t_pagebutton");

        if (record.getButtonId() != null) {
            sql.SET("button_id = #{buttonId,jdbcType=INTEGER}");
        }

        if (record.getMenuId() != null) {
            sql.SET("menu_id = #{menuId,jdbcType=INTEGER}");
        }

        sql.WHERE("pageButton_id = #{pagebuttonId,jdbcType=INTEGER}");

        return sql.toString();
    }
}
