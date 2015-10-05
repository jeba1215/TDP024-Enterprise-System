package se.liu.ida.tdp024.account.logic.impl.facade;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.logic.api.facade.AccountLogicFacade;
import se.liu.ida.tdp024.account.logic.impl.facade.entity.Bank;
import se.liu.ida.tdp024.account.logic.impl.facade.entity.Person;
import se.liu.ida.tdp024.account.util.http.HTTPHelperImpl;
import se.liu.ida.tdp024.account.util.json.AccountJsonSerializerImpl;
import se.liu.ida.tdp024.account.util.logger.AccountLogger;
import se.liu.ida.tdp024.account.util.logger.AccountLoggerMonlog;

public class AccountLogicFacadeImpl implements AccountLogicFacade {
    
    private AccountEntityFacade accountEntityFacade;
    private static final AccountLogger accountLogger = new AccountLoggerMonlog();
    
    public AccountLogicFacadeImpl(AccountEntityFacade accountEntityFacade) {
        this.accountEntityFacade = accountEntityFacade;
    }
    
    public String create(String accounttype, String name, String bank) {
        HTTPHelperImpl plzhalp = new HTTPHelperImpl();
        String personJSON = plzhalp.get("http://enterprise-systems.appspot.com/person/find.name", "name", name);
        String bankJSON = plzhalp.get("http://enterprise-systems.appspot.com/bank/find.name", "name", bank);
        
        
        AccountJsonSerializerImpl jsonHalp = new AccountJsonSerializerImpl();
        Person person = jsonHalp.fromJson(personJSON, Person.class);        
        Bank bankerino = jsonHalp.fromJson(bankJSON, Bank.class);                       
        
        if(bankerino == null || person == null){
            accountLogger.log(
                    AccountLogger.TodoLoggerLevel.CRITICAL,
                    "The person or bank given does not exist in database",
                    "Could not find data in database for: " + name + " and " + bank);
            return "FAILED";
        }
        
        if( !Arrays.asList(new String[]{"CHECK", "SAVINGS"}).contains(accounttype)){
            accountLogger.log(
                    AccountLogger.TodoLoggerLevel.CRITICAL,
                    "Accounttype is not a valid value",
                    "Could not create account with accounttype: " + accounttype);
            return "FAILED";
        }
        
        System.out.println("--------- CREATE --------");
        System.out.println("Accounttype: " + accounttype);
        System.out.println("Person: " + person.getName() + " - " + person.getKey());
        System.out.println("Bank: " + bankerino.getName() + " - " + bankerino.getKey());
        
        return accountEntityFacade.create(accounttype, person.getKey(), bankerino.getKey());
    }
    
    public List<Account> find(String name) {
        
        HTTPHelperImpl plzhalp = new HTTPHelperImpl();
        String personJSON = plzhalp.get("http://enterprise-systems.appspot.com/person/find.name", "name", name);
        
        AccountJsonSerializerImpl jsonHalp = new AccountJsonSerializerImpl();
        Person person = jsonHalp.fromJson(personJSON, Person.class);
        
        String id;
        if(person == null){
            accountLogger.log(
                    AccountLogger.TodoLoggerLevel.CRITICAL,
                    "Name doesn't exist in name database",
                    "We couldn't find a name for: " + name);
            return null;
        }
        else
            id = person.getKey();        
        return accountEntityFacade.findById(id);
    }
    
}
