package com.nikita.pischik.orderlunch.service;


import com.nikita.pischik.orderlunch.model.OrderModel;

import java.util.List;

public interface OrderService {
    OrderModel findById(int id);
    List<OrderModel> findAllOrders();
    List<OrderModel> findNotSentOrders();
    void save(OrderModel orderModel);
}
