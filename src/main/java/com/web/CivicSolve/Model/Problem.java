package com.web.CivicSolve.Model;

public class Problem {

    private String type;
    private String title;
    private String description;
    private String[] image;
    private Integer hipe;
    private String status;

    public Problem() {
    }

    public Problem(String type, String title, String description, String[] image, Integer hipe, String status) {
        this.type = type;
        this.title = title;
        this.description = description;
        this.image = image;
        this.hipe = hipe;
        this.status = status;
    }

    public String getType() {
        return this.type;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public String[] getImage() {
        return this.image;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(String[] image) {
        this.image = image;
    }

    public Integer getHipe() {
        return hipe;
    }

    public void setHipe(Integer hipe) {
        this.hipe = hipe;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
