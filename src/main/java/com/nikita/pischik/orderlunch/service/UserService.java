package com.nikita.pischik.orderlunch.service;

import com.nikita.pischik.orderlunch.model.User;

public interface UserService {

    User findById(int id);
    User findByLogin(String login);
}
