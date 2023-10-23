package finterest.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "transaction_category_id")
    private TransactionCategory transactionCategory;
    private String date;
    private String note;
    private BigDecimal amount;
    private String currency;
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    public Transaction() {
    }

    public Transaction(TransactionCategory transactionCategory, String date, String note,
                       BigDecimal amount, String currency, Account account) {
        this.transactionCategory = transactionCategory;
        this.date = date;
        this.note = note;
        this.amount = amount;
        this.currency = currency;
        this.account = account;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public TransactionCategory getTransactionCategory() {
        return transactionCategory;
    }

    public void setTransactionCategory(TransactionCategory transactionCategory) {
        this.transactionCategory = transactionCategory;
    }

    @Override
    public String toString() {
        return "Transaction{"
                + "id=" + id
                + ", date='" + date + '\''
                + ", note='" + note + '\''
                + ", amount=" + amount
                + ", currency='" + currency + '\''
                + ", account=" + account
                + ", transactionCategory=" + transactionCategory
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Transaction that = (Transaction) o;
        return Objects.equals(id, that.id)
                && Objects.equals(date, that.date)
                && Objects.equals(note, that.note)
                && Objects.equals(amount, that.amount)
                && Objects.equals(currency, that.currency)
                && Objects.equals(account, that.account)
                && Objects.equals(transactionCategory, that.transactionCategory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, note, amount, currency,
                account, transactionCategory);
    }
}
