package com.example.project.Models;

public class Userss {


    public Userss(String userId, String userName, String email) {
        UserId = userId;
        UserName = userName;
        Email = email;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    String UserId,UserName,Email;


}
