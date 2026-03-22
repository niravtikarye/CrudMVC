package com.web.CivicSolve.Model;

import java.util.List;

public class Problem {
    private Long probId;

    // Actors
    private Long userId; // Citizen
    private Long solverId; // Assigned Worker (VMC/NGO/Noble)

    // Classification
    private Long subcategoryId;
    
    // Location
    private Long areaId;
    private String addressDescription;

    private String title;
    private String userDesc;
    private String solverDesc;
    private String status;
    private String verificationRemark;

    // Stats
    private Integer hypeCount;
    
    // Associated Images
    private List<ProblemImage> images; 

    public Problem() {}

    public Problem(Long probId, Long userId, Long solverId, Long subcategoryId, Long areaId, String addressDescription, String title, String userDesc, String solverDesc, String status, String verificationRemark, Integer hypeCount, List<ProblemImage> images) {
        this.probId = probId;
        this.userId = userId;
        this.solverId = solverId;
        this.subcategoryId = subcategoryId;
        this.areaId = areaId;
        this.addressDescription = addressDescription;
        this.title = title;
        this.userDesc = userDesc;
        this.solverDesc = solverDesc;
        this.status = status;
        this.verificationRemark = verificationRemark;
        this.hypeCount = hypeCount;
        this.images = images;
    }

    public Long getProbId() {
        return probId;
    }

    public void setProbId(Long probId) {
        this.probId = probId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getSolverId() {
        return solverId;
    }

    public void setSolverId(Long solverId) {
        this.solverId = solverId;
    }

    public Long getSubcategoryId() {
        return subcategoryId;
    }

    public void setSubcategoryId(Long subcategoryId) {
        this.subcategoryId = subcategoryId;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public String getAddressDescription() {
        return addressDescription;
    }

    public void setAddressDescription(String addressDescription) {
        this.addressDescription = addressDescription;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserDesc() {
        return userDesc;
    }

    public void setUserDesc(String userDesc) {
        this.userDesc = userDesc;
    }

    public String getSolverDesc() {
        return solverDesc;
    }

    public void setSolverDesc(String solverDesc) {
        this.solverDesc = solverDesc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVerificationRemark() {
        return verificationRemark;
    }

    public void setVerificationRemark(String verificationRemark) {
        this.verificationRemark = verificationRemark;
    }

    public Integer getHypeCount() {
        return hypeCount;
    }

    public void setHypeCount(Integer hypeCount) {
        this.hypeCount = hypeCount;
    }

    public List<ProblemImage> getImages() {
        return images;
    }

    public void setImages(List<ProblemImage> images) {
        this.images = images;
    }
}
