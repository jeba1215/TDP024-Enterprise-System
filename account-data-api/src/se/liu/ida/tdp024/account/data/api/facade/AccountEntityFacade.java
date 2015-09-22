package se.liu.ida.tdp024.account.data.api.facade;

import java.util.List;
import java.util.Vector;
import se.liu.ida.tdp024.account.data.api.entity.Account;

public interface AccountEntityFacade {
    public long create(String accountType, String name, String bank);
    public List<Account> find(String id);
    public boolean debit(double debit);
    public boolean credit(double credit);
}
