package finterest.dto.response;


import java.math.BigDecimal;

public class TransactionsListDto {
    private Long id;
    private String date;
    private String account;
    private String categoryName;
    private String note;
    private BigDecimal amount;

    public TransactionsListDto() {
    }

    public TransactionsListDto(Long transactionId, String date, String account, String categoryName,
                               String note, BigDecimal amount) {
        this.id = transactionId;
        this.date = date;
        this.account = account;
        this.categoryName = categoryName;
        this.note = note;
        this.amount = amount;
    }

    public Long getTransactionId() {
        return id;
    }

    public void setTransactionId(Long transactionId) {
        this.id = transactionId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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
}
