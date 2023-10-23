package finterest.dao;

import finterest.model.Account;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface AccountDao {
    Account add(Account account);

    Optional<Account> get(Long id);

    Optional<Account> get(Long id, Long userId);

    List<Account> getAll();

    List<Account> getAllByUser(Long userId);

    Account update(Account account);

    Account updateBalanceWithExpenses(Account account, BigDecimal expenseAmount);

    BigDecimal getTotalBalance(Long userId);

    void delete(Long id);
}
