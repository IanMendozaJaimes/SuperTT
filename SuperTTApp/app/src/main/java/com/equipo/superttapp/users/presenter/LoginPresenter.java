package com.equipo.superttapp.users.presenter;

import com.equipo.superttapp.users.model.LoginFormModel;

public interface LoginPresenter {
    void logIn(LoginFormModel form);
    void logInSuccess();
    void logInError(LoginFormModel error);
}
