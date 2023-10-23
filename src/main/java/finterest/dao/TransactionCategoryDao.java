package finterest.dao;

import finterest.model.TransactionCategory;

import java.util.List;
import java.util.Optional;

public interface TransactionCategoryDao {
    List<TransactionCategory> getAll();

    TransactionCategory add(TransactionCategory category);

    TransactionCategory getByName(String category);

    Optional<TransactionCategory> get(Long id);
}
