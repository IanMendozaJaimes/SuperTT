package com.equipo.superttapp.util;

import android.util.Patterns;

public class RN002 {

    public static boolean isEmailValid(String email) {
        if (email == null) {
            return false;
        }
        if (email.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches();
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
        return name != null && name.length() > 0;
    }

    public static Boolean isLastnameValid(String lastname) {
        return lastname != null && lastname.length() > 0;
    }

    public static Boolean isProyectoNombreValid(String name) {
        return isNameValid(name);
    }
}
