package finterest.controller;

import finterest.dto.request.TransactionRequestDto;
import finterest.dto.response.*;
import finterest.model.Account;
import finterest.model.Transaction;
import finterest.model.TransactionCategory;
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

    private final RequestDtoMapper<TransactionRequestDto, Transaction> requestDtoMapper;
    private final ResponseDtoMapper<TransactionResponseDto, Transaction> responseDtoMapper;

    public AccountController(UserService userService,
                             AccountService accountService,
                             UserNameProvider userNameProvider,
                             TransactionService transactionService,
                             TransactionCategoryService transactionCategoryService,
                             RequestDtoMapper<TransactionRequestDto, Transaction> requestDtoMapper,
                             ResponseDtoMapper<TransactionResponseDto, Transaction> responseDtoMapper) {
        this.userService = userService;
        this.accountService = accountService;
        this.userNameProvider = userNameProvider;
        this.transactionService = transactionService;
        this.transactionCategoryService = transactionCategoryService;
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
                .map(category -> new TransactionCategoryDto(category.getId(), category.getCategory().name()))
                .collect(Collectors.toList());
        return categoriesDtos;
    }

    @GetMapping("/transactions")
        public List<TransactionsListDto> getAllTransactions() {
        List<Transaction> transactions = transactionService.getAll();
        List<TransactionsListDto> transactionsDto = transactions.stream()
                .map(transaction -> new TransactionsListDto(transaction.getDate(), transaction.getAccount().getName(),
                        transaction.getTransactionCategory().getCategory().name(), transaction.getNote(),
                        transaction.getAmount())).collect(Collectors.toList());
        return transactionsDto;
    }

    @PostMapping("/add-transaction")
    public ResponseEntity<String> add(@RequestBody TransactionRequestDto requestDto) {
            Transaction transaction = requestDtoMapper.mapToModel(requestDto);
            transactionService.add(transaction);
            return ResponseEntity.ok("{\"message\": \"Transaction is successfully sent\"}");
    }
}