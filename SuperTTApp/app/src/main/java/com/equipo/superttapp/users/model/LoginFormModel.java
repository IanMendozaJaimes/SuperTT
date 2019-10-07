package com.equipo.superttapp.users.model;

public class LoginFormModel {
    private String email;
    private String password;
    private Integer resultCode;
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

    public Integer getResultCode() {
        return resultCode;
    }

    public void setResultCode(Integer resultCode) {
        this.resultCode = resultCode;
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
}
