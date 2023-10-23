package finterest.service.mapper;

import finterest.dto.request.TransactionRequestDto;
import finterest.dto.response.TransactionResponseDto;
import finterest.model.Transaction;
import finterest.service.AccountService;
import finterest.service.TransactionCategoryService;
import finterest.service.TransactionService;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper implements RequestDtoMapper<TransactionRequestDto, Transaction>,
        ResponseDtoMapper<TransactionResponseDto, Transaction> {
    private final TransactionService transactionService;
    private final AccountService accountService;
    private final TransactionCategoryService transactionCategoryService;

    public TransactionMapper(TransactionService transactionService, AccountService accountService,
                             TransactionCategoryService transactionCategoryService) {
        this.transactionService = transactionService;
        this.accountService = accountService;
        this.transactionCategoryService = transactionCategoryService;
    }

    @Override
    public Transaction mapToModel(TransactionRequestDto dto) {
        Transaction transaction = new Transaction();
        transaction.setAccount(accountService.get(dto.getAccountId()).get());
        transaction.setTransactionCategory(transactionCategoryService.get(dto.getCategoryId()).get());
        transaction.setAmount(dto.getAmount());
        transaction.setCurrency(dto.getCurrency());
        transaction.setNote(dto.getNote());
        transaction.setDate(dto.getDate());
        return transaction;
    }

    @Override
    public TransactionResponseDto mapToDto(Transaction transaction) {
        TransactionResponseDto responseDto = new TransactionResponseDto();
        responseDto.setAccountId(transaction.getAccount().getId());
        responseDto.setCategoryId(transaction.getTransactionCategory().getId());
        responseDto.setAmount(transaction.getAmount());
        responseDto.setCurrency(transaction.getCurrency());
        responseDto.setNote(transaction.getNote());
        responseDto.setDate(transaction.getDate());
        return responseDto;
    }
}
