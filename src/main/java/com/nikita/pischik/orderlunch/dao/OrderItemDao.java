package com.nikita.pischik.orderlunch.dao;

import com.nikita.pischik.orderlunch.model.OrderItem;

public interface OrderItemDao {
    OrderItem findById(int id);
    OrderItem findByDishId(int id);
    void save(OrderItem orderItem);
    void delete(OrderItem orderItem);
}
