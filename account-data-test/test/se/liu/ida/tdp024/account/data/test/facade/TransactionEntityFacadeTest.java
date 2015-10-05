package se.liu.ida.tdp024.account.data.test.facade;

import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.data.api.facade.TransactionEntityFacade;
import se.liu.ida.tdp024.account.data.api.util.StorageFacade;
import se.liu.ida.tdp024.account.data.impl.db.entity.TransactionDB;
import se.liu.ida.tdp024.account.data.impl.db.facade.AccountEntityFacadeDB;
import se.liu.ida.tdp024.account.data.impl.db.facade.TransactionEntityFacadeDB;
import se.liu.ida.tdp024.account.data.impl.db.util.StorageFacadeDB;

public class TransactionEntityFacadeTest {
    
    //---- Unit under test ----//
    private TransactionEntityFacade transactionEntityFacade = new TransactionEntityFacadeDB();
    private AccountEntityFacade accountEntityFacade = new AccountEntityFacadeDB();
    private StorageFacade storageFacade = new StorageFacadeDB();
    
    @After
    public void tearDown() {
        storageFacade.emptyStorage();
    }        
    
    @Test
    public void testCredit(){
        {
            //Credit nonexisting account
            String status = transactionEntityFacade.debit(1l, 100000l);
            Assert.assertEquals("FAILED", status);
        }  
        accountEntityFacade.create("CHECK", "abc", "qwerty");        
        {
            //Credit existing account
            String status = transactionEntityFacade.credit(1l, 100l);
            Assert.assertEquals("OK", status);
            
            status = transactionEntityFacade.credit(99999l, 100l);
            Assert.assertEquals("FAILED", status);
        }
    }
    
    @Test
    public void testDebit() {
        {
            //Debit nonexisting account
            String status = transactionEntityFacade.debit(1l, 100000l);
            Assert.assertEquals("FAILED", status);
        }     
        accountEntityFacade.create("CHECK", "abc", "qwerty");        
        {
            //Debit existing account with valid amount
            transactionEntityFacade.credit(1l, 100l);
            String status = transactionEntityFacade.debit(1l, 100l);
            System.out.print("Credit status: " + status);
            Assert.assertEquals("OK", status);
        }
        {
            //Debit existing account with to much money
            String status = transactionEntityFacade.debit(1l, 100000l);
            Assert.assertEquals("FAILED", status);
        }
    }      
    
    @Test
    public void testFindById(){
        {
            //Find nonexisting transaction
            List<Transaction> lst = transactionEntityFacade.findById(999l);
            Assert.assertTrue( ( lst.isEmpty()) );
        }
        
        {
            //Find existing transaction
            accountEntityFacade.create("CHECK", "abc", "qwerty");
            transactionEntityFacade.credit(1l, 10);
            List<Transaction> lst = transactionEntityFacade.findById(1l);
            Assert.assertTrue(( !lst.isEmpty() ));
        }
    }
}