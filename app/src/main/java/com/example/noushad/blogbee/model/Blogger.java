package com.example.noushad.blogbee.model;

/**
 * Created by noushad on 7/19/17.
 */

public class Blogger {

    private String name;
    private String email;
    private String phone;
    private String password;

    public Blogger(String name, String email, String phone, String password) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }
}
