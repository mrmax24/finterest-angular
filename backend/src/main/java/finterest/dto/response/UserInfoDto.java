package finterest.dto.response;

import java.math.BigDecimal;
import java.util.List;

public class UserInfoDto {
    private String userName;
    private BigDecimal currentBalance;
    private List<AccountDto> userAccounts;

    public UserInfoDto(String userName, BigDecimal currentBalance,
                       List<AccountDto> userAccounts) {
        this.userName = userName;
        this.currentBalance = currentBalance;
        this.userAccounts = userAccounts;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
    }

    public List<AccountDto> getUserAccounts() {
        return userAccounts;
    }

    public void setUserAccounts(List<AccountDto> userAccounts) {
        this.userAccounts = userAccounts;
    }
}
