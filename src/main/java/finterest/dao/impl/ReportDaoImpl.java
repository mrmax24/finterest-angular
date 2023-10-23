package finterest.dao.impl;

import finterest.dao.ReportDao;
import finterest.model.Transaction;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Repository
public class ReportDaoImpl implements ReportDao {
    private final SessionFactory sessionFactory;

    public ReportDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Object[]> getExpensesByCategoryAndMonthAndCategory(
            Long userId, List<Long> accountIds, Long categoryId, String monthYear) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Object[]> criteriaQuery = builder.createQuery(Object[].class);
            Root<Transaction> root = criteriaQuery.from(Transaction.class);

            Expression<BigDecimal> sumAmount = builder.sum(root.get("amount"));
            Expression<Number> percentage;

            boolean filtersApplied = (accountIds != null && !accountIds.isEmpty())
                    || (categoryId != null) || (monthYear != null);

            if (filtersApplied) {
                if (monthYear != null && !monthYear.isEmpty()) {
                    String[] dateParts = monthYear.split("-");
                    if (dateParts.length == 2) {
                        int month = Integer.parseInt(dateParts[0]);
                        int year = Integer.parseInt(dateParts[1]);

                        Subquery<BigDecimal> totalAmountMonthSubquery =
                                criteriaQuery.subquery(BigDecimal.class);
                        Root<Transaction> totalAmountMonthRoot
                                = totalAmountMonthSubquery.from(Transaction.class);
                        totalAmountMonthSubquery.select(builder.sum(totalAmountMonthRoot
                                .get("amount")));
                        totalAmountMonthSubquery.where(
                                builder.and(
                                        builder.equal(totalAmountMonthRoot.get("account")
                                                .get("user").get("id"), userId),
                                        builder.equal(builder.function("MONTH", Integer.class,
                                                builder.function("STR_TO_DATE", Date.class,
                                                        totalAmountMonthRoot.get("date"),
                                                        builder.literal("%d-%m-%Y"))), month),
                                        builder.equal(builder.function("YEAR", Integer.class,
                                                builder.function("STR_TO_DATE", Date.class,
                                                        totalAmountMonthRoot.get("date"),
                                                        builder.literal("%d-%m-%Y"))), year)
                                )
                        );
                        Expression<BigDecimal> totalAmountMonth =
                                totalAmountMonthSubquery.getSelection();
                        percentage = builder.quot(sumAmount, totalAmountMonth);
                    } else {
                        percentage = builder.literal(0);
                    }
                } else {
                    Subquery<BigDecimal> totalAmountSubquery =
                            criteriaQuery.subquery(BigDecimal.class);
                    Root<Transaction> totalAmountRoot = totalAmountSubquery.from(Transaction.class);
                    totalAmountSubquery.select(builder.sum(totalAmountRoot.get("amount")));
                    totalAmountSubquery.where(builder.equal(totalAmountRoot.get("account")
                            .get("user").get("id"), userId));

                    Expression<BigDecimal> totalAmount = totalAmountSubquery.getSelection();
                    percentage = builder.quot(sumAmount, totalAmount);
                }
            } else {
                Subquery<BigDecimal> totalAmountSubquery = criteriaQuery.subquery(BigDecimal.class);
                Root<Transaction> totalAmountRoot = totalAmountSubquery.from(Transaction.class);
                totalAmountSubquery.select(builder.sum(totalAmountRoot.get("amount")));
                totalAmountSubquery.where(builder.equal(totalAmountRoot.get("account").get("user")
                        .get("id"), userId));

                Expression<BigDecimal> totalAmount = totalAmountSubquery.getSelection();

                percentage = builder.quot(sumAmount, totalAmount);
            }

            Expression<Long> categoryIdExpression = root.get("transactionCategory").get("id");

            criteriaQuery.multiselect(
                    root.get("transactionCategory").get("category"),
                    sumAmount,
                    builder.function("ROUND", BigDecimal.class, builder.prod(percentage, 100),
                            builder.literal(0))
            );
            Predicate userIdPredicate = builder.equal(root.get("account").get("user")
                    .get("id"), userId);

            if (accountIds != null && !accountIds.isEmpty()) {
                if (!accountIds.contains(-1L)) {
                    Predicate accountIdPredicate = root.get("account").get("id").in(accountIds);
                    userIdPredicate = builder.and(userIdPredicate, accountIdPredicate);
                }
            }
            Predicate categoryAndAccountPredicate = userIdPredicate;

            if (categoryId != null) {
                Predicate categoryIdPredicate = builder.equal(categoryIdExpression, categoryId);
                categoryAndAccountPredicate = builder.and(categoryAndAccountPredicate,
                        categoryIdPredicate);
            }
            if (monthYear != null && !monthYear.isEmpty()) {
                String[] dateParts = monthYear.split("-");
                if (dateParts.length == 2) {
                    int month = Integer.parseInt(dateParts[0]);
                    int year = Integer.parseInt(dateParts[1]);

                    Predicate monthYearPredicate = builder.and(
                            builder.equal(builder.function("MONTH", Integer.class,
                                    builder.function("STR_TO_DATE", Date.class, root.get("date"),
                                            builder.literal("%d-%m-%Y"))), month),
                            builder.equal(builder.function("YEAR", Integer.class,
                                    builder.function("STR_TO_DATE", Date.class, root.get("date"),
                                            builder.literal("%d-%m-%Y"))), year)
                    );
                    categoryAndAccountPredicate = builder.and(categoryAndAccountPredicate,
                            monthYearPredicate);
                }
            }
            criteriaQuery.where(categoryAndAccountPredicate)
                    .groupBy(
                            root.get("transactionCategory").get("category"),
                            categoryIdExpression
                    )
                    .orderBy(builder.desc(sumAmount));

            Query<Object[]> query = session.createQuery(criteriaQuery);
            return query.getResultList();
        }
    }
}
