package com.web.CivicSolve.Model;

public class User {

    private String userName;
    private String name;
    private String image;

    public User() {
    }

    public User(String name, String userName, String image) {
        this.image = image;
        this.name = name;
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    
}
