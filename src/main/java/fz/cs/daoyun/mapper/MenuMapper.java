package fz.cs.daoyun.mapper;

import fz.cs.daoyun.domain.Menu;
import fz.cs.daoyun.mapper.provider.MenuSqlProvider;
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
public interface MenuMapper {
    @Delete({
        "delete from t_menu",
        "where menu_id = #{menuId,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer menuId);

    @Insert({
        "insert into t_menu (menu_id, menu_name, ",
        "menu_icon, menu_link, ",
        "menu_sort, isShow, isPage, ",
        "parent_menu_id)",
        "values (#{menuId,jdbcType=INTEGER}, #{menuName,jdbcType=VARCHAR}, ",
        "#{menuIcon,jdbcType=VARCHAR}, #{menuLink,jdbcType=VARCHAR}, ",
        "#{menuSort,jdbcType=INTEGER}, #{isshow,jdbcType=BIT}, #{ispage,jdbcType=BIT}, ",
        "#{parentMenuId,jdbcType=INTEGER})"
    })
    int insert(Menu record);

    @InsertProvider(type= MenuSqlProvider.class, method="insertSelective")
    int insertSelective(Menu record);

    @Select({
        "select",
        "menu_id, menu_name, menu_icon, menu_link, menu_sort, isShow, isPage, parent_menu_id",
        "from t_menu",
        "where menu_id = #{menuId,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="menu_id", property="menuId", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="menu_name", property="menuName", jdbcType=JdbcType.VARCHAR),
        @Result(column="menu_icon", property="menuIcon", jdbcType=JdbcType.VARCHAR),
        @Result(column="menu_link", property="menuLink", jdbcType=JdbcType.VARCHAR),
        @Result(column="menu_sort", property="menuSort", jdbcType=JdbcType.INTEGER),
        @Result(column="isShow", property="isshow", jdbcType=JdbcType.BIT),
        @Result(column="isPage", property="ispage", jdbcType=JdbcType.BIT),
        @Result(column="parent_menu_id", property="parentMenuId", jdbcType=JdbcType.INTEGER)
    })
    Menu selectByPrimaryKey(Integer menuId);

    @UpdateProvider(type=MenuSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Menu record);

    @Update({
        "update t_menu",
        "set menu_name = #{menuName,jdbcType=VARCHAR},",
          "menu_icon = #{menuIcon,jdbcType=VARCHAR},",
          "menu_link = #{menuLink,jdbcType=VARCHAR},",
          "menu_sort = #{menuSort,jdbcType=INTEGER},",
          "isShow = #{isshow,jdbcType=BIT},",
          "isPage = #{ispage,jdbcType=BIT},",
          "parent_menu_id = #{parentMenuId,jdbcType=INTEGER}",
        "where menu_id = #{menuId,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Menu record);


    @Select("select * from t_menu")
    List<Menu> selectAll();


    @Select("select * from t_menu where  menu_name = #{menuName,jdbcType=VARCHAR}")
    Menu selectByName(String name);


    @Select("select menu_name from t_menu")
    List<String> getNames();
}
