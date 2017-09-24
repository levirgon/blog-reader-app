package com.example.noushad.blogbee.model.registerResponseModel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class Error {

    @SerializedName("phone_no")
    private List<String> phoneNo;

    @SerializedName("email")
    private List<String> email;

    public List<String> getPhoneNo() {
        return phoneNo;
    }

    public List<String> getEmail() {
        return email;
    }

    @Override
    public String toString() {
        String msg = "";

        if (!(phoneNo==null))
            msg +=phoneNo.get(0)+"\n";
        if (!(email==null))
            msg +=  email.get(0);

        return msg;
    }
}