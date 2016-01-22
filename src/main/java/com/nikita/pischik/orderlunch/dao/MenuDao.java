package com.nikita.pischik.orderlunch.dao;


import com.nikita.pischik.orderlunch.model.MenuItem;

import java.util.List;

public interface MenuDao {
    MenuItem findById(int id);
    void save(MenuItem menuItem);
    List<MenuItem> findAllMenu();
    void deleteAllMenu();
    MenuItem findByMenuId(int id);
}
