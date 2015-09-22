package se.liu.ida.tdp024.account.data.impl.db.facade;

import java.util.List;
import java.util.Vector;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.data.impl.db.entity.AccountDB;
import se.liu.ida.tdp024.account.data.impl.db.util.EMF;
import se.liu.ida.tdp024.account.util.http.HTTPHelperImpl;

public class AccountEntityFacadeDB implements AccountEntityFacade {
    
    public long create(String accountType, String personKey, String bankKey){
        EntityManager em = EMF.getEntityManager();
        
        em.getTransaction().begin();
        Account account = new AccountDB();              
        
        //set type, personkey and bankkey
        account.setAccountType(accountType);
        account.setPersonKey(personKey);
        account.setBankKey(bankKey);

        em.persist(account);
        em.getTransaction().commit();
        em.close();
        return account.getId();
    }
    
    public List<Account> find(String id) {
        
        EntityManager em = EMF.getEntityManager();        
        Query query = em.createQuery("SELECT a FROM AccountDB a where a.personKey = :id");
        query.setParameter("id", id);
        return query.getResultList();        
    } 
    
    @Override
    public boolean debit(double debit){
        /*
        if(account.getHoldings() > debit){
            account.setHoldings(account.getHoldings() - debit);
            return true;
        }else{
            return false;     
        }
                */
        return true;
    }
    
    @Override
    public boolean credit(double credit){
        /*
        Account account = new AccountDB();
        
        account.setHoldings(account.getHoldings() + credit);
        return true;
        */
        return true;
    }
}
