package fz.cs.daoyun.mapper;

import fz.cs.daoyun.mapper.provider.SubMenuSqlProvider;
import fz.cs.daoyun.domain.SubMenu;
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
public interface SubMenuMapper {
    @Delete({
        "delete from t_sub_menu",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into t_sub_menu (id, menu_id, ",
        "sub_menu_id)",
        "values (#{id,jdbcType=INTEGER}, #{menuId,jdbcType=INTEGER}, ",
        "#{subMenuId,jdbcType=INTEGER})"
    })
    int insert(SubMenu record);

    @InsertProvider(type= SubMenuSqlProvider.class, method="insertSelective")
    int insertSelective(SubMenu record);

    @Select({
        "select",
        "id, menu_id, sub_menu_id",
        "from t_sub_menu",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="menu_id", property="menuId", jdbcType=JdbcType.INTEGER),
        @Result(column="sub_menu_id", property="subMenuId", jdbcType=JdbcType.INTEGER)
    })
    SubMenu selectByPrimaryKey(Integer id);

    @UpdateProvider(type=SubMenuSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(SubMenu record);

    @Update({
        "update t_sub_menu",
        "set menu_id = #{menuId,jdbcType=INTEGER},",
          "sub_menu_id = #{subMenuId,jdbcType=INTEGER}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(SubMenu record);


    @Select("select * from t_sub_menu where menu_id = #{menuId,jdbcType=INTEGER}")
    List<SubMenu> selectByParentId(Integer menuId);


    @Delete({
            "delete from t_sub_menu",
            "where menu_id = #{id,jdbcType=INTEGER}"
    })
    void deleteByParentId(Integer id);



    @Insert({
            "insert into t_sub_menu ( menu_id, ",
            "sub_menu_id)",
            "values (#{parentMenuId,jdbcType=INTEGER}, ",
            "#{menuId,jdbcType=INTEGER})"
    })
    void updateByParam(Integer menuId, Integer parentMenuId);

    @Insert({
            "insert into t_sub_menu ( menu_id, ",
            "sub_menu_id)",
            "values (#{parentMenuId,jdbcType=INTEGER}, ",
            "#{menuId,jdbcType=INTEGER})"
    })
    void insertSubMenuParam(Integer menuId, Integer parentMenuId);


    @Delete({
            "delete from t_sub_menu",
            "where sub_menu_id = #{subMenuId,jdbcType=INTEGER}"
    })
    void deleteBySUbMenuId(Integer id);
}
