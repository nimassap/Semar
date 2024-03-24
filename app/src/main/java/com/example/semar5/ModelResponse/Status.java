package com.example.semar5.ModelResponse;

public class Status {

    public enum StatusPesanan {
        BELUM_BAYAR,
        SELESAI,
        DIBATALKAN
    }

    private StatusPesanan statusPemesanan;

    public Status(StatusPesanan statusPemesanan) {
        this.statusPemesanan = statusPemesanan;
    }

    public StatusPesanan getStatusPemesanan() {
        return statusPemesanan;
    }

    public void setStatusPemesanan(StatusPesanan statusPemesanan) {
        this.statusPemesanan = statusPemesanan;
    }
}
