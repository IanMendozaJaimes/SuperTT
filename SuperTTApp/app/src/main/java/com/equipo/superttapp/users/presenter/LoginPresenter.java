package com.equipo.superttapp.users.presenter;

import com.equipo.superttapp.users.model.LoginForm;

public interface LoginPresenter {
    void logIn(LoginForm form);
    void logInSuccess();
    void logInError(LoginForm error);
}
