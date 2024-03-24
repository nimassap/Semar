package com.example.semar5.ModelResponse;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BuktiBayarResponse {

    @SerializedName("error")
    private boolean error;

    @SerializedName("message")
    private String message;

    @SerializedName("imageData")
    private List<BuktiBayarList> buktiBayarList;

    public BuktiBayarResponse(boolean error, String message, List<BuktiBayarList> buktiBayarList) {
        this.error = error;
        this.message = message;
        this.buktiBayarList = buktiBayarList;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<BuktiBayarList> getBuktiBayarList() {
        return buktiBayarList;
    }

    public void setBuktiBayarList(List<BuktiBayarList> buktiBayarList) {
        this.buktiBayarList = buktiBayarList;
    }
}
