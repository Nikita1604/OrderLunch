package com.nikita.pischik.orderlunch.dao;


import com.nikita.pischik.orderlunch.model.OrderList;
import org.springframework.stereotype.Repository;


@Repository("orderListDao")
public class OrderListDaoImpl extends AbstractDao<Integer, OrderList> implements OrderListDao {
    public OrderList findById(int id) {
        return getByKey(id);
    }
}
