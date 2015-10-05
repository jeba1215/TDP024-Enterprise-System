/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.liu.ida.tdp024.account.logic.api.facade;

import java.util.List;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;

/**
 *
 * @author jesba289
 */
public interface TransactionLogicFacade {
    List<Transaction> find(Long id);
    String debit(Long id, long amount);
    String credit(Long id, long amount);
}
