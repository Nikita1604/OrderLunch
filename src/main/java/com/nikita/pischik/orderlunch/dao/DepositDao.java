package com.nikita.pischik.orderlunch.dao;


import com.nikita.pischik.orderlunch.model.Deposit;

import java.util.List;

public interface DepositDao {

    Deposit findById(int id);
    void save (Deposit deposit);
    List<Deposit> findAllDeposits();
}
