package finterest.service.mapper;

import finterest.dto.request.AccountRequestDto;
import finterest.dto.response.AccountResponseDto;
import finterest.model.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper implements RequestDtoMapper<AccountRequestDto, Account>,
        ResponseDtoMapper<AccountResponseDto, Account> {

    @Override
    public Account mapToModel(AccountRequestDto dto) {
        Account account = new Account();
        account.setName(dto.getName());
        account.setBalance(dto.getBalance());
        return account;
    }

    @Override
    public AccountResponseDto mapToDto(Account account) {
        AccountResponseDto responseDto = new AccountResponseDto();
        responseDto.setId(account.getId());
        responseDto.setName(account.getName());
        responseDto.setBalance(account.getBalance());
        return responseDto;
    }
}
