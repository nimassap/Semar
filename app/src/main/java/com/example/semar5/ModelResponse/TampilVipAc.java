package com.example.semar5.ModelResponse;

import com.google.gson.annotations.SerializedName;

public class TampilVipAc {

    @SerializedName("id_vipac")
    private int id;

    @SerializedName("text_edit")
    private String textEdit;

    public TampilVipAc(int id, String textEdit) {
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
