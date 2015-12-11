package com.nikita.pischik.orderlunch.dao;

import com.nikita.pischik.orderlunch.model.User;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userDao")
public class UserDaoImpl extends AbstractDao<Integer, User> implements UserDao{

    public User findById(int id) {
        User user = getByKey(id);
        if (user != null) {
            Hibernate.initialize(user.getUserRoles());
        }
        return user;
    }

    public User findByLogin(String login) {
        Criteria crit = createEntityCriteria();
        crit.add(Restrictions.eq("login", login));
        User user = (User) crit.uniqueResult();

        if (user != null) {
            Hibernate.initialize(user.getUserRoles());
        }
        return user;
    }

    public void save(User user) {
        persist(user);
    }

    public void deleteByLogin(String login) {
        Criteria crit = createEntityCriteria();
        crit.add(Restrictions.eq("login", login));
        User user = (User) crit.uniqueResult();
        delete(user);
    }

    public List<User> findAllUsers() {
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("name"));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List<User> users = (List<User>) criteria.list();
        return users;
    }
}
