package com.nikita.pischik.orderlunch.dao;

import com.nikita.pischik.orderlunch.model.OrderItem;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository("orderItemDao")
public class OrderItemDaoImp extends AbstractDao<Integer, OrderItem> implements OrderItemDao {
    public OrderItem findById(int id) {
        return getByKey(id);
    }

    public OrderItem findByDishId(int id) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("dish_id", id));
        OrderItem orderItem = (OrderItem) criteria.uniqueResult();
        return orderItem;
    }

    public void save(OrderItem orderItem) {
        persist(orderItem);
    }
}
