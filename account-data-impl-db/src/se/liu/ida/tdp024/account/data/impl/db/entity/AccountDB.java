package se.liu.ida.tdp024.account.data.impl.db.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import se.liu.ida.tdp024.account.data.api.entity.Account;

@Entity
public class AccountDB implements Account {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String personKey;
    private String bankKey;
    private String accountType;
    private double holdings;
    
    @Override
    public long getId(){
        return id;   
    }
    
    @Override
    public String getPersonKey(){
        return personKey;
    }
    
    @Override
    public String getBankKey(){
        return bankKey;
    }
    
    @Override
    public String getAccountType(){
        return accountType;
    }
    
    @Override
    public double getHoldings(){
        return holdings;
    }
    
    //-----------------------------------------------------------------
    
    @Override
    public void setId(long id){
        this.id = id;
    }
    
    @Override
    public void setPersonKey(String personKey){
        this.personKey = personKey;
    }
    
    @Override
    public void setBankKey(String bankKey){
        this.bankKey = bankKey;
    }
    
    @Override
    public void setAccountType(String accounttype){
        this.accountType = accounttype;
    }
    
    @Override
    public void setHoldings(double holdings){
        this.holdings = holdings;
    }    
}
