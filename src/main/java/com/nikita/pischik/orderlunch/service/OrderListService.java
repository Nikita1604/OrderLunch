package com.nikita.pischik.orderlunch.service;


import com.nikita.pischik.orderlunch.model.OrderList;

public interface OrderListService {
    OrderList findById(int id);
}
