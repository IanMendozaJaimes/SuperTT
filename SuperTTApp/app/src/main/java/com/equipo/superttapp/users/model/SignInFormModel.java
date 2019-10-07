package com.equipo.superttapp.users.model;

public class SignInFormModel extends LoginFormModel{
    private String secondPassword;
    private String name;
    private String lastname;
    private Boolean validLastName;
    private Boolean validName;
    private Boolean validSecondPassword;

    public String getSecondPassword() {
        return secondPassword;
    }

    public void setSecondPassword(String secondPassword) {
        this.secondPassword = secondPassword;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Boolean isValidLastName() {
        return validLastName;
    }

    public void setValidLastName(Boolean validLastName) {
        this.validLastName = validLastName;
    }

    public Boolean isValidName() {
        return validName;
    }

    public void setValidName(Boolean validName) {
        this.validName = validName;
    }

    public Boolean isValidSecondPassword() {
        return validSecondPassword;
    }

    public void setValidSecondPassword(Boolean validSecondPassword) {
        this.validSecondPassword = validSecondPassword;
    }
}
