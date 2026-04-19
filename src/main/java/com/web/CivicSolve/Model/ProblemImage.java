package com.web.CivicSolve.Model;

public class ProblemImage {
    private Long imageId;
    private Long probId;
    private String imageUrl;
    private String imageType; // 'before' or 'after'

    public ProblemImage() {}

    public ProblemImage(Long imageId, Long probId, String imageUrl, String imageType) {
        this.imageId = imageId;
        this.probId = probId;
        this.imageUrl = imageUrl;
        this.imageType = imageType;
    }

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public Long getProbId() {
        return probId;
    }

    public void setProbId(Long probId) {
        this.probId = probId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }
}
