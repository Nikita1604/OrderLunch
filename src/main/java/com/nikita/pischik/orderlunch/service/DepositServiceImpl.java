package com.nikita.pischik.orderlunch.service;

import com.nikita.pischik.orderlunch.dao.DepositDao;
import com.nikita.pischik.orderlunch.model.Deposit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("depositService")
@Transactional
public class DepositServiceImpl implements DepositService{

    @Autowired
    DepositDao dao;

    public Deposit findById(int id) {
        return dao.findById(id);
    }

    public void saveDeposit(Deposit deposit) {
        dao.save(deposit);
    }

    public void updateDeposit(Deposit deposit) {
        Deposit entity = dao.findById(deposit.getId());
        if (entity != null) {
            entity.setInvoice(deposit.getInvoice());
            entity.setResidue(deposit.getResidue());
            entity.setTomorrow_cost(deposit.getTomorrow_cost());
            entity.setUser(deposit.getUser());
        }
    }
}
