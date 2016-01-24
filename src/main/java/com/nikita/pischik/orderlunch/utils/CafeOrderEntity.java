package com.nikita.pischik.orderlunch.utils;


import java.util.List;

public class CafeOrderEntity {
    private int OrderId;
    private String AdminName;
    private String CustomerName;
    private String Phone;
    private String Address;
    private String Email;
    private String Note;
    private int Day;
    private List<GuyOrders> GuysOrders;

    public List<GuyOrders> getGuysOrders() {
        return GuysOrders;
    }

    public void setGuysOrders(List<GuyOrders> guysOrders) {
        GuysOrders = guysOrders;
    }

    public int getDay() {
        return Day;
    }

    public void setDay(int day) {
        Day = day;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getAdminName() {
        return AdminName;
    }

    public void setAdminName(String adminName) {
        AdminName = adminName;
    }

    public int getOrderId() {
        return OrderId;
    }

    public void setOrderId(int orderId) {
        OrderId = orderId;
    }

    public static class GuyOrders {
        private int GuyOrderId;
        private int OrderId;
        private String Name;
        private String Dishes;
        private String Counts;

        public int getGuyOrderId() {
            return GuyOrderId;
        }

        public void setGuyOrderId(int guyOrderId) {
            GuyOrderId = guyOrderId;
        }

        public int getOrderId() {
            return OrderId;
        }

        public void setOrderId(int orderId) {
            OrderId = orderId;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public String getDishes() {
            return Dishes;
        }

        public void setDishes(String dishes) {
            Dishes = dishes;
        }

        public String getCounts() {
            return Counts;
        }

        public void setCounts(String counts) {
            Counts = counts;
        }
    }
}
