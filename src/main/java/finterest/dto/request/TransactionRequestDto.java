package finterest.dto.request;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class TransactionRequestDto {
    private Long accountId;
    private Long categoryId;
    private BigDecimal amount;
    private String currency;
    private String note;
    private String date;

    public Long getAccountId() {
        return accountId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public String getNote() {
        return note;
    }

    public String getDate() {
        return date;
    }
}
