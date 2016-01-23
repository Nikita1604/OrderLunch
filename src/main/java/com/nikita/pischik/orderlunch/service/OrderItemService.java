package com.nikita.pischik.orderlunch.service;


import com.nikita.pischik.orderlunch.model.OrderItem;

public interface OrderItemService {
    OrderItem findById(int id);
    OrderItem findByDishId(int id);
    void save(OrderItem orderItem);
}
