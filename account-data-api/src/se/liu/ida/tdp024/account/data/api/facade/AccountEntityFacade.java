package se.liu.ida.tdp024.account.data.api.facade;

public interface AccountEntityFacade {
    public long create(String accountType, String name, String bank);
}
