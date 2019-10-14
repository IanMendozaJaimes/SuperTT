package com.equipo.superttapp.users.presenter;

import com.equipo.superttapp.users.model.UsuarioModel;
import com.equipo.superttapp.util.BusinessResult;

public interface ForgotPasswordPresenter {
    void sendEmail(UsuarioModel model);
    void showMessage(BusinessResult<UsuarioModel> errorModel);

}
