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
import se.liu.ida.tdp024.account.util.logger.AccountLogger;
import se.liu.ida.tdp024.account.util.logger.AccountLoggerMonlog;

public class AccountEntityFacadeDB implements AccountEntityFacade {

    private static final AccountLogger accountLogger = new AccountLoggerMonlog();

    public String create(String accountType, String personKey, String bankKey){
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
        if(account.getId() != 0)
            return "OK";
        else{
            accountLogger.log(
                    AccountLogger.TodoLoggerLevel.CRITICAL,
                    "Account couldn't be saved to database",
                    "We could not get an id for personKey: " + personKey);
            return "FAILED";
        }
    }
    
    public List<Account> findById(String id) {
        
        EntityManager em = EMF.getEntityManager();        
        Query query = em.createQuery("SELECT a FROM AccountDB a where a.personKey = :id");
        query.setParameter("id", id);
        return query.getResultList();        
    }   
}
