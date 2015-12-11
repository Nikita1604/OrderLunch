package com.nikita.pischik.orderlunch.dao;


import com.nikita.pischik.orderlunch.model.Deposit;

public interface DepositDao {

    Deposit findById(int id);
    void save (Deposit deposit);
}
