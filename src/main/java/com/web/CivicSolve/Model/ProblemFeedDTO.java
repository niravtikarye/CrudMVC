package com.web.CivicSolve.Model;

import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;

public class ProblemFeedDTO {
    private Long probId;
    private String title;
    private String description;
    private String status;
    private Integer hypeCount;
    private Timestamp createdAt;

    // Joined Fields for Display
    private String authorName;
    private String areaName;
    private String categoryName;
    private String subcategoryName;

    // Associated Images (just URLs for the slider)
    private List<String> imageUrls = new ArrayList<>();

    public ProblemFeedDTO() {}

    public Long getProbId() { return probId; }
    public void setProbId(Long probId) { this.probId = probId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Integer getHypeCount() { return hypeCount; }
    public void setHypeCount(Integer hypeCount) { this.hypeCount = hypeCount; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public String getAuthorName() { return authorName; }
    public void setAuthorName(String authorName) { this.authorName = authorName; }

    public String getAreaName() { return areaName; }
    public void setAreaName(String areaName) { this.areaName = areaName; }

    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

    public String getSubcategoryName() { return subcategoryName; }
    public void setSubcategoryName(String subcategoryName) { this.subcategoryName = subcategoryName; }

    public List<String> getImageUrls() { return imageUrls; }
    public void setImageUrls(List<String> imageUrls) { this.imageUrls = imageUrls; }
    public void addImageUrl(String url) { this.imageUrls.add(url); }
}
