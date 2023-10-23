package finterest.service;

import finterest.model.Transaction;

import java.util.List;
import java.util.Optional;

public interface TransactionService {
    Transaction add(Transaction transaction);

    List<Transaction> getAll();

    Optional<Transaction> get(Long id);

    List<Transaction> getByAccount(Long accountId);

    void delete(Long id);

    Transaction update(Transaction transaction);

    Transaction deleteWithUpdate(Long id);
}
