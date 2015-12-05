package com.nikita.pischik.orderlunch.dao;

import com.nikita.pischik.orderlunch.model.User;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository("userDao")
public class UserDaoImpl extends AbstractDao<Integer, User> implements UserDao{

    public User findById(int id) {
        return getByKey(id);
    }

    public User findByLogin(String login) {
        Criteria crit = createEntityCriteria();
        crit.add(Restrictions.eq("login", login));
        return (User) crit.uniqueResult();
    }
}
