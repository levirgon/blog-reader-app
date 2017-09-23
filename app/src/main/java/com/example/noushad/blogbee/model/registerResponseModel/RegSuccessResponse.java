package com.example.noushad.blogbee.model.registerResponseModel;

/**
 * Created by noushad on 9/22/17.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegSuccessResponse {

    @SerializedName("data")
    @Expose
    private Data data;
    boolean recieved = false;

    /**
     * No args constructor for use in serialization
     */
    public RegSuccessResponse() {
    }

    /**
     * @param data
     */
    public RegSuccessResponse(Data data) {
        super();
        if (data != null)
            recieved = true;

        this.data = data;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
    public boolean isRecieved(){
        return recieved;
    }

}