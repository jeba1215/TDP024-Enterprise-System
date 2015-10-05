package se.liu.ida.tdp024.account.data.impl.db.entity;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;

@Entity
public class AccountDB implements Account {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String personKey;
    private String bankKey;
    private long holdings;
    private String accountType;
    private List<Transaction> transactions;
    
    public AccountDB(){}
    
    
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
    public long getHoldings(){
        return holdings;
    }
    
    //-----------------------------------------------------------------
    
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
    public void setHoldings(long holdings){
        this.holdings = holdings;
    }    
}
