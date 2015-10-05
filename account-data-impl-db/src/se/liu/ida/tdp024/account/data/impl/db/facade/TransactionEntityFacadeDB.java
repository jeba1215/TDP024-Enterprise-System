/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.liu.ida.tdp024.account.data.impl.db.facade;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.Query;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.api.facade.TransactionEntityFacade;
import se.liu.ida.tdp024.account.data.impl.db.entity.AccountDB;
import se.liu.ida.tdp024.account.data.impl.db.entity.TransactionDB;
import se.liu.ida.tdp024.account.data.impl.db.util.EMF;
import se.liu.ida.tdp024.account.util.logger.AccountLogger;
import se.liu.ida.tdp024.account.util.logger.AccountLoggerMonlog;

/**
 *
 * @author jesba289
 */
public class TransactionEntityFacadeDB implements TransactionEntityFacade{

    private static final AccountLogger accountLogger = new AccountLoggerMonlog();
    
    @Override
    public List<Transaction> findById(Long id) {
        EntityManager em = EMF.getEntityManager();        
        Query query = em.createQuery("SELECT t FROM TransactionDB t where t.account.id = :id");
        query.setParameter("id", id);
        return query.getResultList();
    }

    @Override
    public String debit(Long id, long amount) {                
        EntityManager em = EMF.getEntityManager();       
        Account account = em.find(AccountDB.class, id, LockModeType.PESSIMISTIC_WRITE);
        if(account == null){
            System.out.println("Ayy Lmao");
            em.close();
            return "FAILED";
        }
        
        System.out.print("Found account");
        em.getTransaction().begin();
        
        Transaction transaction = new TransactionDB();
        transaction.setType("DEBIT");
        transaction.setAmount(amount);
        transaction.setCreated(new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
        transaction.setStatus("OK");
        transaction.setAccount(account);
        
        System.out.println("----- Debit -----");
        System.out.println("debit AMMOUNT: " + amount);
        System.out.println("debit holdings: " + account.getHoldings());
        System.out.println("debit math: " + (account.getHoldings() - amount));
        long holdings = (account.getHoldings() - amount);
        System.out.println("debit variable: " + (account.getHoldings() - amount));
        
        if(amount >= 0 && account.getHoldings() >= amount)
            account.setHoldings(holdings);
        else{
            accountLogger.log(
                    AccountLogger.TodoLoggerLevel.CRITICAL,
                    "Debit amount was less than 0 or greater than the account holdings",
                    "Debit amount was less than 0 or greater than the account holdings for account: " + account.getPersonKey());
            transaction.setStatus("FAILED");
        }
        
        em.persist(transaction);
        em.getTransaction().commit();
        em.close();
        return transaction.getStatus();
    }

    @Override
    public String credit(Long id, long amount) {
        
        
        EntityManager em = EMF.getEntityManager();       
        Account account = em.find(AccountDB.class, id, LockModeType.PESSIMISTIC_WRITE);
        if(account == null){            
            em.close();
            return "FAILED";
        }
            
        System.out.print("Found account");
        em.getTransaction().begin();
        
        Transaction transaction = new TransactionDB();
        transaction.setType("CREDIT");
        transaction.setAmount(amount);
        transaction.setCreated(new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
        transaction.setStatus("OK");
        transaction.setAccount(account);
        
        System.out.println("----- Credit -----");
        System.out.println("credit AMMOUNT: " + amount);
        System.out.println("credit holdings: " + account.getHoldings());
        long holdings = (account.getHoldings() + amount);
        System.out.println("credit variable: " + (amount + account.getHoldings()));
        
        //Account Credit        
        if(amount >= 0)
            account.setHoldings(holdings);
        else{
            accountLogger.log(
                    AccountLogger.TodoLoggerLevel.CRITICAL,
                    "Credit amount has to be greater than 0",
                    "Can't credit ammount: " + amount);
            transaction.setStatus("FAILED");
        }
        
        em.persist(transaction);
        em.getTransaction().commit();
        em.close();
        return transaction.getStatus();
    }    
}
