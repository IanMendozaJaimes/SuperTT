package com.equipo.superttapp.users.presenter;

import com.equipo.superttapp.users.model.LoginFormModel;
import com.equipo.superttapp.util.BusinessResult;

public interface LoginPresenter {
    void logIn(LoginFormModel form);
    void logInSuccess(BusinessResult<LoginFormModel> resultado);
    void logInError(BusinessResult<LoginFormModel> resultado);
}
