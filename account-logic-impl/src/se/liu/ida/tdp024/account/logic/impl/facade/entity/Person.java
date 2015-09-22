/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.liu.ida.tdp024.account.logic.impl.facade.entity;

/**
 *
 * @author jesba289
 */
public class Person {
    private String name;
    private String key;
    
    public Person(){}
    
    public Person(String name, String key){
        this.name = name;
        this.key = key;
    }
    
    public String getName(){
        return this.name;
    }
    
    public String getKey(){
        return this.key;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public void setKey(String key){
        this.key = key;
    }
}
