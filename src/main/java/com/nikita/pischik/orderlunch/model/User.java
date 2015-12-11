package com.nikita.pischik.orderlunch.model;

import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "login", unique = true, nullable = false)
    private String login;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "user_name", nullable = false)
    private String name;

    @Column(name = "e_mail", nullable = false)
    private String e_mail;

    @Column(name = "state", nullable = false)
    private String state = State.ACTIVE.getState();

    @OneToOne(cascade = javax.persistence.CascadeType.ALL)
    @JoinColumn(name = "deposit_id")
    private Deposit deposit;

    @Column(name = "company", nullable = false)
    private String company;

    @ManyToMany(fetch = FetchType.EAGER, cascade = javax.persistence.CascadeType.MERGE)
    @JoinTable(name = "user_user_role",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "user_role_id") })
    private Set<UserRole> userProfiles = new HashSet<UserRole>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getE_mail() {
        return e_mail;
    }

    public void setE_mail(String e_mail) {
        this.e_mail = e_mail;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }


    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Set<UserRole> getUserRoles() {
        return userProfiles;
    }

    public void setUserRoles(Set<UserRole> userProfiles) {
        this.userProfiles = userProfiles;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((login == null) ? 0 : login.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof User))
            return false;
        User other = (User) obj;
        if (id != other.id)
            return false;
        if (login == null) {
            if (other.login != null)
                return false;
        } else if (!login.equals(other.login))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", login=" + login + ", password=" + password
                + ", userName =" + name
                + ", email=" + e_mail + ", state=" + state + ", userProfiles=" + userProfiles +
                ", deposit_id=" + deposit + ", company=" + company + "]";
    }

    public Deposit getDeposit() {
        return deposit;
    }

    public void setDeposit(Deposit deposit) {
        this.deposit = deposit;
    }
}
