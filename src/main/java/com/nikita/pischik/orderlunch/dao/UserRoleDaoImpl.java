package com.nikita.pischik.orderlunch.dao;


import com.nikita.pischik.orderlunch.model.UserRole;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userRoleDao")
public class UserRoleDaoImpl extends AbstractDao<Integer, UserRole> implements UserRoleDao{

    @SuppressWarnings("unchecked")
    public List<UserRole> findAll() {
        Criteria crit = createEntityCriteria();
        crit.addOrder(Order.asc("type"));
        return (List<UserRole>)crit.list();
    }

    public UserRole findByType(String type) {
        Criteria crit = createEntityCriteria();
        crit.add(Restrictions.eq("type", type));
        return (UserRole) crit.uniqueResult();
    }

    public UserRole findById(int id) {
        return (UserRole) getByKey(id);
    }
}
