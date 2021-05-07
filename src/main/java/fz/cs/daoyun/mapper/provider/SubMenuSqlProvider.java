package fz.cs.daoyun.mapper.provider;

import fz.cs.daoyun.domain.SubMenu;
import org.apache.ibatis.jdbc.SQL;

public class SubMenuSqlProvider {

    public String insertSelective(SubMenu record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("t_sub_menu");

        if (record.getId() != null) {
            sql.VALUES("id", "#{id,jdbcType=INTEGER}");
        }

        if (record.getMenuId() != null) {
            sql.VALUES("menu_id", "#{menuId,jdbcType=INTEGER}");
        }

        if (record.getSubMenuId() != null) {
            sql.VALUES("sub_menu_id", "#{subMenuId,jdbcType=INTEGER}");
        }

        return sql.toString();
    }

    public String updateByPrimaryKeySelective(SubMenu record) {
        SQL sql = new SQL();
        sql.UPDATE("t_sub_menu");

        if (record.getMenuId() != null) {
            sql.SET("menu_id = #{menuId,jdbcType=INTEGER}");
        }

        if (record.getSubMenuId() != null) {
            sql.SET("sub_menu_id = #{subMenuId,jdbcType=INTEGER}");
        }

        sql.WHERE("id = #{id,jdbcType=INTEGER}");

        return sql.toString();
    }
}
