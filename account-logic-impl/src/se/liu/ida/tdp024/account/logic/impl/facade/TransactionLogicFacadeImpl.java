/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.liu.ida.tdp024.account.logic.impl.facade;

import java.util.List;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.api.facade.TransactionEntityFacade;
import se.liu.ida.tdp024.account.logic.api.facade.TransactionLogicFacade;

/**
 *
 * @author jesba289
 */
public class TransactionLogicFacadeImpl implements TransactionLogicFacade{
    private final TransactionEntityFacade transactionEntityFacade;

    public TransactionLogicFacadeImpl(TransactionEntityFacade accountEntityFacade) {
        this.transactionEntityFacade = accountEntityFacade;
    }
    
    @Override
    public List<Transaction> find(Long id) {
        return transactionEntityFacade.findById(id);
    }

    @Override
    public String debit(Long id, long amount) {
        return transactionEntityFacade.debit(id, amount);
    }

    @Override
    public String credit(Long id, long amount) {        
        return transactionEntityFacade.credit(id, amount);
    }
    
}
