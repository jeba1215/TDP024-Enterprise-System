package se.liu.ida.tdp024.account.data.api.facade;

import java.util.List;
import java.util.Vector;
import se.liu.ida.tdp024.account.data.api.entity.Account;

public interface AccountEntityFacade {
    public String create(String accountType, String name, String bank);
    public List<Account> findById(String id);
}
