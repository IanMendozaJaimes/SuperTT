package com.equipo.superttapp.users.view;

import com.equipo.superttapp.users.model.UsuarioModel;
import com.equipo.superttapp.util.BusinessResult;

public interface ForgotPasswordView {
    void showMessage(BusinessResult<UsuarioModel> model);
    void hideProgressBar();

    void showProgressBar();
}
