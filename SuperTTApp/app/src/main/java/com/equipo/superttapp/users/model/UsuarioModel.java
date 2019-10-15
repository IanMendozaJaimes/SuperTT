package com.equipo.superttapp.users.model;

public class UsuarioModel {
    private Integer id;
    private String email;
    private String password;
    private String keyAuth;
    private String name;
    private String secondPassword;
    private String lastname;
    private String currentPassword;
    private Boolean validLastName = false;
    private Boolean validName = false;
    private Boolean validSecondPassword = false;
    private Boolean validEmail = false;
    private Boolean validPassword = false;
    private Boolean validCurrentPassword = false;

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

    public Boolean getValidEmail() {
        return validEmail;
    }

    public void setValidEmail(Boolean validEmail) {
        this.validEmail = validEmail;
    }

    public Boolean getValidPassword() {
        return validPassword;
    }

    public void setValidPassword(Boolean validPassword) {
        this.validPassword = validPassword;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecondPassword() {
        return secondPassword;
    }

    public void setSecondPassword(String secondPassword) {
        this.secondPassword = secondPassword;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Boolean getValidLastName() {
        return validLastName;
    }

    public void setValidLastName(Boolean validLastName) {
        this.validLastName = validLastName;
    }

    public Boolean getValidName() {
        return validName;
    }

    public void setValidName(Boolean validName) {
        this.validName = validName;
    }

    public Boolean getValidSecondPassword() {
        return validSecondPassword;
    }

    public void setValidSecondPassword(Boolean validSecondPassword) {
        this.validSecondPassword = validSecondPassword;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public Boolean getValidCurrentPassword() {
        return validCurrentPassword;
    }

    public void setValidCurrentPassword(Boolean validCurrentPassword) {
        this.validCurrentPassword = validCurrentPassword;
    }
}
