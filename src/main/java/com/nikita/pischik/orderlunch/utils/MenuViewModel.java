package com.nikita.pischik.orderlunch.utils;


import com.nikita.pischik.orderlunch.model.MenuItem;

import java.util.List;

public class MenuViewModel {
    private int id;
    private String name;
    private List<MenuItem> menu;

    public List<MenuItem> getMenu() {
        return menu;
    }

    public void setMenu(List<MenuItem> menu) {
        this.menu = menu;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
