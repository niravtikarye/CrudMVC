package com.web.CivicSolve.Model;

public class SubCategory {
    private Long subcategoryId;
    private Long categoryId;
    private String subcategoryName;

    public SubCategory() {}

    public SubCategory(Long subcategoryId, Long categoryId, String subcategoryName) {
        this.subcategoryId = subcategoryId;
        this.categoryId = categoryId;
        this.subcategoryName = subcategoryName;
    }

    public Long getSubcategoryId() {
        return subcategoryId;
    }

    public void setSubcategoryId(Long subcategoryId) {
        this.subcategoryId = subcategoryId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getSubcategoryName() {
        return subcategoryName;
    }

    public void setSubcategoryName(String subcategoryName) {
        this.subcategoryName = subcategoryName;
    }
}
