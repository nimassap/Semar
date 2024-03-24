package com.example.semar5.ModelResponse;

public class LoginResponse {

    User user;
    Role role;
    String error, message;

    public LoginResponse(User user, Role role, String error, String message) {
        this.user = user;
        this.role = role;
        this.error = error;
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
