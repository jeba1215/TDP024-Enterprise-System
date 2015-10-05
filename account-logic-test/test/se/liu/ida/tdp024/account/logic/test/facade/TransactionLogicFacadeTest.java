/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.liu.ida.tdp024.account.logic.test.facade;

import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.api.util.StorageFacade;
import se.liu.ida.tdp024.account.data.impl.db.facade.AccountEntityFacadeDB;
import se.liu.ida.tdp024.account.data.impl.db.facade.TransactionEntityFacadeDB;
import se.liu.ida.tdp024.account.data.impl.db.util.StorageFacadeDB;
import se.liu.ida.tdp024.account.logic.api.facade.AccountLogicFacade;
import se.liu.ida.tdp024.account.logic.api.facade.TransactionLogicFacade;
import se.liu.ida.tdp024.account.logic.impl.facade.AccountLogicFacadeImpl;
import se.liu.ida.tdp024.account.logic.impl.facade.TransactionLogicFacadeImpl;

/**
 *
 * @author jesba289
 */
public class TransactionLogicFacadeTest {

    //--- Unit under test ---//
    public AccountLogicFacade accountLogicFacade = new AccountLogicFacadeImpl(new AccountEntityFacadeDB());
    public TransactionLogicFacade transactionLogicFacade = new TransactionLogicFacadeImpl(new TransactionEntityFacadeDB());
    public StorageFacade storageFacade = new StorageFacadeDB();
    
    @After
    public void tearDown() {
        storageFacade.emptyStorage();
    }
           
    @Test
    public void testFind() {
        accountLogicFacade.create("CHECK","Q","SWEDBANK");
        transactionLogicFacade.credit(1l, 10l);
        {
            //Find existing
            List<Transaction> lst = transactionLogicFacade.find(1l);
            Assert.assertTrue(!lst.isEmpty());
        }
        {
            //Find nonexisting
            List<Transaction> lst = transactionLogicFacade.find(99999l);
            Assert.assertTrue(lst.isEmpty());
        }
        {
            //Find null
            List<Transaction> lst = transactionLogicFacade.find(null);
            Assert.assertTrue(lst.isEmpty());
        }
    }
    
    @Test
    public void testDebit() {
        accountLogicFacade.create("CHECK","Q","SWEDBANK");
        transactionLogicFacade.credit(1l, 100);
        {
            //Debit valid amount
            String status = transactionLogicFacade.debit(1l, 10);
            Assert.assertEquals("OK", status);
        }
        {
            //Debit to much
            String status = transactionLogicFacade.debit(1l, 100000);
            Assert.assertEquals("FAILED", status);
        }
        {
            //Debit 0
            String status = transactionLogicFacade.debit(1l, -10);
            Assert.assertEquals("FAILED", status);
        }
    }
    
    @Test
    public void testCredit() {
        accountLogicFacade.create("CHECK","Q","SWEDBANK");
        {
            //Credit valid amount
            String status = transactionLogicFacade.credit(1l, 10);
            Assert.assertEquals("OK", status);
        }
        {
            //Credit invalid amount
            String status = transactionLogicFacade.credit(1l, -10);
            Assert.assertEquals("FAILED", status);
        }
    }
}
