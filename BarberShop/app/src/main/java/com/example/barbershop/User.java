package com.example.barbershop;

public class User {
    private String username;
    private String role;
    private int user_id;

    public User(int user_id, String username, String role) {
        this.username = username;
        this.role = role;
        this.user_id = user_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }
}
