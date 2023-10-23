package finterest.service.impl;

import finterest.dao.AccountDao;
import finterest.model.Account;
import finterest.service.AccountService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    private AccountDao accountDao;

    public AccountServiceImpl(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public Account add(Account account) {
        if (account.getBalance().compareTo(BigDecimal.ZERO) >= 0) {
            return accountDao.add(account);
        } else {
            throw new RuntimeException("The account balance cannot be negative");
        }
    }

    @Override
    public Optional<Account> get(Long id) {
        return accountDao.get(id);
    }

    @Override
    public Optional<Account> get(Long id, Long userId) {
        return accountDao.get(id, userId);
    }

    @Override
    public List<Account> getAll() {
        return accountDao.getAll();
    }

    @Override
    public List<Account> getAllByUser(Long userId) {
        return accountDao.getAllByUser(userId);
    }

    @Override
    public Account update(Account account) {
        if (account.getBalance().compareTo(BigDecimal.ZERO) >= 0) {
            return accountDao.update(account);
        } else {
            throw new RuntimeException("The account balance cannot be negative");
        }
    }

    @Override
    public Account updateBalanceWithExpenses(Account account, BigDecimal expenseAmount) {
        if (account.getBalance().compareTo(BigDecimal.ZERO) >= 0) {
            BigDecimal currentBalance = account.getBalance();
            BigDecimal newBalance = currentBalance.subtract(expenseAmount);
            account.setBalance(newBalance);
            accountDao.update(account);
        } else {
            throw new RuntimeException("The account balance cannot be negative");
        }
        return account;
    }

    @Override
    public Account updateBalanceWithRefund(Account account, BigDecimal amountToRefund) {
        BigDecimal currentBalance = account.getBalance();
        BigDecimal newBalance = currentBalance.add(amountToRefund);
        account.setBalance(newBalance);
        accountDao.update(account);
        return account;
    }

    @Override
    public BigDecimal getTotalBalance(Long userId) {
        return accountDao.getTotalBalance(userId);
    }

    @Override
    public void delete(Long accountId) {
        accountDao.delete(accountId);
    }
}
