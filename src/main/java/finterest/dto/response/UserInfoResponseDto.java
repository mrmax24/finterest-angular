package finterest.dto.response;

import finterest.model.Account;
import finterest.model.Transaction;
import finterest.model.TransactionCategory;
import finterest.model.User;

import java.math.BigDecimal;
import java.util.List;

public class UserInfoResponseDto {
    private User user;
    private List<Account> accounts;
    private List<Transaction> transactions;
    private BigDecimal currentBalance;
    List<TransactionCategory> allCategories;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
    }

    public List<TransactionCategory> getAllCategories() {
        return allCategories;
    }

    public void setAllCategories(List<TransactionCategory> allCategories) {
        this.allCategories = allCategories;
    }
}
