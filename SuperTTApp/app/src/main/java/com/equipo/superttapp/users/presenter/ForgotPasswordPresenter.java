package com.equipo.superttapp.users.presenter;

import com.equipo.superttapp.users.model.LoginFormModel;

public interface ForgotPasswordPresenter {
    void sendEmail(LoginFormModel model);
    void showMessage(LoginFormModel errorModel);

}
