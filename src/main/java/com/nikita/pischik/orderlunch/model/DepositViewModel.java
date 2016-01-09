package com.nikita.pischik.orderlunch.model;


public class DepositViewModel {
    private User user;
    private Deposit deposit;
    public DepositViewModel(User user, Deposit deposit) {
        this.user = user;
        this.deposit = deposit;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Deposit getDeposit() {
        return deposit;
    }

    public void setDeposit(Deposit deposit) {
        this.deposit = deposit;
    }
}
