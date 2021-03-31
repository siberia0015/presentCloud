package fz.cs.daoyun.service;

import fz.cs.daoyun.domain.Menu;
import fz.cs.daoyun.service.impl.MenuUtils;

import java.util.List;

public interface IMenuService {

    /*查询所有的菜单*/
    public List<Menu>  findAll();

    /*根据菜单名查询*/
    public Menu findByName(String name);

    /*根据Id查询*/
    public Menu findById(Integer id);

    /*更新菜单， 参数为实体*/
    public void updateMenu(Menu menu) throws Exception;

    /*添加菜单*/
    public void addMenu(Menu menu) throws Exception;


    /*删除菜单*/
    public void deleteMenu(Integer id) throws Exception;


    List<MenuUtils> findAllMenus();

    void deleteSubMenu(Integer id) throws Exception;

    List<Menu> findAllSubMenus(Integer parentid) throws Exception;

    void updateSubMenu(Menu submenu) throws Exception;

    void addSubMenu(Menu submenu) throws Exception;

    List<String> getNames() throws Exception;
}
