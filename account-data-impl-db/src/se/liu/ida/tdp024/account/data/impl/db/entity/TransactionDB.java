/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.liu.ida.tdp024.account.data.impl.db.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;

/**
 *
 * @author jesba289
 */

@Entity
public class TransactionDB implements Transaction{
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String type;
    private long amount;
    private String created;
    private String status;
   
    
    @ManyToOne(targetEntity = AccountDB.class)
    private Account account;
    
    public TransactionDB(){}

    @Override
    public long getId() {
        return this.id;
    }

    @Override
    public String getType() {
        return this.type;
    }

    @Override
    public String getCreated() {
        return this.created;
    }

    @Override
    public long getAmount() {
        return amount;
    }

    @Override
    public String getStatus() {
        return this.status;
    }

    @Override
    public Account getAccount() {
        return this.account;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public void setCreated(String created) {
        this.created = created;
    }

    @Override
    public void setAmount(long amount) {
        this.amount = amount;
    }

    @Override
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public void setAccount(Account account) {
        this.account = account;
    }

    
    
}
