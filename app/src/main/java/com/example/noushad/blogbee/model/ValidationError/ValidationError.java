
package com.example.noushad.blogbee.model.ValidationError;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ValidationError {

    @SerializedName("error")
    @Expose
    private Error error;
    @SerializedName("code")
    @Expose
    private Integer code;

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return this.getError().toString();
    }
}
