package se.liu.ida.tdp024.account.logic.impl.facade;

import java.util.List;
import java.util.Vector;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.logic.api.facade.AccountLogicFacade;
import se.liu.ida.tdp024.account.logic.impl.facade.entity.Bank;
import se.liu.ida.tdp024.account.logic.impl.facade.entity.Person;
import se.liu.ida.tdp024.account.util.http.HTTPHelperImpl;
import se.liu.ida.tdp024.account.util.json.AccountJsonSerializerImpl;

public class AccountLogicFacadeImpl implements AccountLogicFacade {
    
    private AccountEntityFacade accountEntityFacade;
    
    public AccountLogicFacadeImpl(AccountEntityFacade accountEntityFacade) {
        this.accountEntityFacade = accountEntityFacade;
    }
    
    public long create(String accounttype, String name, String bank) {
        HTTPHelperImpl plzhalp = new HTTPHelperImpl();
        String personJSON = plzhalp.get("http://enterprise-systems.appspot.com/person/find.name", "name", name);
        String bankJSON = plzhalp.get("http://enterprise-systems.appspot.com/bank/find.name", "name", bank);
        
        
        AccountJsonSerializerImpl jsonHalp = new AccountJsonSerializerImpl();
        Person person = jsonHalp.fromJson(personJSON, Person.class);        
        Bank bankerino = jsonHalp.fromJson(bankJSON, Bank.class);
        
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
        if(person == null) 
            id = "0";
        else
            id = person.getKey();        
        return accountEntityFacade.find(id);
    }
    
}
