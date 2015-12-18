package com.nikita.pischik.orderlunch.service;


import com.nikita.pischik.orderlunch.model.Deposit;

import java.util.List;

public interface DepositService {

    Deposit findById(int id);
    void saveDeposit(Deposit deposit);
    void updateDeposit(Deposit deposit);
    List<Deposit> findAllDeposits();

}
