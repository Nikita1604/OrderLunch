package com.nikita.pischik.orderlunch.dao;


import com.nikita.pischik.orderlunch.model.UserRole;

import java.util.List;

public interface UserRoleDao {

    List<UserRole> findAll();
    UserRole findByType(String type);
    UserRole findById(int id);
}
