package com.equipo.superttapp.users.view;

import com.equipo.superttapp.users.model.LoginFormModel;

public interface ForgotPasswordView {
    void showMessage(LoginFormModel model);
    void hideProgressBar();

    void showProgressBar();
}
