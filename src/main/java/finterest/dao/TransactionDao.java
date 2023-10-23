package finterest.dao;

import finterest.model.Transaction;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface TransactionDao {
    Transaction add(Transaction transaction);

    List<Transaction> getAll();

    Optional<Transaction> get(Long id);

    List<Transaction> getByAccount(Long id);

    void delete(Long id);

    Transaction update(Transaction transaction);

    Transaction deleteWithUpdate(Long id);

    Transaction updateBalanceWithRefund(Transaction t, BigDecimal amountToRefund);
}
