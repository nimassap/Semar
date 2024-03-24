package com.example.semar5.ModelResponse;

import com.google.gson.annotations.SerializedName;

public class TampilDeluxe3 {

    @SerializedName("id_deluxe3")
    private int id;

    @SerializedName("text_edit")
    private String textEdit;

    public TampilDeluxe3(int id, String textEdit) {
        this.id = id;
        this.textEdit = textEdit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTextEdit() {
        return textEdit;
    }

    public void setTextEdit(String textEdit) {
        this.textEdit = textEdit;
    }
}
