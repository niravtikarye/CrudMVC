package com.web.CivicSolve.Model;

import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;

public class ProblemFeedDTO {
    private Long probId;
    private String title;
    private String userDesc;
    private String solverDesc;
    private String status;
    private Integer hypeCount;
    private boolean isHypedByCurrentUser;
    private Timestamp createdAt;

    // Joined Fields for Display
    private Long userId;
    private String authorName;
    private Long areaId;
    private String areaName;
    private Long categoryId;
    private String categoryName;
    private Long subcategoryId;
    private String subcategoryName;
    private Long solverId; // null = unassigned, non-null = already has a solver
    private String addressDescription;

    // Associated Images
    private List<String> citizenImageUrls = new ArrayList<>();
    private List<String> solverImageUrls = new ArrayList<>();

    public ProblemFeedDTO() {}

    public Long getProbId() { return probId; }
    public void setProbId(Long probId) { this.probId = probId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getUserDesc() { return userDesc; }
    public void setUserDesc(String userDesc) { this.userDesc = userDesc; }

    public String getSolverDesc() { return solverDesc; }
    public void setSolverDesc(String solverDesc) { this.solverDesc = solverDesc; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Integer getHypeCount() { return hypeCount; }
    public void setHypeCount(Integer hypeCount) { this.hypeCount = hypeCount; }

    public boolean isHypedByCurrentUser() { return isHypedByCurrentUser; }
    public void setHypedByCurrentUser(boolean isHypedByCurrentUser) { this.isHypedByCurrentUser = isHypedByCurrentUser; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public String getAuthorName() { return authorName; }
    public void setAuthorName(String authorName) { this.authorName = authorName; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getAreaId() { return areaId; }
    public void setAreaId(Long areaId) { this.areaId = areaId; }

    public String getAreaName() { return areaName; }
    public void setAreaName(String areaName) { this.areaName = areaName; }

    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }

    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

    public Long getSubcategoryId() { return subcategoryId; }
    public void setSubcategoryId(Long subcategoryId) { this.subcategoryId = subcategoryId; }

    public String getSubcategoryName() { return subcategoryName; }
    public void setSubcategoryName(String subcategoryName) { this.subcategoryName = subcategoryName; }

    public String getAddressDescription() { return addressDescription; }
    public void setAddressDescription(String addressDescription) { this.addressDescription = addressDescription; }

    public List<String> getCitizenImageUrls() { return citizenImageUrls; }
    public void setCitizenImageUrls(List<String> citizenImageUrls) { this.citizenImageUrls = citizenImageUrls; }
    public void addCitizenImageUrl(String url) { this.citizenImageUrls.add(url); }

    public List<String> getSolverImageUrls() { return solverImageUrls; }
    public void setSolverImageUrls(List<String> solverImageUrls) { this.solverImageUrls = solverImageUrls; }
    public void addSolverImageUrl(String url) { this.solverImageUrls.add(url); }

    public Long getSolverId() { return solverId; }
    public void setSolverId(Long solverId) { this.solverId = solverId; }
}
