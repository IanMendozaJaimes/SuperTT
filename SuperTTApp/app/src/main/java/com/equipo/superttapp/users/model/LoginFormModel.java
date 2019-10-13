package com.equipo.superttapp.users.model;

public class LoginFormModel {
    private Integer id;
    private String email;
    private String password;
    private String keyAuth;
    private Boolean isValidEmail = false;
    private Boolean isValidPassword = false;


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

    public Boolean isValidEmail() {
        return isValidEmail;
    }

    public void setValidEmail(Boolean validEmail) {
        isValidEmail = validEmail;
    }

    public Boolean isValidPassword() {
        return isValidPassword;
    }

    public void setValidPassword(Boolean validPassword) {
        isValidPassword = validPassword;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKeyAuth() {
        return keyAuth;
    }

    public void setKeyAuth(String keyAuth) {
        this.keyAuth = keyAuth;
    }
}
