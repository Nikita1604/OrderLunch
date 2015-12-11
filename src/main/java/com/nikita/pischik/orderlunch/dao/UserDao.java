package com.nikita.pischik.orderlunch.dao;

import com.nikita.pischik.orderlunch.model.User;

import java.util.List;

public interface UserDao {
    User findById(int id);
    User findByLogin(String login);

    void save (User user);
    void deleteByLogin(String login);

    List<User> findAllUsers();
}
