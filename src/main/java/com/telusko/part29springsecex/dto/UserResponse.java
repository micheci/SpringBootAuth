package com.telusko.part29springsecex.dto;

public class UserResponse {
    private int id;
    private String username;

    public UserResponse(int id, String username) {
        this.id = id;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
}
