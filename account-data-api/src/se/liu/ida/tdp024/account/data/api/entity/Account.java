package se.liu.ida.tdp024.account.data.api.entity;

import java.io.Serializable;

public interface Account extends Serializable {
    public long getId();
    public String getPersonKey();
    public String getBankKey();
    public String getAccountType();
    public double getHoldings();
    
    public void setId(long id);
    public void setPersonKey(String personKey);
    public void setBankKey(String bankKey);
    public void setAccountType(String accounttype);
    public void setHoldings(double holdings);    
}
