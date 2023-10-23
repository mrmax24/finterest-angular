package finterest.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "transaction_category")
public class TransactionCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(value = EnumType.STRING)
    private CategoryName category;

    public TransactionCategory(CategoryName category) {
        this.category = category;
    }

    public TransactionCategory() {
    }

    public enum CategoryName {
        FOOD,
        SHOPPING,
        TRANSPORT,
        HOME,
        BILLS,
        ENTERTAINMENT,
        CAR,
        TRAVEL,
        FAMILY,
        HEALTHCARE,
        EDUCATION,
        GIFTS,
        SPORT,
        BEAUTY,
        WORK,
        OTHER
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CategoryName getCategory() {
        return category;
    }

    public void setCategory(CategoryName category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "TransactionCategory{"
                + "id=" + id
                + ", category=" + category
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TransactionCategory transactionCategory = (TransactionCategory) o;
        return Objects.equals(id, transactionCategory.id)
                && Objects.equals(category, transactionCategory.category);

    }

    @Override
    public int hashCode() {
        return Objects.hash(id, category);
    }
}
