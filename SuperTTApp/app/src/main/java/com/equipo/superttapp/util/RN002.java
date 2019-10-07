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
            //return !email.trim().isEmpty();
            return false;
        }
    }

    public static boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }
}
