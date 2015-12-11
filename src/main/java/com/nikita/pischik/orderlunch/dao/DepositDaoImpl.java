package com.nikita.pischik.orderlunch.dao;

import com.nikita.pischik.orderlunch.model.Deposit;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

@Repository("depositDao")
public class DepositDaoImpl extends AbstractDao<Integer, Deposit> implements DepositDao {

    public Deposit findById(int id) {
        Deposit deposit = getByKey(id);
        if (deposit != null) {
            Hibernate.initialize(deposit.getUser());
        }
        return deposit;
    }

    public void save(Deposit deposit) {
        persist(deposit);
    }
}
