package com.nikita.pischik.orderlunch.dao;

import com.nikita.pischik.orderlunch.model.User;

public interface UserDao {
    User findById(int id);
    User findByLogin(String login);
}
