package com.example.vibtran.model;

public class User {
    private int user_id;
    private String user_email;

    public User(int user_id, String user_email) {
        this.user_id = user_id;
        this.user_email = user_email;
    }

    public int getUserId() {
        return user_id;
    }

    public void setUserId(int user_id) {
        this.user_id = user_id;
    }

    public String getUserEmail() {
        return user_email;
    }

    public void setUserEmail(String user_email) {
        this.user_email = user_email;
    }



}
