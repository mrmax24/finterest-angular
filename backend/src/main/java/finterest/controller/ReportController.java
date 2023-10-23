package finterest.controller;

import finterest.model.Account;
import finterest.model.TransactionCategory;
import finterest.service.AccountService;
import finterest.service.ReportService;
import finterest.service.TransactionCategoryService;
import finterest.service.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/report")
public class ReportController {
    private final ReportService reportService;
    private final TransactionCategoryService transactionCategoryService;
    private final AccountService accountService;
    private final TransactionService transactionService;

    public ReportController(ReportService reportService,
                            TransactionCategoryService transactionCategoryService,
                            AccountService accountService,
                            TransactionService transactionService) {
        this.reportService = reportService;
        this.transactionCategoryService = transactionCategoryService;
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

    @ModelAttribute("accounts")
    public List<Account> getAccounts() {
        return null;
    }

    @ModelAttribute("categories")
    public List<TransactionCategory> getCategories() {
        return transactionCategoryService.getAll();
    }

    @GetMapping
    public String getExpensesByCategoryAndMonth(
            @RequestParam(value = "accountId", required = false) List<Long> accountIds,
            @RequestParam(value = "categoryId", required = false) Long categoryId,
            @RequestParam(name = "month", required = false) String selectedDate, Model model) {
        return "report";
    }
}
