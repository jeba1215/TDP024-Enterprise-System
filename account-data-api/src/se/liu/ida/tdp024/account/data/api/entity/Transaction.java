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
    public float getAmount();
    public String getStatus();
    public Account getAccount();
    
    public void setId();
    public void setType();
    public void setAmount();
    public void setStatus();
    public void setAccount();
}
