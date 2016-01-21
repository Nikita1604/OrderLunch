package com.nikita.pischik.orderlunch.service;


import com.nikita.pischik.orderlunch.dao.MenuDao;
import com.nikita.pischik.orderlunch.model.MenuItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("menuService")
@Transactional
public class MenuServiceImpl implements MenuService{

    @Autowired
    MenuDao dao;

    public MenuItem findById(int id) {
        return dao.findById(id);
    }

    public void saveMenu(MenuItem menuItem) {
        dao.save(menuItem);
    }

    public List<MenuItem> findAllMenu() {
        return dao.findAllMenu();
    }

    public void deleteAllMenu() {
        dao.deleteAllMenu();
    }
}
