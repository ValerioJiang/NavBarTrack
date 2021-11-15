package com.example.navbartrack.model;


public class UserLogin {
    private String email;
    private String password;
    private String token;
    private String lastLogin;

    public UserLogin(String email, String password) {
        this.token = null; //token is given after successfull Login
        this.email = email;
        this.password = password;
        this.lastLogin = null;
    }

    public UserLogin(String email, String password, String lastLogin) {
        this.token = null; //token is given after successfull Login
        this.email = email;
        this.password = password;
        this.lastLogin = lastLogin;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserLogin{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", token='" + token + '\'' +
                ", lastLogin=" + lastLogin +
                '}';
    }
}
