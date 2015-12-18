package com.nikita.pischik.orderlunch.dao;

import com.nikita.pischik.orderlunch.model.Deposit;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    public List<Deposit> findAllDeposits() {
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("id"));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List<Deposit> deposits = (List<Deposit>) criteria.list();
        return deposits;
    }
}
