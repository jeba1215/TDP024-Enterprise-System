/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.liu.ida.tdp024.account.data.api.entity;

import java.io.Serializable;

/**
 *
 * @author jesba289
 */
public interface Transaction extends Serializable{
    public long getId();
    public String getType();
    public String getCreated();
    public long getAmount();
    public String getStatus();
    public Account getAccount();
    
    public void setType(String type);
    public void setCreated(String created);
    public void setAmount(long amount);
    public void setStatus(String status);
    public void setAccount(Account account);
}
