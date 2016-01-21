package com.nikita.pischik.orderlunch.model;

import javax.persistence.*;

@Entity
@Table(name = "deposit")
public class Deposit {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(mappedBy = "deposit")
    private User user;

    @Column(name = "invoice", nullable = false)
    private int invoice;

    @Column(name = "tomorrow_cost", nullable = false)
    private int tomorrow_cost;

    @Column(name = "residue", nullable = false)
    private int residue;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getInvoice() {
        return invoice;
    }

    public void setInvoice(int invoice) {
        this.invoice = invoice;
    }

    public int getTomorrow_cost() {
        return tomorrow_cost;
    }

    public void setTomorrow_cost(int tomorrow_cost) {
        this.tomorrow_cost = tomorrow_cost;
    }

    public int getResidue() {
        return residue;
    }

    public void setResidue(int residue) {
        this.residue = residue;
    }
}
