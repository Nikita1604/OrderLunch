package com.nikita.pischik.orderlunch.service;


import com.nikita.pischik.orderlunch.model.MenuItem;
import java.util.List;

public interface MenuService {
    MenuItem findById(int id);
    void saveMenu(MenuItem menuItem);
    List<MenuItem> findAllMenu();
    void deleteAllMenu();
    MenuItem findByDishId(int id);
}
