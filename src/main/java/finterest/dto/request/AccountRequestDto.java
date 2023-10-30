package finterest.dto.request;

import finterest.model.TransactionCategory;
import finterest.model.User;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Set;

public class AccountRequestDto {
    private String name;
    private BigDecimal balance;

    public String getName() {
        return name;
    }

    public BigDecimal getBalance() {
        return balance;
    }

}
