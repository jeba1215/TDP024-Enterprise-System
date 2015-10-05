package se.liu.ida.tdp024.account.data.api.entity;

import java.io.Serializable;
import java.util.List;

public interface Account extends Serializable {
    public long getId();
    public String getPersonKey();
    public String getBankKey();
    public String getAccountType();
    public long getHoldings();
    
    public void setPersonKey(String personKey);
    public void setBankKey(String bankKey);
    public void setAccountType(String accounttype);
    public void setHoldings(long holdings);
}
