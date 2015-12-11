package com.nikita.pischik.orderlunch.service;

import com.nikita.pischik.orderlunch.model.User;

import java.util.List;

public interface UserService {

    User findById(int id);
    User findByLogin(String login);
    void saveUser(User user);
    void updateUser(User user);
    void deleteUserByLogin(String login);
    List<User> findAllUsers();
    boolean isUserLoginUnique(Integer id, String login);
}
