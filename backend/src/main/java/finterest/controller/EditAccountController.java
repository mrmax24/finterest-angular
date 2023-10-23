package finterest.controller;

import finterest.model.Account;
import finterest.model.Transaction;
import finterest.model.User;
import finterest.service.AccountService;
import finterest.service.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/accounts")
public class EditAccountController {
    private final AccountService accountService;
    private final TransactionService transactionService;

    public EditAccountController(AccountService accountService,
                                 TransactionService transactionService) {
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

}
