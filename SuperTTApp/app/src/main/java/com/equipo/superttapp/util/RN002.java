package com.equipo.superttapp.util;

import java.util.regex.Pattern;

public class RN002 {

    public static boolean isEmailValid(String email) {
        Pattern pattern = Pattern.compile(
                "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                        "\\@" +
                        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                        "(" +
                        "\\." +
                        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                        ")+"
        );
        if (email == null) {
            return false;
        }
        if (email.contains("@")) {
            return pattern.matcher(email).matches();
        } else {
            return false;
        }
    }

    public static boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }

    public static boolean isSecondPasswordValid(String password, String secondPassword) {
        return isPasswordValid(secondPassword) && secondPassword.equals(password);
    }

    public static Boolean isNameValid(String name) {
        return name != null && name.trim().length() > 0;
    }

    public static Boolean isLastnameValid(String lastname) {
        return lastname != null && lastname.trim().length() > 0;
    }

    public static Boolean isProyectoNombreValid(String name) {
        return isNameValid(name);
    }
}
