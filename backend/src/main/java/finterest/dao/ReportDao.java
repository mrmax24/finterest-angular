package finterest.dao;

import java.util.List;

public interface ReportDao {

    List<Object[]> getExpensesByCategoryAndMonthAndCategory(
            Long userId, List<Long> accountIds, Long categoryId, String selectedDate);
}
