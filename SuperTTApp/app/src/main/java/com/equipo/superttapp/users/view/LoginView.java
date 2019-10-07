package com.equipo.superttapp.users.view;

import com.equipo.superttapp.users.model.LoginForm;

public interface LoginView {
    void goCreateAccount();
    void goForgotPassword();
    void showProgressBar();
    void hideProgressBar();
    void loginError(LoginForm error);
    void goHome();
}
