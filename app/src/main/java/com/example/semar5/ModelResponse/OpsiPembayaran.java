package com.example.semar5.ModelResponse;

import com.google.gson.annotations.SerializedName;

public class OpsiPembayaran {

    @SerializedName("id_opsipembayaran")
    private int idOpsiPembayaran;

    @SerializedName("opsi_pembayaran")
    private String opsiPembayaran;


    public int getIdOpsiPembayaran() {
        return idOpsiPembayaran;
    }

    public void setIdOpsiPembayaran(int idOpsiPembayaran) {
        this.idOpsiPembayaran = idOpsiPembayaran;
    }

    public String getOpsiPembayaran() {
        return opsiPembayaran;
    }

    public void setOpsiPembayaran(String opsiPembayaran) {
        this.opsiPembayaran = opsiPembayaran;
    }

    @Override
    public String toString() {
        return opsiPembayaran;
    }

}
