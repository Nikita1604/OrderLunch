package com.nikita.pischik.orderlunch.service;


import com.nikita.pischik.orderlunch.dao.UserRoleDao;
import com.nikita.pischik.orderlunch.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("userRoleService")
@Transactional
public class UserRoleServiceImpl implements UserRoleService{

    @Autowired
    UserRoleDao dao;

    public UserRole findById(int id) {
        return dao.findById(id);
    }

    public UserRole findByType(String type) {
        return dao.findByType(type);
    }

    public List<UserRole> findAll() {
        return dao.findAll();
    }
}
