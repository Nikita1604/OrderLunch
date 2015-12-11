package com.nikita.pischik.orderlunch.service;

import com.nikita.pischik.orderlunch.dao.UserDao;
import com.nikita.pischik.orderlunch.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDao dao;

    public User findById(int id) {
        return dao.findById(id);
    }

    public User findByLogin(String login) {
        return dao.findByLogin(login);
    }

    public void saveUser(User user) {
        dao.save(user);
    }

    public void updateUser(User user) {
        User entity = dao.findById(user.getId());
        if (entity != null) {
            entity.setLogin(user.getLogin());
            entity.setPassword(user.getPassword());
            entity.setE_mail(user.getE_mail());
            entity.setName(user.getName());
            entity.setCompany(user.getCompany());
            entity.setUserRoles(user.getUserRoles());
        }
    }

    public void deleteUserByLogin(String login) {
        dao.deleteByLogin(login);
    }

    public List<User> findAllUsers() {
        return dao.findAllUsers();
    }

    public boolean isUserLoginUnique(Integer id, String login) {
        User user = dao.findByLogin(login);
        return ( user == null || ((id != null) && (user.getId() == id)));
    }
}
