package fz.cs.daoyun.service.impl;

import fz.cs.daoyun.domain.Menu;
import fz.cs.daoyun.domain.SubMenu;
import fz.cs.daoyun.mapper.MenuMapper;
import fz.cs.daoyun.mapper.SubMenuMapper;
import fz.cs.daoyun.service.IMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class MenuServiceImpl implements IMenuService {

    @Resource
    private MenuMapper menuMapper;


    @Resource
    private SubMenuMapper subMenuMapper;

    @Override
    public List<Menu> findAll() {

        return menuMapper.selectAll();
    }

    @Override
    public Menu findByName(String name) {
        return menuMapper.selectByName(name);
    }

    @Override
    public Menu findById(Integer id) {
        return menuMapper.selectByPrimaryKey(id);
    }



    @Override
    public void updateMenu(Menu menu) throws Exception{
        menuMapper.updateByPrimaryKey(menu);
    }

    @Override
    public void addMenu(Menu menu) throws  Exception {
        menuMapper.insert(menu);

    }

    @Override
    public void deleteMenu(Integer id) throws Exception{
        menuMapper.deleteByPrimaryKey(id);

    }

    @Override
    public List<MenuUtils> findAllMenus() {

        List<MenuUtils> menuUtilsList = new ArrayList<MenuUtils>();
        List<Menu> menus = menuMapper.selectAll();
        for (Menu m: menus
             ) {
            if(m.getParentMenuId() == 0){
                MenuUtils menuUtils = new MenuUtils();
                List<SubMenu> subMenus = subMenuMapper.selectByParentId(m.getMenuId());
                for (SubMenu sub :subMenus
                ) {
                    Menu menu = menuMapper.selectByPrimaryKey(sub.getSubMenuId());

                    if(menu != null){
                        menuUtils.addSubMenus(menu);
                    }

                }
                menuUtils.setMenu(m);

                menuUtilsList.add(menuUtils);
            }

        }
        return menuUtilsList;
    }

    @Override
    public void deleteSubMenu(Integer id)throws Exception{
        subMenuMapper.deleteBySUbMenuId(id);
    }

    @Override
    public List<Menu> findAllSubMenus(Integer parentid) throws Exception{
        List<SubMenu> subMenus = subMenuMapper.selectByParentId(parentid);
        List<Menu> menus = new ArrayList<Menu>();
        for (SubMenu sub: subMenus
             ) {
            Menu menu = menuMapper.selectByPrimaryKey(sub.getSubMenuId());
            menus.add(menu);
        }
        return menus;
    }

    @Override
    public void updateSubMenu(Menu submenu) throws  Exception{
        int i = menuMapper.updateByPrimaryKey(submenu);
        subMenuMapper.deleteByParentId(submenu.getParentMenuId());
        subMenuMapper.updateByParam(submenu.getMenuId(), submenu.getParentMenuId());
    }

    @Override
    public void addSubMenu(Menu submenu) throws  Exception {
        menuMapper.insert(submenu);
        Menu menu = menuMapper.selectByName(submenu.getMenuName());
        subMenuMapper.insertSubMenuParam(menu.getMenuId(), menu.getParentMenuId());
    }

    @Override
    public List<String> getNames() throws  Exception{
        return menuMapper.getNames();
    }
}
