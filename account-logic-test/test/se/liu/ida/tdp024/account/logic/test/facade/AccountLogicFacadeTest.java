package se.liu.ida.tdp024.account.logic.test.facade;

import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.util.StorageFacade;
import se.liu.ida.tdp024.account.data.impl.db.facade.AccountEntityFacadeDB;
import se.liu.ida.tdp024.account.data.impl.db.util.StorageFacadeDB;
import se.liu.ida.tdp024.account.logic.api.facade.AccountLogicFacade;
import se.liu.ida.tdp024.account.logic.impl.facade.AccountLogicFacadeImpl;

public class AccountLogicFacadeTest {

    
    //--- Unit under test ---//
    public AccountLogicFacade accountLogicFacade = new AccountLogicFacadeImpl(new AccountEntityFacadeDB());
    public StorageFacade storageFacade = new StorageFacadeDB();
    
    @After
    public void tearDown() {
        storageFacade.emptyStorage();
    }
           
    @Test
    public void testCreate() {
        String name;
        String type;
        String bank;
        String status;
        
        {
            name = null;
            type = "CHECK";
            bank = "SWEDBANK";
            
            status = accountLogicFacade.create(type,name,bank);
            Assert.assertEquals("FAILED",status);
        }
        
        {
            name = "Ayy Lmao";
            type = "CHECK";
            bank = "dank bank";
            
            status = accountLogicFacade.create(type,name,bank);
            Assert.assertEquals("FAILED",status);
        }
        
        {
            name = "Ayy Lmao";
            type = "non existing bank account type";
            bank = "SWEDBANK";
            
            status = accountLogicFacade.create(type,name,bank);
            Assert.assertEquals("FAILED",status);
        }
        
        {
            name = "Q";
            type = "CHECK";
            bank = "SWEDBANK";
            
            status = accountLogicFacade.create(type,name,bank);
            Assert.assertEquals("OK",status);
        }
        
        {
            name = null;
            type = null;
            bank = null;

            status = accountLogicFacade.create(type,name,bank);
            Assert.assertEquals("FAILED",status);
        }
        
        
    }
    
    @Test
    public void testFind() {
        String name;
        
        List<Account> result;
        
        {
            name = "Q";
            
            accountLogicFacade.create("CHECK",name,"SWEDBANK");
            result = accountLogicFacade.find(name);
            Assert.assertTrue(!result.isEmpty());
        }
        
        {
            name = "ayy lmao";
            result = accountLogicFacade.find(name);
            Assert.assertTrue(result == null);

        }
    }
}