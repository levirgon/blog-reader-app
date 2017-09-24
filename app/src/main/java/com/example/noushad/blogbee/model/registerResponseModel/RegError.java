package com.example.noushad.blogbee.model.registerResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class RegError {

    @SerializedName("name")
    @Expose
    private List<String> name = null;
    @SerializedName("email")
    @Expose
    private List<String> email = null;
    @SerializedName("password")
    @Expose
    private List<String> password = null;
    @SerializedName("phone_no")
    @Expose
    private List<String> phoneNo = null;

    /**
     * No args constructor for use in serialization
     */
    public RegError() {
    }

    /**
     * @param phoneNo
     * @param email
     * @param name
     * @param password
     */
    public RegError(List<String> name, List<String> email, List<String> password, List<String> phoneNo) {
        super();
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNo = phoneNo;
    }

    public List<String> getName() {
        return name;
    }

    public void setName(List<String> name) {
        this.name = name;
    }

    public List<String> getEmail() {
        return email;
    }

    public void setEmail(List<String> email) {
        this.email = email;
    }

    public List<String> getPassword() {
        return password;
    }

    public void setPassword(List<String> password) {
        this.password = password;
    }

    public List<String> getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(List<String> phoneNo) {
        this.phoneNo = phoneNo;
    }

    @Override
    public String toString() {
        String msg = "";

        if (!(phoneNo == null))
            msg += phoneNo.get(0) + "\n";
        if (!(email == null))
            msg += email.get(0) + "\n";
        if (!(name == null))
            msg += name.get(0) + "\n";
        if (!(password == null))
            msg += password.get(0);

        return msg;
    }

}

