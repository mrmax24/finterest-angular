package finterest.config;

import finterest.model.TransactionCategory;
import finterest.service.TransactionCategoryService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class DatabaseInitializer {

    private final TransactionCategoryService transactionCategoryService;

    public DatabaseInitializer(TransactionCategoryService transactionCategoryService) {
        this.transactionCategoryService = transactionCategoryService;
    }

    @PostConstruct
    public void initialize() {
        if (transactionCategoryService.getAll().isEmpty()) {
            TransactionCategory.CategoryName[] categoryNames =
                    TransactionCategory.CategoryName.values();

            List<TransactionCategory> transactionCategories = new ArrayList<>();
            for (TransactionCategory.CategoryName categoryName : categoryNames) {
                TransactionCategory transactionCategory = new TransactionCategory(categoryName);
                transactionCategories.add(transactionCategory);
            }
            for (TransactionCategory transactionCategory : transactionCategories) {
                transactionCategoryService.add(transactionCategory);
            }
        }
    }
}
