package com.nikita.pischik.orderlunch.dao;


import com.nikita.pischik.orderlunch.model.MenuItem;
import com.nikita.pischik.orderlunch.model.User;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("menuDao")
public class MenuDaoImpl extends AbstractDao<Integer, MenuItem> implements MenuDao{

    public MenuItem findById(int id) {
        MenuItem menuItem = getByKey(id);
        return menuItem;
    }

    public void save(MenuItem menuItem) {
        persist(menuItem);
    }

    public List<MenuItem> findAllMenu() {
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("category_id"));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List<MenuItem> menuItemList = (List<MenuItem>) criteria.list();
        return menuItemList;
    }

    public void deleteAllMenu() {
        List<MenuItem> menu = findAllMenu();
        for (int i=0; i<menu.size(); i++) {
            Criteria crit = createEntityCriteria();
            crit.add(Restrictions.eq("id", menu.get(i).getId()));
            MenuItem menuItem = (MenuItem) crit.uniqueResult();
            delete(menuItem);
        }

    }
}
