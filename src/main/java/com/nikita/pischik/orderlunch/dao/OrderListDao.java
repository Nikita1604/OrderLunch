package com.nikita.pischik.orderlunch.dao;


import com.nikita.pischik.orderlunch.model.OrderList;

public interface OrderListDao {
    OrderList findById(int id);
}
