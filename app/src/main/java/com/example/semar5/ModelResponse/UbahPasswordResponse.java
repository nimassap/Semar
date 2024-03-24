package com.example.semar5.ModelResponse;

import com.google.gson.annotations.SerializedName;

public class UbahPasswordResponse {

    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    public UbahPasswordResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
