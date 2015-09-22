package se.liu.ida.tdp024.account.logic.api.facade;

import java.util.List;
import java.util.Vector;
import se.liu.ida.tdp024.account.data.api.entity.Account;


public interface AccountLogicFacade {
    public long create(String accountType, String name, String bank);
    public List<Account> find(String name);
}
