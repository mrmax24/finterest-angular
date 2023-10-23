package finterest.dto.response;

public class TransactionCategoryDto {
    private Long id;
    private String categoryName;

    public TransactionCategoryDto() {
    }

    public TransactionCategoryDto(Long id, String categoryName) {
        this.id = id;
        this.categoryName = categoryName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
