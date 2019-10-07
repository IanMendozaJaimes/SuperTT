package com.equipo.superttapp.users.view;

import com.equipo.superttapp.users.model.LoginFormModel;

public interface LoginView {
    void goCreateAccount();
    void goForgotPassword();
    void showProgressBar();
    void hideProgressBar();
    void loginError(LoginFormModel error);
    void goHome();
    void saveUser(LoginFormModel model);

}
