package com.equipo.superttapp.users.presenter;

import com.equipo.superttapp.users.model.UsuarioModel;
import com.equipo.superttapp.util.BusinessResult;

public interface SignInPresenter {
    void signIn(UsuarioModel model);
    void showMessage(BusinessResult<UsuarioModel> result);
}
