package finterest.service;

import java.util.List;

public interface ReportService {

    List<Object[]> getExpensesByCategoryAndMonthAndCategory(
            Long userId, List<Long> accountIds, Long categoryId, String selectedDate);
}
