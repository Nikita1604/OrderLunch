package com.nikita.pischik.orderlunch.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "order_list")
public class OrderList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(mappedBy = "orderList")
    private Set<OrderItem> orderItems;

    @OneToOne(mappedBy = "orderList")
    private OrderModel orderModel;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public Set<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public OrderModel getOrderModel() {
        return orderModel;
    }

    public void setOrderModel(OrderModel orderModel) {
        this.orderModel = orderModel;
    }
}
