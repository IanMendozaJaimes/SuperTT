package com.equipo.superttapp.users.interactor;

import com.equipo.superttapp.users.model.UsuarioModel;
import com.equipo.superttapp.util.BusinessResult;

public interface UserInteractor {
    BusinessResult<UsuarioModel> logIn(UsuarioModel loginFormModel);
    BusinessResult<UsuarioModel> sendEmail(UsuarioModel loginFormModel);
    BusinessResult<UsuarioModel> createAccount(UsuarioModel signInFormModel);
    BusinessResult<UsuarioModel>  updateAccount(UsuarioModel model);
}
