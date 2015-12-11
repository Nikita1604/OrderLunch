package com.nikita.pischik.orderlunch.service;

import com.nikita.pischik.orderlunch.model.UserRole;

import java.util.List;

public interface UserRoleService {

    UserRole findById(int id);
    UserRole findByType(String type);
    List<UserRole> findAll();
}
