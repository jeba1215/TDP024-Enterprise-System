package tdp024.account.rest.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.Vector;
import javax.naming.spi.DirStateFactory.Result;
import javax.transaction.Transaction;
import javax.ws.rs.core.Response;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Test;
import se.liu.ida.tdp024.account.data.api.util.StorageFacade;
import se.liu.ida.tdp024.account.data.impl.db.util.StorageFacadeDB;
import se.liu.ida.tdp024.account.logic.impl.facade.entity.Person;
import se.liu.ida.tdp024.account.rest.service.AccountService;
import se.liu.ida.tdp024.account.util.json.AccountJsonSerializer;
import se.liu.ida.tdp024.account.util.json.AccountJsonSerializerImpl;

public class AccountServiceTest {

    //-- Units under test ---//
    private AccountService accountService = new AccountService();
    private StorageFacade storageFacade  = new StorageFacadeDB();
    private AccountJsonSerializer jsonSerializer = new AccountJsonSerializerImpl();

    @After
    public void tearDown() {
        storageFacade.emptyStorage();
    }

    @Test
    public void testCreateandFind() {
        Response result = accountService.create("CHECK", "Q", "SWEDBANK");        
        Assert.assertEquals("OK", result.getEntity());
        Assert.assertEquals(200, result.getStatus());
        
        result = accountService.find("Q");
        Vector<Person> persons = (Vector<Person>) result.getEntity();
        Assert.assertTrue(!persons.isEmpty());
        
        result = accountService.find("nonexistent");
        Assert.assertEquals("[]", result.getEntity());
    }
    
    @Test
    public void testDebit() {
        accountService.create("CHECK", "Q", "SWEDBANK");
        accountService.credit(1l, 10);
        
        Response result = accountService.debit(1l, 10);
        Assert.assertEquals("OK", result.getEntity());
        
        result = accountService.debit(1l, 100000);
        Assert.assertEquals("FAILED", result.getEntity());               
        
        result = accountService.debit(1l, -10);
        Assert.assertEquals("FAILED", result.getEntity());               
    }
    
    @Test
    public void testCredit() {
        accountService.create("CHECK", "Q", "SWEDBANK");
        
        Response result = accountService.credit(1l, 10);
        Assert.assertEquals("OK", result.getEntity());                      
        
        result = accountService.credit(1l, -10);
        Assert.assertEquals("FAILED", result.getEntity());
    }
    
    @Test
    public void testTransactions() {
        accountService.create("CHECK", "Q", "SWEDBANK");
        accountService.credit(1l, 10);
                       
        Response result = accountService.transactions(1l);
        Vector<Transaction> transactions = (Vector<Transaction>) result.getEntity();
        Assert.assertTrue(!transactions.isEmpty());
        
        result = accountService.transactions(999l);
        Assert.assertEquals(400, result.getStatus());
    }
}