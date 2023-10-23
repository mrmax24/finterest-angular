package finterest.service.impl;

import finterest.dao.TransactionDao;
import finterest.model.Account;
import finterest.model.Transaction;
import finterest.service.AccountService;
import finterest.service.TransactionService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionDao transactionDao;
    private final AccountService accountService;

    public TransactionServiceImpl(TransactionDao transactionDao,
                                  AccountService accountService) {
        this.transactionDao = transactionDao;
        this.accountService = accountService;
    }

    @Override
    public Transaction add(Transaction transaction) throws RuntimeException {
        Transaction addedTransaction = transactionDao.add(transaction);
        Account account = transaction.getAccount();
        BigDecimal expenseAmount = transaction.getAmount();
        accountService.updateBalanceWithExpenses(account, expenseAmount);
        return addedTransaction;
    }

    @Override
    public List<Transaction> getAll() {
        return transactionDao.getAll();
    }

    @Override
    public Optional<Transaction> get(Long id) {
        return transactionDao.get(id);
    }

    @Override
    public List<Transaction> getByAccount(Long accountId) {
        return transactionDao.getByAccount(accountId);
    }

    @Override
    public void delete(Long id) {
        transactionDao.delete(id);
    }

    @Override
    public Transaction update(Transaction transaction) {

        return transactionDao.update(transaction);
    }

    @Override
    public Transaction deleteWithUpdate(Long id) {
        Transaction deletedTransaction = transactionDao.deleteWithUpdate(id);
        Account account = deletedTransaction.getAccount();
        accountService.updateBalanceWithRefund(account, deletedTransaction.getAmount());
        return deletedTransaction;
    }
}
