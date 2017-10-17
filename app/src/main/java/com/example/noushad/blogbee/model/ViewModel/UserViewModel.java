package com.example.noushad.blogbee.model.ViewModel;

/**
 * Created by tapos on 10/4/17.
 */

public class UserViewModel {
    private final Integer id;
    private final String name;
    private final String phoneNo;
    private final String email;
    private final String coverPhoto;


    public UserViewModel(Integer id, String name, String email, String phone, String coverPhoto) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.coverPhoto = coverPhoto;
        this.phoneNo = phone;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getCoverPhoto() {
        return coverPhoto;
    }

    public String getPhoneNo() {
        return phoneNo;
    }
}
