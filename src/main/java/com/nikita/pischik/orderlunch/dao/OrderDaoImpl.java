package com.nikita.pischik.orderlunch.dao;


import com.nikita.pischik.orderlunch.model.OrderModel;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


@Repository("orderDao")
public class OrderDaoImpl extends AbstractDao<Integer, OrderModel> implements OrderDao{

    public OrderModel findById(int id) {
        return getByKey(id);
    }

    public List<OrderModel> findAllOrders() {
        Criteria criteria = createEntityCriteria().addOrder(org.hibernate.criterion.Order.asc("id"));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List<OrderModel> orderModels = (List<OrderModel>) criteria.list();
        return orderModels;
    }

    public List<OrderModel> findNotSentOrders() {
        Criteria criteria = createEntityCriteria();
        criteria.addOrder(org.hibernate.criterion.Order.asc("id"));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List<OrderModel> orderModels = (List<OrderModel>) criteria.list();
        List<OrderModel> ans = new ArrayList<OrderModel>();
        for (int i = 0; i< orderModels.size(); i++) {
            if (!orderModels.get(i).is_send()) {
                ans.add(orderModels.get(i));
            }
        }
        return ans;
    }

    public void save(OrderModel orderModel) {
        persist(orderModel);
    }

    public void update(OrderModel orderModel) {
        OrderModel entity = findById(orderModel.getId());
        if (entity != null) {
            entity.setIs_send(orderModel.is_send());
            entity.setDate(orderModel.getDate());
            entity.setOrderList(orderModel.getOrderList());
            entity.setUser(orderModel.getUser());
        }
    }
}
