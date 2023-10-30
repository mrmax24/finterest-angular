package finterest.controller;

import finterest.dto.request.AccountRequestDto;
import finterest.dto.request.TransactionRequestDto;
import finterest.dto.response.*;
import finterest.model.Account;
import finterest.model.Transaction;
import finterest.model.TransactionCategory;
import finterest.model.User;
import finterest.service.AccountService;
import finterest.service.TransactionCategoryService;
import finterest.service.TransactionService;
import finterest.service.UserService;
import finterest.service.impl.UserNameProvider;
import finterest.service.mapper.RequestDtoMapper;
import finterest.service.mapper.ResponseDtoMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
public class AccountController {
    private final UserNameProvider userNameProvider;
    private final UserService userService;
    private final AccountService accountService;
    private final TransactionService transactionService;
    private final TransactionCategoryService transactionCategoryService;
    private final RequestDtoMapper<AccountRequestDto, Account> accountRequestDtoMapper;
    private final ResponseDtoMapper<AccountResponseDto, Account> accountResponseDtoMapper;
    private final RequestDtoMapper<TransactionRequestDto, Transaction> requestDtoMapper;
    private final ResponseDtoMapper<TransactionResponseDto, Transaction> responseDtoMapper;

    public AccountController(UserService userService,
                             AccountService accountService,
                             UserNameProvider userNameProvider,
                             TransactionService transactionService,
                             TransactionCategoryService transactionCategoryService,
                             RequestDtoMapper<AccountRequestDto, Account> accountRequestDtoMapper,
                             ResponseDtoMapper<AccountResponseDto, Account> accountResponseDtoMapper,
                             RequestDtoMapper<TransactionRequestDto, Transaction> requestDtoMapper,
                             ResponseDtoMapper<TransactionResponseDto, Transaction> responseDtoMapper) {
        this.userService = userService;
        this.accountService = accountService;
        this.userNameProvider = userNameProvider;
        this.transactionService = transactionService;
        this.transactionCategoryService = transactionCategoryService;
        this.accountRequestDtoMapper = accountRequestDtoMapper;
        this.accountResponseDtoMapper = accountResponseDtoMapper;
        this.requestDtoMapper = requestDtoMapper;
        this.responseDtoMapper = responseDtoMapper;
    }

    @GetMapping("/")
    public ResponseEntity<UserInfoDto> getUserInfo() {
        String username = userNameProvider.getUserName();
        Long userId = userService.findByEmail(username).get().getId();
        BigDecimal currentBalance = accountService.getTotalBalance(userId);
        List<Account> userAccounts = accountService.getAllByUser(userId);
        List<AccountDto> accountDtos = userAccounts.stream()
                .map(account -> new AccountDto(account.getId(), account.getName(), account.getBalance()))
                .collect(Collectors.toList());
        UserInfoDto userInfoDto = new UserInfoDto(username, currentBalance, accountDtos);
        return new ResponseEntity<>(userInfoDto, HttpStatus.OK);
    }

    @GetMapping("/categories")
    public List<TransactionCategoryDto> getCategories() {
        List<TransactionCategory> categories = transactionCategoryService.getAll();
        List<TransactionCategoryDto> categoriesDtos = categories.stream()
                .map(category -> new TransactionCategoryDto(category.getId(),
                        category.getCategory().name())).toList();
        return categoriesDtos;
    }

    @GetMapping("/transactions")
        public List<TransactionsListDto> getAllTransactions() {
        List<Transaction> transactions = transactionService.getAll();
        List<TransactionsListDto> transactionsDto = transactions.stream()
                .map(transaction -> new TransactionsListDto(transaction.getId(), transaction.getDate(),
                        transaction.getAccount().getName(), transaction.getTransactionCategory().getCategory().name(),
                        transaction.getNote(), transaction.getAmount())).collect(Collectors.toList());
        Collections.reverse(transactionsDto);
        return transactionsDto;
    }

    @GetMapping("/accounts")
    public List<AccountResponseDto> getAllAccounts() {
        List<Account> allAccounts = accountService.getAll();
        List<AccountResponseDto> accountsDto = allAccounts.stream().map(account ->
                new AccountResponseDto(account.getId(), account.getName(),
                account.getBalance())).toList();
        return accountsDto;
    }

    @PostMapping("/add-transaction")
    public ResponseEntity<String> add(@RequestBody TransactionRequestDto requestDto) {
            Transaction transaction = requestDtoMapper.mapToModel(requestDto);
            transactionService.add(transaction);
            return ResponseEntity.ok("{\"message\": \"Transaction is successfully sent\"}");
    }

    @PostMapping("/edit-transaction")
    public ResponseEntity<String> edit(@RequestBody TransactionRequestDto requestDto) {
        Transaction transactionModel = requestDtoMapper.mapToModel(requestDto);
        Transaction transaction = transactionService.get(requestDto.getId()).get();
        transaction.setAccount(transactionModel.getAccount());
        transaction.setTransactionCategory(transactionModel.getTransactionCategory());
        transaction.setAmount(transactionModel.getAmount());
        transaction.setCurrency(transactionModel.getCurrency());
        transaction.setNote(transactionModel.getNote());
        transaction.setDate(transactionModel.getDate());
        transactionService.update(transaction);
        return ResponseEntity.ok("{\"message\": \"Transaction is successfully edited\"}");
    }

    @PostMapping("/delete-transaction/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        transactionService.delete(id);
        return ResponseEntity.ok("{\"message\": \"Transaction is successfully deleted\"}");
    }

    @PostMapping("/create-account")
    public ResponseEntity<String> createAccount(@RequestBody AccountRequestDto accountRequestDto) {
        String username = userNameProvider.getUserName();
        User user = userService.findByEmail(username).get();
        Account account = new Account(accountRequestDto.getName(),
        accountRequestDto.getBalance(), user);
        accountService.add(account);
        return ResponseEntity.ok("{\"message\": \"Account is successfully created\"}");
    }
}