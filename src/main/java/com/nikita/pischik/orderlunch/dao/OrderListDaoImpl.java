package com.nikita.pischik.orderlunch.dao;


import com.nikita.pischik.orderlunch.model.OrderList;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;


@Repository("orderListDao")
public class OrderListDaoImpl extends AbstractDao<Integer, OrderList> implements OrderListDao {
    public OrderList findById(int id) {
        OrderList orderList = getByKey(id);
        if (orderList != null) {
            Hibernate.initialize(orderList.getOrderItems());
        }
        return getByKey(id);
    }

    public void save(OrderList orderList) {
        persist(orderList);
    }
}
