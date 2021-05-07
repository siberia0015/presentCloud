package fz.cs.daoyun.service.impl;


import fz.cs.daoyun.domain.Menu;

import java.util.ArrayList;
import java.util.List;

public class MenuUtils {
    private Menu menu;
    private List<Menu> subMenus = new ArrayList<Menu>();


    public void addSubMenus(Menu subMenu){
        this.subMenus.add(subMenu);
    }

    @Override
    public String toString() {
        return "MenuUtils{" +
                "menu=" + menu +
                ", subMenus=" + subMenus +
                '}';
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public List<Menu> getSubMenus() {
        return subMenus;
    }

    public void setSubMenus(List<Menu> subMenus) {
        this.subMenus = subMenus;
    }
}
