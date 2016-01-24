package com.nikita.pischik.orderlunch.dao;


import com.nikita.pischik.orderlunch.model.OrderModel;

import java.util.List;

public interface OrderDao {

    OrderModel findById(int id);
    List<OrderModel> findAllOrders();
    List<OrderModel> findNotSentOrders();
    void save(OrderModel orderModel);
    void update(OrderModel orderModel);
    void delete(OrderModel orderModel);
}
