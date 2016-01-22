package com.nikita.pischik.orderlunch.service;

import com.nikita.pischik.orderlunch.dao.OrderItemDao;
import com.nikita.pischik.orderlunch.model.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("orderItemService")
@Transactional
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemDao dao;

    public OrderItem findById(int id) {
        return dao.findById(id);
    }

    public OrderItem findByDishId(int id) {
        return dao.findByDishId(id);
    }
}
