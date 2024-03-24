package com.example.semar5.ModelResponse;

import com.google.gson.annotations.SerializedName;

public class Role {

    @SerializedName("id_role")
    private int idRole;

    @SerializedName("role")
    private String role;

    public Role(int idRole, String role) {
        this.idRole = idRole;
        this.role = role;
    }

    public int getIdRole() {
        return idRole;
    }

    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
