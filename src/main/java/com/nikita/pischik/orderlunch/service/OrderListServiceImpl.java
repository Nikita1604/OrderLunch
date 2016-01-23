package com.nikita.pischik.orderlunch.service;


import com.nikita.pischik.orderlunch.dao.OrderListDao;
import com.nikita.pischik.orderlunch.model.OrderList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("orderListService")
@Transactional
public class OrderListServiceImpl implements OrderListService{

    @Autowired
    private OrderListDao dao;

    public OrderList findById(int id) {
        return dao.findById(id);
    }

    public void save(OrderList orderList) {
        dao.save(orderList);
    }
}
