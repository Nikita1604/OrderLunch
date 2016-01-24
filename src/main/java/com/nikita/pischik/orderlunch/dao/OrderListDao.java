package com.nikita.pischik.orderlunch.dao;


import com.nikita.pischik.orderlunch.model.OrderList;

public interface OrderListDao {
    OrderList findById(int id);
    void save(OrderList orderList);
    void delete(OrderList orderList);
}
