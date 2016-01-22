package com.nikita.pischik.orderlunch.service;

import com.nikita.pischik.orderlunch.dao.OrderDao;
import com.nikita.pischik.orderlunch.model.OrderModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("orderService")
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao dao;

    public OrderModel findById(int id) {
        return dao.findById(id);
    }

    public List<OrderModel> findAllOrders() {
        return dao.findAllOrders();
    }

    public List<OrderModel> findNotSentOrders() {
        return dao.findNotSentOrders();
    }
}
