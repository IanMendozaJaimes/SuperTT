package com.equipo.superttapp.users.presenter;

import com.equipo.superttapp.users.model.UsuarioModel;
import com.equipo.superttapp.util.BusinessResult;

public interface LoginPresenter {
    void logIn(UsuarioModel form);
    void logInSuccess(BusinessResult<UsuarioModel> resultado);
    void logInError(BusinessResult<UsuarioModel> resultado);
}
