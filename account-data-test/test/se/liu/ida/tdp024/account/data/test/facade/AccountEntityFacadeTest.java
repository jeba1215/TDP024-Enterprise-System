package se.liu.ida.tdp024.account.data.test.facade;

import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.data.api.util.StorageFacade;
import se.liu.ida.tdp024.account.data.impl.db.facade.AccountEntityFacadeDB;
import se.liu.ida.tdp024.account.data.impl.db.util.StorageFacadeDB;

public class AccountEntityFacadeTest {
    
    //---- Unit under test ----//
    private AccountEntityFacade accountEntityFacade = new AccountEntityFacadeDB();
    private StorageFacade storageFacade = new StorageFacadeDB();
    
    @After
    public void tearDown() {
        storageFacade.emptyStorage();
    }
    
    @Test
    public void testCreate() {
        String status = accountEntityFacade.create("CHECK", "abc", "qwerty");     
        Assert.assertEquals("OK", status);        
    }
    
    @Test
    public void testFindById(){
        String status = accountEntityFacade.create("CHECK", "abc", "qwerty");
        List<Account> accounts = accountEntityFacade.findById("1");
        if(!accounts.isEmpty()){
            Account account = accounts.get(0);
            Assert.assertEquals("abc", account.getPersonKey());
            Assert.assertEquals("qwerty", account.getBankKey());
            Assert.assertEquals("CHECK", account.getAccountType());
        }
    }
}