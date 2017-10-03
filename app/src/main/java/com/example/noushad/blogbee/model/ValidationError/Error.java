
package com.example.noushad.blogbee.model.ValidationError;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Error {

    @SerializedName("username")
    @Expose
    private List<String> username = null;
    @SerializedName("password")
    @Expose
    private List<String> password = null;

    public List<String> getUsername() {
        return username;
    }

    public void setUsername(List<String> username) {
        this.username = username;
    }

    public List<String> getPassword() {
        return password;
    }

    public void setPassword(List<String> password) {
        this.password = password;
    }

    @Override
    public String toString() {
        String msg = "";
        for (String aData:
             username) {
            msg += aData + "\n";
        }

        for (String aData:
                password) {
            msg += aData + "\n";
        }
        return  msg;
    }
}
