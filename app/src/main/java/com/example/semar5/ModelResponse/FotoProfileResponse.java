package com.example.semar5.ModelResponse;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FotoProfileResponse {

    @SerializedName("error")
    private boolean error;

    @SerializedName("message")
    private String message;

    @SerializedName("imageData")
    private List<FotoProfileList> fotoProfileList;
}
